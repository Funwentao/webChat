$(function(){
	
	var paperid;
	//获取文章列表
	getList();
	function getList(title){
		$.post("/user/getpapers",{
			title:title,
		},function(data){
			var chtml="";
			for(var i=0;i<data.length;i++){
				chtml+='<li id="'+data[i].paperid+'"><div class="lidiv" ><img src="img/backg.jpg" class="head"><h7>'
				      +data[i].title+'</h7></div></li>';
			}
			$(".content ul").html(chtml);
			$(".content ul li").click(function(){
				$(".title").show();
				$("#backg").hide();
				$(".account").hide();
				$(".commit").show();
				$(".content2").show();
				$("#deletepaper").show();
				paperid=$(this).attr("id");
				getZan();
				getFullCom();
				getAllFullcom();
				$.post("/user/getpapercontent",{
					paperid:paperid,
				},function(data){
					$("#headtitle").val(data.title);
					UE.getEditor('editor').setContent(data.content);
				},'json');
			});
		},'json');
	}
	
	//搜索
	$("#searchspan").click(function(){
		getList($("#search").val());
	});
	
	//新增或编辑
	$("#addpaper").click(function(){
		$("#backg").hide();
		$(".title").show();
		$(".content2").show();
		UE.getEditor('editor').setContent("");
		$("#headtitle").val("");
		$(".commit").hide();
		$(".account").hide();
		$("#deletepaper").hide();
		paperid="-1";
	});
	$("#submitpaper").click(function(){
		$("#message2").text("确认提交此文章？");
		$(".modal2").modal("show");
	});
	$("#button3").click(function(){
		$(".modal2").modal("hide");
		$.post("/user/submitpaper",{
			paperid:paperid,
			title:$("#headtitle").val(),
			content:UE.getEditor('editor').getContent()
		},function(data){
			if(data.result){
				$(".modal1").modal("show");
				$("#message").text(data.message);
			}else{
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message);
			}
		},'json');		
	});
	
	$("#button2").click(function(){
		$(".modal2").modal("hide");

	});
	
	
	//删除文章
	$("#button5").click(function(){
		$(".modal4").modal("hide");
	});
	$("#deletepaper").click(function(){
		$(".modal4").modal("show");
		$("#message5").text("确认删除此文章？")
	});
	$("#button6").click(function(){
		$(".modal4").modal("hide");
		$.post("/user/deletepaper",{
			paperid:paperid
		},function(data){
			if(data.result){
				$(".modal1").modal("show");
				$("#message").text(data.message);
			}else{
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message);
			}
		},'json');
	});
	
	
	
	
	
	$("#button1").click(function(){
		$(".modal1").modal("hide");
		window.location.reload();
	});
	$("#button4").click(function(){
		$(".modal3").modal("hide");
	});
		
	$("#set").click(function(){
		$(".before").hide();
		$(".after1").show();
		$("#backpic").show();
		$("#set").hide();
		$("#editname").val("");
		$("#oldpassword").val("");
		$("#password1").val("");
	});
	$("#backpic").click(function(){
		$(".before").show();
		$(".after").hide();
		$(".after1").hide();
		$("#backpic").hide();
		$("#set").show();
		getinformation();
	});
	$("#bianji").click(function(){
		$(".before").hide();
		$(".after").show();
		$("#backpic").show();
		$("#set").hide();
		$.post("/user/getinformation",function(data){
			if(data.result==false){
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message);
				return;
			}
			$("#editname").val(data.name);
			$("#editsex").val(data.sex);
			$("#editphone").val(data.phone);
			$("#editmailbox").val(data.mailbox);
			$("#editsay").val(data.say);
		},'json');
	});
	
	
	//点击头像获取信息
	function getinformation(){
		$(".title").hide();
		$(".main").hide();
		$(".content2").hide();
		$("#backg").hide();
		$(".account").show();
		$("#set").show();
		$(".before").show();
		$("#backpic").hide();
		$(".after").hide();
		$(".after1").hide();
		$.post("/user/getinformation",function(data){
			if(data.result==false){
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message);
				return;
			}
			$("#account").text(data.account);
			$("#name").text(data.name);
			$("#sex").text(data.sex);
			$("#phone").text(data.phone);
			$("#mailbox").text(data.mailbox);
			$("#say").text(data.say);
		},'json');
	}
	$("#headpic2").click(function(){
		getinformation();
	});
	
	//编辑基本信息
	$("#confirm").click(function(){
		var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
		var filter1=/^[0-9]*$/;
		 if (!filter1.test($("#editphone").val())&&$("#editphone").val()!=""){
			 $(".modal3").modal("show");
				$("#warnmessage").text("手机格式错误");
			 return;
		 }
		 if (!filter.test($("#editmailbox").val())&&$("#editmailbox").val()!=""){
			 $(".modal3").modal("show");
				$("#warnmessage").text("邮箱格式错误");
			 return;
		 }
		
		$.post("/user/updatebase",{
			name:$("#editname").val(),
			sex:$("#editsex").val(),
			phone:$("#editphone").val(),
			mailbox:$("#editmailbox").val(),
			say:$("#editsay").val()
		},function(data){
				if(data.result){
					$(".modal1").modal("show");
					$("#message").text(data.message)	;
				}else{
					$(".modal3").modal("show");
					$("#message").text(data.message)	;
				}
			
		},'json');
	});
	
	//修改密码
	$("#confirm1").click(function(){
		if($("#oldpassword").val()==""){
			 $(".modal3").modal("show");
			$("#warnmessage").text("请输入旧密码");
			return;
		}
		if($("#password1").val()==""){
			 $(".modal3").modal("show");
			$("#warnmessage").text("请输入新密码");
			return;
		}
		if($("#password2").val()==""){
			 $(".modal3").modal("show");
			$("#warnmessage").text("请输入确认密码");
			return;
		}
		if($("#password1").val()!=$("#password2").val()){
			 $(".modal3").modal("show");
			$("#warnmessage").text("新密码与确认密码不同");
			return;
		}
		$.post("/user/updatepass",{
			oldpassword:$("#oldpassword").val(),
			newpassword:$("#password1").val()
		},function(data){
			if(data.result){
				$(".modal1").modal("show");
				$("#message").text(data.message)	;
			}else{
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message)	;
			}
		},'json');
	});
	
	//退出
	$("#tuichu").click(function(){
		$.post("/user/away");
		window.location.href="login.html";
	});
	function getZan(){
		$.post("/user/getZan",{
			paperid:paperid,
		},function(data){
			$("#zannum").text(data.sum);
		},'json');
	}
	function getFullCom(){
		$.post("/user/getFullCom",{
			paperid:paperid,
		},function(data){
			$("#comnum").text(data.sum);
		},'json');
	}
	function getAllFullcom(){
		$.post("/user/getAllFullcom",{
			paperid:paperid,
		},function(data){
			var thtml="";
			for (var i=0;i<data.length;i++){
				thtml+='<li><div class="cccontent"><img src="img/head.jpg"  class="cimg"><span class="cname">'
					+data[i].user1id+'</span><span class="ctime">'
					+data[i].time+'</span><p class="cp">'+data[i].commitment+'</div></p>'
					+'<div class="shenhe"><button  commid="'
					+data[i].commid+'" class="btn btn-primary none" style="margin-right:20px">'+(data[i].flag=="0"?"隐藏":"显示")+'</button>'
					+'<button  commid="'+data[i].commid
					+'" class="btn btn-primary delete">删除</button></div></li>';
			}	
			$(".cul1").html(thtml);
			$(".delete").click(function(){
					var a=$(this);
				$.post("/user/deletecom",{
					commid:$(this).attr("commid")
				},function(data){
					if(data.result){
						a.parent().parent().remove();
						getFullCom();
					}
				},'json');
			});
			$(".none").click(function(){
				var flag="";
				var a=$(this);
				if($(this).text()=="显示")
					flag="1";
				else
					flag="0";
				$.post("/user/shenhe",{
					flag:flag,
					commid:$(this).attr("commid")
				},function(data){
					if(data.result){
						if(flag=="1")
							a.text("隐藏");
						else
							a.text("显示");
					}
				},'json');
			});
		},'json');
	}
	
	function getAllzan(){
		$.post("/user/getAllzan",{
			paperid:paperid,
		},function(data){
			var thtml="";
			for (var i=0;i<data.length;i++){
				thtml+='<li><div class="cccontent"><img src="img/head.jpg"  class="cimg"><span class="cname">'
					+data[i].user1id+'</span><span class="ctime">'+data[i].time+'</span></div></li>';
			}	
			$(".cul2").html(thtml);
		},'json');
	}
	$("#zannum").click(function(){
		$(".cul2").show();
		$(".cul1").hide();
		getAllzan();
	});
	$("#comnum").click(function(){
		$(".cul2").hide();
		$(".cul1").show();
		getAllFullcom();
	});

	
	
	
	
	
});
