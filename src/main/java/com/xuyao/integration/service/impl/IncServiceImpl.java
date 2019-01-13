package com.xuyao.integration.service.impl;


import com.xuyao.integration.dao.IncDao;
import com.xuyao.integration.model.Inc;
import com.xuyao.integration.service.IncService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IncServiceImpl implements IncService {

    @Autowired
    private IncDao incDao;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return incDao.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Inc record) {
        return incDao.insert(record);
    }

    @Override
    public int insertSelective(Inc record) {
        return incDao.insertSelective(record);
    }

    @Override
    public Inc selectByPrimaryKey(Integer id) {
        return incDao.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Inc record) {
        return incDao.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Inc record) {
        return incDao.updateByPrimaryKey(record);
    }

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    //@Transactional
    @Override
    public synchronized int updateNumByType(String type, int add)  {
        //Thread.sleep(1000L);
        int i = incDao.updateNumByType(type, add);
        //Thread.sleep(3000L);
        //System.out.println(((String) null).length());
        incDao.selectByPrimaryKey(2);
        return i;
    }

    @Transactional
    @Override
    public void batchInsert(List<Inc> list) {
        SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        IncDao tmp = session.getMapper(IncDao.class);
        for (int i = 0; i < list.size(); i++) {
            tmp.insertSelective(list.get(i));
            if(i%1000 == 999){
                session.commit();
                session.clearCache();
            }
        }
        session.commit();
        session.clearCache();
    }
}
