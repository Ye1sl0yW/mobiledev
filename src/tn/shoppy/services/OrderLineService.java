package tn.shoppy.services;

import java.util.ArrayList;
import tn.shoppy.model.OrderLine;



/**
 *
 * @author asus
 */
public class OrderLineService {
      
    private static OrderLineService orderLineServiceInstance;
       
    public static OrderLineService getInstance() {   //Singleton Design Pattern
        if (orderLineServiceInstance==null)
        {
            orderLineServiceInstance = new OrderLineService();
        }
        return orderLineServiceInstance;  
//        return new OrderLineService();
    }
        
        public ArrayList<OrderLine> getOrderLines(int orderId){
            return null;
        }

        

}
