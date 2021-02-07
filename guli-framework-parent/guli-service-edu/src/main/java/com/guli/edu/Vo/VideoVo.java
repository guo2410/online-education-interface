package com.guli.edu.Vo;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-27 10:37
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
@ApiModel(value = "章节信息")
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Boolean free;
    private String videoOriginalName;
    private String videoSourceId;
}
