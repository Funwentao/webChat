$(function(){
	$.post("/user/getcookie",function(data){
		$("#username").val(data.account);
		$("#password").val(data.password);
	});
	$("#yzmi").click(function(){
		$("#yzmi").attr("src","yzm.jsp?a="+Math.random());
	});
	$("#login").click(function(){
		var type=$("#type").val();
		if($("#username").val()==""){
			$(".modal1").modal("show");
			$("#message").text("用户名不能为空！")
			return;
		}
		 if($("#password").val()==""){
			$(".modal1").modal("show");
			$("#message").text("密码不能为空！")
			return;
		}
		 if($("#yzm").val()==""){
			$(".modal1").modal("show");
			$("#message").text("请输入验证码！");
			return;
		}
		 $.post("/user/login",{
			 type:type,
			 account:$("#username").val(),
			 password:$("#password").val(),
			 yzm:$("#yzm").val(),
			 check:$("#remember").is(":checked")
		 },function(data){
			 if(data.result){
				 if(type=="1")
					 window.location.href="user.html";
				else
					 window.location.href="user1.html";
			 }else{
					$(".modal1").modal("show");
					$("#message").text(data.message);
			 }
		 },'json');
	});
	$("#d1").click(function(){
		$("#username").val("");
	});
	$("#d2").click(function(){
		$("#password").val("");
	});
	$("#d3").click(function(){
		$("#password2").val("");
	});
	$("#button").click(function(){
		$(".modal1").modal("hide");
	});
});
