package com.atguigu.ggkt.vod.controller;

import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.CourseFormVo;
import com.atguigu.ggkt.vo.vod.CoursePublishVo;
import com.atguigu.ggkt.vo.vod.CourseQueryVo;
import com.atguigu.ggkt.vod.entity.Course;
import com.atguigu.ggkt.vod.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@Api(tags = "点播管理")
@RequestMapping("/admin/vod/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @ApiOperation("点播课程列表获取")
    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable int page,
                             @PathVariable int limit,
                             CourseQueryVo courseQueryVo){
        Page<Course> page1 = new Page(page, limit);
        Map<String,Object>map =courseService.findPageCourse(page1,courseQueryVo);
        return Result.ok(map);
    }
    @ApiOperation("添加课程的基本信息")
    @PostMapping("/save")
    public Result save(@RequestBody CourseFormVo courseFormVo){
      Long courseId=  courseService.saveCourseInfo(courseFormVo);
     return Result.ok(courseId);
    }
    @ApiOperation("根据id查询课程")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id){
        CourseFormVo courseFormVo=courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);
    }
    @ApiOperation("修改课程信息")
    @PostMapping("/update")
    @Transactional(rollbackFor = Exception.class)
    public Result update(@RequestBody CourseFormVo courseFormVo){
       courseService.updateCourse(courseFormVo);
       return   Result.ok(courseFormVo.getId());
    }
    @ApiOperation("根据id查询课程的发布信息")
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Long id){
       CoursePublishVo coursePublishVo =courseService.getPublishVo(id);
       return Result.ok(coursePublishVo);
    }
    @ApiOperation("课程发布确认")
    @PutMapping("/publishCourse/{id}")
    public Result publishCourse(@PathVariable Long id){
        courseService.publishCourse(id);
        return Result.ok(null);
    }
    @ApiOperation(value = "删除课程")
    @DeleteMapping("remove/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result remove(@PathVariable Long id) {
        courseService.removeCourseById(id);
        return Result.ok(null);
    }


}
