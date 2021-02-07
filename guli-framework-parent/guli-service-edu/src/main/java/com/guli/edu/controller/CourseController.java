package com.guli.edu.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.R;
import com.guli.edu.Vo.ChapterVo;
import com.guli.edu.Vo.CourseWebVo;
import com.guli.edu.entity.Course;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
@CrossOrigin
@RestController
@RequestMapping("/edu/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    ChapterService chapterService;

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping(value = "{courseId}")
    public R getById(
            @ApiParam(name = "courseId", value = "课程ID", required = true)
            @PathVariable String courseId) {
        //查询课程信息和讲师信息
        CourseWebVo courseWebVo = courseService.selectInfoWebById(courseId);
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("course", courseWebVo).data("chapterVoList", chapterVoList);
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping(value = "{page}/{limit}")
    public R pageList(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit) {
        Page<Course> pageParam = new Page<>(page, limit);
        Map<String, Object> map = courseService.pageListWeb(pageParam);
        return R.ok().data(map);
    }


}

