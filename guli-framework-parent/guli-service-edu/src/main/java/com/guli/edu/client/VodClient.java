package com.guli.edu.client;

import com.guli.common.vo.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 19:51
 */
@FeignClient("guli-vod")
@Component
public interface VodClient {

    @DeleteMapping(value = "/admin/vod/video/{videoId}")
    public R removeVideo(@PathVariable("videoId") String videoId);

    @DeleteMapping("/admin/vod/video/delete-batch")
    public R removeVideoList(@RequestParam("videoIdList") List videoIdList);
}
