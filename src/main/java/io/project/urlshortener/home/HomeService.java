package io.project.urlshortener.home;

import io.project.urlshortener.url.UrlHistory;

public interface HomeService {
	UrlHistory findByAnyUrl(String shortUrl);
}
