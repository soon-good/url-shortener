package io.project.urlshortener.home;

import io.project.urlshortener.url.UrlHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

	private final HomeService homeService;

	@Autowired
	public HomeController(HomeService homeService){
		this.homeService = homeService;
	}

	@GetMapping("/")
	public String getHome(Model model){
		return "index";
	}

	@GetMapping("/{shortUrl}")
	public String randomRequest(Model model, @PathVariable(name = "shortUrl") String shortUrl) {
		UrlHistory urlHistory = homeService.findByAnyUrl(shortUrl);
		model.addAttribute("originUrl", urlHistory.getOriginalUrl());
		return "index";
	}
}
