package com.globits.da.validate;

import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidateAddress {
    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    public ResponseStatus validateCommune(UUID communeId, CommuneDto dto, boolean checkDistrict){
        if (!StringUtils.hasText(dto.getName())){
            return ResponseStatus.COMMUNE_NAME_REQUIRED;
        }
        if (!StringUtils.hasText(dto.getCode())){
            return ResponseStatus.COMMUNE_CODE_REQUIRED;
        }
        if (communeId == null && communeRepository.existsByCode(dto.getCode())){
            return ResponseStatus.COMMUNE_CODE_IS_EXIST;
        }
        UUID districtId = dto.getDistrictId();
        if (checkDistrict && districtId == null){
            return ResponseStatus.DISTRICT_ID_IS_NULL;
        }
        if (checkDistrict && !districtRepository.existsById(districtId)){
            return ResponseStatus.DISTRICT_ID_NOT_EXIST;
        }
        return ResponseStatus.SUCCESS;
    }
    public ResponseStatus validateDistrict(UUID districtId, DistrictDto dto, boolean checkProvince) {
        return validateDistrict(districtId, dto, checkProvince, new ArrayList<>());
    }
    public ResponseStatus validateDistrict(UUID districtId, DistrictDto dto, boolean checkProvince,List<String> listNewCode){
        if (!StringUtils.hasText(dto.getName())){
            return ResponseStatus.DISTRICT_NAME_NOT_NULL;
        }
        if (!StringUtils.hasText(dto.getCode())){
            return ResponseStatus.DISTRICT_CODE_NOT_NULL;
        }
        if (districtId == null && districtRepository.existsByCode(dto.getCode())){
            return ResponseStatus.DISTRICT_CODE_IS_EXIST;
        }
        UUID provinceId = dto.getProvinceId();
        if (checkProvince && provinceId == null){
            return ResponseStatus.PROVINCE_ID_IS_NULL;
        }
        if (checkProvince && !provinceRepository.existsById(provinceId)){
            return ResponseStatus.PROVINCE_ID_NOT_EXITS;
        }
        return validateCommuneDistrictRelation(districtId, dto, listNewCode);
    }
    public ResponseStatus validateProvince(UUID provinceId, ProvinceDto dto){
        if (!StringUtils.hasText(dto.getName())){
            return ResponseStatus.PROVINCE_NAME_REQUIRED;
        }
        if (!StringUtils.hasText(dto.getCode())){
            return ResponseStatus.PROVINCE_CODE_REQUIRED;
        }
        if (provinceId == null && provinceRepository.existsByCode(dto.getCode())){
            return ResponseStatus.PROVINCE_CODE_IS_EXIST;
        }
        return validateDistrictProvinceRelation(provinceId, dto);
    }
    private ResponseStatus validateCommuneDistrictRelation(UUID districtID, DistrictDto dto, List<String> listNewCode) {
        List<CommuneDto> communeDtoList = dto.getCommuneDtoList();
        if (!CollectionUtils.isEmpty(communeDtoList)) {
            for (CommuneDto communeDto : communeDtoList) {
                UUID communeId = communeDto.getId();
                ResponseStatus status = validateCommune(communeId, communeDto, false);
                if (status != ResponseStatus.SUCCESS) {
                    return status;
                }
                if (communeId != null) {
                    UUID communeDistrictID = communeRepository.getCommuneById(communeId).getDistrict().getId();
                    if (!communeDistrictID.equals(districtID)) {
                        return ResponseStatus.NON_DISTRIC_COMMUNE;
                    }
                }
                if (isCodeDuplicate(listNewCode, communeDto.getCode())) {
                    return ResponseStatus.COMMUNE_CODE_DUPLICATE;
                }
            }
        }
        return ResponseStatus.SUCCESS;
    }
    public ResponseStatus validateDistrictProvinceRelation(UUID provinceId, ProvinceDto dto){
        List<DistrictDto> districtDtoList = dto.getDistrictDtoList();
        if (!CollectionUtils.isEmpty(districtDtoList)){
            List<String> listNewDistrictCode = new ArrayList<>();
            List<String> listNewCommuneCode = new ArrayList<>();
            for (DistrictDto districtDto : districtDtoList){
                UUID districtId = districtDto.getId();
                ResponseStatus status = validateDistrict(districtId,districtDto,false, listNewCommuneCode);
                if (status != ResponseStatus.SUCCESS){
                    return status;
                }
                if (districtId !=null){
                    UUID districtProvinceId = districtRepository.getDistrictsById(districtId).getProvince().getId();
                    if (!districtProvinceId.equals(provinceId)){
                        return ResponseStatus.NON_PROVINCE_DISTRICT;
                    }
                }
                if (isCodeDuplicate(listNewDistrictCode, districtDto.getCode())) {
                    return ResponseStatus.DISTRICT_CODE_DUPLICATE;
                }
            }
        }
        return ResponseStatus.SUCCESS;
    }
    private boolean isCodeDuplicate(List<String> list, String newCode) {
        for (String element : list) {
            if (element.equals(newCode))
                return true;
        }
        list.add(newCode);
        return false;
    }
}
