package com.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.common.constants.ResultCodeEnum;
import com.guli.common.exception.GuliException;
import com.guli.edu.Vo.SubjectNestedVo;
import com.guli.edu.Vo.SubjectVo;
import com.guli.edu.entity.Subject;
import com.guli.edu.mapper.SubjectMapper;
import com.guli.edu.service.SubjectService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author guoch
 * @since 2021-01-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Transactional
    @Override
    public List<String> batchImport(MultipartFile file) {

        //接受错误信息
        List<String> msg = null;
        try {
            msg = new ArrayList<>();
            //使用XSSF创建Workbook
            XSSFWorkbook xssfSheets = new XSSFWorkbook(file.getInputStream());
            Sheet sheetAt = xssfSheets.getSheetAt(0);
            //获取最后一行的下标
            int lastRowNum = sheetAt.getLastRowNum();

            //从第二行开始遍历到最后一行
            for (int i = 1; i <= lastRowNum; i++) {
                Row row = sheetAt.getRow(i);
                if (row == null) {
                    String str = "该行没有数据";
                    msg.add(str);
                    continue;
                }
                //根据row获取第一列数据
                Cell cell = row.getCell(0);
                if (cell == null) {
                    String str = "该列没有数据";
                    msg.add(str);
                    continue;
                }
                String cellOneData = cell.getStringCellValue();
                //根据title查询数据库是否存在相同一级课程分类
                Subject byTitle = this.getByTitle(cellOneData);
                //获取一级标题id为实现二级标题
                String parent_id = null;
                if (byTitle == null) {
                    //创建Subject对象插入数据库
                    Subject subject = new Subject();
                    subject.setTitle(cellOneData);
                    subject.setParentId("0");
                    subject.setSort(0);
                    baseMapper.insert(subject);
                    parent_id = subject.getId();
                } else {
                    //数据库存在就取出id
                    parent_id = byTitle.getId();
                }

                //获取第二列的值
                Cell cellTwo = row.getCell(1);
                if (cellTwo == null) {
                    String str = "该列没有数据";
                    msg.add(str);
                    continue;
                }
                String cellTwoData = cellTwo.getStringCellValue();
                Subject subByTitle = this.getSubByTitle(cellTwoData, parent_id);
                if (subByTitle == null) {
                    Subject twosubject = new Subject();
                    twosubject.setTitle(cellTwoData);
                    twosubject.setParentId(parent_id);
                    twosubject.setSort(0);
                    baseMapper.insert(twosubject);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_ERROR);
        }
        return msg;
    }

    /**
     *
     * @return 数据一级分类中带有二级分类集合
     */
    @Override
    public List<SubjectNestedVo> nestedList() {
        //最终要的到的数据列表
        ArrayList<SubjectNestedVo> subjectNestedVoArrayList = new ArrayList<>();
        //获取一级分类数据记录
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subjects = baseMapper.selectList(queryWrapper);
        //获取二级分类数据记录
        QueryWrapper<Subject> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.ne("parent_id", 0);
        queryWrapper.orderByAsc("sort", "id");
        List<Subject> subSubjects = baseMapper.selectList(queryWrapper1);
        //填充一级分类vo数据
        int size = subjects.size();
        for (int i = 0; i < size; i++) {
            Subject subject = subjects.get(i);
            //创建一级类别vo对象
            SubjectNestedVo subjectNestedVo = new SubjectNestedVo();
            BeanUtils.copyProperties(subject, subjectNestedVo);
            subjectNestedVoArrayList.add(subjectNestedVo);
            //填充二级分类vo数据
            ArrayList<SubjectVo> subjectVoArrayList = new ArrayList<>();
            int size1 = subSubjects.size();
            for (int j = 0; j < size1; j++) {
                Subject subSubject = subSubjects.get(j);
                if (subject.getId().equals(subSubject.getParentId())) {
                    //创建二级类别vo对象
                    SubjectVo subjectVo = new SubjectVo();
                    BeanUtils.copyProperties(subSubject, subjectVo);
                    subjectVoArrayList.add(subjectVo);
                }
            }
            subjectNestedVo.setChildren(subjectVoArrayList);
        }
        return subjectNestedVoArrayList;
    }

    /**
     * 根据id删除节点
     * @param id
     * @return 删除成功
     */
    @Override
    public Boolean deleteSubjectById(String id) {
        //将参数id作为parent_id查询。如果有结果，说明有子节点
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count>0){
            return false;
        }else {
            int deleteById = baseMapper.deleteById(id);
            return deleteById>0;
        }
    }

    @Override
    public Boolean saveLevelOne(Subject subject) {

        Subject byTitle = this.getByTitle(subject.getTitle());
        if (byTitle == null){
            subject.setParentId("0");
            int insert = baseMapper.insert(subject);
            return insert>0;
        }else {
            return false;
        }
    }

    @Override
    public Boolean saveLevelTwo(Subject subject) {
        Subject subByTitle = this.getSubByTitle(subject.getTitle(), subject.getParentId());
        if (subByTitle == null){
            int insert = baseMapper.insert(subject);
            return insert>0;
        }else {
            return false;
        }
    }

    /**
     * 判断是否存在一级标题
     *
     * @param title 一级标题
     * @return Subject
     */
    private Subject getByTitle(String title) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");
        return baseMapper.selectOne(queryWrapper);
    }


    /**
     * 判断是否存在二级标题
     *
     * @param title    一级标题
     * @param parentId 一级标题id
     * @return Subject
     */
    private Subject getSubByTitle(String title, String parentId) {
        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return baseMapper.selectOne(queryWrapper);
    }
}
