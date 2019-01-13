package com.xuyao.integration.mybatis;

import com.xuyao.integration.model.Inc;
import com.xuyao.integration.service.IncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisTest {

    @Autowired
    private IncService incService;

    @Test
    public void batchInsert(){
        Map<String, Object> result = new HashMap<>();
        long start = System.currentTimeMillis();
        List<Inc> list = new ArrayList<>(10000);
        for(int i = 0; i < 10000; i++){
            Inc inc = new Inc();
            inc.setType(i + "");
            inc.setNum(1);
            list.add(inc);
        }
        incService.batchInsert(list);
        long end = System.currentTimeMillis();
        result.put("time cost: ", (end - start));
    }

}
