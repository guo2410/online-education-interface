package com.guli.edu.service;

import com.guli.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程简介 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface CourseDescriptionService extends IService<CourseDescription> {

    void deleteCourseDescriptionByCourseId(String id);
}
