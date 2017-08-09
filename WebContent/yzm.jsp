<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.awt.*"
    import="java.awt.image.BufferedImage"
    import="java.util.*"
    import="javax.imageio.ImageIO"%>


<% 
	response.setHeader("Cache-Control","no-cache");
	response.setHeader("Pragma","No-cache");
	response.setDateHeader("Expires", 0);
	//在内存中创建头像
	int width=60,height=20;
	BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
	//获取画笔
	Graphics g=image.getGraphics();
	//背景色
	g.setColor(new Color(200,200,200));
	g.fillRect(0, 0, width, height);
	//随机数
	Random rnd=new Random();
	int randNum=rnd.nextInt(8999)+1000;
	String randstr=String.valueOf(randNum);
	//存入session
	session.setAttribute("randstr", randstr);
	//显示验证码
	g.setColor(Color.blue);
	g.setFont(new Font("",Font.PLAIN,20));
	g.drawString(randstr, 10, 17);
	//随机产生干扰点
	for(int i=0;i<100;i++)
	{
		int x=rnd.nextInt(width);
		int y=rnd.nextInt(height);
		g.drawOval(x, y,1, 1);
	}
	//输出到页面
	ImageIO.write(image, "JPEG", response.getOutputStream());
	out.clear();
	out=pageContext.pushBody();

%>
