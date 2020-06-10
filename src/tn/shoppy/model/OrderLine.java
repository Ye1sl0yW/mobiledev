package tn.shoppy.model;

import javafx.beans.property.IntegerProperty;

/**
 *
 * @author asus
 */
public class OrderLine {
  
    
  private int idLC ;  
  private int id_cmd ;
  private int id_product; 
  private  float totalLc; 
  private int qte ; 

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.idLC;
        hash = 79 * hash + this.id_product;
        hash = 79 * hash + this.id_cmd;
        hash = 79 * hash + Float.floatToIntBits(this.totalLc);
        hash = 79 * hash + this.qte;
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
        final OrderLine other = (OrderLine) obj;
        if (this.idLC != other.idLC) {
            return false;
        }
        if (this.id_cmd != other.id_cmd) {
            return false;
        }
        if (this.id_product != other.id_product) {
            return false;
        }
        if (Float.floatToIntBits(this.totalLc) != Float.floatToIntBits(other.totalLc)) {
            return false;
        }
        if (this.qte != other.qte) {
            return false;
        }
        return true;
    
    }

    public OrderLine( int id_product, float totalLc, int qte) {
    
        this.id_product = id_product;
        this.totalLc = totalLc;
        this.qte = qte;
    }

  
    public OrderLine(float totalLc, int qte) 
    
    {
        this.totalLc = totalLc;
        this.qte = qte;
    }

   
    
     public OrderLine() 
    {
        
    }

    public int getIdLC() {
        return idLC;
    }

    public void setIdLC(int idLC) {
        this.idLC = idLC;
    }
 public int getId_cmd() {
        return id_cmd;
    }

    public void setId_cmd(int id_cmd) {
        this.id_cmd = id_cmd;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }
    public float getTotalLc() {
        return totalLc;
    }

    public void setTotalLc(float totalLc) {
        this.totalLc = totalLc;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    @Override
    public String toString() {
        return "OrderLine{" + "idLC=" + idLC + ", id_cmd=" + id_cmd + ", id_product=" + id_product + ", totalLc=" + totalLc + ", qte=" + qte + '}';
    }
   
}
