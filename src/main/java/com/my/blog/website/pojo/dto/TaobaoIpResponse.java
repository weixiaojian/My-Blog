package com.my.blog.website.pojo.dto;

import lombok.Data;

/**
 * @author imyzt
 * @date 2019/08/29
 * @description 淘宝ip返回信息
 */
@Data
public class TaobaoIpResponse {

    private Integer code;

    private AddressDTO data;
}
