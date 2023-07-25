package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Commune;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.util.UUID;

@Getter
@Setter
public class CommuneDto extends BaseObjectDto {
    private String code;
    private String name;
    private UUID districtId;
    public CommuneDto(){
    }
    public CommuneDto(Commune entity){
        if (!ObjectUtils.isEmpty(entity)){
            this.setId(entity.getId());
            this.setCode(entity.getCode());
            this.setName(entity.getName());
            this.districtId= entity.getDistrict().getId();
        }
    }
}
