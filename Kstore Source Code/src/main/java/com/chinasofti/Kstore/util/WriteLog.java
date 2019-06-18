package com.chinasofti.Kstore.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

import com.chinasofti.Kstore.vo.Collect;
import com.chinasofti.Kstore.vo.Goods;
import com.chinasofti.Kstore.vo.User;



public class WriteLog {
	private static Logger logger = Logger.getLogger("Kstore");  
    public static void writeLogin(User user)
    {
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time=df.format(new Date());
    	String content=";"+user.getUser_id()+";"+time+";yes;no;null;null;null;no;null;no";	
    	logger.debug(content);
    }
    public static void writeGoods(User user,Goods goods)
    {
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time=df.format(new Date());
    	String content;
    	if(user!=null)
    	{
    		content=";"+user.getUser_id()+";"+time+";no,yes;"+goods.getGoods_id()+";"+goods.getName()+";"+goods.getType()+";no;null;no";
    	}
    	else
    	{
    		content=";null;"+time+";no,yes;"+goods.getGoods_id()+";"+goods.getName()+";"+goods.getType()+";no;null;no";
    	}
    	logger.debug(content);
    }
    public static void writeSearch(String message)
    {
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time=df.format(new Date());
    	String content=";null;"+time+";no;no;null;null;null;yes;"+message+";no";
    	logger.debug(content);
    }
    public static void writeOrder(Collect collect)
    {
    	DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time=df.format(new Date());
    	String content=";null;"+time+";no;no;"+collect.getGoods_id()+"£»null;null;no;null;yes";
    	logger.debug(content);
    }
    public static void main(String[] args)
    {
    	User user=new User();
    	user.setUser_id(15);
    	Goods goods=new Goods();
    	goods.setGoods_id(5);
    	goods.setName("iphone");
    	goods.setType("phone");
    	Collect c=new Collect();
    	c.setGoods_id(1);
    	writeOrder(c);
    	//writeGoods(user,goods);
    	//writeLogin(user);
    	writeSearch("»ªÎª");
    }
}
