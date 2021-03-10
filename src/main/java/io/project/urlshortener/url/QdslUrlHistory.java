package io.project.urlshortener.url;

import static io.project.urlshortener.url.QUrlHistory.urlHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class QdslUrlHistory {

	private final EntityManager entityManager;

	private final JPAQueryFactory queryFactory;

	@Autowired
	public QdslUrlHistory(EntityManager entityManager){
		this.entityManager = entityManager;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	public UrlHistory findByAnyUrl(String inputUrl){
		UrlHistory urlHistory = queryFactory
			.selectFrom(QUrlHistory.urlHistory)
			.where(QUrlHistory.urlHistory.shortUrl.eq(inputUrl)
				.or(QUrlHistory.urlHistory.originalUrl.eq(inputUrl)))
			.fetchOne();

		return urlHistory;
	}

	public boolean existsAnyUrl(String inputUrl){
		Integer selectOne = queryFactory   // SELECT 1 WHERE ...
			.selectOne()
			.from(urlHistory)
			.where(urlHistory.shortUrl.eq(inputUrl).or(urlHistory.originalUrl.eq(inputUrl)))
			.fetchFirst();

		final boolean isExist = selectOne != null ? true : false;
		return isExist;
	}
}
