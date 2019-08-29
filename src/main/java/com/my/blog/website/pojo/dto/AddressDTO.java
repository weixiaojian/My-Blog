package com.my.blog.website.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author imyzt
 * @date 2019/08/29
 * @description 根据ip获取的区域信息
 */
@Data
public class AddressDTO implements Serializable {

    /**
     * 省
     */
    private String region;

    /**
     * 市
     */
    private String city;

    /**
     * 区县
     */
    private String county;

    /**
     * 运营商
     */
    private String isp;
}
