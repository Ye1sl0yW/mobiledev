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
 * @author asus
 */
public class PdfForm extends SideMenuBaseForm {

    private Resources res;

    public PdfForm(Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.res = res;
        setTitle("Ajouter un produit");
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });
Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
                 Container formContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        Label nom_prd = new Label("Facture envoyé ");
        formContainer.addComponent(nom_prd);
        enclosure.addAll(formContainer);
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
    protected void showOrderList(Resources res){
        new OrderList(res).show();
    }
@Override
    protected void showCart(Resources res) {
        new CartPage(res).show();
    }
}
