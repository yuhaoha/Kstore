package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinasofti.Kstore.vo.Message;
import com.chinasofti.Kstore.dao.MessageMapper;

@Service
public class MessageService {
	@Autowired
	MessageMapper messagemapper;
	
	public List<Message> getAllMessagesByGoodsid(int id){
		return messagemapper.selectMessagesByGoodsId(id);
	}
	
	public void writeMessagesIntoDB(Message msg) {
		messagemapper.insertIntoMessage(msg);
	}
}
