$(function(){
	getlist1();
	var searchkey=1;
	var user2id="";
	var paperid="";
	var user2account="";
	var zan="";
	var type=1; //判断返回后是否关注还是未关注
	function getlist1(){
		$.post("/user/guanzhu",{
			key:$("#search").val(),
		},function(data){
			var chtml="";
			for(var i=0;i<data.length;i++){
				chtml+='<li class="guanzhuli" id="'+data[i].user2id+'" account="'+data[i].account+'"><div class="lidiv" ><img src="img/head.jpg" class="head"><h7>'
				      +data[i].account+'</h7><p class="lispan">'+(data[i].say==null?"":data[i].say)+'</p></div></li>';
			}
			$(".content ul").html(chtml);
			$(".guanzhuli").click(function(){
				$(".title").show();
				$("#back").hide();
				$("#title").text($(this).attr("account"));
				user2id=$(this).attr("id");
				user2account=$(this).attr("account");
				$(".main").show();
				$("#backg").hide();
				$(".account").hide();
				$(".content2").hide();
				$("#searchdiv").show();
				$("#like").addClass("glyphicon-heart");
				$("#like").removeClass("glyphicon-star");
				$("#like").css("color","red");
				$("#quxiao").text("取消关注");
				$.post("/user/getguanzhupaper",{
					account:$(this).attr("account"),
				},function(data){
					var thtml="";
					for(i=0;i<data.length;i++){
						thtml+='<li class="mainli" paperid="'+data[i].paperid+'"><a href="#" class="maina">'
						+'<div class="picdiv"><img class="pic" src="img/a1.jpg"/>'
						+'<p>'+data[i].title+'</p></div></a></li>';
					}
					$(".mainul").html(thtml);
					$(".mainli").click(function(){
						type=1;
						$(".main").hide();
						$("#back").show();
						$(".content2").show();
						$("#like").removeClass("glyphicon-heart");
						$("#like").addClass("glyphicon-star");		
						$("#searchdiv").hide();
						paperid=$(this).attr("paperid");
						getZan();
						getCom();
						getAllcom();
						$.post("/user/shifoushoucang",{
							paperid:paperid,
						},function(data){
							if(data.result){
								$("#quxiao").text("取消收藏");
								$("#like").css("color","red");
							}else{
								$("#quxiao").text("收藏");
								$("#like").css("color","black");
							}
						},'json');
						$.post("/user/shifoudianzan",{
							paperid:paperid,
						},function(data){
							if(data.result){
								zan="-1";
								$("#zan").css("color","red");
							}else{
								zan="1";
								$("#zan").css("color","black");
							}
						},'json');
						$.post("/user/getpapercontent",{
							paperid:$(this).attr("paperid"),
						},function(data){
							$("#paperh2").text(data.title);
							$("#papertime").text(data.edittime);
							$("#paperauthour").text(data.user2id);
							$("#papercontent").html(data.content);
						},'json');
					});
				},'json');
			});
		},'json');
	}
	function getlist2(){
		$.post("/user/faxian",{
			key:$("#search").val(),
		},function(data){
			var chtml="";
			for(var i=0;i<data.length;i++){
				chtml+='<li class="gaunzhuli" id="'+data[i].id+'" account="'+data[i].account+'"><div class="lidiv" ><img src="img/head.jpg" class="head"><h7>'
				      +data[i].account+'</h7><p class="lispan">'+(data[i].say==null?"":data[i].say)+'</p></div></li>';
			}
			$(".content ul").html(chtml);
			$(".gaunzhuli").click(function(){
				$(".title").show();
				$("#back").hide();
				$("#title").text($(this).attr("account"));
				user2id=$(this).attr("id");
				user2account=$(this).attr("account");
				$(".main").show();
				$("#backg").hide();
				$(".account").hide();
				$(".content2").hide();
				$("#searchdiv").show();
				$("#like").addClass("glyphicon-heart");
				$("#like").css("color","black");
				$("#quxiao").text("关注");
				$("#like").removeClass("glyphicon-star");	
				$.post("/user/getguanzhupaper",{
					account:$(this).attr("account"),
				},function(data){
					var thtml="";
					for(i=0;i<data.length;i++){
						thtml+='<li class="mainli" paperid="'+data[i].paperid+'"><a href="#" class="maina">'
						+'<div class="picdiv"><img class="pic" src="img/a1.jpg"/>'
						+'<p>'+data[i].title+'</p></div></a></li>';
					}
					$(".mainul").html(thtml);
					$(".mainli").click(function(){
						type=2;
						paperid=$(this).attr("paperid");
						getZan();
						getCom();
						getAllcom();
						$(".main").hide();
						$("#back").show();
						$(".content2").show();
						$("#like").removeClass("glyphicon-heart");
						$("#like").addClass("glyphicon-star");		
						$("#searchdiv").hide();
						$.post("/user/shifoudianzan",{
							paperid:paperid,
						},function(data){
							if(data.result){
								zan="-1";
								$("#zan").css("color","red");
							}else{
								zan="1";
								$("#zan").css("color","black");
							}
						},'json');
						$.post("/user/shifoushoucang",{
							paperid:paperid,
						},function(data){
							if(data.result){
								$("#quxiao").text("取消收藏");
								$("#like").css("color","red");
							}else{
								$("#quxiao").text("收藏");
								$("#like").css("color","black");
							}
						},'json');
						$.post("/user/getpapercontent",{
							paperid:$(this).attr("paperid"),
						},function(data){
							$("#title").text(data.user2id);
							$("#paperh2").text(data.title);
							$("#papertime").text(data.edittime);
							$("#paperauthour").text(data.user2id);
							$("#papercontent").html(data.content);
						},'json');
					});
				},'json');
			});
		},'json');
	}
	$("#searchepaper").click(function(){
		$.post("/user/getguanzhupaper",{
			account:user2account,
			key:$("#search2").val()
		},function(data){
			var thtml="";
			for(i=0;i<data.length;i++){
				thtml+='<li class="mainli" paperid="'+data[i].paperid+'"><a href="#" class="maina">'
				+'<div class="picdiv"><img class="pic" src="img/a1.jpg"/>'
				+'<p>'+data[i].title+'</p></div></a></li>';
			}
			$(".mainul").html(thtml);
			$(".mainli").click(function(){
				$(".main").hide();
				$("#back").show();
				$(".content2").show();
				$("#like").removeClass("glyphicon-heart");
				$("#like").addClass("glyphicon-star");		
				$("#searchdiv").hide();
				paperid=$(this).attr("paperid");
				getZan();
				getCom();
				getAllcom();
				$.post("/user/shifoudianzan",{
					paperid:paperid,
				},function(data){
					if(data.result){
						$("#zan").css("color","red");
					}else{
						$("#zan").css("color","black");
					}
				},'json');
				$.post("/user/shifoushoucang",{
					paperid:paperid,
				},function(data){
					if(data.result){
						$("#quxiao").text("取消收藏");
						$("#like").css("color","red");
					}else{
						$("#quxiao").text("收藏");
						$("#like").css("color","black");
					}
				},'json');
				$.post("/user/getpapercontent",{
					paperid:$(this).attr("paperid"),
				},function(data){
					$("#title").text(data.user2id);
					$("#paperh2").text(data.title);
					$("#papertime").text(data.edittime);
					$("#paperauthour").text(data.user2id);
					$("#papercontent").html(data.content);
				},'json');
			});
		},'json');
	});
	function getlist3(){
		$.post("/user/shoucang",{
			key:$("#search").val(),
		},function(data){
			var chtml="";
			for(var i=0;i<data.length;i++){
				chtml+='<li class="contentulli" id="'+data[i].paperid+'" account="'+data[i].user2id+'"><div class="lidiv" ><img src="img/backg.jpg" class="head"><h7>'
				      +data[i].title+'</h7></div></li>';
			}
			$(".content ul").html(chtml);
			$(".contentulli").click(function(){
	
				$("#backg").hide();
				$(".main").hide();
				$(".title").show();
				$(".content2").show();
				$("#like").removeClass("glyphicon-heart");
				$("#like").addClass("glyphicon-star");	
				$("#like").css("color","red");
				$("#quxiao").text("取消收藏");
				$("#searchdiv").hide();
				paperid=$(this).attr("id");
				getZan();
				getCom();
				getAllcom();
				$.post("/user/getpapercontent",{
					paperid:$(this).attr("id"),
				},function(data){
					$("#paperh2").text(data.title);
					$("#papertime").text(data.edittime);
					$("#paperauthour").text(data.user2id);
					$("#papercontent").html(data.content);
				},'json');
			});
		},'json');
	}
	//搜索
	$("#guanzhu").click(function(){
		searchkey=1;
		$("#guanzhu").addClass("active");
		$("#faxian").removeClass("active");
		$("#shoucang").removeClass("active");
		getlist1();
	});
	$("#faxian").click(function(){
		searchkey=2;
		$("#guanzhu").removeClass("active");
		$("#faxian").addClass("active");
		$("#shoucang").removeClass("active");
		getlist2();
	});
	$("#shoucang").click(function(){
		searchkey=3;
		$("#guanzhu").removeClass("active");
		$("#faxian").removeClass("active");
		$("#shoucang").addClass("active");
		getlist3();
	});
	function getalllist(){
		if(searchkey==3)
			getlist3();
		if(searchkey==2)
			getlist2();
		if(searchkey==1)
			getlist1();
	}
	$("#searchspan").click(function(){
		if(searchkey==1){
			getlist1();
			return;
		}
		if(searchkey==2){
			getlist2();
			return;
		}
		if(searchkey==3){
			getlist3();
			return;
		}
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
	$("#button4").click(function(){
		$(".modal3").modal("hide");
	});
	$("#button1").click(function(){
		$(".modal1").modal("hide");
		window.location.reload();
	});
	$("#button10").click(function(){
		$(".modal10").modal("hide");
			
			$("#like").css("color","black");
			$("#quxiao").text("关注");
			getalllist();
	});
	$("#button11").click(function(){
		$(".modal11").modal("hide");
		
			$("#like").css("color","red");
			$("#quxiao").text("取消关注");
			getalllist();
	});
	
	
	$("#button12").click(function(){
		$(".modal12").modal("hide");
		
			$("#like").css("color","black");
			$("#quxiao").text("收藏");
			getalllist();
	});
	$("#button13").click(function(){
	
		$(".modal13").modal("hide");
			$("#like").css("color","red");
			$("#quxiao").text("取消收藏");
			getalllist();
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
	//退出
	$("#tuichu").click(function(){
		$.post("/user/away");
		window.location.href="login.html";
	});
	$("#button").click(function(){
		$(".modal1").modal("hide");
		$("#like").css("color","#000000");
	});

	$("#back").click(function(){
		$(".main").show();
		$("#back").hide();
		$(".content2").hide();
		$("#like").removeClass("glyphicon-star");
		$("#like").addClass("glyphicon-heart");
		$("#searchdiv").show();
		if(type==1){
			$("#like").css("color","red");
			$("#quxiao").text("取消关注");
		}else{
			$("#like").css("color","black");
			$("#quxiao").text("关注");
		}
	})
	$("#com").click(function(){
		$(".modal4").modal("show");
		$("#commit").val("");
	});
	$("#button2").click(function(){
		$(".modal2").modal("hide");
		$(".modal1").modal("show");
		$("#message").text("评论成功！");
	});
	$("#button3").click(function(){
		$(".modal2").modal("hide");
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
	});
	$("#bianji").click(function(){
		$(".before").hide();
		$(".after").show();
		$("#backpic").show();
		$("#set").hide();
	});
	
	$("#myaccount").click(function(){
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
	});
	
	//取消关注收藏
	$("#quxiao").click(function(){
		if($("#quxiao").text()=="取消关注"){
			$.post("/user/quguan",{
				user2id:user2id,
			},function(data){
				if(data.result){
					$(".modal10").modal("show");
					$("#message10").text(data.message);
				}else{
					$(".modal3").modal("show");
					$("#warnmessage").text(data.message);
				}
			},'json');
		}
		if($("#quxiao").text()=="关注"){
			$.post("/user/guanzhu1",{
				user2id:user2id,
			},function(data){
				if(data.result){
					$(".modal11").modal("show");
					$("#message11").text(data.message);
				}else{
					$(".modal3").modal("show");
					$("#warnmessage").text(data.message);
				}
			},'json');		
		}
		if($("#quxiao").text()=="取消收藏"){
			$.post("/user/quxiaoshoucang",{
				paperid:paperid,
			},function(data){
				if(data.result){
					$(".modal12").modal("show");
					$("#message12").text(data.message);
				}else{
					$(".modal3").modal("show");
					$("#warnmessage").text(data.message);
				}
			},'json');
		}
		if($("#quxiao").text()=="收藏"){
			$.post("/user/shoucang1",{
				paperid:paperid,
			},function(data){
				if(data.result){
					$(".modal13").modal("show");
					$("#message13").text(data.message);
				}else{
					$(".modal3").modal("show");
					$("#warnmessage").text(data.message);
				}
			},'json');
		}
	});
	$("#button20").click(function(){
		$(".modal20").modal("hide");
	});
	//评论
	$("#button5").click(function(){
		$(".modal4").modal("hide");
		$("#commit").val("");
	});
	$("#button6").click(function(){
		if($("#commit").val()==""){
			$(".modal3").modal("show");
			$("#warnmessage").text("输入评论不能为空！");
			return;
		}
		$.post("/user/commit",{
			paperid:paperid,
			commit:$("#commit").val(),
		},function(data){
			$(".modal4").modal("hide");
			if(data.result){
				getCom();
				getAllcom();
				$(".modal20").modal("show");
				$("#message20").text(data.message);
				
			}else{
				$(".modal3").modal("show");
				$("#warnmessage").text(data.message);
			}
		},'json');
	});
	
	//点赞 
	$("#zan").click(function(){
		$.post("/user/zan",{
			zan:zan,
			paperid:paperid
		},function(data){
			if(data.result){
				getZan();
				getAllzan();
				if(zan=="-1"){
					$("#zan").css("color","black");
					zan=1;
				}else{
					$("#zan").css("color","red");
					zan=-1;
				}
			}
				
		},"json");
	});
	
	
	function getZan(){
		$.post("/user/getZan",{
			paperid:paperid,
		},function(data){
			$("#zannum").text(data.sum);
		},'json');
	}
	function getCom(){
		$.post("/user/getCom",{
			paperid:paperid,
		},function(data){
			$("#comnum").text(data.sum);
		},'json');
	}
	function getAllcom(){
		$.post("/user/getAllcom",{
			paperid:paperid,
		},function(data){
			var thtml="";
			for (var i=0;i<data.length;i++){
				thtml+='<li><div class="cccontent"><img src="img/head.jpg"  class="cimg"><span class="cname">'
					+data[i].user1id+'</span><span class="ctime">'
					+data[i].time+'</span><p class="cp">'+data[i].commitment+'</div></p></li>';
			}
			$(".cul1").html(thtml);
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
		getAllcom();
	});
	
	
});
