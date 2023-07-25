package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_certificate")
@Getter
@Setter
public class Certificate extends BaseObject {
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;


}
