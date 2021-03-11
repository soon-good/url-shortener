package io.project.urlshortener.url;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UrlHistoryController {

	private final UrlHistoryService urlHistoryService;

	@Autowired
	public UrlHistoryController(UrlHistoryService urlHistoryService){
		this.urlHistoryService = urlHistoryService;
	}

	@GetMapping("/url/encoding/base32")
	public UrlHistoryDto getSample(@RequestParam(value = "targetUrl") String targetUrl){
		UrlHistoryDto urlHistoryDto = new UrlHistoryDto();
		urlHistoryDto.setOriginalUrl(targetUrl);
		urlHistoryDto.setShortUrl(targetUrl);
		urlHistoryDto.setRequestCnt(111);
		return urlHistoryDto;
	}
}
