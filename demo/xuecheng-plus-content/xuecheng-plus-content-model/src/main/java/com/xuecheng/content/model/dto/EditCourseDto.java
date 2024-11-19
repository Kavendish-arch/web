package com.xuecheng.content.model.dto;

import com.xuecheng.base.valid.ValidationGroup;
import com.xuecheng.content.model.vo.AddCourseVo1;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.model.dto
 * @className com.xuecheng.content.model.dto.EditCourseDto
 * @date 2024/11/19 21:07
 * @description 修改课程DTO
 */
@Data
public class EditCourseDto extends AddCourseDto {
    private Long id;
}
