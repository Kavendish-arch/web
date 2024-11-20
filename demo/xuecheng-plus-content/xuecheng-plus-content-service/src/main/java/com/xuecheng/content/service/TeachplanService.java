package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.EditTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanNodeTreeDto;

import java.util.List;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.service
 * @className com.xuecheng.content.service.TeachplanService
 * @date 2024/11/20 13:58
 * @description @todo
 */
public interface TeachplanService {

    List<TeachPlanNodeTreeDto> findTeachplanTree(Long courseId);

    void addUpdateSaveCourseBase(EditTeachPlanDto editTeachPlanDto);
}
