/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.view;


import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import tn.shoppy.model.Order;
import tn.shoppy.model.OrderLine;
import tn.shoppy.model.Product;
import tn.shoppy.services.OrderService;
import tn.shoppy.utils.SideMenuBaseForm;

/**
 *
 * @author bouzmen
 */
public class OrderDetails extends SideMenuBaseForm {

    private Order order;
    private Resources res;

    public OrderDetails(Order order, Form previous, Resources res) {
        super("Détails produit", new BoxLayout(BoxLayout.Y_AXIS));
        this.order = order;
        this.res = res;
        setTitle(order.getDateCreation());
        Toolbar tb = this.getToolbar();
        tb.addCommandToRightBar("Back", null, (evt) -> {
            previous.show();
        });
        
//        String pattern = "yyyy-MM-dd";
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//
//String datee = simpleDateFormat.format(new Date());
//System.out.println(datee);



//  String input = "Thu Jun 18 20:56:02 EDT 2009";
//        SimpleDateFormat parser = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
//       // Date dateee = parser.parse(input);
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String formattedDate = formatter.format(dateee);


         Label date = new Label("Date : " + order.getDateCreation());
      //  System.out.println("Date : " + order.getDateCreation());
         //Label date = new Label("Date : " + simpleDateFormat.format(order.getDateCreation()));
     
        Label address = new Label("Adresse de livraison : " + order.getAdresseLiv());
        Label total = new Label("Total : " + Float.toString(order.getTotal()) + " TND.");    

        Container enclosure = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        enclosure.addAll(date, address, total);
        
        this.addAll(enclosure);

        ArrayList<OrderLine> orderLines = OrderService.getInstance().getOrderLines(order.getIdCmd());
        for (OrderLine ol : orderLines) {
            //displayOrderLine(ol, enclosure);
          //  System.out.println(ol);
        }
        
        
        Button pdfButton = new Button("Envoyer par mail");
        enclosure.add(pdfButton);
  pdfButton.addActionListener(ev -> {
       OrderService.getInstance().MailPdf(order.getIdCmd());
            new PdfForm(this, res).show();
        });
    }

    public void displayOrderLine(OrderLine orderline, Container enclosure) {

        Container card = new Container(new FlowLayout());
        Container textContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        card.getStyle().setMargin(TOP, 230);
        
    //    Label product = new Label("Article : " + orderline.getId_product());
        Label quantity = new Label("Quantité: " + orderline.getQte());
        Label total = new Label("Total : " + Float.toString(orderline.getTotalLc() ) + " TND.");
        
        textContainer.addAll( quantity, total);
         //textContainer.addAll(product, quantity, total);
        card.addAll(textContainer);
        enclosure.add(card);

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
