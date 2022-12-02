package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.vod.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author FIREBAT
* @description 针对表【video(课程视频)】的数据库操作Service
* @createDate 2022-11-28 18:51:29
*/
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(Long id);
}
