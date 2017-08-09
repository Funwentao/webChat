package controller;

import java.util.HashMap;

import org.apache.catalina.ha.backend.Sender;

import com.jfinal.core.Controller;
import fuc.*;
import service.*;

public class UserController extends Controller{
	
	private userService userService=new userService();
	
	public void  getcookie(){
		HashMap<String,Object> map=new HashMap<>();
		String account=getCookie("account")==null?"":getCookie("account");
		String password=getCookie("password")==null?"":getCookie("password");
		System.out.println(getCookie("account"));
		map.put("account", account);
		map.put("password",password);
		renderJson(map);
	}
	
	public void register(){
		String type=getPara("type");
		String account=getPara("account");
		String password=getPara("password");
		String yzm=getPara("yzm");
		String randstr=getSessionAttr("randstr");
		renderJson(userService.register(type,account,password,yzm,randstr));
	}
	
	
	//登录
	public void login(){
		String check=getPara("check");
		String type=getPara("type");
		String account=getPara("account");
		String password=getPara("password");
		String yzm=getPara("yzm");
		String randstr=getSessionAttr("randstr");
		setSessionAttr("account", account);
		setSessionAttr("type", type);
		if(check.equals("true")){
			setCookie("account",account ,86400);
			setCookie("password",password ,86400);
		}else{
			setCookie("account","" ,86400);
			setCookie("password","" ,86400);
		}
		renderJson(userService.login(type,account,password,yzm,randstr));
		
	}
	//获取登录用户信息
	public void getinformation(){
		String account=getSessionAttr("account");
		String type=getSessionAttr("type");
		renderJson(userService.getinformation(account, type));
	}
	//更新个人基本资料
	public void updatebase(){
		String account=getSessionAttr("account");
		String type=getSessionAttr("type");
		String name=getPara("name");
		String sex=getPara("sex");
		String phone=getPara("phone");
		String mailbox=getPara("mailbox");
		String say=getPara("say");
		HashMap<String , String >mp=new HashMap<>();
		mp.put("account", account);
		mp.put("type", type);
		mp.put("name", name);
		mp.put("sex", sex);
		mp.put("phone", phone);
		mp.put("mailbox", mailbox);
		mp.put("say", say);
		renderJson(userService.updatebase(mp));
	}
	//修改密码
	
	public void updatepass(){
		String account=getSessionAttr("account");
		String type=getSessionAttr("type");
		String oldpassword=getPara("oldpassword");
		String newpassword=getPara("newpassword");
		HashMap<String , String >mp=new HashMap<>();
		mp.put("account", account);
		mp.put("type", type);
		mp.put("oldpassword", oldpassword);
		mp.put("newpassword", newpassword);
		renderJson(userService.updatepass(mp));
		
	}
	
	//退出
	
	public void away(){
		removeSessionAttr("account");
		removeSessionAttr("type");
	}
	
	//编辑或修改文章
	public void submitpaper(){
		String account=getSessionAttr("account");
		String paperid=getPara("paperid");
		String title=getPara("title");
		String content=getPara("content");
		renderJson(userService.submitpaper(account,paperid,title,content));
	}
	
	
	//获取当前公众号的所有文章
	public void getpapers(){
		String account=getSessionAttr("account");
		String title=getPara("title");
		renderJson(userService.getpapers(account,title));
	}
	//删除文章
	public void deletepaper(){
		String account=getSessionAttr("account");
		String paperid=getPara("paperid");
		renderJson(userService.deletepaper(account,paperid));
	}
	
	//获取文章内容
	public void getpapercontent(){
		String paperid=getPara("paperid");
		renderJson(userService.getpapercontent(paperid));
	}
	
	
	
	//获取关注列表
	public void guanzhu(){
		String account=getSessionAttr("account");
		String key=getPara("key");
		renderJson(userService.guanzhu(account,key));
	}
	
	//获取未关注的列表
	public void faxian(){
		String account=getSessionAttr("account");
		String key=getPara("key");
		renderJson(userService.faxian(account,key));
	}
	
	//获取收藏列表
	public void shoucang(){
		String account=getSessionAttr("account");
		String key=getPara("key");
		renderJson(userService.shoucang(account,key));
	}
	//获取关注公众号的文章
	public void getguanzhupaper(){
		String account=getPara("account");
		String key=getPara("key");
		renderJson(userService.getguanzhupaper(account,key));
	}

	//是否收藏
	public void shifoushoucang(){
		String paperid=getPara("paperid");
		String account=getSessionAttr("account");
		renderJson(userService.shifoushoucang(account,paperid));
	}
	//关注
	public void guanzhu1(){
		String user2id=getPara("user2id");
		String account=getSessionAttr("account");
		renderJson(userService.guanzhu1(account,user2id));
	}
	//取关
	public void quguan(){
		String user2id=getPara("user2id");
		String account=getSessionAttr("account");
		renderJson(userService.quguan(account,user2id));
	}
	//收藏
		public void shoucang1(){
			String paperid=getPara("paperid");
			String account=getSessionAttr("account");
			renderJson(userService.shoucang1(account,paperid));
		}
		//取消收藏
		public void quxiaoshoucang(){
			String paperid=getPara("paperid");
			String account=getSessionAttr("account");
			renderJson(userService.quxiaoshoucang(account,paperid));
		}
		//评论
		public void commit(){
			String account=getSessionAttr("account");
			String paperid=getPara("paperid");
			String commit=getPara("commit");
			renderJson(userService.commit(account,paperid,commit));
		}
		//是否点赞
		public void shifoudianzan(){
			String account=getSessionAttr("account");
			String paperid=getPara("paperid");
			renderJson(userService.shifoudianzan(account,paperid));
		}
		public void zan(){
			String account=getSessionAttr("account");
			String zan=getPara("zan");
			String paperid=getPara("paperid");
			renderJson(userService.zan(account,zan,paperid));
		}
		//点赞数量
		public void getZan(){
			String paperid=getPara("paperid");
			renderJson(userService.getZan(paperid));
		}
		//可以显示的评论数量
		public void getCom(){
			String paperid=getPara("paperid");
			renderJson(userService.getCom(paperid));
		}
		//所有评论
		public void getFullCom(){
			String paperid=getPara("paperid");
			renderJson(userService.getFullCom(paperid));
		}
		//赞的详细信息
		public void getAllzan(){
			String paperid=getPara("paperid");
			renderJson(userService.getAllzan(paperid));
		}
		//评论的详细信息
		public void getAllFullcom(){
			String paperid=getPara("paperid");
			renderJson(userService.getAllFullcom(paperid));
		}
		//所有评论的详细信息
		public void getAllcom(){
			String paperid=getPara("paperid");
			renderJson(userService.getAllcom(paperid));
		}
		
		//审核评论
		public void shenhe(){
			String commid=getPara("commid");
			String flag=getPara("flag");
			renderJson(userService.shenhe(commid,flag));
		}
		//删除评论
		public void deletecom(){
			String commid=getPara("commid");
			renderJson(userService.deletecom(commid));
		}
}
