package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;


@Getter
@Setter
public class DistrictDto extends BaseObjectDto {
    private String code;
    private String name;
    private UUID provinceId;
    private List<CommuneDto> communeDtoList;
    public DistrictDto(){
    }
    public DistrictDto(District entity) {
        if (!ObjectUtils.isEmpty(entity)) {
            this.setId(entity.getId());
            this.provinceId = entity.getProvince().getId();
            this.setCode(entity.getCode());
            this.setName(entity.getName());
        }
    }

}
