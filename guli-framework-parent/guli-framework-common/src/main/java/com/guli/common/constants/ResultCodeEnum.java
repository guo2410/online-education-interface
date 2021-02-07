package com.guli.common.constants;

import lombok.Getter;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-16 19:16
 */
@Getter
public enum  ResultCodeEnum {

    SUCCESS(true, 20000,"成功"),
    UNKNOWN_REASON(false, 20001, "未知错误"),
    JSON_PARSE_ERROR(false, 21002, "json解析异常"),
    PARAM_ERROR(false, 21002,"参数不正确"),
    FILE_UPLOAD_ERROR(false, 21004, "文件上传错误"),
    EXCEL_DATA_ERROR(false, 21005, "Excel数据导入错误");

    private Boolean success;

    private Integer code;

    private String message;

    private ResultCodeEnum(Boolean success, Integer code, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

}
