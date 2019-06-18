package com.chinasofti.Kstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinasofti.Kstore.service.SearchService;
import com.chinasofti.Kstore.vo.Search;

@Controller
public class NavbarController {
	@Autowired
	SearchService searchservice;
	
	@ModelAttribute()
	public void getHotSearch(Model model) {
		List<Search> searches =  searchservice.getFourHotSearch();
		model.addAttribute("searchlist", searches);
	}
	
	@RequestMapping(value="/coming-soon",method=RequestMethod.GET)
	public String gotoComingsoon() {
		return "coming-soon";
	}
	
}
