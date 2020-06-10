package tn.shoppy.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.shoppy.model.Order;
import tn.shoppy.model.OrderLine;
import tn.shoppy.model.Product;
import tn.shoppy.utils.Statics;
import java.util.Date;

/**
 *
 * @author asus
 */
public class OrderService {

    private static OrderService orderServiceInstance;
    private final ConnectionRequest cn;

    private Map result = new HashMap<>();
    private ArrayList<Order> orders;
    private ArrayList<OrderLine> orderLines;

    public boolean DeleteOrder() {
        System.out.println("tn.shoppy.services.OrderService.DeleteOrder()");
        return true;
    }

    public static OrderService getInstance() {   //Singleton Design Pattern
        if (orderServiceInstance == null) {
            orderServiceInstance = new OrderService();
        }
        return orderServiceInstance;
//        return new ShopService();
    }

    public OrderService() {
        cn = new ConnectionRequest();
    }

    public ArrayList<Order> parseOrders(String jsonText) {
        try {
            orders = new ArrayList<>();
            JSONParser parser = new JSONParser();
            Map<String, Object> ordersListJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //    System.out.println(productsListJson);
            //   List<Map<String, Object>> list = (List<Map<String, Object>>) productsListJson.get("root");
            List<List<Map<String, Object>>> list = (List<List<Map<String, Object>>>) ordersListJson.get("root");

            List<Map<String, Object>> list_s = list.get(0);

            //  System.out.println(list_s);
            for (Map<String, Object> obj : list_s) {
                Order o = new Order();
                o.setIdCmd((int) Double.parseDouble(obj.get("id").toString()));
                o.setAdresseLiv(obj.get("adresseLiv").toString());

                Map<String, Object> dated = (Map<String, Object>) obj.get("dateCreation");
                float da = Float.parseFloat(dated.get("timestamp").toString());
                Date dCeation = new Date((long) (da - 3600) * 1000);

                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                String datee = simpleDateFormat.format(dCeation);
                

                o.setDateCreation(datee);
                float total = (float) Double.parseDouble(obj.get("total").toString());
                o.setTotal(total);

                orders.add(o);
            }

        } catch (IOException ex) {
            System.out.println(ex);

        }
        return orders;
    }

    public ArrayList<Order> getAllOrders() {
        String url = Statics.SERVER_URL + "panier/mobile/orders";
        cn.setUrl(url);
        cn.setPost(false);
        cn.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                orders = parseOrders(new String(cn.getResponseData()));
                cn.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cn);
        return orders;
    }

    public ArrayList<Order> getUserOrders(int userId) {

        String url = Statics.SERVER_URL + "panier/mobile/ordersByUser/" + userId;
        cn.setUrl(url);
        cn.setPost(false);
        cn.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                orders = parseOrders(new String(cn.getResponseData()));
                cn.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cn);
        return orders;
    }

    public ArrayList<OrderLine> getOrderLines(int idCmd) {
        String url = Statics.SERVER_URL + "panier/mobile/orderlines/" + idCmd;
        cn.setUrl(url);
        cn.setPost(false);
        cn.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                orderLines = parseOrderLines(new String(cn.getResponseData()));
                cn.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cn);
        return orderLines;
    }

    public ArrayList<OrderLine> parseOrderLines(String jsonText) {
        try {
            orderLines = new ArrayList<>();
            JSONParser parser = new JSONParser();
            Map<String, Object> orderLinesListJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //    System.out.println(productsListJson);
            //   List<Map<String, Object>> list = (List<Map<String, Object>>) productsListJson.get("root");
            List<List<Map<String, Object>>> list = (List<List<Map<String, Object>>>) orderLinesListJson.get("root");

            List<Map<String, Object>> list_s = list.get(0);

            //  System.out.println(list_s);
            for (Map<String, Object> obj : list_s) {
                OrderLine ol = new OrderLine();
                ol.setIdLC((int) Double.parseDouble(obj.get("id").toString()));

                //System.out.println(obj.get("idProduit").toString());
                //Product p = new Product();
                Product p = parseOneProduct(obj.get("idProduit").toString());
                // ol.setId_product(p.getId());

                ol.setQte((int) Double.parseDouble(obj.get("qte").toString()));
                float total = (float) Double.parseDouble(obj.get("totalLigne").toString());
                ol.setTotalLc(total);

                orderLines.add(ol);
            }

        } catch (IOException ex) {
            System.out.println(ex);

        }
        return orderLines;
    }

    public Product parseOneProduct(String jsonText) {
        try {
            Product p = new Product();
            JSONParser parser = new JSONParser();
            Map<String, Object> productMapJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            System.out.println(productMapJson);
            //  p.setId((int) Double.parseDouble(productMapJson.get("id").toString()));
            //  p.setNom(productMapJson.get("nom").toString());
            //  p.setQuantite((int) Double.parseDouble(productMapJson.get("quantite").toString()));
            //  p.setDescription(productMapJson.get("description").toString());
            //  double prix = Double.parseDouble(productMapJson.get("prix").toString());
            //  p.setPrix(prix);
            //   p.setMarque(productMapJson.get("marque").toString());

            //  return p;
        } catch (IOException ex) {
            System.out.println(ex);

        }
        return new Product();
    }

    public void MailPdf(int id) {
        System.out.println("tn.shoppy.services.OrderService.MailPdf()");

        MultipartRequest req = new MultipartRequest();
        req.setUrl(Statics.CART_URL + "mailC/" + id);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (0 < 1) {
                    req.setPost(true);
                    Dialog.show("Confirmation Envoi Mail", "Appuyez sur OK pour Terminer", "Ok", null);

                    ToastBar.showMessage("service envoi facture", FontImage.MATERIAL_INFO);

                } else {
                    Dialog.show("Confirmation", "update failed", "Ok", null);
                }
            }
        });
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInfiniteBlocking();
        req.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueueAndWait(req);

    }
}
