package com.globits.da.dto;

import com.globits.core.dto.BaseObjectDto;
import com.globits.da.domain.EmployeeCertificate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
public class EmployeeCertificateDto extends BaseObjectDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID provinceId;
    private UUID employeeId;
    private UUID certificateId;

    public EmployeeCertificateDto(EmployeeCertificate entity){
        if (!ObjectUtils.isEmpty(entity)){
            this.setId(entity.getId());
            this.startDate = entity.getStartDate();
            this.endDate = entity.getEndDate();
            this.employeeId = entity.getEmployee().getId();
            this.provinceId= entity.getProvince().getId();
            this.certificateId = entity.getCertificate().getId();
        }
    }
}
