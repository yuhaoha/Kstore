package com.chinasofti.Kstore.vo;


public class Goods {
private int goods_id ;
private String name;
private String type;
private String pictures;
private int owner_id;
private String release_time;
private String description ;
private float price;
private boolean state;

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
public int getGoods_id() {
	return goods_id;
}
public void setGoods_id(int goods_id) {
	this.goods_id = goods_id;
}
public String getRelease_time() {
	return release_time;
}
public void setRelease_time(String release_time) {
	this.release_time = release_time;
}

public int getOwner_id() {
	return owner_id;
}
public void setOwner_id(int owner_id) {
	this.owner_id = owner_id;
}
@Override
public String toString() {
	return "Goods [goods_id=" + goods_id + ", name=" + name + ", type=" + type + ", pictures=" + pictures
			 + ", release_time=" + release_time + ", description=" + description + ", price="
			+ price + ", state=" + state + "]";
}


}
