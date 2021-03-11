package io.project.urlshortener.home;

import io.project.urlshortener.url.UrlHistory;
import io.project.urlshortener.url.repository.QdslUrlHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

	private final QdslUrlHistory qdslUrlHistory;

	@Autowired
	public HomeController(QdslUrlHistory qdslUrlHistory){
		this.qdslUrlHistory = qdslUrlHistory;
	}

	@GetMapping("/")
	public String getHome(Model model){
		return "index";
	}

	@GetMapping("/{shortUrl}")
	public String randomRequest(Model model, @PathVariable(name = "shortUrl") String shortUrl) {
		UrlHistory urlHistory = qdslUrlHistory.findByAnyUrl(shortUrl);
		model.addAttribute("originUrl", urlHistory.getOriginalUrl());
		return "index";
	}
}
