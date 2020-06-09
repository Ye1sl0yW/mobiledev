/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.model;

/**
 *
 * @author os
 */
public class Note {
    private int id;
    private int user_id;
    private int type;
    private int magasin_id;
    private int produit_id;
    private int value;
    private String text;

    public int getId() {
        return id;
    }

    

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMagasin_id() {
        return magasin_id;
    }

    public void setMagasin_id(int magasin_id) {
        this.magasin_id = magasin_id;
    }

    public int getProduit_id() {
        return produit_id;
    }

    public void setProduit_id(int produit_id) {
        this.produit_id = produit_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public Note(int user_id, int value, int type, int o_id, String text){
        this.user_id=user_id;
        this.value=value;
        this.type=type;
        if (type==1) this.magasin_id=o_id;
        else this.produit_id=o_id;
        this.text=text;
    }
    public Note(){
        
    }
    @Override
    public String toString(){
        return "ID: " + id + "\nUID: "+user_id + "\nType: " + type + "\nMID: "+magasin_id +"\nPID: "+produit_id  + "\nTexte: "+ text;
    }
    @Override
    public boolean equals(Object o){
        return (o.getClass()==getClass() && ((Note)o).getId()==getId());
    }
}
