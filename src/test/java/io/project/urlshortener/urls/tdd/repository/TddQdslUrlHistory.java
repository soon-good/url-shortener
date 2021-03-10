package io.project.urlshortener.urls.tdd.repository;

import io.project.urlshortener.url.QdslUrlHistory;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TddQdslUrlHistory {

	@Autowired
	EntityManager entityManager;

	QdslUrlHistory qdslUrlHistory;

	@BeforeEach
	void init(){
		qdslUrlHistory = new QdslUrlHistory(entityManager);
	}

	@Test
	@DisplayName("EXIST 쿼리테스트")
	void EXIST_쿼리테스트(){
		boolean asdf = qdslUrlHistory.existsAnyUrl("asdf");
		System.out.println(asdf);
	}
}
