package com.guli.edu.controller.admin;

import com.guli.common.vo.R;
import com.guli.edu.Vo.SubjectNestedVo;
import com.guli.edu.entity.Subject;
import com.guli.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-22 21:05
 */
@Slf4j
@RestController
@CrossOrigin
@Api(description = "课程分类管理")
@RequestMapping("/admin/edu/subject")
public class SubjectAdminController {

    @Autowired
    SubjectService subjectService;

    @ApiOperation(value = "添加二级标题")
    @PostMapping("save-level-two")
    public R saveLevelTwo(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject){
        Boolean result = subjectService.saveLevelTwo(subject);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "添加一级标题")
    @PostMapping("save-level-one")
    public R saveLevelOne(
            @ApiParam(name = "subject", value = "课程分类对象", required = true)
            @RequestBody Subject subject) {
        Boolean result = subjectService.saveLevelOne(subject);
        if (result) {
            return R.ok();
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("{id}")
    public R removeById(@PathVariable String id) {
        log.debug("开始删除节点======");
        Boolean result = subjectService.deleteSubjectById(id);
        if (result) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "嵌套数据列表")
    @GetMapping
    public R nestedList() {
        List<SubjectNestedVo> subjectNestedVoList = subjectService.nestedList();
        System.out.println("封装的数据是====");
        subjectNestedVoList.forEach(System.out::println);
        return R.ok().data("items", subjectNestedVoList);
    }

    @ApiOperation(value = "Excel批量导入")
    @PostMapping("import")
    public R addUser(
            @ApiParam(name = "file", value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {
        List<String> msg = subjectService.batchImport(file);
        if (msg.size() == 0) {
            return R.ok().message("批量导入成功");
        } else {
            return R.error().message("部分数据导入失败").data("messageList", msg);
        }
    }
}
