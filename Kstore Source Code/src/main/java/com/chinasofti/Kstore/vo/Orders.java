/*
 * author: minhe
 * fields: orders
 * describe: 订单状态  1.等待送达 2.已完成
 */

package com.chinasofti.Kstore.vo;

public class Orders {

	private int order_id;
	private int seller_id;
	private int buyer_id;
	private int goods_id;
	private String buy_time;
	private String state;
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(int seller_id) {
		this.seller_id = seller_id;
	}
	public int getBuyer_id() {
		return buyer_id;
	}
	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getBuy_time() {
		return buy_time;
	}
	public void setBuy_time(String buy_time) {
		this.buy_time = buy_time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "Orders [order_id=" + order_id + ", seller_id=" + seller_id + ", buyer_id=" + buyer_id + ", goods_id="
				+ goods_id + ", buy_time=" + buy_time + ", state=" + state + "]";
	}
	
}
