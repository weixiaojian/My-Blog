package com.my.blog.website.interceptor;

import cn.hutool.http.HttpException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.my.blog.website.pojo.dto.AddressDTO;
import com.my.blog.website.pojo.entity.VisitStatistics;
import com.my.blog.website.service.VisitStatisticsService;
import com.my.blog.website.utils.IPKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author imyzt
 * @date 2019/08/28
 * @description 访问统计信息
 */
@Component
public class VisitStatisticsInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(BaseInterceptor.class);
    private final VisitStatisticsService visitStatisticsService;

    private static final Cache<String,List<VisitStatistics>> CACHE = CacheBuilder.newBuilder()
            .maximumSize(2000)
            .build();

    public VisitStatisticsInterceptor(VisitStatisticsService visitStatisticsService) {
        this.visitStatisticsService = visitStatisticsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ip = IPKit.getIpAddrByRequest(httpServletRequest);
        log.info("用户访问地址: {}, 来路地址: {}", httpServletRequest.getRequestURI(), ip);
        httpServletRequest.setAttribute("_ip_", ip);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

        if (httpServletRequest.getRequestURI().contains("admin")) {
            return;
        }

        String ip = (String) httpServletRequest.getAttribute("_ip_");
        String utmSource = httpServletRequest.getParameter("utm_source");
        String referer = httpServletRequest.getHeader("Referer");
        System.out.println(referer);

        List<VisitStatistics> ipVisitList = CACHE.getIfPresent(ip);

        if (CollectionUtils.isEmpty(ipVisitList)) {
            // 第一次访问, 直接加入list 缓存中
            ipVisitList = new ArrayList<>();
            add(ip, utmSource, ipVisitList);
        } else {

            add(ip, utmSource, ipVisitList);

            if (ipVisitList.size() >= 10) {
                // 大于10, 清空一次
                CACHE.invalidate(ip);

                // 获取ip位置信息
                AddressDTO ipAddressInfo = null;
                try {
                    ipAddressInfo = IPKit.getIpAddressInfo(ip);
                } catch (HttpException ex) {
                    log.error("获取ip超时", ex);
                } finally {

                    if (Objects.nonNull(ipAddressInfo)) {
                        AddressDTO finalIpAddressInfo = ipAddressInfo;
                        ipVisitList.forEach(v -> BeanUtils.copyProperties(finalIpAddressInfo, v));
                    }

                    // 保存进去
                    visitStatisticsService.insertBatch(ipVisitList);
                }

            }
        }
    }

    private void add(String ip, String utmSource, List<VisitStatistics> ipVisitList) {
        VisitStatistics visitStatistics = new VisitStatistics();
        visitStatistics.setIp(ip).setCreatedAt(new Date()).setUtmSource(utmSource);
        ipVisitList.add(visitStatistics);
        CACHE.put(ip, ipVisitList);
    }

}
