package com.guli.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author guoch0613@163.com
 * @Description
 * @create 2021-01-30 9:40
 */
public interface VideoService {
    String uploadVideo(MultipartFile file);

    void removeVideo(String videoId);

    void removeVideoList(List videoIdList);
}
