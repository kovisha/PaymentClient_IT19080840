package com;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/BuyerAPI")
public class BuyerAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Payment paymentObj = new Payment();
    
    public BuyerAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/*******************************Insert BUyer Payment details - POST**************************/
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String output = paymentObj.insertBuyerPayment(request.getParameter("cName"),
				request.getParameter("Product"),
				request.getParameter("UserType"),
				request.getParameter("PaymentType"),
				request.getParameter("bank"),
				request.getParameter("paymentDate"),
				request.getParameter("cardNo"),
				request.getParameter("cardName"),
				request.getParameter("cvv"),
				request.getParameter("cmonth"),
				request.getParameter("cyear"));
				response.getWriter().write(output);
	}

	/*******************************Update BUyer Payment details - PUT**************************/
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		
		String output = paymentObj.updateBuyerPaymentDetails( paras.get("hidBuyerIDSave").toString(),
				paras.get("PaymentType").toString(),
				paras.get("UserType").toString(),
				paras.get("bank").toString(),
				paras.get("paymentDate").toString(),
				paras.get("cardNo").toString(),
				paras.get("cardName").toString(),
				paras.get("cvv").toString(),
				paras.get("cmonth").toString(),
				paras.get("cyear").toString(),
				paras.get("Product").toString(),
				paras.get("cName").toString());
				response.getWriter().write(output);
	}

	/*******************************Delete BUyer Payment details - DELETE**************************/
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map paras = getParasMap(request);
		
		String output = paymentObj.deleteBuyerPayment(paras.get("PaymentID").toString());
		
		response.getWriter().write(output);
	}
	
	
	// Convert request parameters to a Map
		private static Map getParasMap(HttpServletRequest request)
		{
			Map<String, String> map = new HashMap<String, String>();
		try
		{
			Scanner scanner = new Scanner(request.getInputStream(), "UTF-8");
			String queryString = scanner.hasNext() ?
			scanner.useDelimiter("\\A").next() : "";
			scanner.close();
			
			String[] params = queryString.split("&");
		for (String param : params)
		{
			String[] p = param.split("=");
			map.put(p[0], p[1]);
		}
		}
		catch (Exception e)
		{
		}
			return map;
		}

}
