/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ci6225.marketzone.servlet.seller;

import com.ci6225.marketzone.ejb.ProductBean;
import com.ci6225.marketzone.pojo.User;
import com.ci6225.marketzone.validation.FormValidation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Ureka
 */
@WebServlet("/AddProduct")
public class AddProductServlet extends HttpServlet {

    @EJB
    ProductBean productBean;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = null;
        String description = null;
        String unitPrice = null;
        String quantity = null;
        FileItem imageItem = null;

        // constructs the folder where uploaded file will be stored
        //String uploadFolder = getServletContext().getRealPath("") + "/productImages";
        // Create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(5000 * 1024);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(5000 * 1024);

        try {
            // Parse the request
            List<FileItem> items = upload.parseRequest(request);
            Iterator iter = items.iterator();

            while (iter.hasNext()) {
                FileItem item = (FileItem) iter.next();

                if (!item.isFormField()) {
                    if (item.getFieldName().equals("productImage") && !item.getString().equals("")) {
                        imageItem = item;
                    }
                    System.out.println(item.getFieldName());
                } else {
                    System.out.println(item.getFieldName() + " " + item.getString());
                    if (item.getFieldName().equals("name")) {
                        name = item.getString();
                    } else if (item.getFieldName().equals("description")) {
                        description = item.getString();
                    } else if (item.getFieldName().equals("unitPrice")) {
                        unitPrice = item.getString();
                    } else if (item.getFieldName().equals("quantity")) {
                        quantity = item.getString();
                    }
                }
            }

        } catch (FileUploadException ex) {
            System.out.println(ex);
            ex.printStackTrace();
            response.sendRedirect("./addProduct");
        } catch (Exception ex) {
            System.out.println(ex);
            ex.printStackTrace();
            response.sendRedirect("./addProduct");
        }
        
        FormValidation validation = new FormValidation();
        List<String> messageList =  new ArrayList<String>();
        if(!validation.validateAddProduct(name, description, quantity, unitPrice, imageItem)) {
            messageList.addAll(validation.getErrorMessages());
            request.setAttribute("errorMessage", messageList);
            request.setAttribute("name", name);
            request.setAttribute("description", description);
            request.setAttribute("quantity", quantity);
            request.setAttribute("unitPrice", unitPrice);
            RequestDispatcher rd = request.getRequestDispatcher("./addProduct");
            rd.forward(request, response);
        }

        try {
            User user = (User) request.getSession().getAttribute("user");
            productBean.addProduct(name, description, user.getUserId(), Integer.parseInt(quantity), Float.parseFloat(unitPrice), imageItem);
            messageList.add("Product Added Successfully.");
            request.setAttribute("successMessage", messageList);
            RequestDispatcher rd = request.getRequestDispatcher("./ViewProductList");
            rd.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("./addProduct");
        }
    }
}
