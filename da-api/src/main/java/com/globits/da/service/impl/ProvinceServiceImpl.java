package com.globits.da.service.impl;

import com.globits.da.converter.AddressConverter;
import com.globits.da.domain.District;
import com.globits.da.domain.Province;
import com.globits.da.dto.DistrictDto;
import com.globits.da.dto.ProvinceDto;
import com.globits.da.repository.ProvinceRepository;
import com.globits.da.response.Response;
import com.globits.da.service.ProvinceService;
import com.globits.da.validate.ResponseStatus;
import com.globits.da.validate.ValidateAddress;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {
    private final ProvinceRepository provinceRepository;
    private final AddressConverter addressConverter;
    private final ValidateAddress validateAddress;

    @Override
    public Response<ProvinceDto> add(ProvinceDto dto) {
        ResponseStatus status = validateAddress.validateProvince(null, dto);
        if (status != ResponseStatus.SUCCESS){
            return new Response<>(status);
        }
        Province province = new Province();
        addressConverter.convertProvinceDtoToProvinceEntity(dto,province);
        provinceRepository.save(province);
        return new Response<>(new ProvinceDto(province));
    }

    @Override
    public Response<ProvinceDto> update(ProvinceDto dto, UUID id) {
        ResponseStatus status = validateAddress.validateProvince(id, dto);
        if (status != ResponseStatus.SUCCESS ){
            return new Response<>(status);
        }
        Province province = provinceRepository.getOne(id);
        addressConverter.convertProvinceDtoToProvinceEntity(dto, province);
        provinceRepository.save(province);
        return new Response<>(new ProvinceDto(province));

    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!provinceRepository.existsById(id)){
            return new Response<>( false,ResponseStatus.PROVINCE_ID_NOT_EXITS);
        }
        provinceRepository.deleteById(id);
        return new Response<>(true,ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<ProvinceDto>> getAll() {
        List<Province> provinceList = provinceRepository.findAll();
        List<ProvinceDto> provinceDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(provinceList)){
            for (Province province : provinceList){
                provinceDtoList.add(new ProvinceDto(province));
            }
        }
        return new Response<>(provinceDtoList,ResponseStatus.SUCCESS);
    }

    @Override
    public Response<ProvinceDto> getById(UUID id) {
        if (!provinceRepository.existsById(id)){
            return new Response<>(ResponseStatus.PROVINCE_ID_NOT_EXITS);
        }
        Province province= provinceRepository.getOne(id);
        return new Response<>(new ProvinceDto(province), ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<DistrictDto>> getDistrictsById(UUID id) {
        Optional<Province> provinceOptional = provinceRepository.findById(id);
        if (!provinceOptional.isPresent()){
            return new Response<>(ResponseStatus.PROVINCE_ID_NOT_EXITS);
        }
        List<DistrictDto> dtoList = new ArrayList<>();
        Province province = provinceOptional.get();
        for(District district : province.getDistrictList()){
            dtoList.add(new DistrictDto(district));
        }
        return new Response<>(dtoList, ResponseStatus.SUCCESS);
    }
}
