package com.chinasofti.Kstore.dao;

import java.util.List;

import com.chinasofti.Kstore.vo.Message;

public interface MessageMapper {
	public List<Message> selectMessagesByGoodsId(int id);
	public void insertIntoMessage(Message msg);
}
