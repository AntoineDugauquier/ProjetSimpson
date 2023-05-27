package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public Avatar avatar, avatar2;
    public Boost boost, boost2;
    public Ressource ressource, ressource2;
    ArrayList<Boost> listeBoost = new ArrayList();
    ArrayList<Ressource> listeRessource = new ArrayList();
    public SoundPlayer musiqueFond;
    public SoundPlayer musiqueBoost;
    public boolean demHaut, demBas, demGauche, demDroite;

    public Jeu() {
        this.carte = new Carte();
        for (int i = 0; i <= 1600; i += 32) {
            for (int j = 0; j < 960; j += 32) {
            }
        }
        this.avatar = new Avatar("Homer.png", 64, 160);
        this.avatar2 = new Avatar("Bart.png", 1600 - 64, 160);
        this.boost = new Boost(true);
        this.boost2 = new Boost(false);
        this.ressource = new Ressource();
        this.ressource2 = new Ressource();
        this.listeRessource.add(ressource);
        this.listeRessource.add(ressource2);
        this.listeBoost.add(boost);
        this.listeBoost.add(boost2);
        musiqueFond = new SoundPlayer("simpson8Bits.mp3", true);
        musiqueFond.play();
        musiqueBoost = new SoundPlayer("doh.mp3", false);

    }

    public void detectCollisionBoost(Avatar avatar, Boost boost) {
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
        }
    }

    public void detectCollisionRessource(Avatar avatar, Ressource ressource) {
        // Obtenez les dimensions des images
        int largeurPersonnage = avatar.sprite.getWidth();
        int taillePersonnage = avatar.sprite.getHeight();
        int largeurBonus = ressource.sprite.getWidth();
        int tailleBonus = ressource.sprite.getHeight();

        // Vérifiez si les rectangles des images se chevauchent
        if (avatar.x < ressource.x + largeurBonus
                && avatar.x + largeurPersonnage > ressource.x
                && avatar.y < ressource.y + tailleBonus
                && avatar.y + taillePersonnage > ressource.y) {
            // Collision détectée
            if (!ressource.attrape) {
                System.out.println("boost !");
                SoundPlayer sound = new SoundPlayer("doh.mp3", false);
                sound.play();
                ressource.attrape = true;
                System.out.println(avatar.compteurBoost);
                if (avatar.compteurBoost == 2) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                }
            }
        }
    }

    public void miseAJour() throws IOException {

        if (!this.demHaut && !this.demBas) {
            if (this.demGauche && !this.demDroite) {
                //direction demandée = gauche
                if (carte.accessible(avatar.coordx - 1, avatar.coordy)) {
                    avatar.setGauche(true);

                }
            }
            if (!this.demGauche && this.demDroite) {
                //direction demandée = droite
                if (carte.accessible(avatar.coordx + 1, avatar.coordy)) {
                    avatar.setDroite(true);
                }
            }
        }

        if (!this.demGauche && !this.demDroite) {
            if (this.demHaut && !this.demBas) {
                //direction demandée = haut
                if (carte.accessible(avatar.coordx, avatar.coordy - 1)) {
                    avatar.setHaut(true);

                }
            }
            if (!this.demHaut && this.demBas) {
                //direction demandée = bas
                if (carte.accessible(avatar.coordx, avatar.coordy + 1)) {
                    avatar.setBas(true);
                }
            }
        }

        this.avatar.miseAJour();
        this.avatar2.miseAJour();
        this.avatar2.miseAJour();
        
        for (int i = 0; i < listeRessource.size(); i++) {
            listeRessource.get(i).miseAJour();
            this.detectCollisionRessource(avatar, listeRessource.get(i));
            this.detectCollisionRessource(avatar2, listeRessource.get(i));
            if (listeRessource.get(i).attrape) {
                listeRessource.remove(i);
            }
        }
        
        for (int i = 0; i < listeBoost.size(); i++) {
            listeBoost.get(i).miseAJour();
            this.detectCollisionBoost(avatar, listeBoost.get(i));
            this.detectCollisionBoost(avatar2, listeBoost.get(i));
            if (listeBoost.get(i).attrape) {
                listeBoost.remove(i);
            }
        }
        avatar.setGauche(false);
        avatar.setDroite(false);
        avatar.setHaut(false);
        avatar.setBas(false);
    }

    void setdemDroite(boolean b) {
        this.demDroite = b;
    }

    void setdemGauche(boolean b) {
        this.demGauche = b;
    }

    void setdemHaut(boolean b) {
        this.demHaut = b;
    }

    void setdemBas(boolean b) {
        this.demBas = b;
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        this.avatar.rendu(contexte);
        this.avatar2.rendu(contexte);

        for (int i = 0; i < listeRessource.size(); i++) {
            listeRessource.get(i).rendu(contexte);

        }
        for (int i = 0; i < listeBoost.size(); i++) {
            listeBoost.get(i).rendu(contexte);

        }

    }
}
