package com.atguigu.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ggkt.vod.entity.Video;
import com.atguigu.ggkt.vod.service.VideoService;
import com.atguigu.ggkt.vod.mapper.VideoMapper;
import org.springframework.stereotype.Service;

/**
* @author FIREBAT
* @description 针对表【video(课程视频)】的数据库操作Service实现
* @createDate 2022-11-28 18:51:29
*/
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video>
    implements VideoService{

    @Override
    public void removeVideoByCourseId(Long id) {
        LambdaQueryWrapper<Video>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Video::getCourseId,id);
        baseMapper.delete(queryWrapper);
    }
}




