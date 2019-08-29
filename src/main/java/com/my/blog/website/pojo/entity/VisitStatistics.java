package com.my.blog.website.pojo.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 访问统计表
 * </p>
 *
 * @author imyzt
 * @date 2019-08-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_visit_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class VisitStatistics implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ip
     */
    private String ip;

    /**
     * 来源
     */
    private String utmSource;

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

    /**
     * 源站
     */
    private String referer;

    /**
     * 访问时间
     */
    private Date createdAt;
}
