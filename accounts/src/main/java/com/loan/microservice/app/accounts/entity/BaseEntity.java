package com.loan.microservice.app.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data @AllArgsConstructor @NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

        @CreationTimestamp
        @Column(updatable = false, nullable = false)
        private LocalDateTime createdAt;
        @CreatedBy
        private String createdBy;
        @LastModifiedBy
        private String updatedBy;
        @Column(insertable = false)
        @UpdateTimestamp
        private LocalDateTime updatedAt;
}
