package io.project.urlshortener.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UrlHistoryController {

	private final UrlHistoryService urlHistoryService;

	@Autowired
	public UrlHistoryController(UrlHistoryService urlHistoryService){
		this.urlHistoryService = urlHistoryService;
	}
}
