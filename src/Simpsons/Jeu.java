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

    private BufferedImage fond;
    public Avatar avatar;
    public Boost boost;

    public Jeu() {
        try {
            this.fond = ImageIO.read(getClass().getClassLoader().getResource("images/village.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.avatar = new Avatar();
        this.boost = new Boost();
    }

    public void miseAJour() {
        this.avatar.miseAJour();
        this.boost.miseAJour();

    }

    public void rendu(Graphics2D contexte) {
        contexte.drawImage(this.fond, 0, 0, null);
        this.avatar.rendu(contexte);
        this.boost.rendu(contexte);
    }

}
