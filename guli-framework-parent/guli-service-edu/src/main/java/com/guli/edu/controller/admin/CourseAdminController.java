package com.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.vo.R;
import com.guli.edu.Vo.CourseInfoForm;
import com.guli.edu.Vo.CoursePublishVo;
import com.guli.edu.Vo.CourseQuery;
import com.guli.edu.entity.Course;
import com.guli.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-25 10:26
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/edu/course")
@Api(description = "课程管理")
public class CourseAdminController {

    @Autowired
    CourseService courseService;

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("publish-course/{id}")
    public R publishCourseById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        courseService.publishCourseById(id);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课程详情信息")
    @GetMapping("course-publish-info/{id}")
    public R getCoursePublishInfoById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishInfoById(id);
        return R.ok().data("item", coursePublishVo);
    }

    @ApiOperation(value = "根据ID删除课程")
    @DeleteMapping("{id}")
    public R removeById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        boolean result = courseService.removeCourseById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("删除失败");
        }
    }

    @ApiOperation(value = "分页课程列表")
    @GetMapping("{page}/{limit}")
    public R pageQuery(@ApiParam(name = "page", value = "当前页码", required = true)
                       @PathVariable Long page,
                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable Long limit,
                       @ApiParam(name = "courseQuery", value = "查询对象", required = false)
                               CourseQuery courseQuery) {
        Page<Course> pageParam = new Page<>(page, limit);
        courseService.pageQuery(pageParam, courseQuery);
        List<Course> records = pageParam.getRecords();
        long total = pageParam.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "更新课程")
    @PostMapping("update-course-info/{id}")
    public R updateCourseInfoById(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm,
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {

        courseService.updateCourseInfoById(courseInfoForm);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(name = "id", value = "课程ID", required = true)
            @PathVariable String id) {
        CourseInfoForm courseInfoFormById = courseService.getCourseInfoFormById(id);
        return R.ok().data("item", courseInfoFormById);
    }

    @ApiOperation(value = "新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(name = "CourseInfoForm", value = "课程基本信息", required = true)
            @RequestBody CourseInfoForm courseInfoForm) {
        String courseId = courseService.saveCourseInfo(courseInfoForm);
        if (!StringUtils.isEmpty(courseId)) {
            return R.ok().data("courseId", courseId);
        } else {
            return R.error().message("保存失败");
        }
    }
}
