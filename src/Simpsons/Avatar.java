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
    public boolean boost;

    public Avatar() {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/homer_droite.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 96;
        this.y = 160;
        this.vitesse = 32;
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

    public void setVitesse(Avatar avatar) {
        if (avatar.boost) {
            avatar.vitesse = 10;
        }
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
        if (x > 50*32 - 64) {
            x = 50*32 - 64;
        }
        if (x < 0) {
            x = 0;
        }
        if (y > 30*32 - 128) {
            y = 30*32 - 128;
        }
        if (y < 0) {
            y = 0;
        }
        
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}
