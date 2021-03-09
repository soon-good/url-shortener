package io.project.urlshortener.url;

import java.math.BigInteger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlHistoryRepository extends JpaRepository<UrlHistory, BigInteger> {

}
