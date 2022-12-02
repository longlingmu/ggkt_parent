package com.atguigu.ggkt.vod.controller;
import com.atguigu.ggkt.model.vod.Subject;
import com.atguigu.ggkt.result.Result;
import com.atguigu.ggkt.vod.service.impl.SubjectServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(tags = "课表管理系统")
@RestController
@RequestMapping("/admin/vod/subject")
@CrossOrigin
public class SubjectController {
    @Autowired
    private SubjectServiceImpl subjectService;
    //课表分类管理
    @ApiOperation("课程分类列表")
    @GetMapping("/getChildSubject/{id}")
    @CrossOrigin
    public Result getChildSubject(@PathVariable Long id){

        List<Subject> subjects = subjectService.selectSubjectList(id);
        return Result.ok(subjects);
    }
    //课程分类导出功能
    @ApiOperation("课程分类导出")
    @GetMapping("/exportData")
    @CrossOrigin
    public void exportData(HttpServletResponse response){
        subjectService.exportData(response);

    }
    @ApiOperation("课程导入")
    @PostMapping("/importData")
    @CrossOrigin
    public Result importData(MultipartFile upLoadFile ){//上传的文件的对象
        subjectService.importData(upLoadFile);
       return Result.ok(null);
    }

}
