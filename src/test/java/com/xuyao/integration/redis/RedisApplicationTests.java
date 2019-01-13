package com.xuyao.integration.redis;

import com.xuyao.integration.model.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisApplicationTests {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void contextLoads() throws InterruptedException {

//		redisTemplate.opsForSet().add("name", "xuyao", "xuyao1","ye");
//		//System.out.println(redisTemplate.opsForSet().pop("name"));
//		redisTemplate.opsForList().leftPush("xuyao", 29);
//		//System.out.println(redisTemplate.opsForList().leftPop("xuyao"));
//		Person p = new Person();
//		p.setName("xuyao");
//		p.setAge(29);
//		redisTemplate.opsForList().leftPush("person", p);
//		List<Person> list = new ArrayList<>();
//		list.add(p);
//		list.add(new Person("ye", 29));
//		redisTemplate.opsForList().leftPush("personList", list);
//		System.out.println("哈哈");
//		redisTemplate.opsForValue().increment("age", 2);
//		stringRedisTemplate.opsForValue().increment("age", 3);
//		for(int i = 0; i < 10; i++){
//			Person p = new Person();
//			p.setName("xuyao" + (10 + i));
//			p.setAge(29);
//			Long person = redisTemplate.opsForList().leftPush("person", p);
//			System.out.println(person);
//		}
//		while(true){
//			System.out.println(redisTemplate.opsForList().leftPop("person", 10L, TimeUnit.SECONDS));
//		}

//		stringRedisTemplate.opsForValue().set("xuyao", "ye");
//		String str = stringRedisTemplate.opsForValue().get("xuyao");
//		redisTemplate.opsForValue().set("xuye", "ye");
//		String str1 = (String) redisTemplate.opsForValue().get("xuye");
//		System.out.println(str);
//		System.out.println(str1);
//		System.out.println(str.equals(str1));

//		BlockingQueue<String> bq = new LinkedBlockingDeque<>();
//		bq.poll(10L, TimeUnit.SECONDS);
		Person person = new Person();
		person.setName("xuyao");
		person.setAge(29);
		long start = System.currentTimeMillis();
		for(int i = 0; i < 100; i ++){
			redisTemplate.opsForList().leftPush("person", person);
		}
		System.out.println(System.currentTimeMillis() - start);
//		System.out.println(redisTemplate.opsForList().leftPop("person"));
	}

}
