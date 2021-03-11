package io.project.urlshortener.url;

import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import lombok.Data;

@Entity
@Data
@SequenceGenerator(
	name = "url_history_seq_gen",
	sequenceName = "url_history_seq",
	initialValue = 1,
	allocationSize = 1
)
public class UrlHistory {

	@Id @Column(name = "seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "url_history_seq_gen")
	private BigInteger urlSeq;

	@Column(name = "original_url")
	private String originalUrl;

	@Column(name = "short_url")
	private String shortUrl;

	@Column(name = "reqeust_cnt")
	private BigInteger requestCnt;
}
