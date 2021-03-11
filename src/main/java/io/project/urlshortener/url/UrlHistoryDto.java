package io.project.urlshortener.url;

import com.querydsl.core.annotations.QueryProjection;
import java.math.BigInteger;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UrlHistoryDto {
	private String originalUrl;
	private String shortUrl;
	private Integer requestCnt;

	public UrlHistoryDto(){}

	@QueryProjection
	@Builder
	public UrlHistoryDto(String originalUrl, String shortUrl, BigInteger requestCnt){
		this.originalUrl = originalUrl;
		this.shortUrl = shortUrl;
		this.requestCnt = requestCnt.intValue();
	}
}
