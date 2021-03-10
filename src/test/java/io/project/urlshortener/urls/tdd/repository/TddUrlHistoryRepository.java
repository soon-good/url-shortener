package io.project.urlshortener.urls.tdd.repository;

import io.project.urlshortener.url.UrlHistoryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TddUrlHistoryRepository {

	@Autowired
	UrlHistoryRepository repository;

	@Test
	void URL데이터가_없을때_URL을_조회하는_경우(){
//		boolean existsFlag = repository.existsByOriginalUrlOrShortUrl("www.naver.com");
//		Assertions.assertThat(existsFlag).isFalse();
	}
}
