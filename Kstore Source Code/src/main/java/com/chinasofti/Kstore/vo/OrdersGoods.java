package com.chinasofti.Kstore.vo;

public class OrdersGoods {

	private int orders_id;
	private float goods_price;
	private String buy_time;
	private String state;
	public int getOrders_id() {
		return orders_id;
	}
	public void setOrders_id(int orders_id) {
		this.orders_id = orders_id;
	}
	public float getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(float goods_price) {
		this.goods_price = goods_price;
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
		return "OrdersGoods [orders_id=" + orders_id + ", goods_price=" + goods_price + ", buy_time=" + buy_time
				+ ", state=" + state + "]";
	}
	
}
