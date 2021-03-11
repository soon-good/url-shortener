package io.project.urlshortener.urls.tdd;

import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.UrlHistoryService;
import io.project.urlshortener.url.UrlHistoryServiceImpl;
import io.project.urlshortener.url.repository.QdslUrlHistory;
import io.project.urlshortener.url.repository.UrlHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TddUrlHistoryService {

	private UrlHistoryService urlHistoryService;

	@Autowired
	private UrlHistoryRepository urlHistoryRepository;

	@Autowired
	private QdslUrlHistory qdslUrlHistory;

	@BeforeEach
	void init(){
		urlHistoryService = new UrlHistoryServiceImpl(urlHistoryRepository, qdslUrlHistory);
	}

	@Test
	@DisplayName("단축URL_생성_테스트")
	void 단축URL_생성_테스트(){
		UrlHistory urlHistory = urlHistoryService.generateShortUrl("http://www.naver.com");
		System.out.println(urlHistory);
	}
}
