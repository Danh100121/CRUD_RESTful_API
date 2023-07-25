package com.globits.da.service.impl;

import com.globits.da.converter.Converter;
import com.globits.da.domain.Employee;
import com.globits.da.dto.CategoryDto;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.EmployeeSearchDto;
import com.globits.da.repository.EmployeeRepository;
import com.globits.da.response.Response;
import com.globits.da.service.EmployeeService;
import com.globits.da.validate.ResponseStatus;
import com.globits.da.validate.ValidateEmployees;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ValidateEmployees validateEmployees;
    private final EntityManager manager;
    private final Converter converter;
    @Override
    public Response<EmployeeDto> add(EmployeeDto dto) {
        ResponseStatus status = validateEmployees.validateEmployee(null, dto);
        if (status != ResponseStatus.SUCCESS ){
            return new Response<>(status);
        }
        Employee employee = new Employee();
        converter.convertEmployeeDtoToEmployeeEntity(dto, employee);
        employeeRepository.save(employee);

        return new Response<>(new EmployeeDto(employee));
    }

    @Override
    public Response<EmployeeDto> update(EmployeeDto dto, UUID id) {
        ResponseStatus status = validateEmployees.validateEmployee(id , dto);
        if (status != ResponseStatus.SUCCESS ){
            return new Response<>(status);
        }
        Employee employee = employeeRepository.getOne(id);
        converter.convertEmployeeDtoToEmployeeEntity(dto, employee);
        employeeRepository.save(employee);

        return new Response<>(new EmployeeDto(employee), ResponseStatus.SUCCESS);
    }

    @Override
    public Response<Boolean> delete(UUID id) {
        if (!employeeRepository.existsById(id)){
            return new Response<>(false,ResponseStatus.EMPLOYEE_ID_NOT_EXIST);
        }
        employeeRepository.deleteById(id);
        return new Response<>(true,ResponseStatus.SUCCESS);
    }

    @Override
    public Response<List<EmployeeDto>> getAll() {
        List<Employee> employeeList = employeeRepository.findAll();
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        if (!CollectionUtils.isEmpty(employeeList)){
            for (Employee employee : employeeList){
                employeeDtos.add(new EmployeeDto(employee));
            }
        }
        return new Response<>(employeeDtos, ResponseStatus.SUCCESS);
    }

    @Override
    public Response<EmployeeDto> getById(UUID id) {
        if (!employeeRepository.existsById(id)){
            return new Response<>(ResponseStatus.EMPLOYEE_ID_NOT_EXIST);
        }
        Employee employee = employeeRepository.getOne(id);
        return new Response<>(new EmployeeDto(employee), ResponseStatus.SUCCESS);
    }

    @Override
    public Page<EmployeeDto> search(EmployeeSearchDto searchDto) {
        if (searchDto == null) {
            return null;
        }

        int pageIndex = searchDto.getPageIndex();
        int pageSize = searchDto.getPageSize();

        if (pageIndex > 0) {
            pageIndex--;
        } else {
            pageIndex = 0;
        }

        String whereClause = "";

        String orderBy = " ORDER BY entity.createDate DESC";

        String sqlCount = "select count(entity.id) from  Employee as entity where (1=1)   ";
        String sql = "select new com.globits.da.dto.EmployeeDto(entity) from  Employee as entity where (1=1)  ";

        if (searchDto.getKeyword() != null && StringUtils.hasText(searchDto.getKeyword())) {
            whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text)";
        }


        sql += whereClause + orderBy;
        sqlCount += whereClause;

        Query q = manager.createQuery(sql, EmployeeDto.class);
        Query qCount = manager.createQuery(sqlCount);

        if (searchDto.getKeyword() != null && StringUtils.hasText(searchDto.getKeyword())) {
            q.setParameter("text", '%' + searchDto.getKeyword() + '%');
            qCount.setParameter("text", '%' + searchDto.getKeyword() + '%');
        }
        int startPosition = pageIndex * pageSize;
        q.setFirstResult(startPosition);
        q.setMaxResults(pageSize);
        List<EmployeeDto> entities = q.getResultList();
        long count = (long) qCount.getSingleResult();

        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<EmployeeDto> result = new PageImpl<EmployeeDto>(entities, pageable, count);
        return result;
    }

    @Override
    public Response<Object> importExcel(MultipartFile file) {
        Response<Object> response = new Response<>();
        Workbook workbook;
        Sheet sheet;
        try {
            workbook = new XSSFWorkbook(file.getInputStream());
            sheet = workbook.getSheetAt(0);
            workbook.close();
        } catch (IOException e) {
            response.setResponseStatus(ResponseStatus.FILE_ERROR);
            response.setData("File excel không thể đọc.");
            return response;
        }

        List<String> listStatus = new ArrayList<>();

        int rowIndex = 0;
        int columnIndex;
        int rowNum = sheet.getLastRowNum();

        while (rowIndex < rowNum) {
            rowIndex++;
            columnIndex = 0;
            Row row = sheet.getRow(rowIndex);
            EmployeeDto dto = new EmployeeDto();

            try {
                dto.setCode(row.getCell(columnIndex++).getStringCellValue());
                dto.setName(row.getCell(columnIndex++).getStringCellValue());
                dto.setAge((int) row.getCell(columnIndex++).getNumericCellValue());
                dto.setEmail(row.getCell(columnIndex++).getStringCellValue());
                dto.setPhone(row.getCell(columnIndex++).getStringCellValue());
                dto.setProvinceId(UUID.fromString(row.getCell(columnIndex++).getStringCellValue()));
                dto.setDistrictId(UUID.fromString(row.getCell(columnIndex++).getStringCellValue()));
                dto.setCommuneId(UUID.fromString(row.getCell(columnIndex++).getStringCellValue()));

            } catch (NullPointerException | IllegalStateException | IllegalArgumentException e) {
                listStatus.add("Dòng " + rowIndex + " 'SAI': (cột " + columnIndex + ")");
                continue;
            }

            ResponseStatus status = validateEmployees.validateEmployee(null, dto);

            if (status != ResponseStatus.SUCCESS) {
                listStatus.add("Dòng " + rowIndex + " 'SAI': (" + status.getMessage() + ")");
                continue;
            }

            Employee employee = new Employee();
            converter.convertEmployeeDtoToEmployeeEntity(dto, employee);
            employeeRepository.save(employee);

            listStatus.add("Dòng " + rowIndex + " 'THÀNH CÔNG':");
        }
        return new Response<>(listStatus);
    }

    @Override
    public Response<?> export(HttpServletResponse response) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("employees");
            ServletOutputStream outputStream = response.getOutputStream();
            List<Employee> listEmployee = employeeRepository.findAll();
            if (CollectionUtils.isEmpty(listEmployee)) {
                ResponseStatus status = ResponseStatus.EXCEL_ERROR;
                status.setMessage("Dữ liệu nhân viên trong cơ sở dữ liệu đang rỗng");
                return new Response<>(status);
            }
            createHeader(workbook, sheet);
            createBody(listEmployee, workbook, sheet);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            return new Response<>();
        } catch (IOException | RuntimeException e) {
            ResponseStatus status = ResponseStatus.EXCEL_ERROR;
            status.setMessage("Lỗi khi cố đọc dữ liệu và tạo exel: " + e.getMessage());
            return new Response<>(status);
        }
    }
    private final String[] header = new String[]{"Code", "Name", "Age", "Email", "Phone", "Province Id", "District Id", "Commune Id"};
    private void createBody(List<Employee> listEmployee, XSSFWorkbook workbook, XSSFSheet sheet) {
        int rowCount = 1;
        int stt = 1;

        CellStyle styleBody = workbook.createCellStyle();
        XSSFFont fontBody = workbook.createFont();
        fontBody.setFontHeight(14);
        styleBody.setFont(fontBody);

        for (Employee element : listEmployee) {
            Row line = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(line, columnCount++, stt++, styleBody, sheet);
            createCell(line, columnCount++, element.getCode(), styleBody, sheet);
            createCell(line, columnCount++, element.getName(), styleBody, sheet);
            createCell(line, columnCount++, element.getAge().toString(), styleBody, sheet);
            createCell(line, columnCount++, element.getEmail(), styleBody, sheet);
            createCell(line, columnCount++, element.getPhone(), styleBody, sheet);
            createCell(line, columnCount++, element.getProvince().getId().toString(), styleBody, sheet);
            createCell(line, columnCount++, element.getDistrict().getId().toString(), styleBody, sheet);
            createCell(line, columnCount, element.getCommune().getId().toString(), styleBody, sheet);
        }
    }
    private void createHeader(XSSFWorkbook workbook, XSSFSheet sheet) {
        Row row = sheet.createRow(0);

        CellStyle styleHeader = workbook.createCellStyle();
        XSSFFont fontHeader = workbook.createFont();
        fontHeader.setBold(true);
        fontHeader.setFontHeight(16);
        styleHeader.setFont(fontHeader);

        createCell(row, 0, "STT", styleHeader, sheet);
        int columnNum = header.length;
        for (int i = 0; i < columnNum; i++) {
            createCell(row, i + 1, header[i], styleHeader, sheet);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style, XSSFSheet sheet) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

}
