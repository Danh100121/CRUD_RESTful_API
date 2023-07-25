package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto extends BaseObjectDto {
    private String code;
    private String name;
    private Integer age;
    private String email;
    private String phone;
    private UUID provinceId;
    private UUID districtId;
    private UUID communeId;
    public EmployeeDto(Employee entity){
        if (!ObjectUtils.isEmpty(entity)){
            this.code = entity.getCode();
            this.name = entity.getName();
            this.age = entity.getAge();
            this.email = entity.getEmail();
            this.phone = entity.getPhone();
            this.provinceId = entity.getProvince().getId();
            this.districtId = entity.getDistrict().getId();
            this.communeId = entity.getCommune().getId();
        }
    }

}
