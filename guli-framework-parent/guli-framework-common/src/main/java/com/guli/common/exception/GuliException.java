package com.guli.common.exception;

import com.guli.common.constants.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-17 12:48
 */
@Data
@ApiModel(value = "全局异常")
public class GuliException extends RuntimeException{
    @ApiModelProperty(value = "状态码")
    private Integer code;

    public GuliException(Integer code, String message) {
        super(message);
        this.code=code;
    }
    public GuliException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }
}
