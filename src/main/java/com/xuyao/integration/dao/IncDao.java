package com.xuyao.integration.dao;

import com.xuyao.integration.model.Inc;
import org.apache.ibatis.annotations.Param;

public interface IncDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Inc record);

    int insertSelective(Inc record);

    Inc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inc record);

    int updateByPrimaryKey(Inc record);

    int updateNumByType(@Param("type") String type, @Param("add") int add);
}