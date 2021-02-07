package com.guli.edu.Vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-28 10:47
 */
@ApiModel(value = "课程发布信息")
@Data
public class CoursePublishVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;//标题
    private String cover;//封面
    private Integer lessonNum;//课时
    private String subjectLevelOne;//一级标题
    private String subjectLevelTwo;//二级标题
    private String teacherName;//讲师名称
    private String price;//只用于显示
}
