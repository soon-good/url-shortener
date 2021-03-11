package io.project.urlshortener.urls.tdd.repository;

import io.project.urlshortener.url.repository.QdslUrlHistory;
import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.repository.UrlHistoryRepository;
import java.math.BigInteger;
import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TddQdslUrlHistory {

	@Autowired
	EntityManager entityManager;

	@Autowired
	UrlHistoryRepository urlHistoryRepository;

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
		Assertions.assertThat(asdf).isTrue();
	}

	@Test
	@DisplayName("URL 단건 조회 테스트")
	void URL_단건_조회테스트(){
		UrlHistory asdf = qdslUrlHistory.findByAnyUrl("asdf");
		System.out.println(asdf);
		Assertions.assertThat(asdf).isEqualTo(asdf);
	}

	@Test
	@DisplayName("현재_시퀀스_값_가져오기_테스트")
	void 현재_시퀀스_값_가져오기_테스트(){
		BigInteger nextVal = qdslUrlHistory.getCurrentSequence();
		System.out.println("nextVal = " + nextVal);
		System.out.println("String.valueOf(nextVal) = " + String.valueOf(nextVal));

		UrlHistory urlHistory = new UrlHistory();
		urlHistory.setRequestCnt(BigInteger.ONE);
		urlHistory.setShortUrl("a");
		urlHistory.setOriginalUrl("abcde");

		urlHistoryRepository.save(urlHistory);

		List<UrlHistory> all = urlHistoryRepository.findAll();
		System.out.println(all);
	}
}
