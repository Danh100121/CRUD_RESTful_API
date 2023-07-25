package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProvinceDto extends BaseObjectDto {
    private String code;
    private String name;
    private List<DistrictDto> districtDtoList;
    public ProvinceDto(){
    }
    public ProvinceDto(Province entity){
        if (!ObjectUtils.isEmpty(entity)){
            this.setId(entity.getId());
            this.setCode(entity.getCode());
            this.setName(entity.getName());
        }
    }
}
