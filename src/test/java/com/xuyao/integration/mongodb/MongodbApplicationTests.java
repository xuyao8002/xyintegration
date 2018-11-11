package com.xuyao.integration.mongodb;

import com.xuyao.integration.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbApplicationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {

		List<Person> all = mongoTemplate.findAll(Person.class);
//		for (Person person : all) {
//			System.out.println(person);
//		}
		List<Person> haha = mongoTemplate.find(new Query().limit(10), Person.class, "haha");
		for (Person person : haha) {
			System.out.println(person);
		}

	}

}
