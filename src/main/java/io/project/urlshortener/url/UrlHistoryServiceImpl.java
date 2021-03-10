package io.project.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlHistoryServiceImpl implements UrlHistoryService{

	private final UrlHistoryRepository urlHistoryRepository;

	@Autowired
	public UrlHistoryServiceImpl(UrlHistoryRepository urlHistoryRepository){
		this.urlHistoryRepository = urlHistoryRepository;
	}

	@Override
	public UrlHistory findUrlHistory(String url) {
		return null;
	}

	@Override
	public boolean existUrl(String userInputUrl) {
		return urlHistoryRepository.existsByOriginalUrlOrShortUrl(userInputUrl);
	}
}
