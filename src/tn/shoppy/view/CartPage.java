/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.components.ImageViewer;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import tn.shoppy.model.Cart;
import tn.shoppy.model.Product;
import tn.shoppy.services.CartService;
import tn.shoppy.services.OrderService;
import tn.shoppy.services.ProductService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author DevSpark
 */
public class CartPage extends SideMenuBaseForm {

    protected Resources res;

    public CartPage(Resources res) {

        super(new BorderLayout());
        this.res = res;
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("eshop.jpg");
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());

        tb.getUnselectedStyle().setBgImage(profilePic);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);

        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent
                = BorderLayout.north(
                        BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                        add(BorderLayout.CENTER, space).
                        add(BorderLayout.SOUTH,
                                FlowLayout.encloseIn(
                                        new Label("  Dev", "WelcomeBlue"),
                                        new Label("Spark", "WelcomeWhite")
                                ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);

        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);

        Container headerContainter = new Container(BoxLayout.y());
        Label title = new Label("Panier");
        title.setAlignment(CENTER);
        headerContainter.add(title);
        headerContainter.add(separator);
        add(BorderLayout.NORTH, headerContainter);
        Container enclosure = new Container(BoxLayout.y());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);

        Label totalLabel = new Label("Total panier: "+CartService.getInstance().getCartTotal()+" TND.");
        enclosure.add(totalLabel);
        
        if (Cart.cart.keySet() != null) {
            for (Product p : Cart.cart.keySet()) {
                this.displayProduct(p, enclosure);
            }
        }

        TextField addressField = new TextField();
        addressField.setHint("Adresse de livraison");
        Button orderButton = new Button("Commander");
        orderButton.addActionListener(evt -> {
            if (addressField.getText().length() > 0 && !Cart.cart.isEmpty()) {
                OrderService.getInstance().addOrder(addressField.getText());
                revalidate();
            } else {
                Dialog.show("Erreur","Veuillez saisir une adresse de livraison et avoir un panier non vide","OK",null);
            }
        });

        Button emptyButton = new Button("Vider");
        emptyButton.addActionListener(evt -> {
            CartService.getInstance().emptyCart();
            new CartPage(res).show();
        });

        enclosure.addAll(addressField, orderButton, emptyButton);
        setupSideMenu(res);
    }


    public void displayProduct(Product product, Container enclosure) {
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("default.jpg").scaled(200, 200), true);
        ImageViewer productImage = new ImageViewer(placeholder);

        URLImage logo = URLImage.createToStorage(placeholder, product.getNom() + ".cache",
                Statics.IMAGE_DIR + product.getImage());
        //EncodedImage image = EncodedImage.create(getClass().getResourceAsStream(Statics.IMAGE_DIR + product.getImage()), TOP);

        productImage.setImage(logo);

        Container card = new Container(new FlowLayout());
        Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setMargin(TOP, 230);

        Label nom_prd = new Label(product.getNom());
        Label quantite_prd = new Label("Quantité  : " + Integer.toString(Cart.cart.get(product)));
        Label marque_prd = new Label(product.getMarque());
        Label prix_prd = new Label(Double.toString(product.getPrix()) + " TND");

        textContainer.addAll(nom_prd, prix_prd, marque_prd, quantite_prd);
        imageContainer.add(productImage);
        Button removeButton = new Button("Retirer");
        removeButton.addActionListener(evt -> {
            CartService.getInstance().remmoveOneProductFromCart(product);
            new CartPage(res).show();
        });
        card.addAll(imageContainer, textContainer, removeButton);
        enclosure.add(card);

        card.addPointerPressedListener((evt) -> {
            ProductDetails details = new ProductDetails(product, this, res);
            details.show();
        });

    }
   

    @Override
    protected void showOtherForm(Resources res) {
        //    new AddProject(res).show();
    }

    @Override
    protected void showRecent(Resources res) {
        new HomePage(res).show();

    }

    @Override
    protected void showProducts(Resources res) {
        new ProductsList(res).show();
    }

    @Override
    protected void showShops(Resources res) {
        new ShopList(res).show();
    }

    protected void showSellerForm(Resources res) {
        new SellerForm(res).show();
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
