package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.content.mapper.TeachplanMapper;
import com.xuecheng.content.model.dto.EditTeachPlanDto;
import com.xuecheng.content.model.dto.TeachPlanNodeTreeDto;
import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.service.TeachplanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.service.impl
 * @className com.xuecheng.content.service.impl.TeachplanServiceImpl
 * @date 2024/11/20 13:59
 * @description @todo
 */

@Slf4j
@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Override
    public List<TeachPlanNodeTreeDto> findTeachplanTree(Long courseId) {

        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void addUpdateSaveCourseBase(EditTeachPlanDto editTeachPlanDto) {
        // 新增，保存，修改功能
        // todo 存在bug
        Long teachplanId = editTeachPlanDto.getId();
        if (teachplanId == null) {
            // 新增功能
            Teachplan teachplan = new Teachplan();
            BeanUtils.copyProperties(editTeachPlanDto, teachplan);
            // 创建时间
            teachplan.setCreateDate(LocalDateTime.now());
            // 确定排序顺序，
            Long parentId = editTeachPlanDto.getParentid();
            Long courseId = editTeachPlanDto.getCourseId();
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper = queryWrapper.eq(Teachplan::getCourseId, courseId).eq(Teachplan::getParentid, parentId);
            Integer count = teachplanMapper.selectCount(queryWrapper);
            teachplan.setOrderby(count + 1);

            int insert = teachplanMapper.insert(teachplan);
            if (insert <= 0) {
                throw new RuntimeException("添加课程失败");
            }

        } else {
            // 修改保存
            Teachplan teachplan = teachplanMapper.selectById(teachplanId);
            BeanUtils.copyProperties(editTeachPlanDto, teachplan);

            // 修改时间
            teachplan.setChangeDate(LocalDateTime.now());

            int update = teachplanMapper.updateById(teachplan);
            if (update <= 0) {
                throw new RuntimeException("修改课程失败");
            }
        }
    }
}
