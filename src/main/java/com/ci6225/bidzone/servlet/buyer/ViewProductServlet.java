/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ci6225.bidzone.servlet.buyer;

import com.ci6225.bidzone.servlet.seller.*;
import com.ci6225.bidzone.ejb.ProductBean;
import com.ci6225.bidzone.ejb.ShoppingCartBean;
import com.ci6225.bidzone.ejb.ShoppingCartBeanLocal;
import com.ci6225.bidzone.pojo.User;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ureka
 */
@WebServlet("/ViewProduct")
public class ViewProductServlet extends HttpServlet{
    //@EJB
    ShoppingCartBeanLocal cartBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		          doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
                    int productIndex = Integer.parseInt(request.getParameter("productIndex"));
                    cartBean = (ShoppingCartBeanLocal) request.getSession().getAttribute("shoppingCartBean");
                    request.setAttribute("detailProduct", cartBean.getProductList().get(productIndex));
                    RequestDispatcher rd = request.getRequestDispatcher("./jsp/cart/product_detail.jsp");
                    rd.forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("./");
		}

	}
}
