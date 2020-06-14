package tn.shoppy.services;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ToastBar;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.events.ActionListener;
import java.util.ArrayList;
import tn.shoppy.model.OrderLine;
import tn.shoppy.utils.Statics;

/**
 *
 * @author asus
 */
public class OrderLineService {

    private static OrderLineService orderLineServiceInstance;

    public static OrderLineService getInstance() {   //Singleton Design Pattern
        if (orderLineServiceInstance == null) {
            orderLineServiceInstance = new OrderLineService();
        }
        return orderLineServiceInstance;
//        return new OrderLineService();
    }

    public ArrayList<OrderLine> getOrderLines(int orderId) {
        return null;
    }

    public void addOrderline(int orderId, int productID, int quantity, float totalLine) {
        OrderLine ol = new OrderLine();
        ol.setId_cmd(orderId);
        ol.setId_product(productID);
        ol.setQte(quantity);
        ol.setTotalLc(totalLine);

        MultipartRequest req = new MultipartRequest();
        req.setUrl(Statics.CART_URL + "mobile/addOrderLine"
                + "?orderId=" + ol.getId_cmd()+ "&productId="
                + ol.getId_product() + "&qte=" + ol.getQte() + "&total=" + ol.getTotalLc());
        req.setPost(true);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                if (0 < 1) {
                   // Dialog.show("Confirmation création de commande.", "Appuyez sur OK pour confirmer.", "OK", null);
                    ToastBar.showMessage("Traitement en cours...", FontImage.MATERIAL_INFO);
                    req.removeResponseListener(this);
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
