package com.guli.edu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guli.edu.Vo.CoursePublishVo;
import com.guli.edu.Vo.CourseWebVo;
import com.guli.edu.entity.Course;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublicById(String id);

    CourseWebVo selectInfoWebById(String courseId);
}
