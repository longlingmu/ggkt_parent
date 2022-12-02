package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.entity.CourseDescription;
import com.atguigu.ggkt.vod.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ggkt.vod.entity.Course;
import com.atguigu.ggkt.vod.mapper.CourseMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author FIREBAT
* @description 针对表【course(课程)】的数据库操作Service实现
* @createDate 2022-11-28 18:50:58
*/
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course>
    implements CourseService{
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private CourseDescriptionService courseDescriptionService;
    @Autowired
    private VideoService videoService;
    @Autowired
    private ChapterService chapterService;

    @Override//根据course中id值封装id
    public void getNameById(Course course) {
        //根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(course.getTeacherId());
        if(teacher!=null) {
            String name = teacher.getName();
            course.getParam().put("teacherName",name);
        }
        Subject subjectOne = subjectService.getById(course.getSubjectParentId());
        if(subjectOne != null) {
            course.getParam().put("subjectParentTitle",subjectOne.getTitle());
        }
        Subject subjectTwo = subjectService.getById(course.getSubjectId());
        if(subjectTwo != null) {
            course.getParam().put("subjectTitle",subjectTwo.getTitle());
        }

    }

    @Override//添加course
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //添加入course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.insert(course);
        //添加入courseDescription表
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);
        return  course.getId();
    }

    @Override
    public CourseFormVo getCourseInfoById(Long id) {
        //课程基本信息
        Course course = baseMapper.selectById(id);
        //课程描述信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);
        //数据封装
        if(course==null){
            return null;
        }
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course,courseFormVo);
        courseFormVo.setDescription(courseDescription.getDescription());
        return courseFormVo;
    }

    @Override
    public void updateCourse(CourseFormVo courseFormVo) {
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo,course);
        baseMapper.updateById(course);
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescription.setCourseId(course.getId());
        courseDescriptionService.updateById(courseDescription);

    }
    @ApiOperation("通过课程id查询课程发布信息")
    @Override
    public CoursePublishVo getPublishVo(Long id) {
        CoursePublishVo coursePublishVo = baseMapper.selectCoursePublishVoById(id);
        return coursePublishVo;
    }

    @Override//最终发布
    public void publishCourse(Long id) {
        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);
    }

    //删除课程
    @Override
    public void removeCourseById(Long id) {
        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);
        //根据课程id删除章节
        chapterService.removeChapterByCourseId(id);
        //根据课程id删除描述
        courseDescriptionService.removeById(id);
        //根据课程id删除课程
        baseMapper.deleteById(id);
    }
    @Override//分页条件查询course
    public Map<String, Object> findPageCourse(Page<Course> page1, CourseQueryVo courseQueryVo) {
        LambdaQueryWrapper<Course> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(courseQueryVo.getSubjectId()!=null,Course::getSubjectId,courseQueryVo.getSubjectId())
                .eq(courseQueryVo.getSubjectParentId()!=null,Course::getSubjectParentId,courseQueryVo.getSubjectParentId())
                .eq(courseQueryVo.getTeacherId()!=null,Course::getTeacherId,courseQueryVo.getTeacherId())
                .like(StringUtils.isNotBlank(courseQueryVo.getTitle()),Course::getTitle,courseQueryVo.getTitle());
        Page<Course> coursePages = baseMapper.selectPage(page1, queryWrapper);
        List<Course> records = coursePages.getRecords();//获取集合部分
        //对获取的数据进行进一步的封装
        records.forEach(item->{
           this.getNameById(item) ;

        });

        long totalCount = coursePages.getTotal();//总记录数
        long totalPage = coursePages.getPages();//总页数
        Map<String,Object>map=new HashMap<>();
        map.put("totalCount",totalCount);
        map.put("totalPage",totalPage);
        map.put("records",records);
        return map;

    }
}




