package com.example.orders.services;

import com.example.orders.client.ProductServiceClient;
import com.example.orders.exceptions.ProductNotFoundException;
import com.example.orders.exceptions.CartNotFoundException;
import com.example.orders.mappers.CartItemMapper;
import com.example.orders.mappers.CartMapper;
import com.example.orders.models.Cart;
import com.example.orders.models.CartItem;
import com.example.orders.models.dtos.CartDTO;
import com.example.orders.models.dtos.CartItemDTO;
import com.example.orders.models.dtos.CartRequestDTO;
import com.example.orders.repositories.CartItemRepository;
import com.example.orders.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartService {

    @Value("${spring.discount.percent}")
    private BigDecimal DISCOUNT_PERCENT;

    @Value("${spring.quantity.for.discount}")
    private int DISCOUNT_QUANTITY;

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;
    private final CartMapper cartMapper;
    private final CartItemMapper cartItemMapper;
    private final ProductServiceClient productServiceClient;


    public CartDTO getCartByUserId(UUID userId) {
        Cart cart = ensureCartExists(userId);
        CartDTO cartDTO = cartMapper.cartToCartDTO(cart);
        List<CartItemDTO> cartItemDTOs = cartItemMapper.cartItemsToCartItemDTOs(cart.getCartItems());
        cartDTO.setCartItems(cartItemDTOs);
        return cartDTO;
    }

    public CartDTO addCartItem(UUID userId, UUID productId, CartRequestDTO cartRequest) {

        validateProduct(productId);

        Cart cart = ensureCartExists(userId);
        CartItem cartItem;

        if (ensureCartItemExists(cart, productId)) {
            cartItem = cartItemRepository.findCartItemByCartIdAndProductIdAndDeletedAtIsNull(cart.getId(), productId);

            int currentQuantity = cartItem.getQuantity();
            int updatedQuantity = currentQuantity + cartRequest.getQuantity();
            cartItem.setQuantity(updatedQuantity);

        } else {
            cartItem = CartItem.builder()
                    .productId(productId)
                    .quantity(cartRequest.getQuantity())
                    .pricePerUnit(cartRequest.getPrice())
                    .build();
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);
        }

        applyDiscountAndCalculateTotalPrice(cartItem);

        cartItemRepository.save(cartItem);

        return cartMapper.cartToCartDTO(cart);
    }

    public CartDTO updateProductQuantityInCartItem(UUID userId, UUID productId, CartRequestDTO cartRequest) {
        validateProduct(productId);

        Cart cart = ensureCartExists(userId);
        CartItem cartItem = findCartItemByProductId(cart, productId);
        cartItem.setQuantity(cartRequest.getQuantity());
        cartItem.setPricePerUnit(cartRequest.getPrice());
        applyDiscountAndCalculateTotalPrice(cartItem);
        cartRepository.save(cart);
        return cartMapper.cartToCartDTO(cart);
    }

    public CartDTO removeProductFromCart(UUID userId, UUID productId) {

        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            throw new CartNotFoundException(userId);
        }

        if (ensureCartItemExists(cart, productId)) {
            CartItem cartItem = cartItemRepository.findCartItemByCartIdAndProductIdAndDeletedAtIsNull(cart.getId(), productId);

            if (cartItem.getDeletedAt() != null) {
                throw new ProductNotFoundException(productId);
            }

            cartItem.setDeletedAt(Instant.now());
            cart.getCartItems().remove(cartItem);
            cartRepository.save(cart);
        }

        return cartMapper.cartToCartDTO(cart);
    }

    private void applyDiscountAndCalculateTotalPrice(CartItem cartItem) {
        BigDecimal totalPrice = cartItem.getPricePerUnit().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
        BigDecimal discountFactor = DISCOUNT_PERCENT.divide(BigDecimal.valueOf(100));

        if (cartItem.getQuantity() >= DISCOUNT_QUANTITY) {
            totalPrice = totalPrice.subtract(totalPrice.multiply(discountFactor));
            cartItem.setDiscount(DISCOUNT_PERCENT);
        }

        cartItem.setTotalPrice(totalPrice);
    }

    private Cart ensureCartExists(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId);
        if (cart == null) {
            cart = new Cart();
            cart.setUserId(userId);
            cartRepository.save(cart);
        }
        return cart;
    }

    private boolean ensureCartItemExists(Cart cart, UUID productId) {
        return cartItemRepository.existsByCartIdAndProductIdAndDeletedAtIsNull(cart.getId(), productId);
    }

    private CartItem findCartItemByProductId(Cart cart, UUID productId) {
        return cart.getCartItems().stream()
                .filter(item -> item.getProductId().equals(productId) && item.getDeletedAt() == null)
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCart(cart);
                    newCartItem.setProductId(productId);
                    return newCartItem;
                });
    }

    private void validateProduct(UUID productId){
        if (!doesProductExist(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }

    private boolean doesProductExist(UUID productId) {
        ResponseEntity<Void> response = productServiceClient.checkProductExistence(productId);
        return response.getStatusCode() == HttpStatus.OK;
    }

}

