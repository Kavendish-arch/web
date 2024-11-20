package com.xuecheng.content.api;

import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanNodeTreeDto;
import com.xuecheng.content.model.vo.EditTeachPlanVo;
import com.xuecheng.content.service.TeachplanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.api
 * @className com.xuecheng.content.api.TeachPlanCourseController
 * @date 2024/11/19 21:45
 * @description 课程查询计划
 */
@Api("课程计划操作接口")
@RestController
public class TeachPlanCourseController {

    @Autowired
    TeachplanService teachplanService;

    @ApiOperation("查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachPlanNodeTreeDto> getTeachplanTree(@PathVariable Long courseId) {

        return teachplanService.findTeachplanTree(courseId);
    }

//    @ApiOperation("课程计划创建或修改")
//    @PostMapping("/teachplan")
//    public void addTeachplan(@RequestBody SaveTeachplanDto dto) {
//        teachplanService.saveTeachplan(dto);
//    }
//
//    @ApiOperation("课程删除")
//    @DeleteMapping("/teachplan/{id}")
//    public void deleteTeachplan(@PathVariable Long id) {
//        teachplanService.deleteTeachplan(id);
//    }

    @ApiOperation("课程信息修改")
    @PostMapping("/teachplan")
    public void updateCourseBase(@RequestBody EditTeachPlanVo editTeachPlanVo) {
        EditTeachPlanDto editTeachPlanDto = editTeachPlanVo;
        teachplanService.addUpdateSaveCourseBase(editTeachPlanDto);
    }

}
