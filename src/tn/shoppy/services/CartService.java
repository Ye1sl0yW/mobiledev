package tn.shoppy.services;

import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Dialog;
import com.codename1.util.regex.RE;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import tn.shoppy.model.Cart;
import tn.shoppy.model.Order;
import tn.shoppy.model.OrderLine;
import tn.shoppy.model.Product;
import tn.shoppy.view.CartPage;

/**
 *
 * @author asus
 */
public class CartService {

    private static CartService orderServiceInstance;
    private final ConnectionRequest cn;

    private Map result = new HashMap<>();

    private ArrayList<Order> orders;
    private ArrayList<OrderLine> orderLines;

    public boolean DeleteOrder() {
        System.out.println("tn.shoppy.services.OrderService.DeleteOrder()");
        return true;
    }

    public static CartService getInstance() {   //Singleton Design Pattern
        if (orderServiceInstance == null) {
            orderServiceInstance = new CartService();
        }
        return orderServiceInstance;
//        return new ShopService();
    }

    public CartService() {
        cn = new ConnectionRequest();
    }

    public void addProductToCart(Product product, String quantite) {
        if (Integer.valueOf(quantite) > 0) {
            if (Cart.cart.get(product) != null) {
                int q = Cart.cart.get(product);
                q += Integer.valueOf(quantite);
                Cart.cart.put(product, q);
            } else {
                Cart.cart.put(product, Integer.parseInt(quantite));
            }
        } else {
            Dialog.show("Erreur", "Format saisi incorrect", "OK", "");
        }
    }

    public int getCartQuantity() {
        int result = 0;
        if (Cart.cart.keySet() != null) {
            for (Product p : Cart.cart.keySet()) {
                result += Cart.cart.get(p);
            }
        }
        return result;
    }

    public double getCartTotal() {
        double result = 0;
        if (Cart.cart.keySet() != null) {
            for (Product p : Cart.cart.keySet()) {
                result += p.getPrix()*Cart.cart.get(p);
            }
        }
        return result;
    }

    public boolean integerInput(String input) {
        RE c = new RE("^[1-9][:digit:]*");
        return (c.match(input));
    }

    public void emptyCart() {
        Cart.cart.clear();
    }

    public void remmoveOneProductFromCart(Product product) {
        Cart.cart.remove(product);
    }

}
