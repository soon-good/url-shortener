package io.project.urlshortener.home;

import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.repository.QdslUrlHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeServiceImpl implements HomeService{

	private final QdslUrlHistory qdslUrlHistory;

	@Autowired
	public HomeServiceImpl(QdslUrlHistory qdslUrlHistory){
		this.qdslUrlHistory = qdslUrlHistory;
	}

	@Override
	public UrlHistory findByAnyUrl(String shortUrl) {
		return qdslUrlHistory.findByAnyUrl(shortUrl);
	}
}
