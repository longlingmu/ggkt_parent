package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.entity.Course;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author FIREBAT
* @description 针对表【course(课程)】的数据库操作Service
* @createDate 2022-11-28 18:50:58
*/
public interface CourseService extends IService<Course> {

    Map<String, Object> findPageCourse(Page<Course> page1, CourseQueryVo courseQueryVo);

    void getNameById(Course item);

    Long saveCourseInfo(CourseFormVo courseFormVo);

    CourseFormVo getCourseInfoById(Long id);

    void updateCourse(CourseFormVo courseFormVo);

    CoursePublishVo getPublishVo(Long id);

    void publishCourse(Long id);

    void removeCourseById(Long id);
}
