package tn.shoppy.model;

import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.Objects;



/**
 *
 * @author asus
 */
public class Order {
    private int idCmd ; 
    private int QteTot;
    private float total ; 
private String  adresseLiv;
    private int id_Acheteur ; 
    private String DateCreation;
    private List<OrderLine> orderLine = new ArrayList<OrderLine> ();

    @Override
    public String toString() {
        return "Order{" + "idCmd=" + idCmd + ", QteTot=" + QteTot + ", total=" + total + ", adresseLiv=" + adresseLiv + ", id_Acheteur=" + id_Acheteur + ", DateCreation=" + DateCreation + ", orderLine=" + orderLine + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.idCmd;
        hash = 37 * hash + this.QteTot;
        hash = 37 * hash + Float.floatToIntBits(this.total);
        hash = 37 * hash + Objects.hashCode(this.adresseLiv);
        hash = 37 * hash + this.id_Acheteur;
        hash = 37 * hash + Objects.hashCode(this.DateCreation);
        hash = 37 * hash + Objects.hashCode(this.orderLine);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.idCmd != other.idCmd) {
            return false;
        }
        if (this.QteTot != other.QteTot) {
            return false;
        }
        if (Float.floatToIntBits(this.total) != Float.floatToIntBits(other.total)) {
            return false;
        }
        if (this.id_Acheteur != other.id_Acheteur) {
            return false;
        }
        if (!Objects.equals(this.adresseLiv, other.adresseLiv)) {
            return false;
        }
        if (!Objects.equals(this.DateCreation, other.DateCreation)) {
            return false;
        }
        if (!Objects.equals(this.orderLine, other.orderLine)) {
            return false;
        }
        return true;
    }

    
    public Order(int idCmd, int QteTot, float total, String adresseLiv, int id_Acheteur, String dateCreation) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
        this.id_Acheteur = id_Acheteur;
        this.DateCreation = dateCreation;
    }
 public Order(int idCmd, int QteTot, float total, String adresseLiv, int id_Acheteur) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
        this.id_Acheteur = id_Acheteur;
    }
 
    public Order() {
       this.adresseLiv=new String();
    }
    public Order(int QteTot, float total) {
        this.QteTot = QteTot;
        this.total = total;
    }

    public Order(int QteTot, float total, String adresseLiv) {
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
    }

    public Order(int idCmd, int QteTot, float total, String adresseLiv) {
        this.idCmd = idCmd;
        this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
    }

    public Order( int QteTot, float total, String adresseLiv,String DateCreation) {
      this.QteTot = QteTot;
        this.total = total;
        this.adresseLiv = adresseLiv;
         this.DateCreation   = DateCreation; 
    }

    public int getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(int idCmd) {
        this.idCmd = idCmd;
    }
       public int getId_Acheteur() {
        return id_Acheteur;
        
    }

    public void setId_Acheteur(int id_Acheteur) {
        this.id_Acheteur = id_Acheteur;
    }

    public String getDateCreation() {
        return DateCreation;
    }

    public void setDateCreation(String DateCreation) {
        this.DateCreation = DateCreation;
    }


    public int getQteTot() {
        return QteTot;
    }

    public void setQteTot(int QteTot) {
        this.QteTot = QteTot;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getAdresseLiv() {
        return adresseLiv;
    }

    public void setAdresseLiv(String adresseLiv) {
        this.adresseLiv = adresseLiv;
    }
}
