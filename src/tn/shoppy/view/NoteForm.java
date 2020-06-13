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

import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;

import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import java.io.OutputStream;
import tn.shoppy.model.Note;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.PointsService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author bouzmen
 */
public class NoteForm extends SideMenuBaseForm {

    private Resources res;
    

    public NoteForm(Product p, Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        setTitle("Noter un produit");
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });

        final String[] imageName = {""};
        final String[] pathToBeStored = {""};
        imageName[0] = "dafault.png";

        Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container iconContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label com = new Label("Commentaire:");
        TextField comField = new TextField("", "Commentaire");

        Label note = new Label("Note:");
        TextField noteField = new TextField("", "Note");

        

        formContainer.addComponent(note);
        formContainer.addComponent(noteField);

        formContainer.addComponent(com);
        formContainer.addComponent(comField);

       

        Button addButton = new Button("Envoyer");
        addButton.addActionListener(ev -> {
            System.out.println("WIP : controles de saisie dans le service");

            if (inputControl(comField.getText())) {
                Note n = new Note();
                n.setText(comField.getText());
                n.setValue((int) Double.parseDouble(noteField.getText()));
                n.setProduit_id(p.getId());
                n.setMagasin_id(p.getId_magasin());
                n.setUser_id(1);
                n.setType(0);

                PointsService.getInstance().addNoteProduit(n);
                  new HomePage(res).show();

            }

        });

        enclosure.addAll(formContainer, addButton);
        this.addAll(enclosure);
    }
public NoteForm(Shop p, Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        setTitle("Noter un magasin");
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });

        final String[] imageName = {""};
        final String[] pathToBeStored = {""};
        imageName[0] = "dafault.png";

        Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container iconContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label com = new Label("Commentaire:");
        TextField comField = new TextField("", "Commentaire");

        Label note = new Label("Note:");
        TextField noteField = new TextField("", "Note");

        

        formContainer.addComponent(note);
        formContainer.addComponent(noteField);

        formContainer.addComponent(com);
        formContainer.addComponent(comField);

       

        Button addButton = new Button("Envoyer");
        addButton.addActionListener(ev -> {
            System.out.println("WIP : controles de saisie dans le service");

            if (inputControl(comField.getText())) {
                Note n = new Note();
                n.setText(comField.getText());
                n.setValue((int) Double.parseDouble(noteField.getText()));
                n.setMagasin_id(p.getId());
                n.setUser_id(1);
                n.setType(1);

                PointsService.getInstance().addNoteProduit(n);
                  new HomePage(res).show();

            }

        });

        enclosure.addAll(formContainer, addButton);
        this.addAll(enclosure);
    }
    public boolean inputControl(String text) {

        if (text.length() == 0) {
            Dialog.show("Champ Vide", "Le nom est obligatoire ", "Ok", null);
            return false;
        }
        return true;
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
}
