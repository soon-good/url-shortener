package io.project.urlshortener.url;

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
}
