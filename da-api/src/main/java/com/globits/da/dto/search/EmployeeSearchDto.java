package com.globits.da.dto.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class EmployeeSearchDto {
    private String keyword;
    private String code;
    private String name;
    private String email;
    private String phone;
    private Integer age;
    private UUID provinceId;
    private UUID districtId;
    private UUID communeId;
    private Integer pageIndex;
    private Integer pageSize;

}
