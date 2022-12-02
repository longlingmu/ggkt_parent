package com.atguigu.ggkt.vod.controller;


import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vo.vod.TeacherQueryVo;
import com.atguigu.ggkt.model.vod.Teacher;
import com.atguigu.ggkt.vod.exception.GgktException;
import com.atguigu.ggkt.vod.service.TeacherService;
import com.atguigu.ggkt.vod.service.impl.TeacherServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "讲师管理系统")
@RestController
@RequestMapping("/admin/vod/teacher")
@CrossOrigin
public class TeacherController {
    @Autowired
    private TeacherServiceImpl teacherService;
    //http://localhost:8301/admin/vod/teacher/findAll
    //查看所有讲师
    @ApiOperation("查询所有讲师")
@GetMapping("/findAll")
    public Result<List<Teacher>> findAll() {
        try {
            int i=1/2;
        }catch (Exception e){
        throw new GgktException(201,"执行了自定义异常处理");
        }
        List<Teacher> list = teacherService.list();

    return Result.ok(list);
}
//删除指定id讲师
    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
     public Result removeById(@ApiParam("指定讲师Id")@PathVariable("id") Long id){
        boolean b = teacherService.removeById(id);
        if(b==true){
            return Result.ok(null).message("查询所有讲师成功");
        }
       return Result.fail(null);

    }
    @ApiOperation("条件查询分页")
    @PostMapping("/findQueryPage/{current}/{limit}")
    public Result findQueryPage(@PathVariable int current, @PathVariable int limit, @RequestBody(required = false) TeacherQueryVo teacherQueryVo){
        //创建分页对象
        Page<Teacher> pageParam=new Page<>(current,limit);
        //判断teacherQueryVo为空就查询全部
        if (teacherQueryVo==null){
            Page<Teacher> page = teacherService.page(pageParam, null);
            return  Result.ok(page);

        }
        //获取条件值进行非空判断再封装
        LambdaQueryWrapper<Teacher> queryWrapper = new LambdaQueryWrapper<>();
        QueryWrapper<Teacher> queryWrapper1=new QueryWrapper<>();
        queryWrapper
                .like(!StringUtils.isBlank(teacherQueryVo.getName()),Teacher::getName,teacherQueryVo.getName())
                .eq(teacherQueryVo.getLevel()!=null,Teacher::getLevel,teacherQueryVo.getLevel())
                .ge(teacherQueryVo.getJoinDateBegin()!=null,Teacher::getCreateTime,teacherQueryVo.getJoinDateBegin())
                .le(teacherQueryVo.getJoinDateEnd()!=null,Teacher::getCreateTime,teacherQueryVo.getJoinDateEnd());
//调用分页查询

       IPage<Teacher> page = teacherService.page(pageParam, queryWrapper);
       return Result.ok(page);



    }
    @ApiOperation("添加讲师")
    @PostMapping("/saveTeacher")
    public Result addTeacher(@RequestBody(required = false) Teacher teacher){

        if(teacher==null){
            return Result.ok(null).message("你还没输入数据哦");
        }
        teacher.setCreateTime(null);
        boolean save = teacherService.save(teacher);
        if(save){
            return Result.ok(null).message("添加讲师数据成功");
        }
       return Result.fail(null).message("数据不合规范，添加失败");

    }
    @ApiOperation("修改讲师")
    @PutMapping("/updateTeacher")
    public Result updateTeacher(@RequestBody(required = false)Teacher teacher)
    {
        boolean update = teacherService.updateById(teacher);

        if(update==true){
            return Result.ok(null).message("查询所有讲师成功");
        }
        return Result.fail(null);
    }
    @ApiOperation("批量删除讲师")
    @DeleteMapping("/removeBatch")
    public Result removeBatchTeacher(@ApiParam("传入删除的id集合") @RequestBody List<Long> idList){
        boolean b = teacherService.removeByIds(idList);

        if(b==true){
            return Result.ok(null).message("删除所有讲师成功");
        }
        return Result.fail(null);


    }

}



