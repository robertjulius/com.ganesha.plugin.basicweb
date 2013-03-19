package com.cjs.basicweb.utility;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JSPUtils {

	public static void forward(HttpServletRequest request,
			HttpServletResponse response, ServletContext context,
			String targetPath) throws ServletException, IOException {

		RequestDispatcher dispatcher = context.getRequestDispatcher(targetPath);
		dispatcher.forward(request, response);
	}
}
