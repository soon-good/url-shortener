package io.project.urlshortener.url.repository;

import static io.project.urlshortener.url.QUrlHistory.urlHistory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.project.urlshortener.url.QUrlHistory;
import io.project.urlshortener.url.UrlHistory;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
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

	public BigInteger getCurrentSequence(){
		Query query = entityManager
			.createNativeQuery("select next_val from url_history_seq");

		BigInteger sequence = (BigInteger)query.getSingleResult();
		return sequence;
	}
}
