package com.diploma.backend.model.entities;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseAuditEntity extends BaseEntity {

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Date createdWhen;

    @LastModifiedBy
    private String modifiedBy;

    @LastModifiedDate
    private Date modifiedWhen;

    @EqualsAndHashCode.Exclude
    @Version
    @Type(type = "dbtimestamp")
    private Date modifiedId;

}
