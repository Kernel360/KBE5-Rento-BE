package com.kbe5.rento.domain.member.entity;

import com.kbe5.rento.common.util.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name="members")
public class Member extends BaseEntity {

    /*@ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;*/
    private String name;
    private String phone;
    private String email;
    private String password;
    private String componyCode;
}
