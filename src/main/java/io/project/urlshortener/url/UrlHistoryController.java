package io.project.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UrlHistoryController {

	private final UrlHistoryService urlHistoryService;

	@Autowired
	public UrlHistoryController(UrlHistoryService urlHistoryService){
		this.urlHistoryService = urlHistoryService;
	}

	@GetMapping("/url/encoding/base32")
	public UrlHistoryDto getShortUrlResult(@RequestParam(value = "targetUrl") String targetUrl){
		return urlHistoryService.generateShortUrl(targetUrl);
	}
}
