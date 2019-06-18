package com.chinasofti.Kstore.vo;

public class UserGoods {
	private int goods_id ;
	private String name;
	private String type;
	private String pictures;
	private int owner_id;
	private String release_time;
	private String description ;
	private float price;
	private boolean state;
	private String owner_name;
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPictures() {
		return pictures;
	}
	public void setPictures(String pictures) {
		this.pictures = pictures;
	}
	public int getOwner_id() {
		return owner_id;
	}
	public void setOwner_id(int owner_id) {
		this.owner_id = owner_id;
	}
	public String getRelease_time() {
		return release_time;
	}
	public void setRelease_time(String release_time) {
		this.release_time = release_time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String getOwner_name() {
		return owner_name;
	}
	public void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}
	public void setByGoods(Goods goods,String owner_name) {
		this.goods_id=goods.getGoods_id();
		this.name=goods.getName();
		this.type=goods.getType();
		this.pictures=goods.getPictures();
		this.owner_id=goods.getOwner_id();
		this.release_time=goods.getRelease_time();
		this.description=goods.getDescription();
		this.price=goods.getPrice();
		this.state=goods.isState();
		this.owner_name=owner_name;
	}
	@Override
	public String toString() {
		return "UserGoods [goods_id=" + goods_id + ", name=" + name + ", type=" + type + ", pictures=" + pictures
				+ ", owner_id=" + owner_id + ", release_time=" + release_time + ", description=" + description
				+ ", price=" + price + ", state=" + state + ", owner_name=" + owner_name + "]";
	}
	
}
