package com.globits.da.validate;

public enum ResponseStatus {
    COMMUNE_NAME_REQUIRED(4000,"ten xa khong duoc null"),
    COMMUNE_CODE_REQUIRED(4001,"code xa khong duoc null hoac rong"),
    COMMUNE_CODE_IS_EXIST(4002, "code cua xa da ton tai"),
    COMMUNE_CODE_DUPLICATE(4002, "code xã không được lặp"),
    PROVINCE_CODE_REQUIRED(4004, "code cua tinh khong duoc null hoac rong"),
    PROVINCE_NAME_REQUIRED(4006,"ten cua tinh khong duoc rong hoac null"),
    DISTRICT_ID_NOT_EXIST(4007,"id cua huyen khong ton tai"),
    DISTRICT_CODE_NOT_NULL(4008,"code cua huyen khong duoc null"),
    DISTRICT_CODE_IS_EXIST(4009,"code cua huyen da ton tai"),
    DISTRICT_NAME_NOT_NULL(4010,"name cua huyen khong duoc null"),
    DISTRICT_ID_IS_NULL(4011, "id cua huyen khong duoc null"),
    PROVINCE_ID_IS_NULL(4012, "id cua tinh khong duoc null"),
    PROVINCE_ID_NOT_EXITS(4013, "id cua tinh khong ton tai"),
    PROVINCE_CODE_IS_EXIST(4014, "code cua tinh da ton tai"),
    NON_PROVINCE_DISTRICT(4015, "huyen khong thuoc tinh"),
    DISTRICT_CODE_DUPLICATE(4016, "code cua huyen trung nhau"),
    NON_DISTRIC_COMMUNE(4017,"xa khong thuoc huyen"),
    COMMUNE_ID_NOT_EXIST(4018, "id cua xa khong ton tai"),
    EMPLOYEE_ID_NOT_EXIST(4019,"id cua nhan vien khong ton tai"),
    EMPLOYEE_NAME_NOT_NULL(4020,"ten cua nhan vien khong duoc null"),
    EMPLOYEE_CODE_NOT_NULL(4021,"code cua nhan vien khong duoc null"),
    EMPLOYEE_CODE_IS_EXIST(4022,"code cua nhan vien da ton tai"),
    EMPLOYEE_CODE_NOT_FORMAT(4023,"code cua nhan vien khong dung dinh dang"),
    EMPLOYEE_EMAIL_NOT_NULL(4024,"email cua nhan vien khong duoc null"),
    EMPLOYEE_EMAIL_NOT_FORMAT(4025,"email cua nhan vien khong dung dinh dang"),
    EMPLOYEE_PHONE_NOT_NULL(4026,"so dien thoai cua nhan vien khong duoc null"),
    EMPLOYEE_PHONE_NOT_FORMAT(4027,"so dien thoai cua nhan vien khong dung dinh dang"),
    EMPLOYEE_AGE_NOT_FORMAT(4028,"tuoi cua nhan vien khong dung dinh dang"),
    COMMUNE_ID_IS_NULL(4029, "id cua xa khong duoc null"),
    CERTIFICATE_ID_NOT_EXIST(4030,"id cua van bang khong ton tai"),
    CERTIFICATE_CODE_NOT_NULL(4031,"code cua van bang khong duoc null"),
    CERTIFICATE_NAME_NOT_NULL(4032,"name cua van bang khong duoc null"),
    EMPLOYEE_ID_NOT_NULL(4033,"id cua nhan vien khong duoc null"),
    CERTIFICATE_ID_NOT_NULL(4034,"id cua van bang khong duoc null"),
    START_DATE_NOT_NULL(4035,"ngay bat dau khong duoc null"),
    END_DATE_NOT_NULL(4036,"ngay ket thuc khong duoc null"),
    END_DATE_BEFORE_START_DATE(4037,"ngay ket thuc khong duoc truoc ngay bat dau"),
    END_DATE_IS_BEFORE_NOW(4038,"ngay ket thuc khong duoc truoc ngay hien tai"),
    HAVE_3_CERTIFICATE(4039,"da co 3 van bang hien tai con han , khong the them"),
    CERTIFICATE_ALREADY_EXIST(4040,"nhan vien da co van bang nay"),
    EMPLOYEE_CERTIFICATE_ID_NOT_EXIST(4041,"id cua employeeCertificate khong duoc null"),
    FILE_ERROR(4042, "File gửi lên sai. [code, name, age, email, phone, province code, district code, ward code]"),
    EXCEL_ERROR(4043, ""),
    SUCCESS(200, "Thành công!");

    private final int code;
    private String message;

    ResponseStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    public void setMessage(String message) {
        this.message = message;
    }
}
