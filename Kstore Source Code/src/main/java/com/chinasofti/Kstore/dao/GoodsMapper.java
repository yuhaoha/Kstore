package com.chinasofti.Kstore.dao;

import java.util.List;

import com.chinasofti.Kstore.vo.Goods;

public interface GoodsMapper {
	public int Post(Goods goods);
	public List<Goods> MyPost(int owner_id);
	public Goods GetGoodsById(int goods_id);
	public List<Goods> GetCategory(String type);
	public List<Goods> GetCategoryOnlySix(String type);
	public List<Goods> GetCategoryByReleaseTime(String type);
	public List<Goods> GetCategoryByPrice_1(String type);
	public List<Goods> GetCategoryByPrice_2(String type);
	public List<Goods> GetTotalGoods();
	public List<Goods> GetTotalGoodsByReleaseTime();
	public List<Goods> GetTotalGoodsByPrice_1();
	public List<Goods> GetTotalGoodsByPrice_2();
	public List<Goods> SearchGoods(String search);
	public List<Goods> SearchGoodsByReleaseTime(String search);
	public List<Goods> SearchGoodsByPrice_1(String search);
	public List<Goods> SearchGoodsByPrice_2(String search);
	/*
	 * author: minhe
	 * fields: ¹ºÎï³µ
	 */
	public Goods getGoodsByGoodsId(int goods_id);
	public int changeGoodsState(int goods_id);
}
