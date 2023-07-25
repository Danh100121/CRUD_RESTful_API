package com.globits.da.domain;

import com.globits.core.domain.BaseObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.EnableMBeanExport;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_district")
@Getter
@Setter
public class District extends BaseObject {
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_id")
    private Province province;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "district")
    private List<Commune> communeList;
}
