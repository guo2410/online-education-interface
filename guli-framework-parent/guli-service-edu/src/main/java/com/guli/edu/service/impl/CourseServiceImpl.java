package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.exception.GuliException;
import com.guli.edu.Vo.CourseInfoForm;
import com.guli.edu.Vo.CoursePublishVo;
import com.guli.edu.Vo.CourseQuery;
import com.guli.edu.Vo.CourseWebVo;
import com.guli.edu.entity.Course;
import com.guli.edu.entity.CourseDescription;
import com.guli.edu.mapper.CourseMapper;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.CourseDescriptionService;
import com.guli.edu.service.CourseService;
import com.guli.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
@Transactional
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseDescriptionService courseDescriptionService;

    @Autowired
    VideoService videoService;

    @Autowired
    ChapterService chapterService;

    @Override
    public void pageQuery(Page<Course> pageParam, CourseQuery courseQuery) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_create");
        if (courseQuery == null) {
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        String title = courseQuery.getTitle();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("title", title);
        }

        if (!StringUtils.isEmpty(teacherId)) {
            queryWrapper.eq("teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.ge("subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.ge("subject_id", subjectId);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {

        Course course = new Course();
        course.setStatus(Course.COURSE_DRAFT);
        BeanUtils.copyProperties(courseInfoForm, course);
        int insert = baseMapper.insert(course);

        if (insert <= 0) {
            throw new GuliException(20001, "课程信息保存失败");
        }

        String courseId = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(courseId);
        courseDescription.setDescription(courseInfoForm.getDescription());
        boolean save = courseDescriptionService.save(courseDescription);

        if (!save) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }
        return courseId;

    }

    @Override
    public CourseInfoForm getCourseInfoFormById(String id) {

        Course course = this.getById(id);
        if (course == null) {
            throw new GuliException(20001, "数据不存在");
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);

        CourseDescription courseDescription = courseDescriptionService.getById(id);

        if (courseDescription != null) {
            courseInfoForm.setDescription(courseDescription.getDescription());
        }
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {

        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);

        boolean resultCourseInfo = this.updateById(course);
        if (!resultCourseInfo) {
            throw new GuliException(20001, "课程信息保存失败");
        }

        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        boolean updateById = courseDescriptionService.updateById(courseDescription);
        if (!updateById) {
            throw new GuliException(20001, "课程详情信息保存失败");
        }
    }

    @Override
    public boolean removeCourseById(String id) {
        //删除video记录
        videoService.deleteVideoByCourseId(id);
        // 删除chapter记录
        chapterService.deleteChapterByCourseId(id);
        // 删除Course记录
        courseDescriptionService.deleteCourseDescriptionByCourseId(id);
        //删除courseDescription记录
        int res = baseMapper.deleteById(id);
        return res>0;
    }

    @Override
    public CoursePublishVo getCoursePublishInfoById(String id) {
        return baseMapper.getCoursePublicById(id);
    }

    @Override
    public Boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        int updateById = baseMapper.updateById(course);
        return updateById > 0;
    }

    @Override
    public List<Course> selectByTeacherId(String id) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("teacher_id",id);
        queryWrapper.orderByDesc("gmt_modified");
        List<Course> courses = baseMapper.selectList(queryWrapper);
        return courses;
    }

    @Override
    public Map<String, Object> pageListWeb(Page<Course> pageParam) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("gmt_modified");
        baseMapper.selectPage(pageParam, queryWrapper);
        List<Course> records = pageParam.getRecords();
        long current = pageParam.getCurrent();
        long pages = pageParam.getPages();
        long size = pageParam.getSize();
        long total = pageParam.getTotal();
        boolean hasNext = pageParam.hasNext();
        boolean hasPrevious = pageParam.hasPrevious();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    @Override
    public CourseWebVo selectInfoWebById(String courseId) {
        this.updatePageViewCount(courseId);
        return baseMapper.selectInfoWebById(courseId);
    }

    @Override
    public void updatePageViewCount(String id) {
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount()+1);
        baseMapper.updateById(course);
    }
}
