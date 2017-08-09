package config;





import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;

import controller.HelloController;
import controller.UserController;
public class WebConfig extends JFinalConfig {

	@Override
	public void configConstant(Constants me) {

		// TODO Auto-generated method stub
		loadPropertyFile("config/dbsource_config.txt");

		me.setDevMode(true);

		me.setEncoding("utf-8");

		me.setViewType(ViewType.JSP); 
	}

	@Override
	public void configRoute(Routes me) {
			// TODO Auto-generated method stub
			me.add("/",HelloController.class);
			me.add("/user",UserController.class);
	}

	@Override
	public void configPlugin(Plugins me) {
		// TODO Auto-generated method stub
		C3p0Plugin c3p0Plugin = new C3p0Plugin(getProperty("datasource-url"),
				getProperty("datasource-user"), getProperty(
						"datasource-password").trim());
		c3p0Plugin.setDriverClass(getProperty("datasource-driver"));
		c3p0Plugin.setMaxPoolSize(1000);
		me.add(c3p0Plugin);
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		//arp.addMapping("jfinaluser", "uid",Jfinaluser.class);
		me.add(arp);
		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
	}

	@Override
	public void configInterceptor(Interceptors me) {
		// TODO Auto-generated method stub

	}

	@Override
	public void configHandler(Handlers me) {
		// TODO Auto-generated method stub
		me.add(new ContextPathHandler("basePath")); 
	}

}
