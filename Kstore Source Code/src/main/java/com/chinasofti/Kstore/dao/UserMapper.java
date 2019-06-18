package com.chinasofti.Kstore.dao;

import com.chinasofti.Kstore.vo.User;

public interface UserMapper {
	public User Login(User user);
	public int Register(User user);
	public User CheckUser(User user);
	public int ChangePwd(User user);
	public int ChangeProfile(User user);
	public User SelById(int user_id);
	public int AddFaithValueById(int user_id);
}
