package com.globits.da.repository;

import com.globits.da.domain.EmployeeCertificate;
import com.globits.da.dto.EmployeeCertificateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface EmployeeCertificateRepository extends JpaRepository<EmployeeCertificate, UUID> {
    @Query(value = "select new com.globits.da.dto.EmployeeCertificateDto(e) from EmployeeCertificate e " +
            "where e.employee.id = :employeeId " +
            "and e.certificate.id = :certificateId and e.endDate >= current_date and e.id <> :employeeCertificateId")
    List<EmployeeCertificateDto> getCertificatesOfEmployee(@Param("employeeId") UUID employeeId, @Param("certificateId") UUID certificateId, @Param("employeeCertificateId") UUID employeeCertificateId);

}
