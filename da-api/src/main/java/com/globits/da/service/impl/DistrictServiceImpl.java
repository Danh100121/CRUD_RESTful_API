package com.globits.da.service.impl;

import com.globits.da.converter.AddressConverter;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.CommuneDto;
import com.globits.da.dto.DistrictDto;
import com.globits.da.repository.DistrictRepository;
import com.globits.da.response.Response;
import com.globits.da.service.DistrictService;
import com.globits.da.validate.ResponseStatus;
import com.globits.da.validate.ValidateAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class DistrictServiceImpl implements DistrictService {
    private final DistrictRepository districtRepository;
    private final AddressConverter addressConverter;
    private final ValidateAddress validateAddress;
    @Override
    public Response<DistrictDto> add(DistrictDto dto) {
        ResponseStatus status = validateAddress.validateDistrict(null, dto, true);
        if (status != ResponseStatus.SUCCESS){
            return new Response<>(status);
        }
        Province province = new Province();
        province.setId(dto.getProvinceId());

        District district = new District();
        addressConverter.convertDistrictDtoToDistrictEntity(dto,province, district);

        districtRepository.save(district);

        return new Response<>(new DistrictDto(district));
    }

    @Override
    public Response<DistrictDto> update(DistrictDto dto, UUID id) {
        ResponseStatus status = validateAddress.validateDistrict(id,dto,true);
        if (status != ResponseStatus.SUCCESS){
            return new Response<>(status);
        }
        Province province = new Province();
        province.setId(dto.getProvinceId());

        District district = districtRepository.getOne(id);
        addressConverter.convertDistrictDtoToDistrictEntity(dto,province,district);

        districtRepository.save(district);

        return new Response<>(new DistrictDto(district));
    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!districtRepository.existsById(id)){
            return new Response<>(false, ResponseStatus.DISTRICT_ID_NOT_EXIST);
        }
        districtRepository.deleteById(id);
        return new Response<>(true, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<DistrictDto>> getAll() {
        List<District> districtList = districtRepository.findAll();
        List<DistrictDto> districtDtoList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(districtList)){
            for (District district : districtList){
                districtDtoList.add(new DistrictDto(district));
            }
        }
        return new Response<>(districtDtoList, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<DistrictDto> getById(UUID id) {
        if (!districtRepository.existsById(id)){
            return new Response<>(ResponseStatus.DISTRICT_ID_NOT_EXIST);
        }
        District district = districtRepository.getOne(id);
        return new Response<>(new DistrictDto(district), ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<CommuneDto>> getCommunesById(UUID id) {
        if (!districtRepository.existsById(id)){
            return new Response<>(ResponseStatus.DISTRICT_ID_NOT_EXIST);
        }
        List<CommuneDto> dtoList = new ArrayList<>();
        District district = districtRepository.getOne(id);
        for (Commune commune : district.getCommuneList()){
            dtoList.add(new CommuneDto(commune));
        }
        return new Response<>(dtoList, ResponseStatus.SUCCESS);
    }
}
