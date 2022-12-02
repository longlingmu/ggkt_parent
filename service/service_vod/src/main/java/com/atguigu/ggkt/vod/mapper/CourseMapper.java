package com.atguigu.ggkt.vod.mapper;

import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vod.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author FIREBAT
* @description 针对表【course(课程)】的数据库操作Mapper
* @createDate 2022-11-28 18:50:58
* @Entity com.atguigu.ggkt.vod.entity.Course
*/
public interface CourseMapper extends BaseMapper<Course> {
    //根据id获取课程发布信息
    CoursePublishVo selectCoursePublishVoById(Long id);

}




