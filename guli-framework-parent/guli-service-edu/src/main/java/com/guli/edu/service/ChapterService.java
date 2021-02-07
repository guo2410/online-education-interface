package com.guli.edu.service;

import com.guli.edu.Vo.ChapterVo;
import com.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface ChapterService extends IService<Chapter> {

    void deleteChapterByCourseId(String id);

    List<ChapterVo> nestedList(String courseId);

    boolean removeChapterById(String id);
}
