package com.chinasofti.Kstore.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Message {
	private int message_id;
	private int releaser_id;
	private int goods_id;
	private String release_time;
	private String content;
	private User user;
	
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
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
	public void setRelease_time(Timestamp releaseTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.release_time = df.format(releaseTime);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getReleaser_id() {
		return releaser_id;
	}
	public void setReleaser_id(int releaser_id) {
		this.releaser_id = releaser_id;
	}
	
	public String toString() {
		return "Message [message_id="+message_id+",releaser_id="+releaser_id+",goods_id="+goods_id+",release_time="+release_time+",content="+content+",user="+user+"]";
	}
}
