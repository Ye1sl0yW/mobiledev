package tn.shoppy.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import tn.shoppy.model.*;
import tn.shoppy.utils.Statics;

/**
 *
 * @author Haroun
 */
public class ShopService {

    private static ShopService shopServiceInstance;
    private final ConnectionRequest cn;

    private Map result = new HashMap<>();
    private ArrayList<Shop> shops;
    int[] shopOffers = {0,0,0};

    public ShopService() {
        cn = new ConnectionRequest();
    }

    public static ShopService getInstance() {   //Singleton Design Pattern
        if (shopServiceInstance == null) {
            shopServiceInstance = new ShopService();
        }
        return shopServiceInstance;
    }

    public Map getResponse(String request_url) {
        String url = "http://localhost/pi_dev_2020/web/app_dev.php/mobile/" + request_url;
        System.out.println(url);
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(false);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInfiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        r.addResponseListener((evt) -> {
            try {
                JSONParser parser = new JSONParser();
                Reader targetReader = new InputStreamReader(new ByteArrayInputStream(r.getResponseData()));
                result = parser.parseJSON(targetReader);

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return result;
    }

    public ArrayList<Shop> parseShops(String jsonText) {
        try {
            shops = new ArrayList<>();
            JSONParser parser = new JSONParser();
            Map<String, Object> shopListJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<List<Map<String, Object>>> list = (List<List<Map<String, Object>>>) shopListJson.get("root");

            List<Map<String, Object>> list_s = list.get(0);

            for (Map<String, Object> obj : list_s) {
                Shop shop = new Shop();
                shop.setId((int) Double.parseDouble(obj.get("id").toString()));
                shop.setNom(obj.get("nom").toString());
                if (obj.get("tailleStock") != null) {
                    shop.setTaille_stock((int) Double.parseDouble(obj.get("tailleStock").toString()));
                } else {
                    shop.setTaille_stock(0);
                }
                shop.setMatricule_fiscal((int) Double.parseDouble(obj.get("matriculeFiscal").toString()));
                shops.add(shop);
            }

        } catch (IOException ex) {
            System.out.println(ex);

        }
        return shops;
    }

    public ArrayList<Shop> getAllShops() {
        if (shops == null) {
            String url = Statics.BASE_URL + "shops";
            cn.setUrl(url);
            cn.setPost(false);
            cn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    shops = parseShops(new String(cn.getResponseData()));
                    cn.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(cn);
        }
        return shops;
    }

    public int[] getShopStats(int idShop) {
        String url = Statics.BASE_URL + "shopStats/" + idShop;
        cn.setUrl(url);
        cn.setPost(false);
        cn.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                shopOffers = parseShopStats(new String(cn.getResponseData()));
                cn.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(cn);

        return shopOffers;
    }

    public int[] parseShopStats(String jsonText) {
        int[] offers = {0,0,0};
        try {            
            JSONParser parser = new JSONParser();
            Map<String, Object> offerMapJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) offerMapJson.get("root");
            Map<String, Object> list_s = list.get(0);
            
            offers[0] = (int) Double.parseDouble(list_s.get("all").toString());
            offers[1] = (int) Double.parseDouble(list_s.get("current").toString());
            offers[2] = (int) Double.parseDouble(list_s.get("future").toString());

        } catch (IOException ex) {
            System.out.println(ex);

        }
        return offers;
    }

}
