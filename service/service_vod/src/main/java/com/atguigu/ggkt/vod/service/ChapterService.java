package com.atguigu.ggkt.vod.service;

import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vod.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author FIREBAT
* @description 针对表【chapter(课程)】的数据库操作Service
* @createDate 2022-11-28 18:50:53
*/
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getTreeList(Long courseId);

    void removeChapterByCourseId(Long id);
}
