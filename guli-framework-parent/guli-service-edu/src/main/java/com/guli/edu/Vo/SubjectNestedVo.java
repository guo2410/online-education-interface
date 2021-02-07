package com.guli.edu.Vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-23 10:46
 */
@Data
public class SubjectNestedVo {
    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();
}
