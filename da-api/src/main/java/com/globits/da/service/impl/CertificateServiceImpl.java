package com.globits.da.service.impl;

import com.globits.da.converter.Converter;
import com.globits.da.domain.Certificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.response.Response;
import com.globits.da.service.CertificateService;
import com.globits.da.validate.ResponseStatus;
import com.globits.da.validate.ValidateCertificate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Service
public class CertificateServiceImpl implements CertificateService {
    private final CertificateRepository certificateRepository;
    private final ValidateCertificate validateCertificate;
    private final Converter converter;
    @Override
    public Response<CertificateDto> add(CertificateDto dto) {
        ResponseStatus status = validateCertificate.validate(null,dto);
        if (status != ResponseStatus.SUCCESS){
            return new Response<>(status);
        }
        Certificate certificate = new Certificate();
        converter.convertCertificateDtoToCertificateEntity(dto,certificate);

        certificateRepository.save(certificate);
        return new Response<>(new CertificateDto(certificate));

    }

    @Override
    public Response<CertificateDto> update(CertificateDto dto, UUID id) {
        ResponseStatus status = validateCertificate.validate(id,dto);
        if (status != ResponseStatus.SUCCESS){
            return new Response<>(status);
        }
        Certificate certificate = certificateRepository.getOne(id);
        converter.convertCertificateDtoToCertificateEntity(dto,certificate);
        certificateRepository.save(certificate);

        return new Response<>(new CertificateDto(certificate), ResponseStatus.SUCCESS);
    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!certificateRepository.existsById(id)){
            return new Response<>(false,ResponseStatus.CERTIFICATE_ID_NOT_EXIST);
        }
        certificateRepository.deleteById(id);
        return new Response<>(true, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<CertificateDto>> getAll() {
        List<Certificate> certificateList = certificateRepository.findAll();
        List<CertificateDto> certificateDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(certificateList)){
            for(Certificate certificate : certificateList){
                certificateDtos.add(new CertificateDto(certificate));
            }
        }
        return new Response<>(certificateDtos, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<CertificateDto> getById(UUID id) {
        if (!certificateRepository.existsById(id)){
            return new Response<>(ResponseStatus.COMMUNE_ID_NOT_EXIST);
        }
        Certificate certificate= certificateRepository.getOne(id);
        return new Response<>(new CertificateDto(certificate), ResponseStatus.SUCCESS);
    }
}
