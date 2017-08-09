package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.spi.RegisterableService;

import org.apache.catalina.Session;

import sun.org.mozilla.javascript.internal.ast.NewExpression;
import sun.security.action.PutAllAction;
import sun.security.util.Password;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.xpath.internal.operations.And;

import fuc.*;
public class userService {
	
	
	
	//用户注册
	public HashMap<String,Object>register(String type,String account,String password,String yzm,String randstr){
		HashMap<String,Object> map=new HashMap<>();
		try{
			if(!yzm.equals(randstr)){
				 map.put("message", "验证码有误");
		         map.put("result", false);
		         return map;
			}
			String sql="";
			if("1".equals(type)){
				sql="select * from user1 where account='"+account+"'";
			}else{
				 sql="select * from user2 where account='"+account+"'";
			}
			Record record=Db.findFirst(sql);
			if(record==null){
				Record record1=new Record();
				String password1=MD5.MD5Encode(password);
				record1.set("account", account);
				record1.set("password",password1);
				if("1".equals(type)){
					if(Db.save("user1", record1)){
				         map.put("message", "注册成功");
				         map.put("result", true);
					}else{
						 map.put("message", "未知错误");
				         map.put("result", false);
					}
				}else{
					if(Db.save("user2", record1)){
				         map.put("message", "注册成功");
				         map.put("result", true);
					}else{
						 map.put("message", "未知错误");
				         map.put("result", false);
					}
				}			
			}else{
				 map.put("message", "该用户名被占用！");
		         map.put("result", false);
			}
		
		}catch(Exception e){
			map.put("result", false);
			map.put("message", "注册失败");
			e.printStackTrace();
			return map;
		}
		return map;
	}
	
	
	//用户登录
	public HashMap<String,Object>login(String type,String account,String password,String yzm,String randstr){
		HashMap<String,Object> map=new HashMap<>();
		try{
			if(!yzm.equals(randstr)){
				 map.put("message", "验证码有误");
		         map.put("result", false);
		         return map;
			}
			String sql="";
			String password1=MD5.MD5Encode(password);
			if("1".equals(type)){
				sql="select * from user1 where account='"+account+"'" +"and password='"+password1+"'";
			}else{
				sql="select * from user2 where account='"+account+"'" +"and password='"+password1+"'";
			}
			Record record=Db.findFirst(sql);
			if(record==null){
				map.put("message", "账号或密码有误");
		         map.put("result", false);		
			}else{
				 map.put("message", "登录成功！");
		         map.put("result", true);
			}
		
		}catch(Exception e){
			map.put("result", false);
			map.put("message", "登录失败");
			e.printStackTrace();
			return map;
		}
		return map;
	}
	
	
	//用户信息
	
	public HashMap<String , Object>getinformation(String account,String type){
		
		HashMap<String,Object> map=new HashMap<>();
		try{
			String sql="";
			if(type.equals("2")){
				sql="select * from user2 where account='"+account+"'";
				Record record=Db.findFirst(sql);	
				if(record!=null){
					map.put("result", true);
					map.put("message", "成功");
					map.put("account", record.get("account"));
					map.put("name", record.get("name"));
					map.put("phone", record.get("phone"));
					map.put("sex", record.get("sex"));
					map.put("say", record.get("say"));
					map.put("mailbox", record.get("mailbox"));
				}else{
					map.put("result", false);
					map.put("message", "获取信息出错！");
				}
			}else{
				sql="select * from user1 where account='"+account+"'";
				Record record=Db.findFirst(sql);	
				if(record!=null){
					map.put("result", true);
					map.put("message", "成功");
					map.put("account", record.get("account"));
					map.put("name", record.get("name"));
					map.put("phone", record.get("phone"));
					map.put("sex", record.get("sex"));
					map.put("mailbox", record.get("mailbox"));
				}else{
					map.put("result", false);
					map.put("message", "获取信息出错！");
				}
			}
			
		
		}catch(Exception e){
			map.put("result", false);
			map.put("message", "获取信息出错");
			e.printStackTrace();
		}			
		return map;
		
	}
	//更新个人基本资料
	public HashMap<String, Object> updatebase(HashMap<String, String> pram) {
		String account=pram.get("account");
		String type=pram.get("type");
		String name=pram.get("name");
		String sex=pram.get("sex");
		String phone=pram.get("phone");
		String mailbox=pram.get("mailbox");
		String say=pram.get("say");
		HashMap<String,Object> mp=new HashMap<>();
		try{
			if("1".equals(type)){
				String SQL = "SELECT account FROM user1 WHERE account ='"+account+"'";
				Record record=Db.findFirst(SQL);
				record.set("name", name);
				record.set("sex", sex);
				record.set("phone",phone);
				record.set("mailbox", mailbox);
				if(Db.update("user1", "account",record)){
					mp.put("result", true);
					mp.put("message", "更新资料成功");
				}else{
					mp.put("result", false);
					mp.put("message", "更新资料失败");
				}
			}else{
				String SQL = "SELECT account FROM user2 WHERE account ='"+account+"'";
				Record record=Db.findFirst(SQL);
				record.set("name", name);
				record.set("sex", sex);
				record.set("phone",phone);
				record.set("mailbox", mailbox);
				record.set("say", say);
				if(Db.update("user2", "account",record)){
					mp.put("result", true);
					mp.put("message", "更新资料成功");
				}else{
					mp.put("result", false);
					mp.put("message", "更新资料失败");
				}
			}
			
		}catch(Exception e){
			mp.put("result", false);
			mp.put("message", "更新资料失败");
			return mp;
		}
		return mp;
	}
	
	//修改密码
	public HashMap<String, Object> updatepass(HashMap<String, String> pram) {
		String account=pram.get("account");
		String type=pram.get("type");
		String oldpassword=pram.get("oldpassword");
		String newpassword=pram.get("newpassword");
		HashMap<String,Object> mp=new HashMap<>();
		try{
			if("1".equals(type)){
				String SQL = "SELECT * FROM user1 WHERE account ='"+account+"'";
				Record record=Db.findFirst(SQL);
				String password1=record.get("password");
				String password2=MD5.MD5Encode(oldpassword);
				String password3=MD5.MD5Encode(newpassword);
				if(!password1.equals(password2)){
					mp.put("result", false);
					mp.put("message", "旧密码有误");
				}
				else{
					record.set("password", password3);
					if(Db.update("user1", "account",record)){
						mp.put("result", true);
						mp.put("message", "更新密码成功");
					}else{
						mp.put("result", false);
						mp.put("message", "更新密码失败");
					}
				}
				
			}else{
				String SQL = "SELECT * FROM user2 WHERE account ='"+account+"'";
				Record record=Db.findFirst(SQL);
				String password1=record.get("password");
				String password2=MD5.MD5Encode(oldpassword);
				String password3=MD5.MD5Encode(newpassword);
				if(!password1.equals(password2)){
					mp.put("result", false);
					mp.put("message", "旧密码有误");
				}else{
					record.set("password", password3);
					if(Db.update("user2", "account",record)){
						System.out.println(2);
						mp.put("result", true);
						mp.put("message", "更新密码成功");
					}else{
						System.out.println(3);
						mp.put("result", false);
						mp.put("message", "更新密码失败");
					}
				}
			}
			
		}catch(Exception e){
			System.out.println(4);
			mp.put("result", false);
			mp.put("message", "更新密码失败");
			return mp;
		}
		return mp;
	}
	
	
	
	//编辑或修改文章
	public HashMap<String, Object>submitpaper(String account,String paperid,String title,String content){
		HashMap<String,Object> mp=new HashMap<>();
		try{
			if(paperid.equals("-1")){
				Record record=new Record();
				record.set("user2id", account);
				record.set("title", title);
				record.set("content",content);
				record.set("state","1");
				SimpleDateFormat   sFmt   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				Date date=new Date();
				String nowtime=sFmt.format(date);
				record.set("createtime",nowtime);
				record.set("edittime",nowtime);
				if(Db.save("paper", record)){
			         mp.put("message", "提交文章成功");
			         mp.put("result", true);
				}else{
					 mp.put("message", "提交文章失败");
			         mp.put("result", false);
				}
			}else{
				String sql="select * from paper where user2id='"+account+"'"+"and paperid='"+paperid+"'";
				Record record=Db.findFirst(sql);
				record.set("title", title);
				record.set("content",content);
				if(Db.update("paper", "paperid",record)){
					mp.put("message", "提交文章成功");
			         mp.put("result", true);
				}else{
					mp.put("message", "提交文章失败");
			         mp.put("result", false);
				}
				
			}
		}catch(Exception e){
			e.printStackTrace();
			 mp.put("message", "提交文章失败");
	         mp.put("result", false);
		}
		
		return mp;
	}
	
	//获取所有文章
	public List<Record> getpapers(String account,String title){
		try{
			String sql="";
			if("".equals(title)||title==null){
				 sql="select * from paper where user2id='"+account+"'"+"and state='1' order by edittime desc";
			}
			else{
				 sql="select * from paper where user2id='"+account+"'"+"and state='1' and title like '%"+title+"%' order by edittime desc";
			}
			List<Record> list=Db.find(sql);	
			return  list;
		}catch(Exception e){
			e.printStackTrace();
			return  null;
		}

	}
	//删除文章
	public HashMap<String, Object> deletepaper(String account,String paperid){
		HashMap<String, Object> mp=new HashMap<>();
		String sql="select * from paper where user2id='"+account+"'"+"and paperid='"+paperid+"'";
		Record record=Db.findFirst(sql);
		record.set("state", "0");
		if(Db.update("paper", "paperid",record)){
			mp.put("result", true);
			mp.put("message", "成功删除文章");
		}else{
			mp.put("result", false);
			mp.put("message", "删除文章失败");
		}
		return mp;
	}
	
	//获取文章内容
	public HashMap<String, Object> getpapercontent(String paperid){
		HashMap<String, Object>mp=new HashMap<>();
		try{
			String sql="select * from paper where  paperid='"+paperid+"'";
			Record record=Db.findFirst(sql);
			String title=record.get("title");
			String content=record.get("content");
			String edittime=record.get("edittime");
			String user2id=record.get("user2id");
			mp.put("result", true);
			mp.put("title",title);
			mp.put("content", content);
			mp.put("edittime",edittime);
			mp.put("user2id", user2id);
		}catch(Exception e){
			e.printStackTrace();
			mp.put("result", false);
		}
		return mp;
		
	}
	
	
	//获取关注列表
	public List<Record> guanzhu(String account,String key){
		try{
			String sql="";
			if("".equals(key)||key==null){
				 sql="select * from user2,guanzhu where user1id='"+account+"' and guanzhu.user2id=user2.id order by time desc;";
			}
			else{
				 sql="select * from guanzhu,user2 where user1id='"+account+"' and guanzhu.user2id=user2.id and account like'%"+key+"%' order by time desc";
		
			}
			List<Record> list=Db.find(sql);	
			return  list;
		}catch(Exception e){
			e.printStackTrace();
			return  null;
		}

	}
	
	
	//获取未关注的列表
	
	public List<Record> faxian(String account,String key){
		try{
			String sql="";
			if("".equals(key)||key==null){
				 sql="select * from user2 where id not in (select user2id from guanzhu where user1id='"+account+"')";
			}
			else{
				 sql="select * from user2 where id not in (select user2id from guanzhu where user1id='"+account+"') and account like'%"+key+"%'";
			}
			List<Record> list=Db.find(sql);	
			return  list;
		}catch(Exception e){
			e.printStackTrace();
			return  null;
		}

	}
	
	
	//搜索收藏列表
	public List<Record> shoucang(String account,String key){
		try{
			ArrayList<HashMap<String,Object>> content=new ArrayList<>();
			String sql="";
			if("".equals(key)||key==null){
				 sql="select * from paper,shoucang where paper.paperid=shoucang.paperid and paper.state='1' and  user1id='"+account+"'";
			}
			else{
				 sql="select * from paper,shoucang where paper.paperid=shoucang.paperid and paper.state='1' and user1id='"+account+"' and title like'%"+key+"%'";
			}
			List<Record> list=Db.find(sql);	
			return  list;
		}catch(Exception e){
			e.printStackTrace();
			return  null;
		}

	}

	//获取关注公众号的文章列表
	
	public List<Record> getguanzhupaper(String account,String key){
		try{
			String sql="";
			if(key==null||"".equals(key)){
				 sql="select * from paper where user2id='"+account+"' and state='1' order by edittime desc";
			}
			else{
				 sql="select * from paper where user2id='"+account+"' and state='1' and title like '%"+key+"%' order by edittime desc";
				 System.out.println(sql);
			}
			List<Record> list=Db.find(sql);	
			return  list;
		}catch(Exception e){
			e.printStackTrace();
			return  null;
		}

	}
	//是否收藏
	public HashMap<String, Object> shifoushoucang(String account,String paperid){
		HashMap<String, Object>mp=new HashMap<>();
		try{
			String sql="select * from paper,shoucang where paper.paperid=shoucang.paperid and paper.state='1' and  user1id='"+account+"' and shoucang.paperid='"+paperid+"'";
			Record record=Db.findFirst(sql);
			if(record==null)
				mp.put("result", false);
			else 
				mp.put("result", true);
		}catch(Exception e){
			e.printStackTrace();
			}
		
		return mp;
	}
	//关注
	public HashMap<String, Object> guanzhu1(String account,String user2id){
		HashMap<String, Object>mp=new HashMap<>();
		Record record=new Record();
		record.set("user1id", account);
		record.set("user2id", user2id);
		SimpleDateFormat   sFmt   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		Date date=new Date();
		String nowtime=sFmt.format(date);
		record.set("time",nowtime);
		if(Db.save("guanzhu", record)){
	         mp.put("message", "关注成功");
	         mp.put("result", true);
		}else{
			 mp.put("message", "关注失败");
	         mp.put("result", false);
		}
		return mp;
	}
	
	//取消关注
		public HashMap<String, Object> quguan(String account,String user2id){
			HashMap<String, Object>mp=new HashMap<>();
			try{
				String sql="select * from guanzhu where user1id='"+account+"' and user2id='"+user2id+"'";
				Record record=Db.findFirst(sql);
				if(Db.delete("guanzhu", record)){
					   mp.put("message", "取关成功");
				        mp.put("result", true);
					}else{
						 mp.put("message", "取关失败");
				         mp.put("result", false);
					}
			}catch(Exception e){
				e.printStackTrace();
				mp.put("message", "取关失败");
		         mp.put("result", false);
		         return mp;
			}
			return mp;
		}
		//收藏
		public HashMap<String, Object> shoucang1(String account,String paperid){
			HashMap<String, Object>mp=new HashMap<>();
			Record record=new Record();
			record.set("user1id", account);
			record.set("paperid", paperid);
			SimpleDateFormat   sFmt   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
			Date date=new Date();
			String nowtime=sFmt.format(date);
			record.set("time",nowtime);
			if(Db.save("shoucang", record)){
		         mp.put("message", "收藏成功");
		         mp.put("result", true);
			}else{
				 mp.put("message", "收藏失败");
		         mp.put("result", false);
			}
			return mp;
		}
		
		//取消收藏
			public HashMap<String, Object> quxiaoshoucang(String account,String paperid){
				HashMap<String, Object>mp=new HashMap<>();
				String sql="select * from shoucang where user1id='"+account+"' and  paperid='"+paperid+"'";
				Record record=Db.findFirst(sql);
				if(Db.delete("shoucang", record)){
					   mp.put("message", "取消收藏成功");
				         mp.put("result", true);
				         System.out.println(2);
					}else{
						 mp.put("message", "取消收藏失败失败");
				         mp.put("result", false);
					}
				return mp;
			}
			
			//评论
			public HashMap<String, Object> commit(String account,String paperid,String commit){
				HashMap<String, Object> mp=new HashMap<>();
				try {
					Record record=new Record();
					SimpleDateFormat   sFmt   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
					Date date=new Date();
					String nowtime=sFmt.format(date);
					record.set("commitment", commit);
					record.set("paperid", paperid);
					record.set("user1id", account);
					record.set("time", nowtime);
					record.set("flag", "0");
					if(Db.save("commitment", record)){
				         mp.put("message", "评论成功，待后台审核通过显示");
				         mp.put("result", true);
					}else{
						 mp.put("message", "评论错误");
				         mp.put("result", false);
					}
				} catch (Exception e) {
					e.printStackTrace();
					mp.put("message", "评论错误");
			         mp.put("result", false);
				}
				
				
				return mp;
			}
			//是否点赞
			public HashMap<String, Object> shifoudianzan(String account,String paperid){
				HashMap<String, Object>mp=new HashMap<>();
				try{
					String sql="select * from paper,zan where paper.paperid=zan.paperid and paper.state='1' and  user1id='"+account+"' and zan.paperid='"+paperid+"'";
					Record record=Db.findFirst(sql);
					if(record==null)
						mp.put("result", false);
					else 
						mp.put("result", true);
				}catch(Exception e){
					e.printStackTrace();
					mp.put("result", false);
				}
				return mp;
			}
			//点赞
			public HashMap<String, Object> zan(String account,String zan,String paperid){
				HashMap< String, Object>mp=new HashMap<>();
				try{
					if("1".equals(zan)){
						Record record=new Record();
						record.set("user1id", account);
						record.set("paperid", paperid);
						SimpleDateFormat   sFmt   =   new   SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
						Date date=new Date();
						String nowtime=sFmt.format(date);
						record.set("time", nowtime);	
						if(Db.save("zan", record)){
					         mp.put("message", "点赞成功");
					         mp.put("result", true);
						}else{
							 mp.put("message", "点赞失败");
					         mp.put("result", false);
						}
					}else{
						String sql="select * from zan where user1id='"+account+"' and paperid='"+paperid+"'";
						Record record=Db.findFirst(sql);
						if(Db.delete("zan", record)){
							   mp.put("message", "取消点赞成功");
						         mp.put("result", true);
							}else{
								 mp.put("message", "取消点赞失败");
						         mp.put("result", false);
							}
					}
				}catch(Exception e){
					e.printStackTrace();
					 mp.put("result", false);
				}
				
				return mp;
			}
		//点赞数量
		public HashMap<String, Object> getZan(String paperid){
			HashMap<String, Object> mp=new HashMap<>();
			String sql="select * from zan where paperid='"+paperid+"'";
			List list=Db.find(sql);
			mp.put("sum", list.size());
			return mp;
		}
		//所有评论数量
				public HashMap<String, Object> getFullCom(String paperid){
					HashMap<String, Object> mp=new HashMap<>();
					String sql="select * from commitment where paperid='"+paperid+"'";
					List list=Db.find(sql);
					mp.put("sum", list.size());
					return mp;
				}
		//可显示评论数量
		public HashMap<String, Object> getCom(String paperid){
			HashMap<String, Object> mp=new HashMap<>();
			String sql="select * from commitment where paperid='"+paperid+"' and flag='1'";
			List list=Db.find(sql);
			mp.put("sum", list.size());
			return mp;
		}
		//赞的详细信息
		public List<Record> getAllzan(String paperid){
			try{
				String sql="select * from zan where paperid='"+paperid+"' order by time desc";
				List<Record> list=Db.find(sql);	
				return  list;
			}catch(Exception e){
				e.printStackTrace();
				return  null;
			}
		}
		
		//评论的详细信息
		public List<Record> getAllcom(String paperid){
			try{
				String sql="select * from commitment where paperid='"+paperid+"' and flag='1' order by time desc";
				System.out.println(sql);
				List<Record> list=Db.find(sql);	
				return  list;
			}catch(Exception e){
				e.printStackTrace();
				return  null;
			}
		}
		//所有评论的详细信息
		public List<Record> getAllFullcom(String paperid){
			try{
				String sql="select * from commitment where paperid='"+paperid+"' order by time desc";
				System.out.println(sql);
				List<Record> list=Db.find(sql);	
				return  list;
			}catch(Exception e){
				e.printStackTrace();
				return  null;
			}
		}
		//审核评论
		public HashMap<String, Object> shenhe(String commid,String flag){
			HashMap< String,Object> mp=new HashMap<>();
			try{
				String sql="select * from commitment where commid='"+commid+"'";
				Record record=Db.findFirst(sql);
				if("1".equals(flag)){
					record.set("flag", "0");
				}else{
					record.set("flag", "1");
				}
				if(Db.update("commitment","commid",record)){
					mp.put("result",true);
				}else{
					mp.put("result",false);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		
			return mp;
		}
		
		//删除评论
		public HashMap<String, Object> deletecom(String commid){
			HashMap<String, Object> mp=new HashMap<>();
			try{
				String sql="select * from commitment where commid='"+commid+"'";
				Record record=Db.findFirst(sql);
				if(Db.delete("commitment","commid",record)){
					mp.put("result", true);
				}else{
					mp.put("result", false);
				}
			}catch(Exception e){
				e.printStackTrace();
				mp.put("result", false);
			}
			
			return mp;
		}
		
		
}
