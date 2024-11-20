package com.xuecheng.content.mapper;

import com.xuecheng.content.model.dto.TeachPlanNodeTreeDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author chenyingtao
 * @version 1.0
 * @projectName demo
 * @package com.xuecheng.content.mapper
 * @className com.xuecheng.content.mapper.TeachplanMapperTests
 * @date 2024/11/19 22:15
 * @description TeachplanMapper 测试
 */
@SpringBootTest
public class TeachplanMapperTests {

    @Autowired
    public TeachplanMapper teachplanMapper;

    @Test
    public void testSelectTreeNodes() {
        List<TeachPlanNodeTreeDto> teachPlanDtos = teachplanMapper.selectTreeNodes(125L);

        System.out.println(teachPlanDtos);
    }
}
