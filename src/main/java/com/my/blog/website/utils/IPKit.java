package com.my.blog.website.utils;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.http.HttpUtil;
import com.google.gson.Gson;
import com.my.blog.website.pojo.dto.AddressDTO;
import com.my.blog.website.pojo.dto.TaobaoIpResponse;
import com.my.blog.website.pojo.entity.VisitStatistics;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * ip工具类
 * Created by BlueT on 2017/3/9.
 */
@Slf4j
public class IPKit {
    /**
     * @param request 请求
     * @return IP Address
     */
    public static String getIpAddrByRequest(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info("新的来访者,IP: {}", ip);
        return ip;
    }

    /**
     * @return 本机IPSocketException
     * @throws SocketException
     */
    public static String getRealIp() throws SocketException {
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        boolean finded = false;// 是否找到外网IP
        while (netInterfaces.hasMoreElements() && !finded) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();
            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {// 外网IP
                    netip = ip.getHostAddress();
                    finded = true;
                    break;
                } else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && !ip.getHostAddress().contains(":")) {// 内网IP
                    localip = ip.getHostAddress();
                }
            }
        }

        if (netip != null && !"".equals(netip)) {
            return netip;
        } else {
            return localip;
        }
    }

    public static AddressDTO getIpAddressInfo(String ip) {
        String taobaoIpUrl = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;

        HttpResponse response = HttpUtil.createGet(taobaoIpUrl).timeout(3000).execute();
        if (response.getStatus() == HttpStatus.HTTP_OK) {
            String body = response.body();
            Gson gson = new Gson();
            TaobaoIpResponse taobaoIpResponse = gson.fromJson(body, TaobaoIpResponse.class);
            if (taobaoIpResponse.getCode() == 0) {
                return taobaoIpResponse.getData();
            }
        }
        return null;
    }
}
