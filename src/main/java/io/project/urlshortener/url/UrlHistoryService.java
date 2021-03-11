package io.project.urlshortener.url;

public interface UrlHistoryService {

	UrlHistory findUrlHistory(String url);

	boolean existUrl(String userInputUrl);

	UrlHistoryDto generateShortUrl(String userInputUrl);

}
