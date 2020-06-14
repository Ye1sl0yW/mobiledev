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
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Display;
import com.codename1.ui.layouts.BoxLayout;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.Statics;

/**
 *
 * @author DevSpark
 */
public class SellerForm extends SideMenuBaseForm {

    protected Resources res;

    public SellerForm(Resources res) {

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
        Label title = new Label("Interface vendeur");
        title.setAlignment(CENTER);
        headerContainter.add(title);
        headerContainter.add(separator);
        add(BorderLayout.NORTH, headerContainter);
        Container enclosure = new Container(new BorderLayout());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);

        //TODO implémenter la gestion d'utilisateurs 
        // int shopId = user.getShop(id);
        //int shopId = 11;

        Button addProductButton = new Button("Ajouter un produit");
        Container buttonContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        buttonContainer.addAll(addProductButton);

        Container statsContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        int[] offers = ShopService.getInstance().getShopStats(Statics.ID_MAGASIN);
        
        Label allOffersLabel = new Label("Nombre total d'offres : "+Integer.toString(offers[0]));
        Label currentOffersLabel = new Label("Nombre d'offres en cours: "+Integer.toString(offers[1]));
        Label futureOffersLabel = new Label("Nombre d'offres planifiées: "+Integer.toString(offers[2]));
        statsContainer.addAll(allOffersLabel, currentOffersLabel, futureOffersLabel);
        
        enclosure.add(BorderLayout.CENTER, statsContainer);
        enclosure.add(BorderLayout.NORTH, buttonContainer);

        addProductButton.addActionListener(ev -> {
            new AddProductForm(this, res).show();
        });

        setupSideMenu(res);
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

    @Override
    protected void showSellerForm(Resources res) {
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

