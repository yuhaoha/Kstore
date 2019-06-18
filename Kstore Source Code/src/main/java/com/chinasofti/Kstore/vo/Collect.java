package com.chinasofti.Kstore.vo;

public class Collect {
	
	private int collect_id;
	private int collector_id;
	private int goods_id;
	public int getCollect_id() {
		return collect_id;
	}
	public void setCollect_id(int collect_id) {
		this.collect_id = collect_id;
	}
	public int getCollector_id() {
		return collector_id;
	}
	public void setCollector_id(int collector_id) {
		this.collector_id = collector_id;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	@Override
	public String toString() {
		return "Collect [collect_id=" + collect_id + ", collector_id=" + collector_id + ", goods_id=" + goods_id + "]";
	}
	
}
