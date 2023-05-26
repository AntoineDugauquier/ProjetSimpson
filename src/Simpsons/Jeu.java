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
    public Boost boost2;
    public SoundPlayer musiqueFond;
    public SoundPlayer musiqueBoost;
    public boolean demHaut, demBas, demGauche, demDroite;

    public Jeu() {
        this.carte = new Carte();
//        try {
//            this.fond = ImageIO.read(getClass().getResource("../images/village.jpg"));
//        } catch (IOException ex) {
//            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//        }
        this.avatar = new Avatar();
        this.boost = new Boost(true);
        this.boost2 = new Boost(false);
        musiqueFond = new SoundPlayer("simpson.mp3", true);
        musiqueFond.play();
        musiqueBoost = new SoundPlayer("doh.mp3", false);

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

            // Si les rectangles se chevauchent, vérifiez chaque pixel pour la collision
//            for (int i = 0; i < taillePersonnage; i++) {
//                for (int j = 0; j < largeurPersonnage; j++) {
//                    int pixel1 = personnage.getRGB(j, i);
//                    int pixel2 = bonus.getRGB(j + (xP - xB), i + (yP - yB));
//                    if (((pixel1 & 0xFF000000) != 0x00) && ((pixel2 & 0xFF000000) != 0x00)) {
            // Collision détectée
            if (!boost.attrape) {
                System.out.println("boost !");
                SoundPlayer sound = new SoundPlayer("doh.mp3", false);
                sound.stop();
                sound.play();
                boost.attrape = true;
                avatar.compteurBoost++;
                System.out.println(avatar.compteurBoost);
                if (avatar.compteurBoost == 2) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                }
            }

//                    }
        }
    }
//        }
    // Pas de collision détectée
//        avatar.boost = false;
//    }
//     }
//throws IOException

    public void miseAJour() throws IOException {
        
        
        if (!this.demHaut && !this.demBas) {
            if (this.demGauche && !this.demDroite) {
                //direction demandée = gauche
                if (carte.accessible(avatar.coordx-1, avatar.coordy)){
                    avatar.setGauche(true);
                    
                }
            }
            if (!this.demGauche && this.demDroite) {
                //direction demandée = droite
                if (carte.accessible(avatar.coordx+1, avatar.coordy)){
                    avatar.setDroite(true);
                }
            }
        }
        
        if(!this.demGauche && !this.demDroite) {
            if (this.demHaut && !this.demBas) {
                //direction demandée = haut
                if (carte.accessible(avatar.coordx, avatar.coordy-1)){
                    avatar.setHaut(true);
                    
                }
            }
            if (!this.demHaut && this.demBas) {
                //direction demandée = bas
                if (carte.accessible(avatar.coordx, avatar.coordy+1)){
                    avatar.setBas(true);
                }
            }
        }   
        
        this.avatar.miseAJour();
        avatar.setGauche(false);
        avatar.setDroite(false);
        avatar.setHaut(false);
        avatar.setBas(false);
        
        
        
        
        this.boost.miseAJour();
        this.boost2.miseAJour();
        this.detectCollision(avatar, boost);
        this.detectCollision(avatar, boost2);

//        Avatar.setVitesse(avatar);
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        //contexte.drawImage(this.fond, 0, 0, null);
        this.avatar.rendu(contexte);
        this.boost.rendu(contexte);
        this.boost2.rendu(contexte);
    }

    void setdemDroite(boolean b) {
        this.demDroite=b ;
    }

    void setdemGauche(boolean b) {
        this.demGauche=b ;
    }

    void setdemHaut(boolean b) {
        this.demHaut=b ;
    }

    void setdemBas(boolean b) {
        this.demBas=b ;
    }

}
