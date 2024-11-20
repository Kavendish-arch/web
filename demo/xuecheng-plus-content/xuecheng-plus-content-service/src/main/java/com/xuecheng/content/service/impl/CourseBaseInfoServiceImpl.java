package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description TODO
 * @date 2023/2/12 10:16
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto courseParamsDto) {

        //拼装查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //根据名称模糊查询,在sql中拼接 course_base.name like '%值%'
        queryWrapper.like(StringUtils.isNotEmpty(courseParamsDto.getCourseName()), CourseBase::getName, courseParamsDto.getCourseName());
        //根据课程审核状态查询 course_base.audit_status = ?
        queryWrapper.eq(StringUtils.isNotEmpty(courseParamsDto.getAuditStatus()), CourseBase::getAuditStatus, courseParamsDto.getAuditStatus());
        //todo:按课程发布状态查询

        //创建page分页参数对象，参数：当前页码，每页记录数
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        //开始进行分页查询
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        //数据列表
        List<CourseBase> items = pageResult.getRecords();
        //总记录数
        long total = pageResult.getTotal();

        //List<T> items, long counts, long page, long pageSize
        PageResult<CourseBase> courseBasePageResult = new PageResult<CourseBase>(items, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        // 1. 参数的合法性校验
//        if (StringUtils.isBlank(dto.getName())) {
////            throw new RuntimeException("课程名称为空");
//            XueChengPlusException.cast("课程名称为空");
//        }
//
//        if (StringUtils.isBlank(dto.getMt())) {
////            throw new RuntimeException("课程分类为空");
//            XueChengPlusException.cast("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getSt())) {
////            throw new RuntimeException("课程分类为空");
//            XueChengPlusException.cast("课程分类为空");
//        }
//
//        if (StringUtils.isBlank(dto.getGrade())) {
////            throw new RuntimeException("课程等级为空");
//            XueChengPlusException.cast("课程等级为空");
//        }
//
//        if (StringUtils.isBlank(dto.getTeachmode())) {
////            throw new RuntimeException("教育模式为空");
//            XueChengPlusException.cast("教育模式为空");
//        }
//
//        if (StringUtils.isBlank(dto.getUsers())) {
////            throw new RuntimeException("适应人群为空");
//            XueChengPlusException.cast("适应人群为空");
//        }
//
//        if (StringUtils.isBlank(dto.getCharge())) {
////            throw new RuntimeException("收费规则为空");
//            XueChengPlusException.cast("收费规则为空");
//        }


        //2. 向课程基本信息表course_base写入数据
        CourseBase courseBaseNew = new CourseBase();
        /**
         * https://blog.csdn.net/u014374173/article/details/121117186
         */
        // 将传入的页面的参数放到courseBaseNew对象
//        courseBaseNew.setName(dto.getName());
//        courseBaseNew.setDescription(dto.getDescription());

        //上边的从原始对象中get拿数据向新对象set，比较复杂
        BeanUtils.copyProperties(dto, courseBaseNew); //只要属性名称一致就可以拷贝

        // 关键属性
        courseBaseNew.setCompanyId(companyId);
        // 创建时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //审核状态默认为未提交
        // # 代码查数据字典
        courseBaseNew.setAuditStatus("202002");
        //发布状态为未发布
        courseBaseNew.setStatus("203001");

        //3. 插入数据库
        int insert = courseBaseMapper.insert(courseBaseNew);
        if (insert <= 0) {
            throw new RuntimeException("添加课程失败");
        }

        //4. 向课程营销系courese_market写入数据
        CourseMarket courseMarketNew = new CourseMarket();

        //将页面输入的数据拷贝到courseMarketNew
        BeanUtils.copyProperties(dto, courseMarketNew);

        //获取课程的id
        Long courseId = courseBaseNew.getId();
        courseMarketNew.setId(courseId);

        //保存营销信息
        saveCourseMarket(courseMarketNew);
        //从数据库查询课程的详细信息，包括两部分
        CourseBaseInfoDto courseBaseInfo = getCourseBaseInfoById(courseId);

        return courseBaseInfo;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto editCourseDto) {
        // 3. 查询
        Long id = editCourseDto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(id);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }
        // 查询到课程
        // 1. 数据合法性校验
        // 2. 具体业务校验
        if (!companyId.equals(courseBase.getCompanyId())) {
            XueChengPlusException.cast("只能修改本机构的课程");
        }

        // 封装数据
        // 4. 更新课程信息
        BeanUtils.copyProperties(editCourseDto, courseBase);
        // 更新时间 等
        courseBase.setChangeDate(LocalDateTime.now());

//        String mt = editCourseDto.getMt();
//        String st = editCourseDto.getSt();

//        LambdaQueryWrapper<CourseCategory> queryWrapper = new LambdaQueryWrapper<>();
////        queryWrapper = queryWrapper.eq(CourseCategory::getName, mt);
//        CourseCategory courseCategory = courseCategoryMapper.selectOne(queryWrapper.eq(CourseCategory::getName, mt));
//
//        if (courseCategory == null) {
//            XueChengPlusException.cast("请选择正确的一级分类");
//        }
//
//        CourseCategory courseCategory1 = courseCategoryMapper.selectOne(queryWrapper.eq(CourseCategory::getLabel, st));
//        if (courseCategory1 == null) {
//            XueChengPlusException.cast("请选择正确的二级分类");
//        }
//        courseBase.setMt(courseCategory.getId());
//        courseBase.setSt(courseCategory1.getId());
        // 更新时间 等

        int i = courseBaseMapper.updateById(courseBase);
        if (i <= 0) {
            XueChengPlusException.cast("修改课程失败");
        }


        return getCourseBaseInfoById(id);
    }

    /**
     * 查询课程信息
     *
     * @param courseId
     * @return
     */
    @Transactional
    public CourseBaseInfoDto getCourseBaseInfoById(long courseId) {

        //从课程基本信息表查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        //组装在一起
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);

        //从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if (courseMarket != null) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        //通过courseCategoryMapper查询分类信息，将分类名称放在courseBaseInfoDto对象
        //todo：课程分类的名称设置到courseBaseInfoDto
        CourseCategory courseCategoryMt = courseCategoryMapper.selectById(courseBase.getMt());
        CourseCategory courseCategorySt = courseCategoryMapper.selectById(courseBase.getSt());

        courseBaseInfoDto.setMt(courseCategoryMt.getName());
        courseBaseInfoDto.setSt(courseCategorySt.getName());

        return courseBaseInfoDto;

    }

    /**
     * 单独写一个方法保存营销信息，逻辑：存在则更新，不存在则添加
     *
     * @param courseMarketNew
     * @return
     */
    private int saveCourseMarket(CourseMarket courseMarketNew) {

        //1. 参数的合法性校验
        String charge = courseMarketNew.getCharge();
        if (StringUtils.isEmpty(charge)) {
//            throw new RuntimeException("收费规则为空");
            XueChengPlusException.cast("收费规则为空");
        }
        // 如果课程收费，价格没有填写也需要抛出异常
        if (charge.equals("201001")) {
            // 浮点数比较
            double epsilon = 1e-6;
            if (courseMarketNew.getPrice() == null || courseMarketNew.getPrice().doubleValue() <= epsilon) {
//               throw new RuntimeException("课程的价格不能为空并且必须大于0");
                XueChengPlusException.cast("课程的价格不能为空并且必须大于0");
            }
        }

        //2. 从数据库查询营销信息,存在则更新，不存在则添加
        Long id = courseMarketNew.getId();//主键
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseMarket == null) {
            // 不存在 插入数据库
            return courseMarketMapper.insert(courseMarketNew);
        } else {
            // 将courseMarketNew拷贝到courseMarket
            BeanUtils.copyProperties(courseMarketNew, courseMarket);
            // 单独设置信息
            courseMarket.setId(courseMarketNew.getId());
            // 存在更新
            return courseMarketMapper.updateById(courseMarket);
        }


    }


}
