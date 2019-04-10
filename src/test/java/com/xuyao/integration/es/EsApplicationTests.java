package com.xuyao.integration.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsApplicationTests {


	@Test
	public void contextLoads() throws InterruptedException, IOException {
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(
				new HttpHost("192.168.0.118", 9200, "http")));
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchAllQuery());
		searchSourceBuilder.aggregation(AggregationBuilders.terms("top_10_states").field("state").size(10));
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.indices("social-*");
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse = client.search(searchRequest);
		System.out.println(searchResponse);
	}

}
