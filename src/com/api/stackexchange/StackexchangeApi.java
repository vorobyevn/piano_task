package com.api.stackexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import models.QuestionList;

@WebServlet("/search")
public class StackexchangeApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int pagesize = 2;
	private static String stackexchangeUrl = "http://api.stackexchange.com/2.2/search?page=%d&pagesize=%d&order=desc&sort=activity&intitle=%s&site=stackoverflow";
	private static String pageUrl = "%s?searchQuery=%s&page=%d";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String searchQuery = request.getParameter("searchQuery");
		String page = request.getParameter("page");
		if (searchQuery != null && searchQuery.trim() != "") {
			PopulatePage(request, searchQuery, page);
		}
		
		request.getRequestDispatcher("/views/search.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String searchQuery = request.getParameter("searchQuery").trim();
		String page = request.getParameter("page");
		PopulatePage(request, searchQuery, page);
		request.getRequestDispatcher("/views/search.jsp").forward(request, response);
	}

	private void PopulatePage(HttpServletRequest request, String searchQuery, String page) {
		try {
			request.setAttribute("searchQuery", searchQuery);
			int pageNum = GetPageNum(page);
			QuestionList questionList = GetData(searchQuery, pageNum);
			request.setAttribute("questionList", questionList);
			if (questionList.isHasMore()) {
				request.setAttribute("nextUrl", String.format(pageUrl, request.getRequestURL(), URLEncoder.encode(searchQuery, "UTF-8"), pageNum + 1));
			}
			
			if (pageNum > 1) {
				request.setAttribute("prevUrl", String.format(pageUrl, request.getRequestURL(), URLEncoder.encode(searchQuery, "UTF-8"), pageNum - 1));
			}
		}
		catch (Exception e) {
			request.setAttribute("message", e.getMessage());
		}
	}
	
	private QuestionList GetData(String query, int pageNum) throws Exception {
		URL url = new URL(String.format(stackexchangeUrl, pageNum, pagesize, URLEncoder.encode(query, "UTF-8")));
		String responceString = RequestHelper.GetGzip(url);
		QuestionList questionList = null;
		if (responceString != null) {
			questionList = new Gson().fromJson(responceString, QuestionList.class);;
		}
		
		return questionList;
	}

	private int GetPageNum(String page) {
		int pageNum = 1;
		if (page != null && page.trim() != "") {
			try {
			   pageNum = Integer.parseInt(page);
			}
			catch (NumberFormatException e)
			{
			   pageNum = 1;
			}
		}
		
		return pageNum;
	}
}
