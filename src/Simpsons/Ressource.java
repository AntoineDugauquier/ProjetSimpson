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
import java.util.Random;

/**
 *
 * @author ybordes
 */
public class Ressource {

    public BufferedImage sprite;
    public int x, y;
    public int vitesse;
    public int largeurMin;
    public int hauteurMin;
    public int largeurMax;
    public int hauteurMax;
    public boolean attrape;
    Random random = new Random();

    public Ressource() {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/Uranium.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 32 * (random.nextInt(28 - 23) + 23) + 4;
        this.y = 32 * (random.nextInt(18 - 13) + 13) + 4;
        this.vitesse = 15;
        this.largeurMin = 100;
        this.hauteurMin = 100;
        this.largeurMax = 1500;
        this.hauteurMax = 900;
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

    public void miseAJour() throws IOException {
        if (this.attrape) {
            this.setSprite(ImageIO.read(getClass().getClassLoader().getResource("images/boost_bis.png")));

        }
    }

    public void desactive() {
        if (this.attrape) {

        }
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}