package com.globits.da.validate;

import com.globits.da.dto.CertificateDto;
import com.globits.da.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ValidateCertificate {
    private final CertificateRepository certificateRepository;
    public ResponseStatus validate(UUID id, CertificateDto dto){
        if (id != null && !certificateRepository.existsById(id)){
            return ResponseStatus.CERTIFICATE_ID_NOT_EXIST;
        }
        if (!StringUtils.hasText(dto.getCode())){
            return ResponseStatus.CERTIFICATE_CODE_NOT_NULL;
        }
        if (!StringUtils.hasText(dto.getName())){
            return ResponseStatus.CERTIFICATE_NAME_NOT_NULL;
        }
        return ResponseStatus.SUCCESS;
    }
}
