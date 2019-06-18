package com.chinasofti.Kstore.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.chinasofti.Kstore.service.CollectService;
import com.chinasofti.Kstore.service.GoodsService;
import com.chinasofti.Kstore.service.MessageService;
import com.chinasofti.Kstore.service.OrdersService;
import com.chinasofti.Kstore.service.SearchService;
import com.chinasofti.Kstore.service.UserService;
import com.chinasofti.Kstore.util.WriteLog;
import com.chinasofti.Kstore.vo.Collect;
import com.chinasofti.Kstore.vo.Goods;
import com.chinasofti.Kstore.vo.Message;
import com.chinasofti.Kstore.vo.Orders;
import com.chinasofti.Kstore.vo.OrdersGoods;
import com.chinasofti.Kstore.vo.Search;
import com.chinasofti.Kstore.vo.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
@SessionAttributes(value= {"attr1","attr2","user1"})
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	GoodsService goodsService;
	@Autowired
	CollectService collectService;
	@Autowired
	OrdersService ordersService;
	@Autowired
	MessageService messageservice;
	@Autowired
	SearchService searchservice;
	
	boolean flag = false;
	boolean existsUser =false;
	User nowUser=new User();
	
	//1
	//设置日期格式
	public static String setFormat(Date date) {
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simple.format(date);
	}
	
	
	@ModelAttribute()
	public void getHotSearch(Model model) {
		List<Search> searches =  searchservice.getFourHotSearch();
		model.addAttribute("searchlist", searches);
	}
	
	@RequestMapping(value="/contact",method=RequestMethod.GET)
	public String gotoContact(Model model) {
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				//System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			return "contact";
			//END - author: minhe
		}
		
		return "contact";
	}
	
	//2
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String gotoLogin(Model model) {
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList<Goods>();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				//System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			return "index";
			//END - author: minhe
		}
		
		return "index";
	}
	
	/*
	 * 登录请求
	 */
	//3
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String gotoLogin(Model model,HttpServletRequest req) {
		//如果没有登录
		if(existsUser==false) {
			return "customer-login";
		}
		//如果已经登录成功了，就前往订单界面
		else {
			//START - author: minhe
			int user_id = nowUser.getUser_id();
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			
			//订单计数
			int ordersCount = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//特殊的列表，orders页面专用
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("login页面成功");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("进入循环");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				//订单计数
				ordersCount += 1;
				
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders编号
				ordersGoods.setOrders_id(o.getOrder_id());
				//时间
				ordersGoods.setBuy_time(o.getBuy_time());
				//金钱
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//状态
				ordersGoods.setState(o.getState());
				//插入准备返回的列表
				ordersGoodsList.add(ordersGoods);
			}
			model.addAttribute("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			model.addAttribute("orderscount", ordersCount);
			
			//设置用户界面信息
			req.setAttribute("profilename", nowUser.getName());
		    req.setAttribute("profileprovince", nowUser.getProvince());
		    req.setAttribute("profilecity", nowUser.getCity());
	  	    req.setAttribute("profiledistrict", nowUser.getDistrict());
	    	req.setAttribute("profilestreet", nowUser.getStreet());
			req.setAttribute("url", nowUser.getProfile_photo());
			req.setAttribute("profilephoto", nowUser.getProfile_photo());
			return "customer-orders";
		}
	}
	
	/*
	 * 登录post
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView Login(Model model,@RequestParam("loginname") String name,
			@RequestParam("loginpassword") String pwd,HttpServletRequest req) {
		//返回视图
		ModelAndView mv = new ModelAndView();
		//得到当前用户
		User user = new User();
		user.setName(name);
		user.setPassword(pwd);
		User user1 =null;
		user1=userService.Login(user);
		//如果密码错误
		if(user1==null) {
			req.setAttribute("loginmessage", "用户名或密码错误，请重试");
			mv.setViewName("customer-login");
		}
		//登录成功
		else {
			//START - author: minhe
			int user_id =user1.getUser_id();
			WriteLog.writeLogin(user1);
			//debug
			System.out.println("user_id: " + user_id);
			
			//订单计数
			int ordersCount = 0;
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//特殊的列表，orders页面专用
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("login页面成功");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("进入循环");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				//订单计数
				ordersCount += 1;
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders编号
				ordersGoods.setOrders_id(o.getOrder_id());
				//时间
				ordersGoods.setBuy_time(o.getBuy_time());
				//金钱
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//状态
				ordersGoods.setState(o.getState());
				//插入准备返回的列表
				ordersGoodsList.add(ordersGoods);
			}
			
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			model.addAttribute("orderscount", ordersCount);
			
			mv.addObject("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			
			mv.setViewName("customer-orders");
			//设置会话信息
			mv.addObject("attr1",name);
			mv.addObject("attr2", pwd);
			mv.addObject("user1", user1);
			//设置页面信息
		    req.setAttribute("profilename", name);
		    req.setAttribute("profileprovince", user1.getProvince());
		    req.setAttribute("profilecity", user1.getCity());
		    req.setAttribute("profiledistrict", user1.getDistrict());
		    req.setAttribute("profilestreet", user1.getStreet());
		    req.setAttribute("url", user1.getProfile_photo());
			req.setAttribute("profilephoto", user1.getProfile_photo());
			req.setAttribute("faith_valuemessage", user1.getFaith_value());
			//保存用户信息，提供给login get方法使用
			nowUser=user1;
			existsUser=true;
		}
		return mv;
	}
	
	/*
	 * 前往注册页面
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String gotoRegister() {
		return "customer-login";
	}
	
	/*
	 * 注册提交
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView Register(@RequestParam("registername") String name,
			@RequestParam("registerpassword") String pwd,
			@RequestParam("registeremail") String email,
			HttpServletRequest req) {
		//获取用户输入
		User user=new User();
		ModelAndView mv=new ModelAndView();
		user.setName(name);
		user.setPassword(pwd);
		user.setEmail(email);
		//如果用户名已存在
		if(flag==true) {
			req.setAttribute("checkmessage", "请更换用户名");
			mv.setViewName("customer-login");
		}
		else {
			int result=userService.Register(user);
			if(result==0) {
				req.setAttribute("checkmessage","注册失败，请重试");
				mv.setViewName("customer-login");
			}
			else {
				//设置页面信息
				mv.setViewName("customer-orders");
			    req.setAttribute("profilename", name);
			    req.setAttribute("profileprovince", user.getProvince());
				req.setAttribute("profilecity", user.getCity());
				req.setAttribute("profiledistrict", user.getDistrict());
				req.setAttribute("profilestreet", user.getStreet());
				//保存用户信息
				existsUser=true;
				nowUser=user;
				//设置会话
				mv.addObject("attr1",name);
				mv.addObject("attr2", pwd);
			}
		}
		return mv;
	}
	
	/*
	 * 检查用户名是否已存在
	 */
	@RequestMapping(value="/checkuser",method=RequestMethod.POST,produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String checkUser(@RequestParam("registername") String name) {
		//获取用户输入的用户名
		User user =new User();
		//标识符设置为未被使用
		flag=false;
		String message="";
		user.setName(name);
		User user1=null;
		//查找是否已有此使用用户名的用户
		user1=userService.CheckUser(user);
		//如果有
		if(user1!=null) {
			message="此用户名已被占用，请重试";
			//标识符设置为已被使用
			flag=true;
		}
		return message;
	}
	
	/*
	 * 用户登出
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView Logout(SessionStatus status) {
		ModelAndView mv=new ModelAndView("customer-login");
		status.setComplete();
		//当前无用户
		existsUser=false;
		nowUser=null;
		return mv;
	}
	
	/*
	 * 订单页面请求 
	 */
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String gotoOrder(Model model,HttpServletRequest req,
			@RequestParam("order_id")int order_id,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//获取用户信息
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//用于view按钮的后续
		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setState("等待配送");
		ordersGoods.setBuy_time(ordersService.getOrderById(order_id).getBuy_time());
		ordersGoods.setGoods_price(goodsService.GetGoodsById(ordersService.getOrderById(order_id).getGoods_id()).getPrice());
		ordersGoods.setOrders_id(order_id);
		//用于view的物品图片显示
		String viewPicture = goodsService.GetGoodsById(ordersService.getOrderById(order_id).getGoods_id()).getPictures();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		model.addAttribute("vieworder", ordersGoods);
		model.addAttribute("viewpicture", viewPicture);
		model.addAttribute("viewgoodsname", goodsService.GetGoodsById(ordersService.getOrderById(ordersGoods.getOrders_id()).getGoods_id()).getName());
		model.addAttribute("goods", goodsService.GetGoodsById(ordersService.getOrderById(ordersGoods.getOrders_id()).getGoods_id()));
		//设置页面信息
		req.setAttribute("profilename", user1.getName());
	    req.setAttribute("profileprovince", user1.getProvince());
	    req.setAttribute("profilecity", user1.getCity());
	    req.setAttribute("profiledistrict", user1.getDistrict());
	    req.setAttribute("profilestreet", user1.getStreet());
		req.setAttribute("profilephoto", user1.getProfile_photo());
		 req.setAttribute("faith_valuemessage", user1.getFaith_value());
		req.setAttribute("url", user1.getProfile_photo());
		return "customer-order";
	}
	
	/*
	 * 总订单页面请求
	 */
	@RequestMapping(value="/orders",method=RequestMethod.GET)
	public String gotoOrders(Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//获取用户信息
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//orders计数统计
		int ordersCount = 0;
        //商品件数统计
		int count = 0;
		//商品金额统计
		float total = 0;
		//store the collect list
		List<Orders> ordersList = ordersService.getOrderByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> goodsList = new ArrayList();
		
		//特殊的列表，orders页面专用
		List<OrdersGoods> ordersGoodsList = new ArrayList();

		//iterate and add to result
		for(Orders o: ordersList) {

			count += 1;
			total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
			System.out.println("goods_id: " + o.getGoods_id());
			goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
			
			//计数
			ordersCount += 1;
			OrdersGoods ordersGoods = new OrdersGoods();
			//orders编号
			ordersGoods.setOrders_id(o.getOrder_id());
			System.out.println(o.getOrder_id());
			//时间
			ordersGoods.setBuy_time(o.getBuy_time());
			System.out.println(o.getBuy_time());
			//金钱
			ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
			System.out.println(ordersGoods.getGoods_price());
			//状态
			ordersGoods.setState(o.getState());
			System.out.println(o.getState());
			//插入准备返回的列表
			ordersGoodsList.add(ordersGoods);
		}
		
		//向页面传值
		model.addAttribute("goods", goodsList);
		model.addAttribute("orders", ordersList);
		model.addAttribute("ordersgoods", ordersGoodsList);
		model.addAttribute("total", total);
		model.addAttribute("count", count);
		model.addAttribute("orderscount", ordersCount);
		
		req.setAttribute("goods", goodsList);
		req.setAttribute("ordersgoods", ordersGoodsList);
	    req.setAttribute("faith_valuemessage", user1.getFaith_value());
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//设置页面个人信息
		req.setAttribute("profilename", user1.getName());
	    req.setAttribute("profileprovince", user1.getProvince());
	    req.setAttribute("profilecity", user1.getCity());
	    req.setAttribute("profiledistrict", user1.getDistrict());
	    req.setAttribute("profilestreet", user1.getStreet());
		req.setAttribute("profilephoto", user1.getProfile_photo());
		req.setAttribute("url", user1.getProfile_photo());
		return "customer-orders";
	}
	
	/*
	 * 个人信息
	 */
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public String gotoAccount(Model model,HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//获取用户信息
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//设置页面用户信息
		req.setAttribute("profilename", user1.getName());
	    req.setAttribute("profileprovince", user1.getProvince());
	    req.setAttribute("profilecity", user1.getCity());
	    req.setAttribute("profiledistrict", user1.getDistrict());
	    req.setAttribute("profilestreet", user1.getStreet());
		req.setAttribute("faith_valuemessage", user1.getFaith_value());
		req.setAttribute("url", user1.getProfile_photo());
		return "customer-account";
	}
	
	/*
	 * 更改密码
	 */
	@RequestMapping(value="/changepwd",method=RequestMethod.POST)
	public ModelAndView changPwd(Model model,@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword,
			@RequestParam("confirmpassword") String confirmpassword,
			HttpServletRequest req,@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//设置返回视图
		ModelAndView mv=new ModelAndView();
		//如果输入密码错误
		if(oldpassword.equals(attr2)==false) {
			req.setAttribute("checkpwdmessage","输入密码错误，请重试");
		}
		else if(newpassword.equals(confirmpassword)==false) {
			req.setAttribute("checkpwdmessage","两次输入不一致，请重试");
		}
		//输入原密码正确
		else {
			//获取当前用户
			User user=new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1=userService.Login(user);
			user1.setPassword(newpassword);
			int result=userService.ChangePwd(user1);
			//如果数据库插入失败
			if(result==0) {
				req.setAttribute("checkpwdmessage", "更改密码失败，请重试");
			}
			//成功
			else {
				//设置页面用户信息
				mv.addObject("attr2",newpassword);
				req.setAttribute("profilename", user1.getName());
			    req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
			    req.setAttribute("profiledistrict", user1.getDistrict());
			    req.setAttribute("profilestreet", user1.getStreet());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("url", user1.getProfile_photo());
				req.setAttribute("checkpwdmessage", "更改密码成功");
			}
		}
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		mv.setViewName("customer-account");
		return mv;
		}
	
	/*
	 * 更改照片
	 */
	@RequestMapping(value="/changephoto",method=RequestMethod.POST)
	public ModelAndView ChangePhoto(Model model,@RequestParam("profilephoto") MultipartFile profilephoto,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2,
			HttpServletRequest req) throws IllegalStateException, IOException {
		ModelAndView mv = new ModelAndView();
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//1.创建上传文件路径
		String uploadPath =req.getServletContext().getRealPath("upload");
		System.out.println(uploadPath);
		//如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		//2.创建保存文件路径
		//a.获取文件扩展名 例：.jpg
		String realFileName = profilephoto.getOriginalFilename();
		String[] tmp = realFileName.split("\\.");
		String type =tmp[tmp.length-1];
		String fileName = ""+System.currentTimeMillis()+"."+type;
		//c.创建新文件
		String filePath = uploadPath + File.separator + fileName;//File.separaotr为/(斜杠)	
		System.out.println("filepath:"+filePath);
		//3.把文件内容写入文件
		profilephoto.transferTo(new File(filePath));
		//4.设置表示文件的src
		String displayFileName=req.getContextPath()+File.separator + "upload"+File.separator +fileName;
		System.out.println("displayFileName:"+displayFileName);
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//设置页面用户信息
		req.setAttribute("url", displayFileName);
		user1.setProfile_photo(displayFileName);
		userService.ChangeProfile(user1);
		req.setAttribute("profilename", user1.getName());
	    req.setAttribute("profileprovince", user1.getProvince());
	    req.setAttribute("profilecity", user1.getCity());
	    req.setAttribute("profiledistrict", user1.getDistrict());
	    req.setAttribute("profilestreet", user1.getStreet());
		req.setAttribute("faith_valuemessage", user1.getFaith_value());
	    mv.setViewName("customer-account");
		return mv;
		}
	
	/*
	 * 更改个人简介
	 */
    @RequestMapping(value="/changeprofile",method=RequestMethod.POST)
    public ModelAndView changPwd(Model model,@RequestParam("birthday") String birthday,
    		@RequestParam("gender") String gender,
    		@RequestParam("phone") String phone,
    		@RequestParam("email") String email,
    		@RequestParam("province") String province,
    		@RequestParam("city") String city,
    		@RequestParam("district") String district,
    		@RequestParam("street") String street,
    		HttpServletRequest req,
    		@ModelAttribute("attr1")String attr1, 
    		@ModelAttribute("attr2")String attr2) {
    	//设置返回视图
    	ModelAndView mv=new ModelAndView();
    	//获取当前用户
    	User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		//重新设值
		user1.setBirthday(birthday);
		user1.setGender(gender);
		user1.setPhone_num(phone);
		user1.setEmail(email);
		user1.setProvince(province);
		user1.setCity(city);
		user1.setDistrict(district);
		user1.setStreet(street);
		try {
			int result=userService.ChangeProfile(user1);
			//如果更改失败
			if(result==0) {
				req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
				req.setAttribute("profilename", user1.getName());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("changeprofilemessage", "更改失败，请重试");
				mv.setViewName("customer-account");
			}
			//成功
			else {
				req.setAttribute("profilename", user1.getName());
			    req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
			    req.setAttribute("profiledistrict", user1.getDistrict());
			    req.setAttribute("profilestreet", user1.getStreet());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("url", user1.getProfile_photo());
				req.setAttribute("changeprofilemessage", "更改成功");
				mv.setViewName("customer-account");
			}
		}catch(Exception e) {
			req.setAttribute("profileprovince", user1.getProvince());
		    req.setAttribute("profilecity", user1.getCity());
			req.setAttribute("profilename", user1.getName());
			req.setAttribute("faith_valuemessage", user1.getFaith_value());
			req.setAttribute("changeprofilemessage", "更改失败，请重试");
			mv.setViewName("customer-account");
		}
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
    	return mv;
    	}
	    
    //跳转到商品发布页面
    @RequestMapping(value="/post",method=RequestMethod.GET)
	public String gotoPost(Model model,HttpServletRequest req) {
    	if(existsUser==false) {
    		return "customer-login";
    	}
    	else {
    		int user_id=nowUser.getUser_id();
    		
    		//copy
    		//商品件数统计
    		int cartbarcount = 0;
    		//商品金额统计
    		float cartbartotal = 0;

    		//store the collect list
    		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
    		
    		//store the goods list
    		List<Goods> cartBarGoodsList = new ArrayList();
    		
    		//iterate and add to result
    		for(Collect c: cartBarCollectList) {
    			cartbarcount += 1;
    			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
    			System.out.println("goods_id: " + c.getGoods_id());
    			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
    		}
    		
    		//向页面传值
    		model.addAttribute("cartbargoods", cartBarGoodsList);
    		model.addAttribute("cartbartotal", cartbartotal);
    		model.addAttribute("cartbarcount", cartbarcount);
    		
        	req.setAttribute("profilename", nowUser.getName());
    	    req.setAttribute("profileprovince", nowUser.getProvince());
    	    req.setAttribute("profilecity", nowUser.getCity());
    	    req.setAttribute("faith_valuemessage", nowUser.getFaith_value());
    	    req.setAttribute("url", nowUser.getProfile_photo());
    	    //获得当前用户id
    	    //获取用户发布的商品
    	    List<Goods> goodsList=userService.MyPost(user_id);
    	    model.addAttribute("goodsList", goodsList);
    	    for(Goods goods:goodsList) {
    	    	System.out.println("goods:"+goods);
    	    }
        	return "post";
    	}
    }
    
    //商品发布
    //author:zhongteng
    //time:8.27 4:30
    @RequestMapping(value="/goodspost",method=RequestMethod.POST)
    public ModelAndView Post(Model model,
    		@RequestParam("goodsphoto") MultipartFile goodsphoto,
    		@RequestParam("goodstitle") String goodstitle,
    		@RequestParam("goodstype") String goodstype,
    		@RequestParam("goodsdescription") String goodsdescription,
    		@RequestParam("goodsprice") float goodsprice,
    		HttpServletRequest req) throws IllegalStateException, IOException {
    	ModelAndView mv=new ModelAndView();
    	if(existsUser==false){
    		mv.setViewName("customer-login");
    		return mv;
    	}
		//设置准备插入数据库的goods
		Goods goods=new Goods();
		goods.setName(goodstitle);
		goods.setType(goodstype);
		goods.setOwner_id(nowUser.getUser_id());
		goods.setRelease_time(setFormat(new Date()));
		goods.setDescription(goodsdescription);
		goods.setPrice(goodsprice);
		//插入图片
		//1.创建上传文件路径
		String uploadPath =req.getServletContext().getRealPath("upload");
		System.out.println(uploadPath);
		//如果目录不存在则创建
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		//2.创建保存文件路径
		//a.获取文件扩展名 例：.jpg
		String realFileName = goodsphoto.getOriginalFilename();
		String[] tmp = realFileName.split("\\.");
		String type =tmp[tmp.length-1];
		String fileName = ""+System.currentTimeMillis()+"."+type;
		//c.创建新文件
		String filePath = uploadPath + File.separator + fileName;//File.separaotr为/(斜杠)	
		System.out.println("filepath:"+filePath);
		//3.把文件内容写入文件
		goodsphoto.transferTo(new File(filePath));
		//4.设置表示文件的src
		String displayFileName=req.getContextPath()+File.separator + "upload"+File.separator +fileName;
		goods.setPictures(displayFileName);
		//插入数据库
		System.out.print(goods);
		int result=userService.Post(goods);
//			mv.addObject("url", displayFileName);
		if(result==0) {
			//插入失败，给提示
			req.setAttribute("postmessage", "发布失败，请重试");
		}
		else {
			req.setAttribute("postmessage", "发布成功");
		}
	    //获取用户发布的商品
	    List<Goods> goodsList=userService.MyPost(nowUser.getUser_id());
	    
         //copy
  		//商品件数统计
  		int cartbarcount = 0;
  		//商品金额统计
  		float cartbartotal = 0;

  		//store the collect list
  		List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
  		
  		//store the goods list
  		List<Goods> cartBarGoodsList = new ArrayList();
  		
  		//iterate and add to result
  		for(Collect c: cartBarCollectList) {
  			cartbarcount += 1;
  			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
  			System.out.println("goods_id: " + c.getGoods_id());
  			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
  		}
  		
  		//向页面传值
  		model.addAttribute("cartbargoods", cartBarGoodsList);
  		model.addAttribute("cartbartotal", cartbartotal);
  		model.addAttribute("cartbarcount", cartbarcount);
	    
	  //设置页面用户信息
		model.addAttribute("goodsList", goodsList);
		req.setAttribute("profilename", nowUser.getName());
	    req.setAttribute("profileprovince", nowUser.getProvince());
	    req.setAttribute("profilecity", nowUser.getCity());
	    req.setAttribute("faith_valuemessage", nowUser.getFaith_value());
	    req.setAttribute("url", nowUser.getProfile_photo());
	    mv.setViewName("post");
    	return mv;
    }
	    
    /*
     * author: minhe
     * fields: orders
     * describe: 添加到购物车
     */
    @RequestMapping(value="/addCollect")
    public String addCollect(
      Model model,
      @RequestParam(value="detailnum") int detailnum) {
    	if(existsUser==false) {
    		return "customer-login";
    	}
     //用id找到当前物品
     Goods goods=goodsService.getGoodsByGoodsId(detailnum);
     //找到物品发布者
     User user=userService.SelById(goods.getOwner_id());
     //推荐商品内容
     List<Goods> mightLikeList=new ArrayList<>(50);
     mightLikeList=goodsService.GetCategoryOnlySix(goods.getType());
           //将物品信息和发布者信息传给前端
     model.addAttribute("goods", goods);
     model.addAttribute("user", user);
     //将mapping传给前端
     model.addAttribute("mightlike", mightLikeList);
     
     if(existsUser==false) {
    	 return "customer-login";
     }
     int user_id =nowUser.getUser_id();
     System.out.println(user_id);
     //插入数据库部分
     Collect collect = new Collect();
     collect.setCollector_id(user_id);
     collect.setGoods_id(goods.getGoods_id());
     Collect collect1=null;
     collect1=collectService.getCollectByUserIdAndGoodsId(collect);
     if(collect1==null) {
    	 collect1 = new Collect();
    	 collect1.setCollector_id(user_id);
	     collect1.setGoods_id(goods.getGoods_id());
	     int result = 0;
	     if(!goods.isState()) {
	    	 result=collectService.addCollect(collect1);
	     }
	     if(result==0) {
	    	 model.addAttribute("collectmessage", "加入购物车失败，请重试");
	     }else {
	    	 model.addAttribute("collectmessage", "加入成功");
	     }
     }else {
    	 model.addAttribute("collectmessage", "已存在此商品");
     }
     
    //copy
	//商品件数统计
	int cartbarcount = 0;
	//商品金额统计
	float cartbartotal = 0;

	//store the collect list
	List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
	
	//store the goods list
	List<Goods> cartBarGoodsList = new ArrayList();
	
	//iterate and add to result
	for(Collect c: cartBarCollectList) {
		cartbarcount += 1;
		cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
		System.out.println("goods_id: " + c.getGoods_id());
		cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
	}
	
	//向页面传值
	model.addAttribute("cartbargoods", cartBarGoodsList);
	model.addAttribute("cartbartotal", cartbartotal);
	model.addAttribute("cartbarcount", cartbarcount);
     
     return "detail";
    }
    
    //进入数码电子类
	@RequestMapping(value="/digital-product",method=RequestMethod.GET)
	public String gotoDigitalProduct(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//设置分页器
		PageHelper.startPage(pn,shownum);
		//获取数码电子类商品信息
		List<Goods> goodsList=new ArrayList<>();
		//排序方法 
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("数码电子");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("数码电子");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("数码电子");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("数码电子");
		}
		//封装到分页器中
		PageInfo page = new PageInfo(goodsList);

		//如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "数码电子");
        //将参数传给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//将mapping信息传给前端
		model.addAttribute("page", "digital-product");
		
		if(existsUser==true) {
			 //copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
		}
		
		return "category-no-sidebar";
	}
	
	//进入游戏交易类
	@RequestMapping(value="/game-trade",method=RequestMethod.GET)
	public String gotoGameTrade(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//设置分页器
		PageHelper.startPage(pn,shownum);
        //获取游戏交易类商品信息
		List<Goods> goodsList=new ArrayList<>();
		//不同的排序方法
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("游戏交易");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("游戏交易");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("游戏交易");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("游戏交易");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "游戏交易");
		 //将参数传给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//传递mapping信息
		model.addAttribute("page", "game-trade");
		return "category-no-sidebar";
	}
	
	//进入生活家居类
	@RequestMapping(value="/home-life",method=RequestMethod.GET)
	public String gotoHomeLife(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		PageHelper.startPage(pn,shownum);
        //获取数码电子类商品信息
		List<Goods> goodsList=new ArrayList<>();
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("生活家居");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("生活家居");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("生活家居");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("生活家居");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "生活家居");
		//将参数传给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//传递mapping
		model.addAttribute("page", "home-life");
		return "category-no-sidebar";
	}
	
	//进入服饰鞋包类
	@RequestMapping(value="/clothes-shoes",method=RequestMethod.GET)
	public String gotoClothesShoes(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		PageHelper.startPage(pn,shownum);
        //获取数码电子类商品信息
		List<Goods> goodsList=new ArrayList<>();
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("服饰鞋包");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("服饰鞋包");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("服饰鞋包");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("服饰鞋包");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "服饰鞋包");
		//将参数传给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//传递mapping
		model.addAttribute("page", "clothes-shoes");
		return "category-no-sidebar";
	}
	
	//进入日常百货类
	@RequestMapping(value="/everything",method=RequestMethod.GET)
	public String gotoEverything(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//设置分页器
		PageHelper.startPage(pn,shownum);
        //获取数码电子类商品信息
		List<Goods> goodsList=new ArrayList<>();
		//不同的排序方法
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("日常百货");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("日常百货");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("日常百货");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("日常百货");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "日常百货");
		//将参数传给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//将mapping信息传给前端
		model.addAttribute("page", "everything");
		return "category-no-sidebar";
	}
	
	//商品总类 category-no-sidebar
	@RequestMapping(value="/category-no-sidebar",method=RequestMethod.GET)
	public String gotoTotal(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//设置分页器
		PageHelper.startPage(pn,shownum);
		 //获取商品总类商品信息
		List<Goods> goodsList=new ArrayList<>();
		//不同的排序方法
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetTotalGoods();
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetTotalGoodsByReleaseTime();
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetTotalGoodsByPrice_1();
		}else {
			goodsList=goodsService.GetTotalGoodsByPrice_2();
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
		model.addAttribute("goodsList", page);
        //设置标题
		model.addAttribute("title", "所有你想要的都在这");
		//参数传给前端
		model.addAttribute("sortfunc", sortfunc);
		model.addAttribute("shownum", shownum);
		//将mapping信息传给前端
		model.addAttribute("page", "category-no-sidebar");
		return "category-no-sidebar";
	}
	
    //搜索条功能
	@RequestMapping(value="/searchgoods")
	public String gotoSearchGoods(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model,@RequestParam("search") String search) {
		WriteLog.writeSearch(search);
		//设置分页器
		PageHelper.startPage(pn,shownum);
		List<Goods> goodsList=new ArrayList<>();
		//不同的排序方法
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.SearchGoods(search);
		}else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.SearchGoodsByReleaseTime(search);
		}else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.SearchGoodsByPrice_1(search);
		}else {
			goodsList=goodsService.SearchGoodsByPrice_2(search);
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
		 //如果没有商品
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "暂无商品出售");
		}
        //分页器传给前端
		model.addAttribute("goodsList", page);
		//搜索内容传给数据库提取和前端
		model.addAttribute("search",search);
        //设置标题
		model.addAttribute("title", search);
		//参数传回给前端
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//将mapping信息传给前端
		model.addAttribute("page", "searchgoods");
		return "category-no-sidebar"; 
	}
	
	//进入商品详情
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String gotoDetail(Model model,@RequestParam(value="detailnum") int detailnum) {
		//用id找到当前物品
		Goods goods=goodsService.getGoodsByGoodsId(detailnum);
		//找到物品发布者
		User user=userService.SelById(goods.getOwner_id());
		WriteLog.writeGoods(user, goods);
		//推荐商品内容
		List<Goods> mightLikeList=new ArrayList<>(50);
		mightLikeList=goodsService.GetCategoryOnlySix(goods.getType());
		
		if(existsUser==true) {
			 //copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(nowUser.getUser_id());
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
		}
		
        //将物品信息和发布者信息传给前端
		model.addAttribute("goods", goods);
		model.addAttribute("user", user);
		//购物车按钮信息
		model.addAttribute("collectmessage", "加入购物车");
		//将mapping传给前端
		model.addAttribute("mightlike", mightLikeList);
		
		// 获取商品详情页面的商品评论部分
		List<Message> messages = new ArrayList<Message>();
		messages = messageservice.getAllMessagesByGoodsid(detailnum);
		model.addAttribute("messages", messages);
		return "detail";
	}
	
    //进入商品详情
	@RequestMapping(value="/quickview",method=RequestMethod.POST)
	@ResponseBody
	public Goods gotoQuickView(Model model,@RequestParam(value="detailnum") int detailnum) {
		//用id找到当前物品
		Goods goods=goodsService.getGoodsByGoodsId(detailnum);
        //将物品信息和发布者信息传给前端
		model.addAttribute("goods", goods);
		//购物车按钮信息
		model.addAttribute("collectmessage", "加入购物车");
		System.out.println(goods);
		return goods;
	}
	
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 更新订单状态
	 */
	@RequestMapping(value="/confirmById",method=RequestMethod.GET)
	public String confirmById(
			Model model,
			HttpServletRequest req,
			@RequestParam(value="order_id") int order_id,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
				
		if(nowUser==null) {
			return "customer-login";
		} else {
			
			//用户验证
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			
			//更新orders状态
			ordersService.confirmById(order_id);
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//特殊的列表，orders页面专用
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("login页面成功");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("进入循环");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders编号
				ordersGoods.setOrders_id(o.getOrder_id());
				//时间
				ordersGoods.setBuy_time(o.getBuy_time());
				//金钱
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//状态
				if(o.getOrder_id() == order_id) {

					userService.AddFaithValueById(ordersService.getOrderById(order_id).getSeller_id());
					ordersService.confirmById(order_id);
					System.out.println(order_id);
					ordersGoods.setState("已完成");
				} else {
					
					ordersGoods.setState(o.getState());
				}
				//插入准备返回的列表
				ordersGoodsList.add(ordersGoods);
			}
			model.addAttribute("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			if(existsUser==true) {
				 //copy
				//商品件数统计
				int cartbarcount = 0;
				//商品金额统计
				float cartbartotal = 0;

				//store the collect list
				List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
				
				//store the goods list
				List<Goods> cartBarGoodsList = new ArrayList();
				
				//iterate and add to result
				for(Collect c: cartBarCollectList) {
					cartbarcount += 1;
					cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
					System.out.println("goods_id: " + c.getGoods_id());
					cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
				}
				
				//向页面传值
				model.addAttribute("cartbargoods", cartBarGoodsList);
				model.addAttribute("cartbartotal", cartbartotal);
				model.addAttribute("cartbarcount", cartbarcount);
			}
			
			 req.setAttribute("profilename", nowUser.getName());
			 req.setAttribute("profileprovince", nowUser.getProvince());
		     req.setAttribute("profilecity", nowUser.getCity());
	  	     req.setAttribute("profiledistrict", nowUser.getDistrict());
			 req.setAttribute("profilestreet", nowUser.getStreet());
			 req.setAttribute("url", nowUser.getProfile_photo());
			 req.setAttribute("profilephoto", nowUser.getProfile_photo());
			 req.setAttribute("faith_valuemessage", nowUser.getFaith_value());
			return "customer-orders";
		}
	}
	
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 进入结算界面1
	 */
	@RequestMapping(value="/checkout1",method=RequestMethod.GET)
	public String gotoCheckout1(Model model, HttpServletRequest req) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			int user_id =nowUser.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			if(existsUser==true) {
				 //copy
				//商品件数统计
				int cartbarcount = 0;
				//商品金额统计
				float cartbartotal = 0;

				//store the collect list
				List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
				
				//store the goods list
				List<Goods> cartBarGoodsList = new ArrayList();
				
				//iterate and add to result
				for(Collect c: cartBarCollectList) {
					cartbarcount += 1;
					cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
					System.out.println("goods_id: " + c.getGoods_id());
					cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
				}
				
				//向页面传值
				model.addAttribute("cartbargoods", cartBarGoodsList);
				model.addAttribute("cartbartotal", cartbartotal);
				model.addAttribute("cartbarcount", cartbarcount);
			}
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			if(collectList.isEmpty()) {
			model.addAttribute("cartmessage", "当前购物车内没有物品");
			return "cart";
		    }
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				count += 1;
				total += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				goodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			
			//向页面传值
			model.addAttribute("goods", goodsList);
			model.addAttribute("total", total);
			model.addAttribute("count", count);
			//debug
			req.setAttribute("goods", goodsList);
			System.out.println(req.getAttribute("goods").toString());
			return "checkout1";
		}
	}
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 进入结算界面2
	 */
	@RequestMapping(value="/checkout2",method=RequestMethod.GET)
	public String gotoCheckout2(Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//用户验证
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				count += 1;
				total += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				goodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			if(existsUser==true) {
				 //copy
				//商品件数统计
				int cartbarcount = 0;
				//商品金额统计
				float cartbartotal = 0;

				//store the collect list
				List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
				
				//store the goods list
				List<Goods> cartBarGoodsList = new ArrayList();
				
				//iterate and add to result
				for(Collect c: cartBarCollectList) {
					cartbarcount += 1;
					cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
					System.out.println("goods_id: " + c.getGoods_id());
					cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
				}
				
				//向页面传值
				model.addAttribute("cartbargoods", cartBarGoodsList);
				model.addAttribute("cartbartotal", cartbartotal);
				model.addAttribute("cartbarcount", cartbarcount);
			}
			
			//向页面传值
			model.addAttribute("goods", goodsList);
			model.addAttribute("total", total);
			model.addAttribute("count", count);
			//debug
			req.setAttribute("goods", goodsList);
			System.out.println(req.getAttribute("goods").toString());
			return "checkout2";
		}
	}
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 进入结算界面3
	 */
	@RequestMapping(value="/checkout3",method=RequestMethod.GET)
	public String gotoCheckout3(Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//用户验证
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				count += 1;
				total += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				goodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			
			 //copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//向页面传值
			model.addAttribute("goods", goodsList);
			model.addAttribute("total", total);
			model.addAttribute("count", count);
			//debug
			req.setAttribute("goods", goodsList);
			System.out.println(req.getAttribute("goods").toString());
			return "checkout3";
		}
	}
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 进入结算界面4
	 */
	@RequestMapping(value="/checkout4",method=RequestMethod.GET)
	public String gotoCheckout4(
			Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//用户验证
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				count += 1;
				total += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				goodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//向页面传值
			model.addAttribute("goods", goodsList);
			model.addAttribute("total", total);
			model.addAttribute("count", count);
			//debug
			req.setAttribute("goods", goodsList);
			System.out.println(req.getAttribute("goods").toString());
			return "checkout4";
		}
	}
	/*
	 * author: minhe
	 * fields: orders
	 * describe: 进入结算界面5
	 */
	@RequestMapping(value="/checkout5",method=RequestMethod.GET)
	public String gotoCheckout5(Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//用户验证
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//商品件数统计
			int count = 0;
			//商品金额统计
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//store the orders list
			List<Orders> ordersList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				//新建Orders对象，以便插入
				WriteLog.writeOrder(c);
				Orders orders = new Orders();
				orders.setSeller_id(goodsService.getGoodsByGoodsId(c.getGoods_id()).getOwner_id());
				orders.setBuyer_id(user_id);
				orders.setGoods_id(c.getGoods_id());
				orders.setBuy_time(setFormat(new Date()));
				orders.setState("等待送达");
				//插入Orders对象
				ordersService.addOrder(orders);
				System.out.println("goods_id: " + c.getGoods_id());
				//从collect中删除
				collectService.deleteCollectByCollectId(c.getCollect_id());
				//将商品改为已出售
				goodsService.changeGoodsState(c.getGoods_id());
			}
			
			//copy
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
				//将商品改为已出售
				goodsService.changeGoodsState(c.getGoods_id());
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//向页面传值
			model.addAttribute("total", 0);
			model.addAttribute("count", 0);
			return "checkout5";
		}
	}
	
	/*
	 * author: minhe
	 * fields: cart
	 * describe: 进入购物车页面
	 */
	@RequestMapping(value="/cart",method=RequestMethod.GET)
	public String gotoCart(Model model, HttpServletRequest req) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			int user_id =nowUser.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//商品件数统计
			int cartbarcount = 0;
			//商品金额统计
			float cartbartotal = 0;

			//store the collect list
			List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> cartBarGoodsList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: cartBarCollectList) {
				cartbarcount += 1;
				cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			}
			
			//向页面传值
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			return "cart";
		}
	}

	/*
	 * author: minhe
	 * fields: cart
	 * describe: 购物车删除物品
	 */
	@RequestMapping(value="/deleteCart",method=RequestMethod.GET)
	public String deleteCart(@RequestParam(value="delete_good_id") int delete_good_id, Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		
		//用户验证
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		int user_id =user1.getUser_id();
		//debug
		System.out.println("user_id: " + user_id);
		
		//商品件数统计
		int count = 0;
		//商品金额统计
		float total = 0;
		//store the collect list
		List<Collect> collectList = collectService.getCollectByUserId(user_id);
		
		//store the goods list
		List<Goods> goodsList = new ArrayList();

		//iterate and add to result
		for(Collect c: collectList) {
			if(c.getGoods_id() != delete_good_id) {

				count += 1;
				total += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
				System.out.println("goods_id: " + c.getGoods_id());
				goodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
			} else {

				//从数据库中删除该collect记录
				collectService.deleteCollectByCollectId(c.getCollect_id());
			}
		}
		
		//向页面传值
		model.addAttribute("goods", goodsList);
		model.addAttribute("total", total);
		model.addAttribute("count", count);
		
		//copy
		//商品件数统计
		int cartbarcount = 0;
		//商品金额统计
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user_id);
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//向页面传值
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//debug
		req.setAttribute("goods", goodsList);
		System.out.println(req.getAttribute("goods").toString());
		
		return "cart";
	}		
}
