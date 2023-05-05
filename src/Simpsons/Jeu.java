package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe jeu
 *
 * @author guillaume.laurent
 */
public class Jeu {
    private Carte carte;
    //private BufferedImage fond;
    public Avatar avatar;
    public Boost boost;

    public Jeu() {
        this.carte = new Carte();
//        try {
//            this.fond = ImageIO.read(getClass().getResource("../images/village.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.avatar = new Avatar();
        this.boost = new Boost();
    }
     public void detectCollision(Avatar avatar, Boost boost) {
        // Obtenez les dimensions des images
        int largeurPersonnage = avatar.sprite.getWidth();
        int taillePersonnage = avatar.sprite.getHeight();
        int largeurBonus = boost.sprite.getWidth();
        int tailleBonus = boost.sprite.getHeight();

        // Vérifiez si les rectangles des images se chevauchent
        if (avatar.x < boost.x + largeurBonus
                && avatar.x + largeurPersonnage > boost.x
                && avatar.y < boost.y + tailleBonus
                && avatar.y + taillePersonnage > boost.y) {
            System.out.println("boost !");
            // Si les rectangles se chevauchent, vérifiez chaque pixel pour la collision
//            for (int i = 0; i < taillePersonnage; i++) {
//                for (int j = 0; j < largeurPersonnage; j++) {
//                    int pixel1 = personnage.getRGB(j, i);
//                    int pixel2 = bonus.getRGB(j + (xP - xB), i + (yP - yB));
//                    if (((pixel1 & 0xFF000000) != 0x00) && ((pixel2 & 0xFF000000) != 0x00)) {
                        // Collision détectée
                        avatar.boost = true;
                        boost.attrape=true;
                        
//                    }
                }
            }
//        }
        // Pas de collision détectée
//        avatar.boost = false;
//    }
//     }
//throws IOException
    public void miseAJour() throws IOException  {
        this.avatar.miseAJour(avatar);         
        this.boost.miseAJour(boost);
        this.detectCollision(avatar , boost);        
        
//        Avatar.setVitesse(avatar);

    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        //contexte.drawImage(this.fond, 0, 0, null);
        this.avatar.rendu(contexte);
        this.boost.rendu(contexte);
    }

}
