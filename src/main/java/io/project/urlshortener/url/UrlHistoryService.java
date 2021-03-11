package io.project.urlshortener.url;

public interface UrlHistoryService {

	UrlHistory findUrlHistory(String url);

	boolean existUrl(String userInputUrl);

	// !TODO 내일 중으로 DTO로 전환 작업 필요
	UrlHistory generateShortUrl(String userInputUrl);

}
