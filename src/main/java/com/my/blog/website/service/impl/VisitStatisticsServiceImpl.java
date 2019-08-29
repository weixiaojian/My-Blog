package com.my.blog.website.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.my.blog.website.dao.mapper.VisitStatisticsMapper;
import com.my.blog.website.pojo.entity.VisitStatistics;
import com.my.blog.website.service.VisitStatisticsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 访问统计表 服务实现类
 * </p>
 *
 * @author imyzt
 * @date 2019-08-28
 */
@Service
public class VisitStatisticsServiceImpl extends ServiceImpl<VisitStatisticsMapper, VisitStatistics> implements VisitStatisticsService {

}
