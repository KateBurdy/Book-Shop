package com.example.files.models;

import com.example.commons.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity
@Builder(toBuilder = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "files")
public class FileEntity extends BaseEntity {

    @Column(name = "filename")
    private String filename;

    @Column(name = "content_type")
    private String contentType;
}