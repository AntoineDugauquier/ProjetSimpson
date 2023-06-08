/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import java.util.Random;

/**
 *
 * @author ybordes
 */
public class Base {

    public BufferedImage sprite;
    public int coordX,coordY,x, y;
    public int vitesse;
    public int largeurMin;
    public int hauteurMin;
    public int largeurMax;
    public int hauteurMax;
    public boolean attrape;
    Random random = new Random();

    public Base() {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/MrBurns.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.coordX = (random.nextInt(44 - 5) + 5);
        this.coordY = (random.nextInt(29 - 1) + 1);
        this.x = 32 * coordX ;
        this.y = 32 * coordY-2 ;
        this.largeurMin = 100;
        this.hauteurMin = 100;
        this.largeurMax = 1500;
        this.hauteurMax = 900;
        this.attrape = false;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

    public int getX() {
        return x;
    }
    public void modifiePosition() {
        this.coordX = (random.nextInt(49 - 1) + 1);
        this.coordY = (random.nextInt(29 - 1) + 1);
        this.x = 32 * coordX ;
        this.y = 32 * coordY -2;
//        try {
//
//            Connection connexion = DriverManager.getConnection("jdbc:mariadb://nemrod.ens2m.fr:3306/2022-2023_s2_vs2_tp1_Simpson", "Simpson", "rQKfwVi)97j3eAAy");
//
//            PreparedStatement requete = connexion.prepareStatement("UPDATE base SET x = ?, y = ?" );
//            requete.setDouble(1, x);
//            requete.setDouble(2, y);
//            System.out.println(requete);
//            int nombreDeModifications = requete.executeUpdate();
//            System.out.println(nombreDeModifications + " enregistrement(s) modifie(s)");
//
//            requete.close();
//            connexion.close();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getVitesse() {
        return vitesse;
    }

    public void setVitesse(int vitesse) {
        this.vitesse = vitesse;
    }

    public void miseAJour() throws IOException {
        
    }


    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}
