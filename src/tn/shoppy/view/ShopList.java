/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import tn.shoppy.model.Shop;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.SideMenuBaseForm;


/**
 *
 * @author DevSpark
 */
public class ShopList extends SideMenuBaseForm {

    protected Resources res;

    public ShopList(Resources res) {

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
        Label title = new Label("Partenaires Shoppy");
        title.setAlignment(CENTER);
        headerContainter.add(title);
        headerContainter.add(separator);
        
        add(BorderLayout.NORTH, headerContainter);
        
        Container enclosure = new Container(BoxLayout.y());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);

        ArrayList<Shop> shops = ShopService.getInstance().getAllShops();
        for (Shop s : shops)
        {
            displayShop(s, enclosure);
        }
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        setupSideMenu(res);
    }
    
      public void displayShop(Shop shop, Container enclosure) {
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("shop-icon.png").scaled(200, 200),true);
        ImageViewer shopImage = new ImageViewer(placeholder);

        
        Container card = new Container(new FlowLayout());
        Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setMargin(TOP, 230);

        Label nom_mg = new Label(shop.getNom());
        Label stock_mg = new Label(Integer.toString(shop.getTaille_stock()));

        textContainer.addAll(nom_mg, stock_mg);
        imageContainer.add(shopImage);
        card.addAll(imageContainer, textContainer);
        enclosure.add(card);
        
        card.addPointerPressedListener((evt) -> {
            ShopDetails details = new ShopDetails(this,res, shop);
            details.show();
            System.out.println("WIP");
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
        // new ProjectsForm(res, this).show();
    }

    @Override
    protected void showShops(Resources res) {
         new ShopList(res).show();
    }
    
    @Override
    protected void showSellerForm(Resources res){
        new SellerForm(res).show();
    }
    @Override
    protected void showOrderList(Resources res){
        new OrderList(res).show();
    }    
    @Override
    protected void showCart(Resources res) {
        new CartPage(res).show();
    }
}
