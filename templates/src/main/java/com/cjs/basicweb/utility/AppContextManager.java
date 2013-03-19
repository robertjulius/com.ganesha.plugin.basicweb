package com.cjs.basicweb.utility;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cjs.core.SessionManager;

public class AppContextManager extends HttpServlet {

	private static final long serialVersionUID = 5224370293529914022L;

	private static AppContextManager instance;

	private SessionManager userSessionManager;
	private PageFail pageFail;

	public static PageFail getPageFail() {
		return instance.pageFail;
	}

	public static SessionManager getSessionManager() {
		return instance.userSessionManager;
	}

	private static void load(ServletContext context) {
		instance = new AppContextManager();

		WebApplicationContext webAppContext = WebApplicationContextUtils
				.getWebApplicationContext(context);

		instance.userSessionManager = (SessionManager) webAppContext
				.getBean("userSessionManager");

		instance.pageFail = (PageFail) webAppContext.getBean("pageFail");
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		AppContextManager.load(config.getServletContext());
	}
}
