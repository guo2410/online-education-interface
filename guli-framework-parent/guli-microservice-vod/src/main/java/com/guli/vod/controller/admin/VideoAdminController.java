package com.guli.vod.controller.admin;

import com.guli.common.vo.R;
import com.guli.vod.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 9:45
 */
@RestController
@CrossOrigin
@Api(description = "阿里云视频点播微服务")
@RequestMapping("/admin/vod/video")
@Slf4j
public class VideoAdminController {

    @Autowired
    VideoService videoService;

    @DeleteMapping("delete-batch")
    public R removeVideoList(
            @ApiParam(name = "videoIdList", value = "云端视频id", required = true)
            @RequestParam("videoIdList") List videoIdList){
        videoService.removeVideoList(videoIdList);
        return R.ok().message("视频删除成功");
    }

    @DeleteMapping("{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
        @PathVariable("videoId") String videoId){
        log.debug("开始删除视频");
        videoService.removeVideo(videoId);
        return R.ok().message("视频删除成功");
    }

    @PostMapping("upload")
    public R uploadVideo(
            @ApiParam(name = "file", value = "文件", required = true)
            @RequestParam("file") MultipartFile file) {
        String videoId = videoService.uploadVideo(file);
        return R.ok().message("视频上传成功").data("videoId", videoId);
    }
}
