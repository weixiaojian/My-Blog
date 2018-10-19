package com.my.blog.website.model.Vo;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 
 */
@TableName("t_relationships")
@Data
public class RelationshipVoKey implements Serializable {
    /**
     * 内容主键
     */
    @TableId
    private Integer cid;

    /**
     * 项目主键
     */
    private Integer mid;

    private static final long serialVersionUID = 1L;
}