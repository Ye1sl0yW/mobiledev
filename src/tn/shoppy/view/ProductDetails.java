/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;

import com.codename1.components.ImageViewer;
import com.codename1.components.ShareButton;
import com.codename1.messaging.Message;
import com.codename1.share.FacebookShare;
import com.codename1.share.ShareService;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import tn.shoppy.model.Product;
import tn.shoppy.services.CartService;
import tn.shoppy.services.FacebookService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author bouzmen
 */
public class ProductDetails extends SideMenuBaseForm {

    private Product product;
    private Resources res;

    public ProductDetails(Product product, Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.product = product;
        this.res = res;
        setTitle(product.getNom());
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });

        Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container imageContainer = new Container(new FlowLayout(CENTER));
        Container iconContainer = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        imageContainer.setSize(calcScrollSize());
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("default.jpg"), true);
        ImageViewer productImage = new ImageViewer(placeholder);
        URLImage logo = URLImage.createToStorage(placeholder, product.getNom() + ".cache", Statics.IMAGE_DIR + product.getImage());
        productImage.setImage(logo);
        imageContainer.add(productImage);

        Label nom_prd = new Label(product.getNom());
        Label quantite_prd = new Label("Quantité en stock: " + Integer.toString(product.getQuantite()));
        Label marque_prd = new Label(product.getMarque());
        Label prix_prd = new Label("Prix: " + Double.toString(product.getPrix()) + " TND");
        Label decription_prd = new Label(product.getDescription());
        textContainer.addAll(nom_prd, prix_prd, marque_prd, quantite_prd, decription_prd);

        EncodedImage mailIcon = EncodedImage.createFromImage(res.getImage("mail.png").scaled(200, 200), true);
        ImageViewer mailImageViewer = new ImageViewer(placeholder);
        Button mailButton = new Button(mailIcon);

        mailButton.addPointerPressedListener((evt) -> {
            System.out.println("Mail WIP");
            Message m = new Message("Bonjour,\nJ'ai des questions concernant ce produit: " + product.getNom() + ".");
            System.out.println(placeholder.getImageData());
            Display.getInstance().sendMessage(new String[]{"shoppy.tn@outlook.com"}, "Question: " + product.getNom(), m);
        });
        iconContainer.add(mailButton);

        EncodedImage twitterIcon = EncodedImage.createFromImage(res.getImage("twitter-icon.png").scaled(200, 200), true);
        ImageViewer twitterImageViewer = new ImageViewer(placeholder);
        Button twitterButton = new Button(twitterIcon);

        twitterButton.addPointerPressedListener((evt) -> {
            System.out.println("Twitter WIP");
        });
        iconContainer.add(twitterButton);

        EncodedImage facebookIcon = EncodedImage.createFromImage(res.getImage("facebook-icon.png").scaled(180, 180), true);
        ImageViewer facebookImageViewer = new ImageViewer(placeholder);
        Button facebookButton = new Button(facebookIcon);

        facebookButton.addPointerPressedListener((evt) -> {
            System.out.println("facebook button pressed");
            FacebookService.getInstance().facebookConnect(this);
        });
        iconContainer.add(facebookButton);

        ShareButton sb = new ShareButton();
        sb.setUIID("Label");
        sb.getAllStyles().setAlignment(Component.TOP);
        //String imageFile = FileSystemStorage.getInstance().getAppHomePath() + getMyFileName();
        //sb.setImageToShare(imageFile, "image/png");
        sb.setTextToShare("My Text to share");
        sb.getIcon().scaled(200, 200);
        //sb.setSize(new Dimension(200, 200));
        iconContainer.add(sb);

        TextField quantityField = new TextField();
        quantityField.setConstraint(TextArea.NUMERIC);
               
        Button buyButton = new Button("Ajouter au panier");
        buyButton.addActionListener(((evt) -> {
            String qt = quantityField.getText();
            if (qt.length() > 0 && CartService.getInstance().integerInput(qt)) {
                CartService.getInstance().addProductToCart(product, quantityField.getText());
                //new CartPage(res).show();
                Dialog.show("Confirmer","Produit ajouté au panier ! ","OK",null);
            } else {
                Dialog.show("Erreur", "Veuillez saisir une quantité à ajouter.", "OK",null); 
            }

        }));

        enclosure.addAll(imageContainer, textContainer, quantityField, buyButton, iconContainer);
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

    @Override
    protected void showOrderList(Resources res) {
        new OrderList(res).show();
    }

    @Override
    protected void showCart(Resources res) {
        new CartPage(res).show();
    }
}
