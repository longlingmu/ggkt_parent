package com.atguigu.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.SubjectEeVo;
import com.atguigu.ggkt.vod.exception.GgktException;
import com.atguigu.ggkt.vod.lisener.SubjectListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.atguigu.ggkt.vod.service.SubjectService;
import com.atguigu.ggkt.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author FIREBAT
* @description 针对表【subject(课程科目)】的数据库操作Service实现
* @createDate 2022-11-27 18:49:15
*/
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject>
    implements SubjectService{
    @Autowired
    private SubjectListener subjectListener;

    @Override
    public void importData(MultipartFile file) {
        SubjectListener subjectListener1 = new SubjectListener();
        try {
            EasyExcel.read(file.getInputStream(),SubjectEeVo.class, this.subjectListener)
                    .sheet().doRead();
        } catch (IOException e) {
            throw new GgktException(20001,"导入文件失败");
        }
    }

    @Override
    public void exportData(HttpServletResponse response) {
        //设置下载类型
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");//防止中文乱码
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            //查询课程表里的所有数据
            List<Subject> subjects = baseMapper.selectList(null);
            List<SubjectEeVo>subjectEeVoList=new ArrayList<>();
            subjects.forEach(subject -> {
                SubjectEeVo subjectEeVo=new SubjectEeVo();
                BeanUtils.copyProperties(subject, subjectEeVo);
                subjectEeVoList.add(subjectEeVo);
            });
            //用EasyExcel进行写操作
            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class).sheet("课程分类").doWrite(subjectEeVoList);
        }catch (Exception e){
    throw new GgktException(20001,"导出失败");
        }

    }

    @Override
    public List<Subject> selectSubjectList(Long id) {
        LambdaQueryWrapper<Subject> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(Subject::getParentId,id);
        List<Subject> subjectList = baseMapper.selectList(queryWrapper);
        //判断是否有下一层数据，如果有则吧hasChildren=true
        subjectList.forEach(subject->{
            QueryWrapper<Subject> queryWrapper1=new QueryWrapper();
            queryWrapper1.eq("parent_id",subject.getId());
            Integer count = baseMapper.selectCount(queryWrapper1);
            subject.setHasChildren(count>0);
        });
      return subjectList;
    }
}




