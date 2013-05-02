package com.ganesha.hibernate;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.LoggerFactory;

public class HibernateSessionServletFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {
			LoggerFactory.getLogger(getClass()).debug(
					"Starting a database transaction.");
			HibernateUtil.getCurrentSession();

			chain.doFilter(request, response);

		} catch (Throwable e) {
			LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
			try {
				if (HibernateUtil.getTransaction().isActive()) {
					LoggerFactory
							.getLogger(getClass())
							.debug("Trying to rollback database transaction after exception.");
					HibernateUtil.rollbackTransaction();
				}
			} catch (Throwable ex) {
				LoggerFactory.getLogger(getClass()).debug(
						"Could not rollback transaction after exception!");
			}

			throw new ServletException(e);

		} finally {
			HibernateUtil.closeSession();
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		LoggerFactory.getLogger(getClass()).debug("Initializing filter...");
	}

}
