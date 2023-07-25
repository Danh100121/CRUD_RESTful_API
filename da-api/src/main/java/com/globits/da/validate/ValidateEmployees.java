package com.globits.da.validate;

import com.globits.da.Constants;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class ValidateEmployees {
    private final EmployeeRepository employeeRepository;
    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    public ResponseStatus validateEmployee(UUID id, EmployeeDto dto){
        if (id != null && !employeeRepository.existsById(id)){
            return ResponseStatus.EMPLOYEE_ID_NOT_EXIST;
        }
        if (!StringUtils.hasText(dto.getName())){
            return ResponseStatus.EMPLOYEE_NAME_NOT_NULL;
        }
        if (!StringUtils.hasText(dto.getCode())){
            return ResponseStatus.EMPLOYEE_CODE_NOT_NULL;
        }
        if (id == null && employeeRepository.existsByCode(dto.getCode())){
            return ResponseStatus.EMPLOYEE_CODE_IS_EXIST;
        }
        if (!Pattern.matches(Constants.REGEX_CODE,dto.getCode())){
            return ResponseStatus.EMPLOYEE_CODE_NOT_FORMAT;
        }
        if (!StringUtils.hasText(dto.getEmail())){
            return ResponseStatus.EMPLOYEE_EMAIL_NOT_NULL;
        }
        if (!Pattern.matches(Constants.REGEX_EMAIL, dto.getEmail())){
            return ResponseStatus.EMPLOYEE_EMAIL_NOT_FORMAT;
        }
        if (!StringUtils.hasText(dto.getPhone())){
            return ResponseStatus.EMPLOYEE_PHONE_NOT_NULL;
        }
        if (!Pattern.matches(Constants.REGEX_PHONE, dto.getPhone())){
            return ResponseStatus.EMPLOYEE_PHONE_NOT_FORMAT;
        }
        if (dto.getAge() == null || dto.getAge() <= Constants.MIN_AGE ){
            return ResponseStatus.EMPLOYEE_AGE_NOT_FORMAT;
        }
        UUID provinceId = dto.getProvinceId();
        UUID districtId = dto.getDistrictId();
        UUID communeId = dto.getCommuneId();
       return validateAddress(provinceId,districtId,communeId);
    }
    public ResponseStatus validateAddress(UUID provinceId, UUID districtId, UUID communeId){
        if (provinceId == null){
            return ResponseStatus.PROVINCE_ID_IS_NULL;
        }
        if (!provinceRepository.existsById(provinceId)){
            return ResponseStatus.PROVINCE_ID_NOT_EXITS;
        }
        if (districtId== null){
            return ResponseStatus.DISTRICT_ID_IS_NULL;
        }
        if ( !districtRepository.existsById(districtId)){
            return ResponseStatus.DISTRICT_ID_NOT_EXIST;
        }
        District district = districtRepository.getOne(districtId);
        if (!provinceId.equals(district.getProvince().getId())){
            return ResponseStatus.NON_PROVINCE_DISTRICT;
        }
        if (communeId == null){
            return ResponseStatus.COMMUNE_ID_IS_NULL;
        }
        if (!communeRepository.existsById(communeId)){
            return ResponseStatus.COMMUNE_ID_NOT_EXIST;
        }
        Commune commune = communeRepository.getOne(communeId);
        if(! districtId.equals(commune.getDistrict().getId())){
            return ResponseStatus.NON_DISTRIC_COMMUNE;
        }
        return ResponseStatus.SUCCESS;
    }
}
