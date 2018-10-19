package com.my.blog.website.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.my.blog.website.model.Bo.ArchiveBo;
import com.my.blog.website.model.Vo.ContentVo;
import com.my.blog.website.model.Vo.ContentVoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ContentVoMapper extends BaseMapper<ContentVo> {
    long countByExample(ContentVoExample example);

    int deleteByExample(ContentVoExample example);

    int deleteByPrimaryKey(Integer cid);

    int insertSelective(ContentVo record);

    List<ContentVo> selectByExampleWithBLOBs(ContentVoExample example);

    List<ContentVo> selectByExample(ContentVoExample example);

    ContentVo selectByPrimaryKey(Integer cid);

    int updateByExampleSelective(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByExampleWithBLOBs(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByExample(@Param("record") ContentVo record, @Param("example") ContentVoExample example);

    int updateByPrimaryKeySelective(ContentVo record);

    int updateByPrimaryKeyWithBLOBs(ContentVo record);

    int updateByPrimaryKey(ContentVo record);

    List<ArchiveBo> findReturnArchiveBo();

    List<ContentVo> findByCatalog(Integer mid);
}