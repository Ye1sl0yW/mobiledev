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
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import java.util.List;
import tn.shoppy.model.Product;
import tn.shoppy.services.ProductService;
import tn.shoppy.utils.SideMenuBaseForm;
import tn.shoppy.utils.Statics;

/**
 *
 * @author DevSpark
 */
public class ProductsList extends SideMenuBaseForm {

    protected Resources res;

    public ProductsList(Resources res) {

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
        Label title = new Label("Liste des produits");
        title.setAlignment(CENTER);
        headerContainter.add(title);
        
        Container searchContainer = new Container(BoxLayout.x());
        TextField searchField = new TextField();
        searchField.setHint("Rechercher un produit");
        searchContainer.addAll(searchField);
        headerContainter.add(searchContainer);
        headerContainter.add(separator);
        add(BorderLayout.NORTH, headerContainter);

        Container enclosure = new Container(BoxLayout.y());
        enclosure.setScrollableY(true);
        enclosure.setSize(calcScrollSize());
        add(BorderLayout.CENTER, enclosure);
        
        
        ProductService productService = ProductService.getInstance();
        ArrayList<Product> listP = productService.getAllProducts();
        for (Product p : listP) {
            this.displayProduct(p, enclosure);
        }

        searchField.addDataChangeListener((i1, i2) -> {
            String t = searchField.getText();
            if (t.length() < 1) {
                toggleProducts(enclosure, false);
            } else {
                toggleProducts(enclosure, true);
                showSearchResult(enclosure, t);
            }
        });

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        setupSideMenu(res);
    }

    public void showSearchResult(Container cont, String search) {
        for (Product p : ProductService.getInstance().searchProducts(search)) {
            displayProduct(p, cont);
        }
    }

    public void toggleProducts(Container enclosure, boolean b) {
        List<Component> productsCards = enclosure.getChildrenAsList(false);
        for (Component comp : productsCards) {
            comp.setHidden(b);
        }
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
        Label quantite_prd = new Label(Integer.toString(product.getQuantite()));
        Label marque_prd = new Label(product.getMarque());
        Label prix_prd = new Label(Double.toString(product.getPrix()) + " TND");

        textContainer.addAll(nom_prd, prix_prd, marque_prd);
        imageContainer.add(productImage);
        card.addAll(imageContainer, textContainer);
        enclosure.add(card);

        card.addPointerPressedListener((evt) -> {
            ProductDetails details = new ProductDetails(product, this, res);
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
}
