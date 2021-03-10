package io.project.urlshortener.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.project.urlshortener.url.QUrlHistory;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QuerydslConfigTest {

	@Autowired
	EntityManager em;

	@Test
	@DisplayName("샘플_QUERYDSL_테스트")
	void 샘플_QUERYDSL_테스트(){
		String originUrl = "www.naver.com";
		String shortUrl = "adf";

		JPAQueryFactory queryFactory = new JPAQueryFactory(em);
		QUrlHistory qUrlHistory = new QUrlHistory("a");

		Integer integer = queryFactory.selectOne()
			.from(qUrlHistory)
			.where(qUrlHistory.originalUrl.eq(originUrl).or(qUrlHistory.shortUrl.eq(shortUrl)))
			.fetchFirst();

		System.out.println(integer);
	}
}
