package io.project.urlshortener.url;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class UrlHistory {

	@Id @Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_history_seq")
	private BigInteger urlSeq;

	@Column(name = "original_url")
	private String originalUrl;

	@Column(name = "short_url")
	private String shortUrl;
}
