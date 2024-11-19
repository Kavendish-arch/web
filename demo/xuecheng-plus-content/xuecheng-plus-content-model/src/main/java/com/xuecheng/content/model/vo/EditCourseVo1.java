package com.xuecheng.content.model.vo;

import com.xuecheng.base.valid.ValidationGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.model.vo
 * @className com.xuecheng.content.model.vo.EditCourseVo1
 * @date 2024/11/19 21:05
 * @description 修改课程的接收数据
 */
@Data
@ApiModel(value = "EditCourseVo", description = "修改课程基本信息")
public class EditCourseVo1 extends AddCourseVo1{
    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
