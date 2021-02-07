package com.guli.edu.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.common.vo.R;
import com.guli.edu.entity.Teacher;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-16 11:31
 */
@Api(description="讲师增删改查")
@RestController
@RequestMapping("/admin/edu/teacher")
@CrossOrigin
public class TeacherAdminController {

    @Autowired
    TeacherService teacherService;

    //{"code":20000,"data":{"token":"admin"}}
    @PostMapping("login")
    public R  login(){
      return   R.ok().data("token","admin");
    }

    //{"code":20000,"data":{"roles":["admin"],"name":"admin","avatar":"https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif"}}
    @GetMapping("info")
    public R info(){
        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    @ApiOperation(value = "根据ID修改讲师")
    @PostMapping("{id}")
    public R updateById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id,
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return R.ok();
    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("{id}")
    public R getById(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("item", teacher);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody Teacher teacher) {
        System.out.println("开始保存");
        teacherService.save(teacher);
        return R.ok();
    }

    @ApiOperation(value = "条件分页查询")
    @PostMapping("{page}/{limit}")
    public R pageQuery(@ApiParam(name = "page", value = "当前页码", required = true)
                       @PathVariable Long page,
                       @ApiParam(name = "limit", value = "每页记录数", required = true)
                       @PathVariable Long limit,
                       @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                       @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherService.pageQuery(teacherPage, teacherQuery);
        List<Teacher> teacherList = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total", total).data("items", teacherList);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("{page}/{limit}")
    public R pageList(@ApiParam(name = "page", value = "当前页码", required = true)
                      @PathVariable Long page,
                      @ApiParam(name = "limit", value = "每页记录数", required = true)
                      @PathVariable Long limit) {
        if (page <= 0 || limit <= 0) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }
        Page<Teacher> teacherPage = new Page<>(page, limit);
        teacherService.page(teacherPage, null);
        List<Teacher> teacherList = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total", total).data("items", teacherList);
    }

    @ApiOperation(value = "查询所有讲师")
    @GetMapping
    public R listAllTeacher() {
        List<Teacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据id删除讲师")
    @DeleteMapping("{id}")
    public boolean deleteTeacherById(@PathVariable String id) {
        return teacherService.removeById(id);
    }
}
