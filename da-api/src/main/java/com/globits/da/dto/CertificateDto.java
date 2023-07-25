package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.Certificate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

@Getter
@Setter
@NoArgsConstructor
public class CertificateDto extends BaseObjectDto {
    private String code;
    private String name;
    public CertificateDto(Certificate entity){
        if (!ObjectUtils.isEmpty(entity)){
            this.setId(entity.getId());
            this.code = entity.getCode();
            this.name = entity.getName();
        }
    }
}
