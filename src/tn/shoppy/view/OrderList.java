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
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import tn.shoppy.model.Order;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.OrderService;
import tn.shoppy.services.ShopService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;


/**
 *
 * @author DevSpark
 */
public class OrderList extends SideMenuBaseForm {

    protected Resources res;

    public OrderList(Resources res) {

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
        Label title = new Label("Liste de mes commandes :");
        title.setAlignment(CENTER);
        headerContainter.add(title);
        headerContainter.add(separator);
        
        add(BorderLayout.NORTH, headerContainter);
        
        Container enclosure = new Container(BoxLayout.y());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);

        ArrayList<Order> orders = OrderService.getInstance().getUserOrders(Statics.BUYER_ID);
        for (Order o : orders)
        {
            displayOrder(o, enclosure);
        }
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        setupSideMenu(res);
    }
    
      
     public void displayOrder(Order order, Container enclosure) {
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("order-icon.png").scaled(200, 200), true);
        ImageViewer orderImage = new ImageViewer(placeholder);

        Container card = new Container(new FlowLayout());
        Container imageContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setMargin(TOP, 230);

        Label date = new Label("Date : " +order.getDateCreation());
        Label address = new Label("Adresse de livraison : " + order.getAdresseLiv());
        Label total = new Label("Total : " + Float.toString(order.getTotal()) + " TND.");

        textContainer.addAll(date, address, total);
        imageContainer.add(orderImage);
        card.addAll(imageContainer, textContainer);
        enclosure.add(card);

        card.addPointerPressedListener((evt) -> {
            OrderDetails details = new OrderDetails(order, this, res);
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
        // new ProjectsForm(res, this).show();
    }

    @Override
    protected void showShops(Resources res) {
         new OrderList(res).show();
    }
    
    @Override
    protected void showSellerForm(Resources res){
        new SellerForm(res).show();
    }
    @Override
    protected void showOrderList(Resources res){
        new OrderList(res).show();
    }
}
