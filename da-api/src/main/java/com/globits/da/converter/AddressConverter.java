package com.globits.da.converter;

import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
@RequiredArgsConstructor
public class AddressConverter {
    private final CommuneRepository communeRepository;
    private final DistrictRepository districtRepository;
    public Commune convertCommuneDtoToCommuneEntity(CommuneDto communeDto, Commune commune,District district) {
        commune.setCode(communeDto.getCode());
        commune.setName(communeDto.getName());
        commune.setDistrict(district);
        return commune;
    }

    public District convertDistrictDtoToDistrictEntity(DistrictDto dto , Province province, District district) {
        district.setCode(dto.getCode());
        district.setName(dto.getName());
        district.setProvince(province);

        List<CommuneDto> communeDtos = dto.getCommuneDtoList();
        if (!CollectionUtils.isEmpty(communeDtos)){
            List<Commune> communes = new ArrayList<>();
            for (CommuneDto communeDto : communeDtos){
                UUID communeId = communeDto.getId();
                Commune commune = communeId != null ? communeRepository.getOne(communeId) : new Commune();
                convertCommuneDtoToCommuneEntity(communeDto, commune,district);
                communes.add(commune);

            }
            district.setCommuneList(communes);
        }
        return district;
    }
    public Province convertProvinceDtoToProvinceEntity(ProvinceDto dto, Province province){
        province.setCode(dto.getCode());
        province.setName(dto.getName());
        List<DistrictDto> districtDtos = dto.getDistrictDtoList();
        List<District> districts = new ArrayList<>();
        if (!CollectionUtils.isEmpty(districtDtos)){

            for  (DistrictDto districtDto : districtDtos){
                UUID districtId = districtDto.getId();
                District district = districtId != null ? districtRepository.getOne(districtId) : new District();
                convertDistrictDtoToDistrictEntity(districtDto, province, district);
                districts.add(district);
            }
        }
        province.setDistrictList(districts);
        return province;
    }
}
