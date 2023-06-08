package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    public Avatar avatar;
    public Boost boost, boost2;
    public Ressource ressource, ressource2;
    ArrayList<Boost> listeBoost = new ArrayList();
    ArrayList<Ressource> listeRessource = new ArrayList();
    public SoundPlayer musiqueFond;
    public SoundPlayer musiqueBoost;
    public boolean demHaut, demBas, demGauche, demDroite;
    public boolean finDePartie;
    public Base base;

    public Jeu() {
        this.carte = new Carte();
        for (int i = 0; i <= 1600; i += 32) {
            for (int j = 0; j < 960; j += 32) {
            }
        }
        this.avatar = new Avatar("Homer.png");
        this.base = new Base();
//        this.avatar2 = new Avatar("Bart.png");
        while (!carte.accessible(base.coordX, base.coordY)) {
            base.modifiePosition();
        }
        this.boost = new Boost(true);
        this.boost2 = new Boost(false);
        this.ressource = new Ressource();
        while (!carte.accessible(ressource.coordX, ressource.coordY)) {
            ressource.modifiePosition();
        }
        this.ressource2 = new Ressource();
        while (!carte.accessible(ressource2.coordX, ressource2.coordY)) {
            ressource.modifiePosition();
        }
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

    public void detectCollisionBob(Avatar avatar, Boost boost) throws IOException {
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
                sound.play();
                avatar.coordx = 2;
                avatar.coordy = 0;
                avatar.x = avatar.coordx * 32 + 9;
                avatar.y = avatar.coordy * 32 + 8;
                avatar.miseAJour();
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
            if (!avatar.porteObjet) {
                SoundPlayer sound = new SoundPlayer("doh.mp3", false);
                sound.play();
                ressource.attrape = true;
                avatar.porteObjet = true;
                System.out.println("A attrape un objet");
                ressource.modifiePosition();
                while (!carte.accessible(ressource.coordX, ressource.coordY)) {
                    ressource.modifiePosition();                  
                }
            }
        }
    }

    public void detectCollisionBase(Avatar avatar, Base base) {
        // Obtenez les dimensions des images
        int largeurPersonnage = avatar.sprite.getWidth();
        int taillePersonnage = avatar.sprite.getHeight();
        int largeurBonus = base.sprite.getWidth();
        int tailleBonus = base.sprite.getHeight();

        // Vérifiez si les rectangles des images se chevauchent
        if (avatar.x < base.x + largeurBonus
                && avatar.x + largeurPersonnage > base.x
                && avatar.y < base.y + tailleBonus
                && avatar.y + taillePersonnage > base.y) {
            // Collision détectée
            if (avatar.porteObjet) {
                SoundPlayer sound = new SoundPlayer("doh.mp3", false);
                sound.play();
                avatar.porteObjet = false;
                avatar.score++;
                base.modifiePosition();
                while (!carte.accessible(base.coordX, base.coordY)) {
                    base.modifiePosition();
                }
                System.out.println("A pose un objet");
                System.out.println(avatar.score);
                if (avatar.score == 2) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                    System.out.println("Victoire");
//                    this.finDePartie=true;

                }
            }
        }
    }

    public void miseAJour() throws IOException {
        if (!finDePartie) {
            if (!this.demHaut && !this.demBas) {
                if (this.demGauche && !this.demDroite) {
                    //direction demandée = gauche
                    if (carte.accessible(avatar.coordx - (1 + avatar.compteurBoost), avatar.coordy)) {
                        avatar.setGauche(true);

                    }
                }
                if (!this.demGauche && this.demDroite) {
                    //direction demandée = droite
                    if (carte.accessible(avatar.coordx + (1 + avatar.compteurBoost), avatar.coordy)) {
                        avatar.setDroite(true);
                    }
                }
            }

            if (!this.demGauche && !this.demDroite) {
                if (this.demHaut && !this.demBas) {
                    //direction demandée = haut
                    if (carte.accessible(avatar.coordx, avatar.coordy - (1 + avatar.compteurBoost))) {
                        avatar.setHaut(true);

                    }
                }
                if (!this.demHaut && this.demBas) {
                    //direction demandée = bas
                    if (carte.accessible(avatar.coordx, avatar.coordy + (1 + avatar.compteurBoost))) {
                        avatar.setBas(true);
                    }
                }
            }

            avatar.miseAJour();

            for (int i = 0; i < listeRessource.size(); i++) {
                listeRessource.get(i).miseAJour();
                this.detectCollisionRessource(avatar, listeRessource.get(i));
                if (listeRessource.get(i).attrape) {
//                listeRessource.remove(i);
                }
            }

//            this.detectCollisionBoost(avatar2, listeBoost.get(i));
//                if (listeBoost.get(i).attrape) {
//                    listeBoost.remove(i);
//                }
        }
        avatar.setGauche(false);
        avatar.setDroite(false);
        avatar.setHaut(false);
        avatar.setBas(false);

        this.detectCollisionBase(avatar, base);
        this.base.miseAJour();

        for (int i = 0; i < listeBoost.size(); i++) {
            this.detectCollisionBob(avatar, listeBoost.get(i));
            listeBoost.get(i).miseAJour();
        }
    }

    public Avatar getAvatar() {
        return avatar;
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
        if (!finDePartie) {
            this.carte.rendu(contexte);

            for (int i = 0; i < listeRessource.size(); i++) {
                listeRessource.get(i).rendu(contexte);

            }
            for (int i = 0; i < listeBoost.size(); i++) {
                listeBoost.get(i).rendu(contexte);

            }
            this.avatar.rendu(contexte);
            this.base.rendu(contexte);
        }
    }
}
