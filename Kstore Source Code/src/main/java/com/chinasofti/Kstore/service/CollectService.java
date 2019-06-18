/*
 * author: minhe
 * fields: 购物车
*/
package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinasofti.Kstore.dao.CollectMapper;
import com.chinasofti.Kstore.vo.Collect;

@Service
public class CollectService {

	@Autowired
	CollectMapper collectMapper;

	//根据用户获取所有购物车内容
	@Transactional
	public List<Collect> getCollectByUserId(int collector_id){

		return collectMapper.getCollectByUserId(collector_id);
	}
	
	//根据购物车id删除购物车条目
	@Transactional
	public int deleteCollectByCollectId(int collect_id) {
		
		return collectMapper.deleteCollectByCollectId(collect_id);
	}
	
	@Transactional
	public int addCollect(Collect collect) {
		
		return collectMapper.addCollect(collect);
	}
	
	@Transactional
	public Collect getCollectByUserIdAndGoodsId(Collect collect) {
		return collectMapper.getCollectByUserIdAndGoodsId(collect);
	}
}
