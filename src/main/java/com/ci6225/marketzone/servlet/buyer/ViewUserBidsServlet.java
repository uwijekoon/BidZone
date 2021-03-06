/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ci6225.marketzone.servlet.buyer;

import com.ci6225.marketzone.ejb.ProductBean;
import com.ci6225.marketzone.pojo.User;
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
@WebServlet("/ViewUserBids")
public class ViewUserBidsServlet extends HttpServlet{
    @EJB
    ProductBean productBean;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewUserBidsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		try {
                    User user = (User) request.getSession().getAttribute("user");
                    //productBean.addProduct(name, description, user.getUsercode());
			request.setAttribute("successMessage", "Product Added Successfully.");
			RequestDispatcher rd = request.getRequestDispatcher("./seller/prodcut_list.jsp");
                rd.forward(request, response);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("./");
		}

	}
}
