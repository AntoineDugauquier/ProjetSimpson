/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author ybordes
 */
public class Boost {

    public BufferedImage sprite;
    public int x, y;
    public int vitesse;
    public boolean attrape;

    public Boost() {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/donuts_bis.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 200;
        this.y = 250;
        this.vitesse = 15;
        this.attrape = false;
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
        final Boost other = (Boost) obj;
        if (this.x != other.x) {
            return false;
        }
        return this.y == other.y;
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

    public void miseAJour() {
        x = (int) (25*32 - 250 * Math.cos(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 39);
        y = (int) (15*32 + 250 * Math.sin(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 25);
    }
public void desactive(boolean attrape){
    //DESACTIVE LE BONUS SI ATTRAPE
    if (attrape){
        
    }
}

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}
