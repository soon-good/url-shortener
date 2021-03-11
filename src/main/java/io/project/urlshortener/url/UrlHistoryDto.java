package io.project.urlshortener.url;

import java.math.BigInteger;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UrlHistoryDto {
	private String originalUrl;
	private String shortUrl;
	private Integer requestCnt;
}
