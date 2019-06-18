/*
 * author: minhe
 * fields: ¶©µ¥
 * describe
 */

package com.chinasofti.Kstore.dao;

import java.util.List;

import com.chinasofti.Kstore.vo.Orders;

public interface OrdersMapper {

	public Orders getOrderById(int order_id);
	public List<Orders> getOrderByUserId(int user_id);
	public void addOrder(Orders orders);
	public void confirmById(int order_id);
}
