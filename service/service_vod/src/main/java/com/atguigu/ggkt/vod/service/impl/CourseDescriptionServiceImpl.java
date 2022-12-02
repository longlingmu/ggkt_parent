package com.atguigu.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ggkt.vod.entity.CourseDescription;
import com.atguigu.ggkt.vod.service.CourseDescriptionService;
import com.atguigu.ggkt.vod.mapper.CourseDescriptionMapper;
import org.springframework.stereotype.Service;

/**
* @author FIREBAT
* @description 针对表【course_description(课程简介)】的数据库操作Service实现
* @createDate 2022-11-28 18:51:05
*/
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription>
    implements CourseDescriptionService{

}




