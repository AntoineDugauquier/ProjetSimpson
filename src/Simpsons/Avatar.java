/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe avatar
 *
 */
public class Avatar {

    public BufferedImage sprite;
    protected int x, y;
    public boolean haut, bas, gauche, droite;
    public double vitesse;
//    public boolean boost;
    public int compteurBoost;
    public int coordx, coordy;
    public boolean porteObjet;
    public Base base;
    public int score;

    public Avatar(String sprite) {
        try {
//            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_droite.png"));
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/" + sprite));

        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.vitesse = 32;
        this.haut = false;
        this.bas = false;
        this.gauche = false;
        this.droite = false;
        this.compteurBoost = 0;
        this.coordx = 2;
        this.coordy = 0;
        this.x = coordx * 32 + 9;
        this.y = coordy * 32 + 8;
        this.base = new Base();
        this.score = 0;
    }

    public void setGauche(boolean gauche) {
//        try {
////            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/TahitiBob.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.gauche = gauche;
    }

    public void setDroite(boolean droite) {
//        try {
////this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/TahitiBob.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.droite = droite;
    }

    public void setHaut(boolean haut) {
//        try {
////            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/TahitiBob.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.haut = haut;
    }

    public void setBas(boolean bas) {
//        try {
//            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/TahitiBob.png"));
//        } catch (IOException ex) {
//            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.bas = bas;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getVitesse() {
        return vitesse;
    }

    public int getScore() {
        return score;
    }
    

    public void miseAJour() throws IOException {
        if (this.gauche) {
            coordx = coordx - (1 + compteurBoost);
            x = coordx * 32 + 9;
        }

        if (this.droite) {
            coordx = coordx + (1 + compteurBoost);
            x = coordx * 32 + 9;
        }

        if (this.haut) {
            coordy = coordy - (1 + compteurBoost);
            y = coordy * 32 + 8;
        }

        if (this.bas) {
            coordy = coordy + (1 + compteurBoost);
            y = coordy * 32 + 8;
        }
        this.base.miseAJour();
//        if (coordx > 50-2) {
//            coordx = 50-2;
//        }
//        if (coordx < 0+1) {
//            coordx = 0+1;
//        }
//        if (coordy > 30-2) {
//            coordy = 30-2;
//        }
//        if (coordy < 0+1) {
//            coordy = 0+1;
//        }

    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
        this.base.rendu(contexte);
    }

}
