package com.globits.da.service.impl;

import com.globits.da.converter.AddressConverter;
import com.globits.da.domain.Commune;
import com.globits.da.domain.District;
import com.globits.da.dto.CommuneDto;
import com.globits.da.repository.CommuneRepository;
import com.globits.da.response.Response;
import com.globits.da.service.CommuneService;
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
public class CommuneServiceImpl implements CommuneService {
    private final CommuneRepository communeRepository;
    private final AddressConverter addressConverter;
    private final ValidateAddress validateAddress;
    @Override
    public Response<CommuneDto> add(CommuneDto dto) {
        ResponseStatus status = validateAddress.validateCommune(null,dto,true);
        if (status != ResponseStatus.SUCCESS) {
            return new Response<>(status);
        }
        District district = new District();
        district.setId(dto.getDistrictId());

        Commune commune = new Commune();
        addressConverter.convertCommuneDtoToCommuneEntity(dto,commune,district);

        communeRepository.save(commune);
        return new Response<>(new CommuneDto(commune));
    }

    @Override
    public Response<CommuneDto> update(CommuneDto dto, UUID id) {
        ResponseStatus status = validateAddress.validateCommune(id, dto, true);

        if (status != ResponseStatus.SUCCESS) {
            return new Response<>(status);
        }
        District district = new District();
        district.setId(dto.getDistrictId());

        Commune commune = communeRepository.getOne(id);
        addressConverter.convertCommuneDtoToCommuneEntity(dto,commune,district);

        communeRepository.save(commune);
        return new Response<>(new CommuneDto(commune));
    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!communeRepository.existsById(id)){
            return new Response<>(false, ResponseStatus.COMMUNE_ID_NOT_EXIST);
        }
        communeRepository.deleteById(id);
        return new Response<>(true, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<CommuneDto>> getAll() {
        List<Commune> communeList = communeRepository.findAll();
        List<CommuneDto> communeDtoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(communeList)){
            for(Commune commune : communeList){
                communeDtoList.add(new CommuneDto(commune));
            }
        }
        return new Response<>(communeDtoList, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<CommuneDto> getById(UUID id) {
        if (!communeRepository.existsById(id)){
            return new Response<>(ResponseStatus.COMMUNE_ID_NOT_EXIST);
        }
        Commune commune = communeRepository.getOne(id);
        return new Response<>(new CommuneDto(commune),ResponseStatus.SUCCESS);
    }
}
