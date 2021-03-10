package io.project.urlshortener.url;

import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlHistoryRepository extends JpaRepository<UrlHistory, BigInteger> {

	boolean existsByOriginalUrlOrShortUrl(String originalUrl, String shortUrl);

	// 컴파일 에러발생
//	@Query("SELECT EXIST (SELECT short_url FROM url_history u WHERE u.original_url = :inputUrl or u.short_url = :inputUrl)")
//	boolean asdf(@Param("inputUrl") String inputUrl);
}
