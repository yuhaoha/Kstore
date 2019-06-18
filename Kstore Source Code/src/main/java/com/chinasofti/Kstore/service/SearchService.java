package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.Kstore.dao.SearchMapper;
import com.chinasofti.Kstore.vo.Search;

@Service
public class SearchService {
	@Autowired
	SearchMapper searchmapper;
	
	public List<Search> getFourHotSearch(){
		return searchmapper.selectFourHotSearch();
	}
}
