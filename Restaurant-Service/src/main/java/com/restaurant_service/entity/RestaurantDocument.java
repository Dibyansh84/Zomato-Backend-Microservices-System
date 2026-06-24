package com.restaurant_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="restaurant_documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "restaurant_documents_id_seq", sequenceName = "restaurant_documents_id_seq", allocationSize = 1)
public class RestaurantDocument
{
    //@SequenceGenerator - Defines a sequence generator at class level
    //@GeneratedValue - Now references the sequence generator

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "restaurant_documents_id_seq")
    private Long id;
    @NotNull(message = "restaurant id cannot be null")
    @Column(name = "restaurant_id", nullable = false)
    private Long restaurantId;
    @Column(name = "document_type")
    private String documentType;
    @Column(name = "document_url")
    private String documentUrl;
    @Column(name = "verified")
    private Boolean verified;
    @Column(name = "uploaded_on")
    private LocalDateTime uploadedOn;

    @PrePersist
    public void onCreate()
    {
        uploadedOn = LocalDateTime.now();

        if(verified == null)
        {
            verified = false;
        }
    }
}
