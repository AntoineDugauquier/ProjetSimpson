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
public class Bob {

    public BufferedImage sprite;
    public int x, y;
   
    public boolean sensRotation;

    public Bob(boolean choixSens) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/TahitiBob-Copie.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.x = 160;
        this.y = 160;    
        this.sensRotation = choixSens;
    }

    public void miseAJour() throws IOException {
        if (this.sensRotation) {
            x = (int) (25 * 32 - 250 * Math.cos(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 39);
            y = (int) (15 * 32 + 250 * Math.sin(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 25);
        }
        if (!this.sensRotation) {
            x = (int) (25 * 32 + 250 * Math.cos(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 39);
            y = (int) (15 * 32 + 250 * Math.sin(System.currentTimeMillis() * 0.0008 + 3.1415926535897932384626433832795028841971693993751058209749445923) - 25);

        }
//        if (this.attrape) {
//            this.setSprite(ImageIO.read(getClass().getClassLoader().getResource("images/boost_bis.png")));
//
//        }
    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.sprite, (int) x, (int) y, null);
    }

}
