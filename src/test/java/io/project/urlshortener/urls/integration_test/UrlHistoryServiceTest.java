package io.project.urlshortener.urls.integration_test;

import io.project.urlshortener.functions.Base32EncoderFunctions;
import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.UrlHistoryService;
import io.project.urlshortener.url.UrlHistoryServiceImpl;
import io.project.urlshortener.url.repository.QdslUrlHistory;
import io.project.urlshortener.url.repository.UrlHistoryRepository;
import java.math.BigInteger;
import java.util.function.Function;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
public class UrlHistoryServiceTest {

	private UrlHistoryService urlHistoryService;

	@Autowired
	private UrlHistoryRepository urlHistoryRepository;

	@Autowired
	private QdslUrlHistory qdslUrlHistory;

	@BeforeEach
	void init(){
		this.urlHistoryService = new UrlHistoryServiceImpl(urlHistoryRepository, qdslUrlHistory);
	}

	// 테스트 시나리오 :
	// 첫번째 입력은 http://www.naver.com 이다.
	// 		insert 된 후에 값을 find 해온다. 이것을 인코딩한 shortUrl과 DB에 저장한 값과 같은지 비교
	// 두번째 입력은 http://www.naver.com 이다.
	// 		insert 하지 않고 requestCnt 값만 2로 업데이트 되었는지 requestCnt 를 assert 해본다.
	// 세번째 입력은 http://www.naver.com 이다.
	// 		insert 하지 않고 requestCnt 값만 3로 업데이트 되었는지 requestCnt 를 assert 해본다.
	@Test
	@DisplayName("아무 데이터도 초기상태 > 단축 URL 여러번 생성 > 시나리오 테스트")
	void test(){
		final String userInputUrl = "http://www.naver.com";
		// 테이블에 아무 데이터도 없을 때 현재 로우의 시퀀스가져오기 ( must be 1)
		BigInteger currentSequence = qdslUrlHistory.getCurrentSequence();
		String beforeSequence = currentSequence.toString();
		System.out.println("beforeSequence = " + beforeSequence);

		// 단축 URL 생성 및 Data Insert
		UrlHistory urlHistory1st = urlHistoryService.generateShortUrl(userInputUrl);
		System.out.println(urlHistory1st);

		// 시퀀스를 base32 인코딩한 결과값 구하기
		Function<String, String> encoder = Base32EncoderFunctions.base32Encoding;
		String shortUrl = encoder.apply(beforeSequence);

		// 인코딩함수의 결과값과 DB에서 인출해온 값이 일치하는지 검사
		Assertions.assertThat(urlHistory1st.getShortUrl()).isEqualTo(shortUrl);

		// 2 번째 호출시 URL 이 이미 존재한다.
		UrlHistory urlHistory2nd = urlHistoryService.generateShortUrl(userInputUrl);
		System.out.println(urlHistory2nd);

		Assertions.assertThat(urlHistory2nd.getRequestCnt()).isEqualTo(2);

		// 3 번째 호출은 축약 URL로 시도해본다
		UrlHistory urlHistory3nd = urlHistoryService.generateShortUrl(shortUrl);
		System.out.println(urlHistory3nd);

		Assertions.assertThat(urlHistory2nd.getRequestCnt()).isEqualTo(3);
	}
}
