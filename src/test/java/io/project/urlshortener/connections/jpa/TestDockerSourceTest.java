package io.project.urlshortener.connections.jpa;

import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.UrlHistoryRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TestDockerSourceTest {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UrlHistoryRepository urlHistoryRepository;

	@Test
	@DisplayName("MariaDB_커넥션_테스트")
	void MariaDB_커넥션_테스트(){
		UrlHistory urlHistory = new UrlHistory();
		System.out.println(urlHistory.getUrlSeq());
		List<UrlHistory> all = urlHistoryRepository.findAll();
		System.out.println(all);
	}
}
