package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinasofti.Kstore.dao.GoodsMapper;
import com.chinasofti.Kstore.dao.UserMapper;
import com.chinasofti.Kstore.vo.Goods;
import com.chinasofti.Kstore.vo.User;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	GoodsMapper goodsMapper;
	
	@Transactional
	public User Login(User user) {
		return userMapper.Login(user);
	}
	
	@Transactional
	public int Register(User user) {
		return userMapper.Register(user);
	}
	
	@Transactional
	public User CheckUser(User user) {
		return userMapper.CheckUser(user);
	}
	
	@Transactional
	public int ChangePwd(User user) {
		return userMapper.ChangePwd(user);
	}
	
	@Transactional
	public int ChangeProfile(User user) {
		return userMapper.ChangeProfile(user);
	}
	
	@Transactional
	public int Post(Goods goods) {
		return goodsMapper.Post(goods);
	}

	@Transactional
	public List<Goods> MyPost(int id){
		return goodsMapper.MyPost(id);
	}
	
	@Transactional
	public User SelById(int id) {
		return userMapper.SelById(id);
	}
	
	@Transactional
	public int AddFaithValueById(int user_id) {
		return userMapper.AddFaithValueById(user_id);
	}
}
