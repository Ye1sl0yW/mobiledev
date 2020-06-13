/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;

import com.codename1.capture.Capture;
import com.codename1.io.File;
import com.codename1.io.FileSystemStorage;
import com.codename1.ui.Button;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.components.ImageViewer;
import com.codename1.ui.EncodedImage;
import java.util.ArrayList;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.OutputStream;
import tn.shoppy.model.Ticket;
import tn.shoppy.services.PointsService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;
import com.codename1.ui.layouts.FlowLayout;

/**
 *
 * @author bouzmen
 */
public class PointsForm extends SideMenuBaseForm {

    private Resources res;
    private String recap="";
    public PointsForm( Resources res) {
        super("affichage points", new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        setTitle("Votre portfolio");
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            new HomePage(res).show();
        });

        final String[] imageName = {""};
        final String[] pathToBeStored = {""};
        imageName[0] = "dafault.png";

        Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container iconContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label total = new Label();
        total.setText("Total des points : " + PointsService.getInstance().afficherPoints()  );


        
        
        formContainer.addComponent(total);

        enclosure.addAll(formContainer);
 enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
ArrayList<Ticket> tickets = PointsService.getInstance().getMyTickets();
        int count=0;
        for (Ticket t : tickets)
        {   count++;
            displayTickets(t, enclosure);
            recap+="Ticket n°"+count+" Montant: " + t.getMontant() + " expire le " + t.getDate() + "\n";
        }

        Button recapSMS = new Button("Envoyer recap par SMS");
        recapSMS.addActionListener(ev->{
        ConnectionRequest c = new ConnectionRequest();
        c.setUrl("https://rest.nexmo.com/sms/json");
        c.setPost(true);
        c.addArgument("from","Shoppy-mobile"); 
        c.addArgument("text",recap); 
        c.addArgument("to",NUMERO); 
        c.addArgument("api_key",KEY); 
        c.addArgument("api_secret",SECRET); 
        NetworkManager.getInstance().addToQueueAndWait(c);
        }



        );
        enclosure.add(recapSMS);
        this.addAll(enclosure);

       
}

    
    
@Override
    protected void showOtherForm(Resources res) {
        //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showShops(Resources res) {
        new ShopList(res).show();
    }

    @Override
    protected void showRecent(Resources res) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showProducts(Resources res) {
        //     throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showSellerForm(Resources res) {
        // new SellerForm(res).show();
    }


public void displayTickets(Ticket t, Container enclosure) {
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("ticket.jpg").scaled(300, 150),true);
        ImageViewer ticketImage = new ImageViewer(placeholder);

        
        Container card = new Container(new FlowLayout());
        Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setMargin(TOP, 230);

        Label montant_mg = new Label(String.valueOf(t.getMontant()));
        Label date_mg = new Label("A utiliser avant : " + t.getDate());

        textContainer.addAll(montant_mg,date_mg);
        imageContainer.add(ticketImage);
        card.addAll(imageContainer, textContainer);
        enclosure.add(card);
        
        

    }
}