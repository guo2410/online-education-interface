package com.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.Vo.CourseInfoForm;
import com.guli.edu.Vo.CoursePublishVo;
import com.guli.edu.Vo.CourseQuery;
import com.guli.edu.Vo.CourseWebVo;
import com.guli.edu.entity.Course;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface CourseService extends IService<Course> {

    void pageQuery(Page<Course> pageParam, CourseQuery courseQuery);

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoFormById(String id);

    void updateCourseInfoById(CourseInfoForm courseInfoForm);

    boolean removeCourseById(String id);

    CoursePublishVo getCoursePublishInfoById(String id);

    Boolean publishCourseById(String id);

    List<Course> selectByTeacherId(String id);

    Map<String, Object> pageListWeb(Page<Course> pageParam);

    CourseWebVo selectInfoWebById(String courseId);

    void updatePageViewCount(String id);
}
