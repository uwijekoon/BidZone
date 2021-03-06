/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ci6225.marketzone.ejb;

import com.ci6225.marketzone.dao.ShoppingCartDao;
import com.ci6225.marketzone.pojo.CartItem;
import com.ci6225.marketzone.pojo.Product;
import com.ci6225.marketzone.pojo.ShoppingCart;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;

/**
 *
 * @author Ureka
 */
@Remote
public interface ShoppingCartBeanLocal {
    public List<Product> searchProducts() throws Exception;

    public List<Product> getProductList() ;
    
    public void addItem(int productIndex, int quantity);

    public ShoppingCart getCart();
    
    public int confirmOrder(String firstName, String lastName, String email, String telephone, String address1, 
            String address2, String city, String postalCode, String country, String comments, int userId) throws Exception;
    
    public void removeItem(int itemIndex);
    
    public void updateCartItems(List<Integer> quantityList);
    
}
