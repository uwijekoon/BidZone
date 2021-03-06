/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ci6225.marketzone.dao;

import com.ci6225.marketzone.pojo.CartItem;
import com.ci6225.marketzone.pojo.Product;
import com.ci6225.marketzone.pojo.ShoppingCart;
import com.ci6225.marketzone.util.ConnectionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Ureka
 */
public class OrderDao {
    public ShoppingCart loadOrderDetails(int orderId) throws Exception{
        String selectStatement = "select * from order_detail where id=?";  
        ShoppingCart order = new ShoppingCart();
	       Connection con = null;
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	try{
		con = ConnectionUtil.createConnection();
		ps = con.prepareStatement(selectStatement);    
		ps.setInt(1, orderId);
		rs = ps.executeQuery();

		if (rs.next()) {
                     float totalPrice = rs.getFloat("total_amount");
                float adminFee = (float) (totalPrice * 0.01);
     float cartTotal = totalPrice + adminFee;
     String firstName = rs.getString("first_name");
     String lastName = rs.getString("last_name");
     String email = rs.getString("email");
     String telephone = rs.getString("telephone");
     String address1 = rs.getString("address1");
     String address2 = rs.getString("address2");
     String city = rs.getString("city");
     String postalCode = rs.getString("postal_code");
     String country = rs.getString("country");
     int paymentMethod = rs.getInt("payment_method");
     String comments = rs.getString("comments");
     Date orderDate = rs.getDate("order_date");
			order = new ShoppingCart(orderId, totalPrice, adminFee, cartTotal, firstName, lastName, email, telephone, address1, address2, city, postalCode, country, paymentMethod, comments, orderDate);
                        
                }                   
                        ps.close();
                        rs.close();
                        
                        selectStatement = "select o.*, p.name as product_name, p.description as description"
                                + ", p.image_path, p.unit_price from order_item o, product p where o.product_id=p.id and o.order_id=?";  
                        ps = con.prepareStatement(selectStatement);    
		ps.setInt(1, orderId);
		rs = ps.executeQuery();

                List<CartItem> orderItems = new ArrayList<>();
		while (rs.next()) {
                    int quantity = rs.getInt("quantity");
                    float amount = rs.getFloat("amount");
                    String productName = rs.getString("product_name");
                    String description = rs.getString("description");
                    String imagePath = rs.getString("image_path");
                    float unitPrice = rs.getFloat("unit_price");
                    CartItem item = new CartItem(quantity, amount);
                    Product product = new Product(productName, description, unitPrice, imagePath);
                    item.setProduct(product);
                    orderItems.add(item);
                            
		} 
                order.setCartItems(orderItems);
	}
	finally{
		if(rs != null) rs.close();
		if(ps != null) ps.close();
		if(con != null) con.close();
	}
        return order;
    }
    
    
    public List<ShoppingCart> getOrderList(int userId) throws Exception{
        String selectStatement = "select * from order_detail where user_id=? order by order_date desc";  
        List<ShoppingCart> orderList = new ArrayList<>();
	       Connection con = null;
	       PreparedStatement ps = null;
	       ResultSet rs = null;
	try{
		con = ConnectionUtil.createConnection();
		ps = con.prepareStatement(selectStatement);    
		ps.setInt(1, userId);
		rs = ps.executeQuery();

		while (rs.next()) {
                    ShoppingCart order = new ShoppingCart();
                    int orderId = rs.getInt("id");
                     float totalPrice = rs.getFloat("total_amount");
                float adminFee = (float) (totalPrice * 0.01);
                float cartTotal = totalPrice + adminFee;
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String telephone = rs.getString("telephone");
                String address1 = rs.getString("address1");
                String address2 = rs.getString("address2");
                String city = rs.getString("city");
                String postalCode = rs.getString("postal_code");
                String country = rs.getString("country");
                int paymentMethod = rs.getInt("payment_method");
                String comments = rs.getString("comments");
                    Date orderDate = rs.getTime("order_date");
			order = new ShoppingCart(orderId, totalPrice, adminFee, cartTotal, firstName, lastName, email, telephone, address1, address2, city, postalCode, country, paymentMethod, comments, orderDate);
                orderList.add(order);
                }                                          
	}
	finally{
		if(rs != null) rs.close();
		if(ps != null) ps.close();
		if(con != null) con.close();
	}
        return orderList;
    }
}
