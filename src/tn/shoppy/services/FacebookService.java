/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.shoppy.services;

import com.codename1.social.FacebookConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.Form;


/**
 *
 * @author bouzmen
 */
public class FacebookService {

    private static FacebookService facebookServiceInstance;

    public static FacebookService getInstance() {   //Singleton Design Pattern
        if (facebookServiceInstance == null) {
            facebookServiceInstance = new FacebookService();
        }
        return facebookServiceInstance;
    }

    public void facebookConnect(Form currentForm) {
        System.out.println("debug");
        //use your own facebook app identifiers here   
        //These are used for the Oauth2 web login process on the Simulator.
        String clientId = "304774530701150";
        String redirectURI = "http://localhost/";
        String clientSecret = "aa234bf6cde9e2f421add0424bd65f92";
        Login fb = FacebookConnect.getInstance();     
        fb.setClientId(clientId);
        fb.setRedirectURI(redirectURI);
        fb.setClientSecret(clientSecret);
        //Sets a LoginCallback listener
        fb.setCallback(new LoginCallback() {
            @Override
            public void loginFailed(String errorMessage) {
               // System.out.println("Login failed !");
              //  currentForm.showBack();
            }

            @Override
            public void loginSuccessful() {
              //  System.out.println("login succesful !" + fb.getAccessToken().getToken());
           //     new FacebookShareForm(currentForm, "Partager sur Facebook","","","",null).show();  
                
            }

        });
        //trigger the login if not already logged in
        if (!fb.isUserLoggedIn()) {
            fb.doLogin();
        } else {
            //get the token and now you can query the facebook API
            String token = fb.getAccessToken().getToken();
            System.out.println(token);
        }
    }


}
