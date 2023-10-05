package com.example.files.models;

import com.example.commons.models.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "file_data")
public class FileDataEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name ="file_id")
    private FileEntity fileId;

    @Column(name = "data")
    private byte[] data;
}
