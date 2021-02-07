package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.exception.GuliException;
import com.guli.edu.Vo.ChapterVo;
import com.guli.edu.Vo.VideoVo;
import com.guli.edu.entity.Chapter;
import com.guli.edu.entity.Video;
import com.guli.edu.mapper.ChapterMapper;
import com.guli.edu.service.ChapterService;
import com.guli.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    VideoService videoService;

    @Override
    public void deleteChapterByCourseId(String id) {
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        //创建封装对象
        ArrayList<ChapterVo> chapterVoArrayList = new ArrayList<>();
        ////获取章节信息
        QueryWrapper<Chapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        queryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapters = baseMapper.selectList(queryWrapper);
        //获取小节信息
        QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("course_id", courseId);
        queryWrapper1.orderByAsc("sort", "id");
        List<Video> videos = videoService.list(queryWrapper1);
        //遍历chapters放入chapterVoArrayList中
        for (int i = 0; i < chapters.size(); i++) {
            Chapter chapter = chapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoArrayList.add(chapterVo);

            //填充视频vo数据
            ArrayList<VideoVo> videoVoArrayList = new ArrayList<>();
            for (int j = 0; j < videos.size(); j++) {
                Video video = videos.get(j);
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVoArrayList.add(videoVo);
                }
            }
            chapterVo.setChildren(videoVoArrayList);
        }
        return chapterVoArrayList;
    }

    @Override
    public boolean removeChapterById(String id) {

        if (videoService.getCountByChapterId(id)) {
            throw new GuliException(20001, "该分章节下存在视频课程，请先删除视频课程");
        }

        int deleteById = baseMapper.deleteById(id);
        return deleteById > 0;
    }
}
