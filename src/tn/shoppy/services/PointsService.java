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
import tn.shoppy.model.Note;
import tn.shoppy.utils.Statics;
import com.codename1.util.regex.RE;

/**
 *
 * @author Haroun
 */
public class PointsService {

    private static PointsService PointsServiceInstance;
    private final ConnectionRequest cn;

    private Map result = new HashMap<>();
 

    public PointsService() {
        cn = new ConnectionRequest();
    }

    public static PointsService getInstance() {   //Singleton Design Pattern
        if (PointsServiceInstance == null) {
            PointsServiceInstance = new PointsService();
        }
        return PointsServiceInstance;
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

    public void addNoteProduit(Note n) {

        MultipartRequest req = new MultipartRequest();
        req.setUrl("http://localhost/pi_dev_2020/web/app_dev.php/" + "notes/mobile/note?user_id=" + n.getUser_id() + "&produit_id="
                + n.getProduit_id() + "&magasin_id=" + n.getMagasin_id() + "&text=" + n.getText()
                + "&value=" + n.getValue() + "&type=" + n.getType());
        req.setPost(true);
       
        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {

                if (0 < 1) {

                    Dialog.show("Confirmation ajout note.", "Appuyez sur OK pour confirmer.", "Ok", null);

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
