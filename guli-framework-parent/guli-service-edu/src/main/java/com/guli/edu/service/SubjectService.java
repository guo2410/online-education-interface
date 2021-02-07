package com.guli.edu.service;

import com.guli.edu.Vo.SubjectNestedVo;
import com.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
public interface SubjectService extends IService<Subject> {

    List<String> batchImport(MultipartFile file);

    List<SubjectNestedVo> nestedList();

    Boolean deleteSubjectById(String id);

    Boolean saveLevelOne(Subject subject);

    Boolean saveLevelTwo(Subject subject);
}
