package com.atguigu.ggkt.vod.service.impl;

import com.atguigu.ggkt.vo.vod.ChapterVo;
import com.atguigu.ggkt.vo.vod.VideoVo;
import com.atguigu.ggkt.vod.entity.Course;
import com.atguigu.ggkt.vod.entity.Video;
import com.atguigu.ggkt.vod.service.CourseService;
import com.atguigu.ggkt.vod.service.VideoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.ggkt.vod.entity.Chapter;
import com.atguigu.ggkt.vod.service.ChapterService;
import com.atguigu.ggkt.vod.mapper.ChapterMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author FIREBAT
* @description 针对表【chapter(课程)】的数据库操作Service实现
* @createDate 2022-11-28 18:50:53
*/
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter>
    implements ChapterService{
    @Autowired
    private VideoService videoService;

    @Override
    public void removeChapterByCourseId(Long id) {
        LambdaQueryWrapper<Chapter>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getCourseId,id);
        baseMapper.delete(queryWrapper);
    }

    @Override
    public List<ChapterVo> getTreeList(Long courseId) {
        //定义一个最终返回数据
        List<ChapterVo>chapterVos=new ArrayList<>();
       //根据课程id获取所有的章节
        LambdaQueryWrapper<Chapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Chapter::getCourseId, courseId);
        List<Chapter> chapters = baseMapper.selectList(queryWrapper);
        //获取课程中的所有小节
        //对chapter进行封装并存入列表中
        chapters.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            LambdaQueryWrapper<Video> videoLambdaQueryWrapper = new LambdaQueryWrapper<Video>();
            //查询video数组并封装成videoVo数组
            videoLambdaQueryWrapper.eq(Video::getChapterId,chapter.getId());
            List<Video> list = videoService.list(videoLambdaQueryWrapper);
            List<VideoVo> videoVoList=new ArrayList<>();
            list.forEach(video -> {
                VideoVo videoVo = new VideoVo();
                BeanUtils.copyProperties(video,videoVo);
              videoVoList.add(videoVo);
            });
            chapterVo.setChildren(videoVoList);
            chapterVos.add(chapterVo);
        });
        return chapterVos;
    }
}




