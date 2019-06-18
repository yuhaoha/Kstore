package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinasofti.Kstore.dao.GoodsMapper;
import com.chinasofti.Kstore.vo.Goods;

@Service
public class GoodsService {
	@Autowired
	GoodsMapper goodsMapper;
	
	@Transactional
	public List<Goods> GetCategory(String type){
		return goodsMapper.GetCategory(type);
	}
	
	@Transactional
	public List<Goods> GetCategoryOnlySix(String type){
		return goodsMapper.GetCategoryOnlySix(type);
	}
	
	@Transactional
	public List<Goods> GetCategoryByReleaseTime(String type){
		return goodsMapper.GetCategoryByReleaseTime(type);
	}
	
	@Transactional
	public List<Goods> GetCategoryByPrice_1(String type){
		return goodsMapper.GetCategoryByPrice_1(type);
	}
	
	@Transactional
	public List<Goods> GetCategoryByPrice_2(String type){
		return goodsMapper.GetCategoryByPrice_2(type);
	}
	
	@Transactional
	public List<Goods> GetTotalGoods(){
		return goodsMapper.GetTotalGoods();
	}
	
	@Transactional
	public List<Goods> GetTotalGoodsByReleaseTime(){
		return goodsMapper.GetTotalGoodsByReleaseTime();
	}
	
	@Transactional
	public List<Goods> GetTotalGoodsByPrice_1(){
		return goodsMapper.GetTotalGoodsByPrice_1();
	}
	
	@Transactional
	public List<Goods> GetTotalGoodsByPrice_2(){
		return goodsMapper.GetTotalGoodsByPrice_2();
	}

	@Transactional
	public List<Goods> SearchGoods(String search){
		return goodsMapper.SearchGoods(search);
	}
	
	@Transactional
	public List<Goods> SearchGoodsByReleaseTime(String search){
		return goodsMapper.SearchGoodsByReleaseTime(search);
	}
	
	@Transactional
	public List<Goods> SearchGoodsByPrice_1(String search){
		return goodsMapper.SearchGoodsByPrice_1(search);
	}
	
	@Transactional
	public List<Goods> SearchGoodsByPrice_2(String search){
		return goodsMapper.SearchGoodsByPrice_2(search);
	}
	
	@Transactional
	public Goods GetGoodsById(int id){
		return goodsMapper.GetGoodsById(id);
	}

	/*
	 * author: minhe
	 * fields: ¹ºÎï³µ
	 */
	@Transactional
	public Goods getGoodsByGoodsId(int collector_id) {
		return goodsMapper.getGoodsByGoodsId(collector_id);
	}
	
	@Transactional
	public int changeGoodsState(int goods_id) {
		  return goodsMapper.changeGoodsState(goods_id);
		 }
	
}
