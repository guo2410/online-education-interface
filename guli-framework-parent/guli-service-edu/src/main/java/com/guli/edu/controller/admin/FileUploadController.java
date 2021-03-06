package com.guli.edu.controller.admin;

import com.guli.common.vo.R;
import com.guli.edu.service.FileService;
import com.guli.edu.util.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-21 17:07
 */
@Api(description="阿里云文件管理")
@RequestMapping("/admin/oss/file")
@CrossOrigin
@RestController
public class FileUploadController {

    @Autowired
    FileService fileService;

    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    public R upload(
            @ApiParam(name = "file", value = "文件", required = true)
            @PathVariable("file") MultipartFile file,
            @ApiParam(name = "host", value = "文件上传路径", required = false)
            @RequestParam(value = "host", required = false) String host){
        if (StringUtils.isEmpty(host)){
            ConstantPropertiesUtil.FILE_HOST = host;
        }
        String uploadUrl  = fileService.upload(file);
        return R.ok().message("文件上传成功").data("url", uploadUrl);
    }
}
