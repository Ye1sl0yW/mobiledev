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
import tn.shoppy.model.Product;
import tn.shoppy.services.ProductService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author bouzmen
 */
public class AddProductForm extends SideMenuBaseForm {

    private Resources res;

    public AddProductForm(Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        setTitle("Ajouter un produit");
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

        Label nom_prd = new Label("Nom:");
        TextField nameField = new TextField("", "Nom du produit");

        Label quantite_prd = new Label("Quantité en stock:");
        TextField quantiteField = new TextField("", "Quantité");

        Label marque_prd = new Label("Marque:");
        TextField marqueField = new TextField("", "Marque");

        Label prix_prd = new Label("Prix en TND:");
        TextField prixField = new TextField("", "Prix");

        Label decription_prd = new Label("Description du produit:");
        TextField descriptionField = new TextField("", "Description");

        formContainer.addComponent(nom_prd);
        formContainer.addComponent(nameField);

        formContainer.addComponent(quantite_prd);
        formContainer.addComponent(quantiteField);

        formContainer.addComponent(marque_prd);
        formContainer.addComponent(marqueField);

        formContainer.addComponent(prix_prd);
        formContainer.addComponent(prixField);

        formContainer.addComponent(decription_prd);
        formContainer.addComponent(descriptionField);

        Button imageButton = new Button("Prendre une photo");
        imageButton.addActionListener(ev -> {
            String i = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (i != null) {
                Image img = null;
                try {
                    img = Image.createImage(i);
                    // this.revalidate();
                } catch (Exception err) {
                    System.err.println(err.getMessage());
                }
                imageName[0] = System.currentTimeMillis() + ".jpg";
                try {
                    pathToBeStored[0] = FileSystemStorage.getInstance().getAppHomePath() + imageName[0];
                    OutputStream os = FileSystemStorage.getInstance().openOutputStream(pathToBeStored[0]);
                    ImageIO.getImageIO().save(img, os, ImageIO.FORMAT_JPEG, 0.9f);
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        Button addButton = new Button("Envoyer");
        addButton.addActionListener(ev -> {
            System.out.println("WIP : controles de saisie dans le service");

            if (inputControl(nameField.getText(), marqueField.getText(), prixField.getText(), quantiteField.getText())) {
                Product p = new Product();
                p.setNom(nameField.getText());
                p.setQuantite((int) Double.parseDouble(quantiteField.getText()));
                p.setId_magasin(Statics.ID_MAGASIN);
                p.setImage(imageName[0]);
                p.setMarque(marqueField.getText());
                p.setPrix(Double.parseDouble(prixField.getText()));
                p.setDescription(descriptionField.getText());

                File imageFile = new File(pathToBeStored[0]);
                ProductService.getInstance().addProduct(p, imageFile);
            }

        });

        enclosure.addAll(formContainer, imageButton, addButton);
        this.addAll(enclosure);
    }

    public boolean inputControl(String nom, String marque, String prix, String quantite) {

        if (nom.length() == 0) {
            Dialog.show("Champ Vide", "Le nom est obligatoire ", "Ok", null);
            return false;
        }
        if (marque.length() == 0) {
            Dialog.show("Champs Vide", "La marque est obligatoire ", "Ok", null);
            return false;
        }
        if (prix.length() == 0) {
            Dialog.show("Champs Vide", "Le prix est obligatoire ", "Ok", null);
            return false;
        }
        if (quantite.length() == 0) {
            Dialog.show("Champs Vide", "La quantite est obligatoire ", "Ok", null);
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
