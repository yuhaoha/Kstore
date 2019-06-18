package com.chinasofti.Kstore.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinasofti.Kstore.dao.UserMapper;
import com.chinasofti.Kstore.service.MessageService;
import com.chinasofti.Kstore.service.SearchService;
import com.chinasofti.Kstore.vo.Message;
import com.chinasofti.Kstore.vo.Search;
import com.chinasofti.Kstore.vo.User;

@Controller
public class CommentsController {
	@Autowired
	MessageService messageservice;
	@Autowired
	UserMapper usermapper;
	@Autowired
	SearchService searchservice;
	
//	@RequestMapping(value="comments",method=RequestMethod.GET)
//	public String getcomments(Model model) throws IOException {
//		List<Message> list = new ArrayList<Message>();
//		list = messageservice.getAllMessagesByGoodsid(1);
//		model.addAttribute("messages",list);
//	    return "detail";
//	}
	
	@ModelAttribute()
	public void getHotSearch(Model model) {
		List<Search> searches =  searchservice.getFourHotSearch();
		model.addAttribute("searchlist", searches);
	}
	
	@RequestMapping(value="comments",method=RequestMethod.POST)
	@ResponseBody
	public Message writecomments(String comments,int goods_id,int user_id) throws IOException {
		Message newmsg = new Message();
		//获取用户信息
		User user = usermapper.SelById(user_id);
		newmsg.setReleaser_id(user.getUser_id());
		newmsg.setUser(user);
		//获取商品id
		newmsg.setGoods_id(goods_id);
		//获取当前时间，作为评论发布时间
		Date date = new Date();
        Timestamp timeStamp = new Timestamp(date.getTime());
		newmsg.setRelease_time(timeStamp);
		newmsg.setContent(comments);
		//写入数据库
		messageservice.writeMessagesIntoDB(newmsg);
		
		return newmsg;
	}
}
