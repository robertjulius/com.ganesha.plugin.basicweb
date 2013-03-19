package com.cjs.struts2.components.Component;

import java.io.IOException;
import java.io.Writer;

import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;

public class Pagination extends Component {

	private String contextPath;
	private String formAction;

	public Pagination(ValueStack stack, String contextPath) {
		super(stack);
		this.contextPath = contextPath;
	}

	public void setFormAction(String formAction) {
		this.formAction = formAction;
	}

	@Override
	public boolean start(Writer writer) {
		try {
			Integer pageNumber = Integer
					.parseInt(findString("%{pagination.pageNumber}"));
			Integer totalPage = Integer
					.parseInt(findString("%{pagination.totalPage}"));
			writer.write(generateHtml(pageNumber, totalPage));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	private String generateHtml(Integer pageNumber, Integer totalPage) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("<form id=\"paginationForm\" method=\"post\" action=\""
						+ contextPath + formAction
						+ "\" name=\"paginationForm\">");

		stringBuilder
				.append("<input id=\"pageNumber\" type=\"hidden\" name=\"pagination.pageNumber\" value=\""
						+ pageNumber + "\">");
		stringBuilder.append("<table class=\"pagination\">").append("<tbody>")
				.append("<tr>");

		if (pageNumber <= 1) {
			stringBuilder.append("<td>First</td>");
			stringBuilder.append("<td>Previous</td>");
		} else {
			stringBuilder
					.append("<td><a onclick=\"pageFirst(); $(this).closest('form').submit();\" href=\"#\">First</a></td>");
			stringBuilder
					.append("<td><a onclick=\"pagePrevious(); $(this).closest('form').submit();\" href=\"#\">Previous</a></td>");
		}

		for (int i = 1; i <= totalPage; ++i) {
			if (i == pageNumber) {
				stringBuilder.append("<td class=\"pageSelected\"><b>" + i
						+ "</b></td>");
			} else {
				stringBuilder.append("<td><a href=\"#\" onclick=\"pageGoto('"
						+ i + "'); $(this).closest('form').submit();\">" + i
						+ "</a></td>");
			}
		}

		if (pageNumber >= totalPage) {
			stringBuilder.append("<td>Next</td>");
			stringBuilder.append("<td>Last</td>");
		} else {
			stringBuilder
					.append("<td><a href=\"#\" onclick=\"pageNext(); $(this).closest('form').submit();\">Next</a></td>");
			stringBuilder.append("<td><a href=\"#\" onclick=\"pageGoto('"
					+ totalPage
					+ "'); $(this).closest('form').submit();\">Last</a></td>");
		}

		stringBuilder.append("</tr>").append("</tbody>").append("</table>")
				.append("</form>");

		return stringBuilder.toString();
	}
}
