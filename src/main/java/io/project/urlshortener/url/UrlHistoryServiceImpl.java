package io.project.urlshortener.url;

import io.project.urlshortener.functions.Base32EncoderFunctions;
import io.project.urlshortener.url.repository.QdslUrlHistory;
import io.project.urlshortener.url.repository.UrlHistoryRepository;
import java.math.BigInteger;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlHistoryServiceImpl implements UrlHistoryService{

	private final UrlHistoryRepository urlHistoryRepository;

	private final QdslUrlHistory qdslUrlHistory;

	@Autowired
	public UrlHistoryServiceImpl(UrlHistoryRepository urlHistoryRepository, QdslUrlHistory qdslUrlHistory){
		this.urlHistoryRepository = urlHistoryRepository;
		this.qdslUrlHistory = qdslUrlHistory;
	}

	@Override
	public UrlHistory findUrlHistory(String url) {
		UrlHistory urlHistoryResult = qdslUrlHistory.findByAnyUrl(url);
		return urlHistoryResult;
	}

	@Override
	public boolean existUrl(String userInputUrl) {
		boolean existsAnyUrlFlag = qdslUrlHistory.existsAnyUrl(userInputUrl);
		return existsAnyUrlFlag;
	}

	@Override
	public UrlHistoryDto generateShortUrl(String userInputUrl) {
		// !TODO:: 오늘 저녁에 Optional 사용로직으로 변경하기
		UrlHistory urlHistory = null;

		if(!userInputUrl.isEmpty() && userInputUrl != null){
			// 이미 생성된 적 있는 url 일 경우
			if (qdslUrlHistory.existsAnyUrl(userInputUrl)){
				urlHistory = qdslUrlHistory.findByAnyUrl(userInputUrl);
				urlHistory.setRequestCnt(urlHistory.getRequestCnt().add(BigInteger.ONE));

				// 호출횟수 업데이트 (update)
				urlHistoryRepository.save(urlHistory);
			}
			// 이미 생성된 적 없는 url 일 경우
			else {
				urlHistory = new UrlHistory();

				// 원본 URL 세팅
				urlHistory.setOriginalUrl(userInputUrl);

				// base32 인코딩 람다
				final Function<String, String> base32Encoding = Base32EncoderFunctions.base32Encoding;

				// 현재 시퀀스값 가져오기
				BigInteger currentSequence = qdslUrlHistory.getCurrentSequence();
				String hash_key_for_base32_encoding = String.valueOf(currentSequence);

				// base32 인코딩
				String shortUrl = base32Encoding.apply(hash_key_for_base32_encoding);

				// shortUrl, requestCnt 값 세팅
				urlHistory.setShortUrl(shortUrl);
				urlHistory.setRequestCnt(BigInteger.ONE);

				// 테이블에 insert
				urlHistory = urlHistoryRepository.save(urlHistory);
			}

		}

		UrlHistoryDto resultDto = Optional.of(urlHistory).map(history -> {
			return UrlHistoryDto.builder()
				.originalUrl(history.getOriginalUrl())
				.shortUrl(history.getShortUrl())
				.requestCnt(history.getRequestCnt())
				.build();
		}).orElseGet(UrlHistoryDto::new);

		return resultDto;
	}
}
