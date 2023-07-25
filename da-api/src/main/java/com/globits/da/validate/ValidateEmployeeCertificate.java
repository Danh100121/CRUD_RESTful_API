package com.globits.da.validate;

import com.globits.da.Constants;
import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.CertificateDto;
import com.globits.da.dto.EmployeeCertificateDto;
import com.globits.da.repository.CertificateRepository;
import com.globits.da.repository.EmployeeCertificateRepository;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.sl.usermodel.FreeformShape;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@RequiredArgsConstructor
@Component
public class ValidateEmployeeCertificate {
    private final EmployeeRepository employeeRepository;
    private final ProvinceRepository provinceRepository;
    private final CertificateRepository certificateRepository;
    private final EmployeeCertificateRepository employeeCertificateRepository;
    public ResponseStatus validate(UUID id, EmployeeCertificateDto dto){
        if (id != null &&  !employeeCertificateRepository.existsById(id)) {
            return ResponseStatus.EMPLOYEE_CERTIFICATE_ID_NOT_EXIST;
        }
        UUID employeeId = dto.getEmployeeId();
        UUID provinceId = dto.getProvinceId();
        UUID certificateId = dto.getCertificateId();
        ResponseStatus status = validateObject(provinceId,employeeId,certificateId);
        if (status != ResponseStatus.SUCCESS){
            return status;
        }
        status = validateDate(dto.getStartDate(), dto.getEndDate());
        if (status != ResponseStatus.SUCCESS){
            return status;
        }
        return validateCertificateNumberOfEmployee(id,employeeId,provinceId,certificateId);

    }
    public ResponseStatus validateObject(UUID provinceId, UUID employeeId, UUID certificateId){
        if (employeeId == null){
            return ResponseStatus.EMPLOYEE_ID_NOT_NULL;
        }
        if (!employeeRepository.existsById(employeeId)){
            return ResponseStatus.EMPLOYEE_ID_NOT_EXIST;
        }
        if (provinceId == null){
            return ResponseStatus.PROVINCE_ID_IS_NULL;
        }
        if (!provinceRepository.existsById(provinceId)){
            return ResponseStatus.EMPLOYEE_ID_NOT_EXIST;
        }
        if (certificateId == null){
            return ResponseStatus.CERTIFICATE_ID_NOT_NULL;
        }
        if (!certificateRepository.existsById(certificateId)){
            return ResponseStatus.CERTIFICATE_ID_NOT_EXIST;
        }
        return ResponseStatus.SUCCESS;
    }
    public ResponseStatus validateDate(LocalDate startDate, LocalDate endDate){
        if (startDate == null){
            return ResponseStatus.START_DATE_NOT_NULL;
        }
        if (endDate == null){
            return ResponseStatus.END_DATE_NOT_NULL;
        }
        if (endDate.isBefore(startDate)){
            return ResponseStatus.END_DATE_BEFORE_START_DATE;
        }
        LocalDate now = LocalDate.now();
        if (endDate.isBefore(now)){
            return ResponseStatus.END_DATE_IS_BEFORE_NOW;
        }
        return ResponseStatus.SUCCESS;
    }
    public ResponseStatus validateCertificateNumberOfEmployee(UUID employeeCertificateId, UUID employeeId,
                                                              UUID provinceId, UUID certificateId){
        if (employeeCertificateId == null){
            employeeCertificateId = new EmployeeCertificate().getId();
        }
        List<EmployeeCertificateDto> employeeCertificateDtos = employeeCertificateRepository.getCertificatesOfEmployee(employeeId,certificateId,employeeCertificateId);
        if (employeeCertificateDtos.size() >= Constants.MAX_SAME_TYPE_CERTIFICATE){
            return ResponseStatus.HAVE_3_CERTIFICATE;
        }
        for (EmployeeCertificateDto certificateDto : employeeCertificateDtos){
            if (certificateDto.getProvinceId().equals(provinceId)){
                return ResponseStatus.CERTIFICATE_ALREADY_EXIST;
            }
        }
        return ResponseStatus.SUCCESS;
    }
}
