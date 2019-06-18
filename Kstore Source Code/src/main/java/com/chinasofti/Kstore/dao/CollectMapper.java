/*
 * author: minhe
 * fields: ¹ºÎï³µ
 */

package com.chinasofti.Kstore.dao;

import java.util.List;

import com.chinasofti.Kstore.vo.Collect;

public interface CollectMapper {
	
	public List<Collect> getCollectByUserId(int collector_addCollectid);
	public int deleteCollectByCollectId(int collect_id);
	public int addCollect(Collect collect);
	public Collect getCollectByUserIdAndGoodsId(Collect collect);
}
