package com.guli.edu.service;

import com.guli.edu.Vo.VideoInfoForm;
import com.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface VideoService extends IService<Video> {

    void deleteVideoByCourseId(String id);

    boolean getCountByChapterId(String id);

    void saveVideoInfo(VideoInfoForm videoInfoForm);

    VideoInfoForm getVideoInfoFormById(String id);

    void updateVideoInfoById(VideoInfoForm videoInfoForm);

    boolean removeVideoById(String id);
}
