package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.mapper.CourseDescriptionMapper;
import com.guli.edu.service.CourseDescriptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

    @Override
    public void deleteCourseDescriptionByCourseId(String id) {
        baseMapper.deleteById(id);
    }
}
