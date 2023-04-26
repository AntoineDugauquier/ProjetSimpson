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

    private BufferedImage sprite;
    protected int x, y;
    private boolean haut, bas, gauche, droite;
    private double vitesse;
    private boolean boost;

    public Avatar() {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_droite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 100;
        this.y = 150;
        this.vitesse = 5;
        this.haut = false;
        this.bas = false;
        this.gauche = false;
        this.droite = false;
    }

    public void setGauche(boolean gauche) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_gauche_tiers.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.gauche = gauche;
    }

    public void setDroite(boolean droite) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_droite_tiers.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.droite = droite;
    }

    public void setHaut(boolean haut) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_haut.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.haut = haut;
    }

    public void setBas(boolean bas) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_droite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bas = bas;
    }

    public void detectCollision(BufferedImage personnage, BufferedImage bonus, int xP, int yP, int xB, int yB) {
        // Obtenez les dimensions des images
        int width1 = personnage.getWidth();
        int height1 = personnage.getHeight();
        int width2 = bonus.getWidth();
        int height2 = bonus.getHeight();

        // Vérifiez si les rectangles des images se chevauchent
        if (xP < xB + width2
                && xP + width1 > xB
                && yP < yB + height2
                && yP + height1 > yB) {
            // Si les rectangles se chevauchent, vérifiez chaque pixel pour la collision
            for (int i = 0; i < height1; i++) {
                for (int j = 0; j < width1; j++) {
                    int pixel1 = personnage.getRGB(j, i);
                    int pixel2 = bonus.getRGB(j + (xP - xB), i + (yP - yB));
                    if (((pixel1 & 0xFF000000) != 0x00) && ((pixel2 & 0xFF000000) != 0x00)) {
                        // Collision détectée
                        boost = true;
                        System.out.println("boost !");
                    }
                }
            }
        }
        // Pas de collision détectée
//        boost = false;
    }

    public void setVitesse(boolean boost) {
        if (boost) {
            vitesse = 10;
        }
    }
    

    public void miseAJour() {
        if (this.gauche) {
            x -= vitesse;
        }
        if (this.droite) {
            x += vitesse;
        }
        if (this.haut) {
            y -= vitesse;
        }
        if (this.bas) {
            y += vitesse;
        }
        if (x > 800 - 50) {
            x = 800 - 50;
        }
        if (x < 0) {
            x = 0;
        }
        if (y > 537 - 109) {
            y = 537 - 109;
        }
        if (y < 0) {
            y = 0;
        }
//        this.detectCollision(sprite, sprite, x, y, Boost.getX(), y);
//        this.setVitesse(boost);
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}
