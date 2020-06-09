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
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import tn.shoppy.model.Product;
import tn.shoppy.model.Shop;
import tn.shoppy.services.ProductService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author DevSpark
 */
public class ShopDetails extends SideMenuBaseForm {

    protected Resources res;
    private Shop shop;
    public ShopDetails(Form previous, Resources res, Shop shop) {

        super(new BorderLayout());
        this.res = res;
        this.shop = shop;
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });
        tb.setTitleCentered(false);
       
        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
              
        Container headerContainter = new Container(BoxLayout.y());
        Label title = new Label(shop.getNom());
        title.setAlignment(CENTER);
        headerContainter.add(title);
        headerContainter.add(separator);
        add(BorderLayout.NORTH, headerContainter);
        Container enclosure = new Container(BoxLayout.y());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);

Button noteButton = new Button("Noter");
        noteButton.addPointerPressedListener((evt) -> {
            new NoteForm(shop,this,res).show();
        });

        enclosure.add(noteButton);
        ProductService productService = ProductService.getInstance();
        ArrayList<Product> listP = productService.getAllProductsByShop(shop.getId());
        for (Product p : listP) {
            //System.out.println(p);       
            this.displayProduct(p, enclosure);
        }

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        setupSideMenu(res);
    }

    public void displayProduct(Product product, Container enclosure) {
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("default.jpg").scaled(200, 200),true);
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
        Label quantite_prd = new Label(Integer.toString(product.getQuantite()));
        Label marque_prd = new Label(product.getMarque());
        Label prix_prd = new Label(Double.toString(product.getPrix()) + " TND");

        textContainer.addAll(nom_prd, prix_prd, marque_prd);
        imageContainer.add(productImage);
        card.addAll(imageContainer, textContainer);
        enclosure.add(card);
        
        card.addPointerPressedListener((evt) -> {
            ProductDetails details = new ProductDetails(product,this,res);
            details.show();
        });

    }

    private Image colorCircle(int color) {
        int size = Display.getInstance().convertToPixels(3);
        Image i = Image.createImage(size, size, 0);
        Graphics g = i.getGraphics();
        g.setColor(color);
        g.fillArc(0, 0, size, size, 0, 360);
        return i;
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
    protected void showSellerForm(Resources res){
        new SellerForm(res).show();
    }
}
