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
	//�������ڸ�ʽ
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			return "index";
			//END - author: minhe
		}
		
		return "index";
	}
	
	/*
	 * ��¼����
	 */
	//3
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String gotoLogin(Model model,HttpServletRequest req) {
		//���û�е�¼
		if(existsUser==false) {
			return "customer-login";
		}
		//����Ѿ���¼�ɹ��ˣ���ǰ����������
		else {
			//START - author: minhe
			int user_id = nowUser.getUser_id();
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
			float total = 0;
			
			//��������
			int ordersCount = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//������б�ordersҳ��ר��
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("loginҳ��ɹ�");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("����ѭ��");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				//��������
				ordersCount += 1;
				
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders���
				ordersGoods.setOrders_id(o.getOrder_id());
				//ʱ��
				ordersGoods.setBuy_time(o.getBuy_time());
				//��Ǯ
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//״̬
				ordersGoods.setState(o.getState());
				//����׼�����ص��б�
				ordersGoodsList.add(ordersGoods);
			}
			model.addAttribute("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			model.addAttribute("orderscount", ordersCount);
			
			//�����û�������Ϣ
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
	 * ��¼post
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ModelAndView Login(Model model,@RequestParam("loginname") String name,
			@RequestParam("loginpassword") String pwd,HttpServletRequest req) {
		//������ͼ
		ModelAndView mv = new ModelAndView();
		//�õ���ǰ�û�
		User user = new User();
		user.setName(name);
		user.setPassword(pwd);
		User user1 =null;
		user1=userService.Login(user);
		//����������
		if(user1==null) {
			req.setAttribute("loginmessage", "�û������������������");
			mv.setViewName("customer-login");
		}
		//��¼�ɹ�
		else {
			//START - author: minhe
			int user_id =user1.getUser_id();
			WriteLog.writeLogin(user1);
			//debug
			System.out.println("user_id: " + user_id);
			
			//��������
			int ordersCount = 0;
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
			float total = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//������б�ordersҳ��ר��
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("loginҳ��ɹ�");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("����ѭ��");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				//��������
				ordersCount += 1;
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders���
				ordersGoods.setOrders_id(o.getOrder_id());
				//ʱ��
				ordersGoods.setBuy_time(o.getBuy_time());
				//��Ǯ
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//״̬
				ordersGoods.setState(o.getState());
				//����׼�����ص��б�
				ordersGoodsList.add(ordersGoods);
			}
			
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			model.addAttribute("orderscount", ordersCount);
			
			mv.addObject("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			
			mv.setViewName("customer-orders");
			//���ûỰ��Ϣ
			mv.addObject("attr1",name);
			mv.addObject("attr2", pwd);
			mv.addObject("user1", user1);
			//����ҳ����Ϣ
		    req.setAttribute("profilename", name);
		    req.setAttribute("profileprovince", user1.getProvince());
		    req.setAttribute("profilecity", user1.getCity());
		    req.setAttribute("profiledistrict", user1.getDistrict());
		    req.setAttribute("profilestreet", user1.getStreet());
		    req.setAttribute("url", user1.getProfile_photo());
			req.setAttribute("profilephoto", user1.getProfile_photo());
			req.setAttribute("faith_valuemessage", user1.getFaith_value());
			//�����û���Ϣ���ṩ��login get����ʹ��
			nowUser=user1;
			existsUser=true;
		}
		return mv;
	}
	
	/*
	 * ǰ��ע��ҳ��
	 */
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public String gotoRegister() {
		return "customer-login";
	}
	
	/*
	 * ע���ύ
	 */
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public ModelAndView Register(@RequestParam("registername") String name,
			@RequestParam("registerpassword") String pwd,
			@RequestParam("registeremail") String email,
			HttpServletRequest req) {
		//��ȡ�û�����
		User user=new User();
		ModelAndView mv=new ModelAndView();
		user.setName(name);
		user.setPassword(pwd);
		user.setEmail(email);
		//����û����Ѵ���
		if(flag==true) {
			req.setAttribute("checkmessage", "������û���");
			mv.setViewName("customer-login");
		}
		else {
			int result=userService.Register(user);
			if(result==0) {
				req.setAttribute("checkmessage","ע��ʧ�ܣ�������");
				mv.setViewName("customer-login");
			}
			else {
				//����ҳ����Ϣ
				mv.setViewName("customer-orders");
			    req.setAttribute("profilename", name);
			    req.setAttribute("profileprovince", user.getProvince());
				req.setAttribute("profilecity", user.getCity());
				req.setAttribute("profiledistrict", user.getDistrict());
				req.setAttribute("profilestreet", user.getStreet());
				//�����û���Ϣ
				existsUser=true;
				nowUser=user;
				//���ûỰ
				mv.addObject("attr1",name);
				mv.addObject("attr2", pwd);
			}
		}
		return mv;
	}
	
	/*
	 * ����û����Ƿ��Ѵ���
	 */
	@RequestMapping(value="/checkuser",method=RequestMethod.POST,produces = "text/plain;charset=utf-8")
	@ResponseBody
	public String checkUser(@RequestParam("registername") String name) {
		//��ȡ�û�������û���
		User user =new User();
		//��ʶ������Ϊδ��ʹ��
		flag=false;
		String message="";
		user.setName(name);
		User user1=null;
		//�����Ƿ����д�ʹ���û������û�
		user1=userService.CheckUser(user);
		//�����
		if(user1!=null) {
			message="���û����ѱ�ռ�ã�������";
			//��ʶ������Ϊ�ѱ�ʹ��
			flag=true;
		}
		return message;
	}
	
	/*
	 * �û��ǳ�
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public ModelAndView Logout(SessionStatus status) {
		ModelAndView mv=new ModelAndView("customer-login");
		status.setComplete();
		//��ǰ���û�
		existsUser=false;
		nowUser=null;
		return mv;
	}
	
	/*
	 * ����ҳ������ 
	 */
	@RequestMapping(value="/order",method=RequestMethod.GET)
	public String gotoOrder(Model model,HttpServletRequest req,
			@RequestParam("order_id")int order_id,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//��ȡ�û���Ϣ
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
		float cartbartotal = 0;

		//store the collect list
		List<Collect> cartBarCollectList = collectService.getCollectByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> cartBarGoodsList = new ArrayList();
		
		//����view��ť�ĺ���
		OrdersGoods ordersGoods = new OrdersGoods();
		ordersGoods.setState("�ȴ�����");
		ordersGoods.setBuy_time(ordersService.getOrderById(order_id).getBuy_time());
		ordersGoods.setGoods_price(goodsService.GetGoodsById(ordersService.getOrderById(order_id).getGoods_id()).getPrice());
		ordersGoods.setOrders_id(order_id);
		//����view����ƷͼƬ��ʾ
		String viewPicture = goodsService.GetGoodsById(ordersService.getOrderById(order_id).getGoods_id()).getPictures();
		
		//iterate and add to result
		for(Collect c: cartBarCollectList) {
			cartbarcount += 1;
			cartbartotal += goodsService.getGoodsByGoodsId(c.getGoods_id()).getPrice();
			System.out.println("goods_id: " + c.getGoods_id());
			cartBarGoodsList.add(goodsService.getGoodsByGoodsId(c.getGoods_id()));
		}
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		model.addAttribute("vieworder", ordersGoods);
		model.addAttribute("viewpicture", viewPicture);
		model.addAttribute("viewgoodsname", goodsService.GetGoodsById(ordersService.getOrderById(ordersGoods.getOrders_id()).getGoods_id()).getName());
		model.addAttribute("goods", goodsService.GetGoodsById(ordersService.getOrderById(ordersGoods.getOrders_id()).getGoods_id()));
		//����ҳ����Ϣ
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
	 * �ܶ���ҳ������
	 */
	@RequestMapping(value="/orders",method=RequestMethod.GET)
	public String gotoOrders(Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//��ȡ�û���Ϣ
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//orders����ͳ��
		int ordersCount = 0;
        //��Ʒ����ͳ��
		int count = 0;
		//��Ʒ���ͳ��
		float total = 0;
		//store the collect list
		List<Orders> ordersList = ordersService.getOrderByUserId(user1.getUser_id());
		
		//store the goods list
		List<Goods> goodsList = new ArrayList();
		
		//������б�ordersҳ��ר��
		List<OrdersGoods> ordersGoodsList = new ArrayList();

		//iterate and add to result
		for(Orders o: ordersList) {

			count += 1;
			total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
			System.out.println("goods_id: " + o.getGoods_id());
			goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
			
			//����
			ordersCount += 1;
			OrdersGoods ordersGoods = new OrdersGoods();
			//orders���
			ordersGoods.setOrders_id(o.getOrder_id());
			System.out.println(o.getOrder_id());
			//ʱ��
			ordersGoods.setBuy_time(o.getBuy_time());
			System.out.println(o.getBuy_time());
			//��Ǯ
			ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
			System.out.println(ordersGoods.getGoods_price());
			//״̬
			ordersGoods.setState(o.getState());
			System.out.println(o.getState());
			//����׼�����ص��б�
			ordersGoodsList.add(ordersGoods);
		}
		
		//��ҳ�洫ֵ
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
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//����ҳ�������Ϣ
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
	 * ������Ϣ
	 */
	@RequestMapping(value="/account",method=RequestMethod.GET)
	public String gotoAccount(Model model,HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//��ȡ�û���Ϣ
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//����ҳ���û���Ϣ
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
	 * ��������
	 */
	@RequestMapping(value="/changepwd",method=RequestMethod.POST)
	public ModelAndView changPwd(Model model,@RequestParam("oldpassword") String oldpassword,
			@RequestParam("newpassword") String newpassword,
			@RequestParam("confirmpassword") String confirmpassword,
			HttpServletRequest req,@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		//���÷�����ͼ
		ModelAndView mv=new ModelAndView();
		//��������������
		if(oldpassword.equals(attr2)==false) {
			req.setAttribute("checkpwdmessage","�����������������");
		}
		else if(newpassword.equals(confirmpassword)==false) {
			req.setAttribute("checkpwdmessage","�������벻һ�£�������");
		}
		//����ԭ������ȷ
		else {
			//��ȡ��ǰ�û�
			User user=new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1=userService.Login(user);
			user1.setPassword(newpassword);
			int result=userService.ChangePwd(user1);
			//������ݿ����ʧ��
			if(result==0) {
				req.setAttribute("checkpwdmessage", "��������ʧ�ܣ�������");
			}
			//�ɹ�
			else {
				//����ҳ���û���Ϣ
				mv.addObject("attr2",newpassword);
				req.setAttribute("profilename", user1.getName());
			    req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
			    req.setAttribute("profiledistrict", user1.getDistrict());
			    req.setAttribute("profilestreet", user1.getStreet());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("url", user1.getProfile_photo());
				req.setAttribute("checkpwdmessage", "��������ɹ�");
			}
		}
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		mv.setViewName("customer-account");
		return mv;
		}
	
	/*
	 * ������Ƭ
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
		
		//1.�����ϴ��ļ�·��
		String uploadPath =req.getServletContext().getRealPath("upload");
		System.out.println(uploadPath);
		//���Ŀ¼�������򴴽�
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		//2.���������ļ�·��
		//a.��ȡ�ļ���չ�� ����.jpg
		String realFileName = profilephoto.getOriginalFilename();
		String[] tmp = realFileName.split("\\.");
		String type =tmp[tmp.length-1];
		String fileName = ""+System.currentTimeMillis()+"."+type;
		//c.�������ļ�
		String filePath = uploadPath + File.separator + fileName;//File.separaotrΪ/(б��)	
		System.out.println("filepath:"+filePath);
		//3.���ļ�����д���ļ�
		profilephoto.transferTo(new File(filePath));
		//4.���ñ�ʾ�ļ���src
		String displayFileName=req.getContextPath()+File.separator + "upload"+File.separator +fileName;
		System.out.println("displayFileName:"+displayFileName);
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//����ҳ���û���Ϣ
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
	 * ���ĸ��˼��
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
    	//���÷�����ͼ
    	ModelAndView mv=new ModelAndView();
    	//��ȡ��ǰ�û�
    	User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		//������ֵ
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
			//�������ʧ��
			if(result==0) {
				req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
				req.setAttribute("profilename", user1.getName());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("changeprofilemessage", "����ʧ�ܣ�������");
				mv.setViewName("customer-account");
			}
			//�ɹ�
			else {
				req.setAttribute("profilename", user1.getName());
			    req.setAttribute("profileprovince", user1.getProvince());
			    req.setAttribute("profilecity", user1.getCity());
			    req.setAttribute("profiledistrict", user1.getDistrict());
			    req.setAttribute("profilestreet", user1.getStreet());
				req.setAttribute("faith_valuemessage", user1.getFaith_value());
				req.setAttribute("url", user1.getProfile_photo());
				req.setAttribute("changeprofilemessage", "���ĳɹ�");
				mv.setViewName("customer-account");
			}
		}catch(Exception e) {
			req.setAttribute("profileprovince", user1.getProvince());
		    req.setAttribute("profilecity", user1.getCity());
			req.setAttribute("profilename", user1.getName());
			req.setAttribute("faith_valuemessage", user1.getFaith_value());
			req.setAttribute("changeprofilemessage", "����ʧ�ܣ�������");
			mv.setViewName("customer-account");
		}
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
    	return mv;
    	}
	    
    //��ת����Ʒ����ҳ��
    @RequestMapping(value="/post",method=RequestMethod.GET)
	public String gotoPost(Model model,HttpServletRequest req) {
    	if(existsUser==false) {
    		return "customer-login";
    	}
    	else {
    		int user_id=nowUser.getUser_id();
    		
    		//copy
    		//��Ʒ����ͳ��
    		int cartbarcount = 0;
    		//��Ʒ���ͳ��
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
    		
    		//��ҳ�洫ֵ
    		model.addAttribute("cartbargoods", cartBarGoodsList);
    		model.addAttribute("cartbartotal", cartbartotal);
    		model.addAttribute("cartbarcount", cartbarcount);
    		
        	req.setAttribute("profilename", nowUser.getName());
    	    req.setAttribute("profileprovince", nowUser.getProvince());
    	    req.setAttribute("profilecity", nowUser.getCity());
    	    req.setAttribute("faith_valuemessage", nowUser.getFaith_value());
    	    req.setAttribute("url", nowUser.getProfile_photo());
    	    //��õ�ǰ�û�id
    	    //��ȡ�û���������Ʒ
    	    List<Goods> goodsList=userService.MyPost(user_id);
    	    model.addAttribute("goodsList", goodsList);
    	    for(Goods goods:goodsList) {
    	    	System.out.println("goods:"+goods);
    	    }
        	return "post";
    	}
    }
    
    //��Ʒ����
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
		//����׼���������ݿ��goods
		Goods goods=new Goods();
		goods.setName(goodstitle);
		goods.setType(goodstype);
		goods.setOwner_id(nowUser.getUser_id());
		goods.setRelease_time(setFormat(new Date()));
		goods.setDescription(goodsdescription);
		goods.setPrice(goodsprice);
		//����ͼƬ
		//1.�����ϴ��ļ�·��
		String uploadPath =req.getServletContext().getRealPath("upload");
		System.out.println(uploadPath);
		//���Ŀ¼�������򴴽�
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		//2.���������ļ�·��
		//a.��ȡ�ļ���չ�� ����.jpg
		String realFileName = goodsphoto.getOriginalFilename();
		String[] tmp = realFileName.split("\\.");
		String type =tmp[tmp.length-1];
		String fileName = ""+System.currentTimeMillis()+"."+type;
		//c.�������ļ�
		String filePath = uploadPath + File.separator + fileName;//File.separaotrΪ/(б��)	
		System.out.println("filepath:"+filePath);
		//3.���ļ�����д���ļ�
		goodsphoto.transferTo(new File(filePath));
		//4.���ñ�ʾ�ļ���src
		String displayFileName=req.getContextPath()+File.separator + "upload"+File.separator +fileName;
		goods.setPictures(displayFileName);
		//�������ݿ�
		System.out.print(goods);
		int result=userService.Post(goods);
//			mv.addObject("url", displayFileName);
		if(result==0) {
			//����ʧ�ܣ�����ʾ
			req.setAttribute("postmessage", "����ʧ�ܣ�������");
		}
		else {
			req.setAttribute("postmessage", "�����ɹ�");
		}
	    //��ȡ�û���������Ʒ
	    List<Goods> goodsList=userService.MyPost(nowUser.getUser_id());
	    
         //copy
  		//��Ʒ����ͳ��
  		int cartbarcount = 0;
  		//��Ʒ���ͳ��
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
  		
  		//��ҳ�洫ֵ
  		model.addAttribute("cartbargoods", cartBarGoodsList);
  		model.addAttribute("cartbartotal", cartbartotal);
  		model.addAttribute("cartbarcount", cartbarcount);
	    
	  //����ҳ���û���Ϣ
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
     * describe: ��ӵ����ﳵ
     */
    @RequestMapping(value="/addCollect")
    public String addCollect(
      Model model,
      @RequestParam(value="detailnum") int detailnum) {
    	if(existsUser==false) {
    		return "customer-login";
    	}
     //��id�ҵ���ǰ��Ʒ
     Goods goods=goodsService.getGoodsByGoodsId(detailnum);
     //�ҵ���Ʒ������
     User user=userService.SelById(goods.getOwner_id());
     //�Ƽ���Ʒ����
     List<Goods> mightLikeList=new ArrayList<>(50);
     mightLikeList=goodsService.GetCategoryOnlySix(goods.getType());
           //����Ʒ��Ϣ�ͷ�������Ϣ����ǰ��
     model.addAttribute("goods", goods);
     model.addAttribute("user", user);
     //��mapping����ǰ��
     model.addAttribute("mightlike", mightLikeList);
     
     if(existsUser==false) {
    	 return "customer-login";
     }
     int user_id =nowUser.getUser_id();
     System.out.println(user_id);
     //�������ݿⲿ��
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
	    	 model.addAttribute("collectmessage", "���빺�ﳵʧ�ܣ�������");
	     }else {
	    	 model.addAttribute("collectmessage", "����ɹ�");
	     }
     }else {
    	 model.addAttribute("collectmessage", "�Ѵ��ڴ���Ʒ");
     }
     
    //copy
	//��Ʒ����ͳ��
	int cartbarcount = 0;
	//��Ʒ���ͳ��
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
	
	//��ҳ�洫ֵ
	model.addAttribute("cartbargoods", cartBarGoodsList);
	model.addAttribute("cartbartotal", cartbartotal);
	model.addAttribute("cartbarcount", cartbarcount);
     
     return "detail";
    }
    
    //�������������
	@RequestMapping(value="/digital-product",method=RequestMethod.GET)
	public String gotoDigitalProduct(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//���÷�ҳ��
		PageHelper.startPage(pn,shownum);
		//��ȡ�����������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		//���򷽷� 
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("�������");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("�������");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("�������");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("�������");
		}
		//��װ����ҳ����
		PageInfo page = new PageInfo(goodsList);

		//���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "�������");
        //����������ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//��mapping��Ϣ����ǰ��
		model.addAttribute("page", "digital-product");
		
		if(existsUser==true) {
			 //copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
		}
		
		return "category-no-sidebar";
	}
	
	//������Ϸ������
	@RequestMapping(value="/game-trade",method=RequestMethod.GET)
	public String gotoGameTrade(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//���÷�ҳ��
		PageHelper.startPage(pn,shownum);
        //��ȡ��Ϸ��������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		//��ͬ�����򷽷�
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("��Ϸ����");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("��Ϸ����");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("��Ϸ����");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("��Ϸ����");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "��Ϸ����");
		 //����������ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//����mapping��Ϣ
		model.addAttribute("page", "game-trade");
		return "category-no-sidebar";
	}
	
	//��������Ҿ���
	@RequestMapping(value="/home-life",method=RequestMethod.GET)
	public String gotoHomeLife(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		PageHelper.startPage(pn,shownum);
        //��ȡ�����������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("����Ҿ�");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("����Ҿ�");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("����Ҿ�");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("����Ҿ�");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "����Ҿ�");
		//����������ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//����mapping
		model.addAttribute("page", "home-life");
		return "category-no-sidebar";
	}
	
	//�������Ь����
	@RequestMapping(value="/clothes-shoes",method=RequestMethod.GET)
	public String gotoClothesShoes(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		PageHelper.startPage(pn,shownum);
        //��ȡ�����������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("����Ь��");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("����Ь��");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("����Ь��");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("����Ь��");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "����Ь��");
		//����������ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//����mapping
		model.addAttribute("page", "clothes-shoes");
		return "category-no-sidebar";
	}
	
	//�����ճ��ٻ���
	@RequestMapping(value="/everything",method=RequestMethod.GET)
	public String gotoEverything(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//���÷�ҳ��
		PageHelper.startPage(pn,shownum);
        //��ȡ�����������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		//��ͬ�����򷽷�
		if(sortfunc.equals("orderby_0")==true) {
			goodsList=goodsService.GetCategory("�ճ��ٻ�");
		}
		else if(sortfunc.equals("orderby_1")==true) {
			goodsList=goodsService.GetCategoryByReleaseTime("�ճ��ٻ�");
		}
		else if(sortfunc.equals("orderby_2")==true) {
			goodsList=goodsService.GetCategoryByPrice_1("�ճ��ٻ�");
		}else {
			goodsList=goodsService.GetCategoryByPrice_2("�ճ��ٻ�");
		}
		PageInfo page = new PageInfo(goodsList);
		
		if(existsUser==true) {
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "�ճ��ٻ�");
		//����������ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//��mapping��Ϣ����ǰ��
		model.addAttribute("page", "everything");
		return "category-no-sidebar";
	}
	
	//��Ʒ���� category-no-sidebar
	@RequestMapping(value="/category-no-sidebar",method=RequestMethod.GET)
	public String gotoTotal(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model) {
		//���÷�ҳ��
		PageHelper.startPage(pn,shownum);
		 //��ȡ��Ʒ������Ʒ��Ϣ
		List<Goods> goodsList=new ArrayList<>();
		//��ͬ�����򷽷�
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
        //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
		model.addAttribute("goodsList", page);
        //���ñ���
		model.addAttribute("title", "��������Ҫ�Ķ�����");
		//��������ǰ��
		model.addAttribute("sortfunc", sortfunc);
		model.addAttribute("shownum", shownum);
		//��mapping��Ϣ����ǰ��
		model.addAttribute("page", "category-no-sidebar");
		return "category-no-sidebar";
	}
	
    //����������
	@RequestMapping(value="/searchgoods")
	public String gotoSearchGoods(@RequestParam(value="sortfunc",defaultValue="orderby_0") String sortfunc,
			@RequestParam(value="shownum",defaultValue="12") int shownum,
			@RequestParam(value="pn",defaultValue="1") int pn,
			Model model,@RequestParam("search") String search) {
		WriteLog.writeSearch(search);
		//���÷�ҳ��
		PageHelper.startPage(pn,shownum);
		List<Goods> goodsList=new ArrayList<>();
		//��ͬ�����򷽷�
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//END - author: minhe
		}
		
		 //���û����Ʒ
		if(goodsList.isEmpty()) {
			model.addAttribute("goodsmessage", "������Ʒ����");
		}
        //��ҳ������ǰ��
		model.addAttribute("goodsList", page);
		//�������ݴ������ݿ���ȡ��ǰ��
		model.addAttribute("search",search);
        //���ñ���
		model.addAttribute("title", search);
		//�������ظ�ǰ��
		model.addAttribute("shownum", shownum);
		model.addAttribute("sortfunc", sortfunc);
		//��mapping��Ϣ����ǰ��
		model.addAttribute("page", "searchgoods");
		return "category-no-sidebar"; 
	}
	
	//������Ʒ����
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String gotoDetail(Model model,@RequestParam(value="detailnum") int detailnum) {
		//��id�ҵ���ǰ��Ʒ
		Goods goods=goodsService.getGoodsByGoodsId(detailnum);
		//�ҵ���Ʒ������
		User user=userService.SelById(goods.getOwner_id());
		WriteLog.writeGoods(user, goods);
		//�Ƽ���Ʒ����
		List<Goods> mightLikeList=new ArrayList<>(50);
		mightLikeList=goodsService.GetCategoryOnlySix(goods.getType());
		
		if(existsUser==true) {
			 //copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
		}
		
        //����Ʒ��Ϣ�ͷ�������Ϣ����ǰ��
		model.addAttribute("goods", goods);
		model.addAttribute("user", user);
		//���ﳵ��ť��Ϣ
		model.addAttribute("collectmessage", "���빺�ﳵ");
		//��mapping����ǰ��
		model.addAttribute("mightlike", mightLikeList);
		
		// ��ȡ��Ʒ����ҳ�����Ʒ���۲���
		List<Message> messages = new ArrayList<Message>();
		messages = messageservice.getAllMessagesByGoodsid(detailnum);
		model.addAttribute("messages", messages);
		return "detail";
	}
	
    //������Ʒ����
	@RequestMapping(value="/quickview",method=RequestMethod.POST)
	@ResponseBody
	public Goods gotoQuickView(Model model,@RequestParam(value="detailnum") int detailnum) {
		//��id�ҵ���ǰ��Ʒ
		Goods goods=goodsService.getGoodsByGoodsId(detailnum);
        //����Ʒ��Ϣ�ͷ�������Ϣ����ǰ��
		model.addAttribute("goods", goods);
		//���ﳵ��ť��Ϣ
		model.addAttribute("collectmessage", "���빺�ﳵ");
		System.out.println(goods);
		return goods;
	}
	
	/*
	 * author: minhe
	 * fields: orders
	 * describe: ���¶���״̬
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
			
			//�û���֤
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			
			//����orders״̬
			ordersService.confirmById(order_id);
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
			float total = 0;
			
			//store the collect list
			List<Orders> ordersList = ordersService.getOrderByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//������б�ordersҳ��ר��
			List<OrdersGoods> ordersGoodsList = new ArrayList();
			//debug
			System.out.println("loginҳ��ɹ�");
			//iterate and add to result
			for(Orders o: ordersList) {
				//debug
				System.out.println("����ѭ��");
				count += 1;
				total += goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice();
				
				goodsList.add(goodsService.getGoodsByGoodsId(o.getGoods_id()));
				
				OrdersGoods ordersGoods = new OrdersGoods();
				//orders���
				ordersGoods.setOrders_id(o.getOrder_id());
				//ʱ��
				ordersGoods.setBuy_time(o.getBuy_time());
				//��Ǯ
				ordersGoods.setGoods_price(goodsService.getGoodsByGoodsId(o.getGoods_id()).getPrice());
				//״̬
				if(o.getOrder_id() == order_id) {

					userService.AddFaithValueById(ordersService.getOrderById(order_id).getSeller_id());
					ordersService.confirmById(order_id);
					System.out.println(order_id);
					ordersGoods.setState("�����");
				} else {
					
					ordersGoods.setState(o.getState());
				}
				//����׼�����ص��б�
				ordersGoodsList.add(ordersGoods);
			}
			model.addAttribute("ordersgoods", ordersGoodsList);
			//END - author: minhe
			
			if(existsUser==true) {
				 //copy
				//��Ʒ����ͳ��
				int cartbarcount = 0;
				//��Ʒ���ͳ��
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
				
				//��ҳ�洫ֵ
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
	 * describe: ����������1
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
				//��Ʒ����ͳ��
				int cartbarcount = 0;
				//��Ʒ���ͳ��
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
				
				//��ҳ�洫ֵ
				model.addAttribute("cartbargoods", cartBarGoodsList);
				model.addAttribute("cartbartotal", cartbartotal);
				model.addAttribute("cartbarcount", cartbarcount);
			}
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			if(collectList.isEmpty()) {
			model.addAttribute("cartmessage", "��ǰ���ﳵ��û����Ʒ");
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
			
			
			//��ҳ�洫ֵ
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
	 * describe: ����������2
	 */
	@RequestMapping(value="/checkout2",method=RequestMethod.GET)
	public String gotoCheckout2(Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//�û���֤
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
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
				//��Ʒ����ͳ��
				int cartbarcount = 0;
				//��Ʒ���ͳ��
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
				
				//��ҳ�洫ֵ
				model.addAttribute("cartbargoods", cartBarGoodsList);
				model.addAttribute("cartbartotal", cartbartotal);
				model.addAttribute("cartbarcount", cartbarcount);
			}
			
			//��ҳ�洫ֵ
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
	 * describe: ����������3
	 */
	@RequestMapping(value="/checkout3",method=RequestMethod.GET)
	public String gotoCheckout3(Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//�û���֤
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//��ҳ�洫ֵ
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
	 * describe: ����������4
	 */
	@RequestMapping(value="/checkout4",method=RequestMethod.GET)
	public String gotoCheckout4(
			Model model, HttpServletRequest req,
			@ModelAttribute("attr1")String attr1, 
			@ModelAttribute("attr2")String attr2) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//�û���֤
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
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
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//��ҳ�洫ֵ
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
	 * describe: ����������5
	 */
	@RequestMapping(value="/checkout5",method=RequestMethod.GET)
	public String gotoCheckout5(Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			//�û���֤
			User user = new User();
			user.setName(attr1);
			user.setPassword(attr2);
			User user1 =null;
			user1=userService.Login(user);
			int user_id =user1.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//��Ʒ����ͳ��
			int count = 0;
			//��Ʒ���ͳ��
			float total = 0;
			//store the collect list
			List<Collect> collectList = collectService.getCollectByUserId(user_id);
			
			//store the goods list
			List<Goods> goodsList = new ArrayList();
			
			//store the orders list
			List<Orders> ordersList = new ArrayList();
			
			//iterate and add to result
			for(Collect c: collectList) {
				//�½�Orders�����Ա����
				WriteLog.writeOrder(c);
				Orders orders = new Orders();
				orders.setSeller_id(goodsService.getGoodsByGoodsId(c.getGoods_id()).getOwner_id());
				orders.setBuyer_id(user_id);
				orders.setGoods_id(c.getGoods_id());
				orders.setBuy_time(setFormat(new Date()));
				orders.setState("�ȴ��ʹ�");
				//����Orders����
				ordersService.addOrder(orders);
				System.out.println("goods_id: " + c.getGoods_id());
				//��collect��ɾ��
				collectService.deleteCollectByCollectId(c.getCollect_id());
				//����Ʒ��Ϊ�ѳ���
				goodsService.changeGoodsState(c.getGoods_id());
			}
			
			//copy
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
				//����Ʒ��Ϊ�ѳ���
				goodsService.changeGoodsState(c.getGoods_id());
			}
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			
			//��ҳ�洫ֵ
			model.addAttribute("total", 0);
			model.addAttribute("count", 0);
			return "checkout5";
		}
	}
	
	/*
	 * author: minhe
	 * fields: cart
	 * describe: ���빺�ﳵҳ��
	 */
	@RequestMapping(value="/cart",method=RequestMethod.GET)
	public String gotoCart(Model model, HttpServletRequest req) {
		
		if(existsUser==false) {
			return "customer-login";
		} else {
			
			int user_id =nowUser.getUser_id();
			//debug
			System.out.println("user_id: " + user_id);
			
			//��Ʒ����ͳ��
			int cartbarcount = 0;
			//��Ʒ���ͳ��
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
			
			//��ҳ�洫ֵ
			model.addAttribute("cartbargoods", cartBarGoodsList);
			model.addAttribute("cartbartotal", cartbartotal);
			model.addAttribute("cartbarcount", cartbarcount);
			return "cart";
		}
	}

	/*
	 * author: minhe
	 * fields: cart
	 * describe: ���ﳵɾ����Ʒ
	 */
	@RequestMapping(value="/deleteCart",method=RequestMethod.GET)
	public String deleteCart(@RequestParam(value="delete_good_id") int delete_good_id, Model model, HttpServletRequest req,@ModelAttribute("attr1")String attr1, @ModelAttribute("attr2")String attr2) {
		
		//�û���֤
		User user = new User();
		user.setName(attr1);
		user.setPassword(attr2);
		User user1 =null;
		user1=userService.Login(user);
		int user_id =user1.getUser_id();
		//debug
		System.out.println("user_id: " + user_id);
		
		//��Ʒ����ͳ��
		int count = 0;
		//��Ʒ���ͳ��
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

				//�����ݿ���ɾ����collect��¼
				collectService.deleteCollectByCollectId(c.getCollect_id());
			}
		}
		
		//��ҳ�洫ֵ
		model.addAttribute("goods", goodsList);
		model.addAttribute("total", total);
		model.addAttribute("count", count);
		
		//copy
		//��Ʒ����ͳ��
		int cartbarcount = 0;
		//��Ʒ���ͳ��
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
		
		//��ҳ�洫ֵ
		model.addAttribute("cartbargoods", cartBarGoodsList);
		model.addAttribute("cartbartotal", cartbartotal);
		model.addAttribute("cartbarcount", cartbarcount);
		
		//debug
		req.setAttribute("goods", goodsList);
		System.out.println(req.getAttribute("goods").toString());
		
		return "cart";
	}		
}
