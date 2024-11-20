package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.model.dto
 * @className com.xuecheng.content.model.dto.TeachPlanDto
 * @date 2024/11/19 22:08
 * @description 课程教学计划DTO
 */
@Data
@ToString
public class TeachPlanNodeTreeDto extends Teachplan {

    private TeachplanMedia teachplanMedia;
    private List<TeachPlanNodeTreeDto> teachPlanTreeNodes;
}
