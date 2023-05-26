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
    public Avatar avatar;
    public Boost boost;
    public Boost boost2;
    ArrayList <Boost> listeBoost = new ArrayList();
    public SoundPlayer musiqueFond;
    public SoundPlayer musiqueBoost;

    public Jeu() {
        this.carte = new Carte();
        this.avatar = new Avatar();
        this.boost = new Boost(true);
        this.boost2 = new Boost(false);
        this.listeBoost.add(boost);
        this.listeBoost.add(boost2);
        musiqueFond = new SoundPlayer("simpson8Bits.mp3", true);
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


    public void miseAJour() throws IOException {
        this.avatar.miseAJour();
        for(int i = 0 ; i < listeBoost.size(); i++){
            listeBoost.get(i).miseAJour();
            this.detectCollision(avatar,  listeBoost.get(i));
            if (listeBoost.get(i).attrape){
                listeBoost.remove(i);
            }
        }        
    }

    public void rendu(Graphics2D contexte) {
        this.carte.rendu(contexte);
        this.avatar.rendu(contexte);
        for(int i = 0 ; i < listeBoost.size(); i++){
            listeBoost.get(i).rendu(contexte);

    }

}
}
