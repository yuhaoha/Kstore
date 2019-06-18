/*
 * author: minhe
 * fields: ¶©µ¥
 * describe
 */
package com.chinasofti.Kstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinasofti.Kstore.dao.OrdersMapper;
import com.chinasofti.Kstore.vo.Orders;

@Service
public class OrdersService {
	@Autowired
	OrdersMapper ordersMapper;
	
	@Transactional
	public Orders getOrderById(int order_id) {
		return ordersMapper.getOrderById(order_id);
	}
	
	@Transactional
	public void addOrder(Orders orders) {
		ordersMapper.addOrder(orders);
	}
	
	@Transactional
	public List<Orders> getOrderByUserId(int user_id){
		return ordersMapper.getOrderByUserId(user_id);
	}
	
	@Transactional
	public void confirmById(int order_id) {
		ordersMapper.confirmById(order_id);
	}
}
