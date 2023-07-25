package com.globits.da.converter;

import com.globits.da.domain.*;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.dto.EmployeeDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {
    public void convertEmployeeDtoToEmployeeEntity(EmployeeDto dto, Employee entity){
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setEmail(dto.getEmail());
        entity.setAge(dto.getAge());
        Province province = new Province();
        province.setId(dto.getProvinceId());
        entity.setProvince(province);
        District district = new District();
        district.setId(dto.getDistrictId());
        entity.setDistrict(district);
        Commune commune = new Commune();
        commune.setId(dto.getCommuneId());
        entity.setCommune(commune);
    }
    public void convertCertificateDtoToCertificateEntity(CertificateDto dto, Certificate entity){
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }
    public void convertEmployeeCertificateDtoToEntity(EmployeeCertificateDto dto, EmployeeCertificate entity){
        Employee employee = new Employee();
        employee.setId(dto.getEmployeeId());
        entity.setEmployee(employee);

        Certificate certificate = new Certificate();
        certificate.setId(dto.getCertificateId());
        entity.setCertificate(certificate);

        Province province = new Province();
        province.setId(dto.getProvinceId());
        entity.setProvince(province);

        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }
}
