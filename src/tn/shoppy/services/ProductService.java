package tn.shoppy.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.notifications.LocalNotification;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.shoppy.model.Product;
import tn.shoppy.utils.Statics;
import com.codename1.util.regex.RE;

/**
 *
 * @author anas fattoum
 */
public class ProductService {

    private static ProductService productServiceInstance;
    private final ConnectionRequest cn;

    private Map result = new HashMap<>();
    private ArrayList<Product> products;

    public ProductService() {
        cn = new ConnectionRequest();
    }

    public static ProductService getInstance() {   //Singleton Design Pattern
        if (productServiceInstance == null) {
            productServiceInstance = new ProductService();
        }
        return productServiceInstance;
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

    public ArrayList<Product> getAllProducts() {
        //if (products == null) {
            String url = Statics.BASE_URL + "latest";
            cn.setUrl(url);
            cn.setPost(false);
            cn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    products = parseProducts(new String(cn.getResponseData()));
                    cn.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(cn);
       // }
        return products;
    }

    public ArrayList<Product> getRecentProducts() {
        ArrayList<Product> products = this.getAllProducts();
        if (products.size() < 10) {
            return products;
        } else {
            ArrayList<Product> result = new ArrayList<>();
            ArrayList<Integer> ids = new ArrayList<>();
            for (Product p : products) {
                ids.add(p.getId());
            }
            int count = 0;
            while (count < 10) {
                for (Product p : products) {
                    Integer max = getMaxValue(ids);
                    if (p.getId() == max) {
                        count++;
                        result.add(p);
                        ids.remove(max);
                    }
                }
            }
            return result;
        }
    }

    public Integer getMaxValue(ArrayList<Integer> list) {
        int max = list.get(0);
        for (int i : list) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public ArrayList<Product> parseProducts(String jsonText) {
        try {
            products = new ArrayList<>();
            JSONParser parser = new JSONParser();
            Map<String, Object> productsListJson = parser.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            //    System.out.println(productsListJson);
            //   List<Map<String, Object>> list = (List<Map<String, Object>>) productsListJson.get("root");
            List<List<Map<String, Object>>> list = (List<List<Map<String, Object>>>) productsListJson.get("root");

            List<Map<String, Object>> list_s = list.get(0);

            //  System.out.println(list_s);
            for (Map<String, Object> obj : list_s) {
                Product p = new Product();
                p.setId((int) Double.parseDouble(obj.get("id").toString()));
                p.setNom(obj.get("nom").toString());
                p.setQuantite((int) Double.parseDouble(obj.get("quantite").toString()));
                p.setDescription(obj.get("description").toString());
                double prix = Double.parseDouble(obj.get("prix").toString());
                p.setPrix(prix);
                p.setMarque(obj.get("marque").toString());
                if(obj.get("imageName") != null){
                    p.setImage(obj.get("imageName").toString());
                }
                

                //    p.setImageProduct(obj.get("image").toString());
                products.add(p);
            }

        } catch (IOException ex) {
            System.out.println(ex);

        }
        return products;
    }

    public ArrayList<Product> searchProducts(String search) {
        RE r = new RE("(.*?)" + search.toLowerCase() + "(.*?)");
        ArrayList<Product> ListP = this.getAllProducts();
        ArrayList<Product> result = new ArrayList<>();
        for (Product p : ListP) {
            if (r.match(p.getNom().toLowerCase()) || r.match(p.getMarque().toLowerCase())) {
                result.add(p);
            }
        }
        return result;
    }

    public ArrayList<Product> getAllProductsByShop(int shopId) {
        if (products == null) {
            String url = Statics.BASE_URL + "shopProducts/" + shopId;
            cn.setUrl(url);
            cn.setPost(false);
            cn.addResponseListener(new ActionListener<NetworkEvent>() {
                @Override
                public void actionPerformed(NetworkEvent evt) {
                    products = parseProducts(new String(cn.getResponseData()));
                    cn.removeResponseListener(this);
                }
            });
            NetworkManager.getInstance().addToQueueAndWait(cn);
        }
        return products;
    }

    public void addProduct(Product p, File imageFile) {

        MultipartRequest req = new MultipartRequest();
        req.setUrl(Statics.BASE_URL + "addProduct?nom=" + p.getNom() + "&id_magasin="
                + p.getId_magasin() + "&prix=" + p.getPrix() + "&marque=" + p.getMarque()
                + "&description=" + p.getDescription() + "&quantite=" + p.getQuantite()
                + "&image=" + p.getImage());
        req.setPost(true);
        String mime = "image/jpg";
        try {
            req.addData("file", imageFile.getPath(), mime);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        req.setFilename("file", fileName);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (0 < 1) {

                    Dialog.show("Confirmation ajout produit.", "Appuyez sur OK pour confirmer.", "Ok", null);

                    ToastBar.showMessage("Traitement en cours...", FontImage.MATERIAL_INFO);

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
