package org.fleet.modules.demo.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.fleet.modules.demo.test.entity.JoaDemo;
import org.fleet.modules.demo.test.mapper.JoaDemoMapper;
import org.fleet.modules.demo.test.service.IJoaDemoService;
import org.springframework.stereotype.Service;

/**
 * @Description: 流程测试
 * @Author: fleet-team
 * @Date: 2019-05-14
 * @Version: V1.0
 */
@Service
public class JoaDemoServiceImpl extends ServiceImpl<JoaDemoMapper, JoaDemo> implements IJoaDemoService {

}
