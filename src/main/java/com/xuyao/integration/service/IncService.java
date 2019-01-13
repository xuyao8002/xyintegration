package com.xuyao.integration.service;


import com.xuyao.integration.model.Inc;

import java.util.List;

public interface IncService {

    int deleteByPrimaryKey(Integer id);

    int insert(Inc record);

    int insertSelective(Inc record);

    Inc selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Inc record);

    int updateByPrimaryKey(Inc record);

    int updateNumByType(String type, int add);

    void batchInsert(List<Inc> list);
}
