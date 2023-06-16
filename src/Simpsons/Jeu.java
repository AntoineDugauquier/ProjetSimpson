package Simpsons;

import static Simpsons.FenetreDeJeu.scoreBart;
import static Simpsons.FenetreDeJeu.scoreHomer;
import static Simpsons.FenetreDeJeu.scoreLisa;
import static Simpsons.FenetreDeJeu.scoreMarge;
import static Simpsons.FenetreDeJeu.titre;
import static Simpsons.FenetreDeJeu.scoredeBart;
import static Simpsons.FenetreDeJeu.scoredeHomer;
import static Simpsons.FenetreDeJeu.scoredeLisa;
import static Simpsons.FenetreDeJeu.scoredeMarge;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import outils.OutilsJDBC;
import outils.SingletonJDBC;
import Simpsons.FenetreDeJeu;

/**
 * Exemple de classe jeu
 *
 * @author guillaume.laurent
 */
public class Jeu {

    private Carte carte;
    private BufferedImage fond;

    public Avatar avatar;
    public Bob boost, boost2;
    public Ressource ressource, ressource2;
    ArrayList<Bob> listeBoost = new ArrayList();
    ArrayList<Ressource> listeRessource = new ArrayList();
    public SoundPlayer musiqueFond;
    public SoundPlayer musiqueBoost;
    public boolean demHaut, demBas, demGauche, demDroite;
    public int scoreFinDePartie;
    public Burns base;
    ArrayList<String> listeNom = new ArrayList();

    public Jeu() {
        //Nettoyage de la base de données
//        try {
//            Connection connexion = SingletonJDBC.getInstance().getConnection();
//
//            Statement statement = connexion.createStatement();
//
//            statement.executeUpdate("DELETE FROM Avatar;");
//            statement.executeUpdate("DELETE FROM Base;");
//            statement.executeUpdate("DELETE FROM Ressource;");
//
//            statement.close();
////                        connexion.close();
//
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
        this.carte = new Carte();
        for (int i = 0; i <= 1600; i += 32) {
            for (int j = 0; j < 960; j += 32) {
            }
        }
        this.listeNom.add("Lisa");
        this.listeNom.add("Bart");
        this.listeNom.add("Homer");
        this.listeNom.add("Marge");

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("SELECT idavatar FROM Avatar");
            ResultSet resultat = requete.executeQuery();

            requete.executeQuery();

            while (resultat.next()) {

                String identifiant = resultat.getString("idavatar");
                this.listeNom.remove(identifiant);
            }

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String nom = JOptionPane.showInputDialog(null, " Choisir un pseudo parmi " + listeNom, "Sélection du personnage", JOptionPane.QUESTION_MESSAGE);
        while (!this.listeNom.contains(nom)) {
            nom = JOptionPane.showInputDialog(null, " Le pseudo n'est pas valide: \n Choisir parmi " + listeNom, "Sélection du personnage", JOptionPane.QUESTION_MESSAGE);
        }
        if (nom != null) {
            this.listeNom.remove(nom);
            System.out.println(" Saisie = " + nom);
        }

        this.avatar = new Avatar(nom);
        this.base = new Burns();
//        this.avatar2 = new Avatar("Bart.png");
        while (!carte.accessible(base.coordX, base.coordY)) {
            base.modifiePosition();
        }
        this.boost = new Bob(true);
        this.boost2 = new Bob(false);
        this.ressource = new Ressource(1);
        while (!carte.accessible(ressource.coordX, ressource.coordY)) {
            ressource.modifiePosition();
        }
        this.ressource2 = new Ressource(2);
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
        this.scoreFinDePartie = 3;

    }

    public void detectCollisionBob(Avatar avatar, Bob boost) throws IOException {
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
//            if (!boost.attrape) {
//                System.out.println("boost !");//
            SoundPlayer sound = new SoundPlayer("doh.mp3", false);
            sound.play();
            avatar.miseAJour();
            avatar.retourDepart();
//            }
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

    public void detectCollisionBase(Avatar avatar, Burns base) throws InterruptedException {
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
                try {
                    Connection connexion = SingletonJDBC.getInstance().getConnection();

                    Statement statement = connexion.createStatement();

                    PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET score = ? WHERE idavatar = ?");
                    requete.setInt(1, avatar.score);
                    requete.setString(2, avatar.identifiant);
                    requete.executeQuery();
                    PreparedStatement requetemaj = connexion.prepareStatement("SELECT score FROM Avatar");
                    ResultSet resultat = requetemaj.executeQuery();

                    while (resultat.next()) {
                        int scoremaj = resultat.getInt("score");
                        System.out.println("Màj BDD  " + scoremaj);
                    }
                    statement.close();
                    requete.close();
                    requetemaj.close();

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                base.modifiePosition();
                while (!carte.accessible(base.coordX, base.coordY)) {
                    base.modifiePosition();
                }
                System.out.println(avatar.score);

                //FIN DE PARTIE
                if (avatar.score == scoreFinDePartie) {
                    titre.setText("La partie est terminée !");
                    if (avatar.identifiant.equals("Homer")) {
                        System.out.println("Homer = " + avatar.score);
                        scoredeHomer.setText("Score de Homer : " + String.valueOf(avatar.score));
                        scoredeHomer.paintImmediately(scoredeHomer.getVisibleRect());
                    }
                    if (avatar.identifiant.equals("Marge")) {
                        System.out.println("Marge = " + avatar.score);
                        scoredeMarge.setText("Score de Marge : " + String.valueOf(avatar.score));
                        scoredeMarge.paintImmediately(scoredeMarge.getVisibleRect());
                    }
                    if (avatar.identifiant.equals("Lisa")) {
                        System.out.println("Lisa = " + avatar.score);
                        scoredeLisa.setText("Score de Lisa : " + String.valueOf(avatar.score));
                        scoredeLisa.paintImmediately(scoredeLisa.getVisibleRect());
                    }
                    if (avatar.identifiant.equals("Bart")) {
                        System.out.println("Bart = " + avatar.score);
                        scoredeBart.setText("Score de Bart : " + String.valueOf(avatar.score));
                        scoredeBart.paintImmediately(scoredeBart.getVisibleRect());
                    }

                }
            }
        }
    }

    public void miseAJour() throws IOException, InterruptedException {
        if (!this.demHaut && !this.demBas) {
            if (this.demGauche && !this.demDroite) {
                if (carte.accessible(avatar.coordx - 1, avatar.coordy)) {
                    avatar.setGauche(true);

                }
            }
            if (!this.demGauche && this.demDroite) {
                if (carte.accessible(avatar.coordx + 1, avatar.coordy)) {
                    avatar.setDroite(true);
                }
            }
        }

        if (!this.demGauche && !this.demDroite) {
            if (this.demHaut && !this.demBas) {
                if (carte.accessible(avatar.coordx, avatar.coordy - 1)) {
                    avatar.setHaut(true);

                }
            }
            if (!this.demHaut && this.demBas) {
                if (carte.accessible(avatar.coordx, avatar.coordy + 1)) {
                    avatar.setBas(true);
                }
            }
        }

        avatar.miseAJour();

        for (int i = 0; i < listeRessource.size(); i++) {
            listeRessource.get(i).miseAJour();
            this.detectCollisionRessource(avatar, listeRessource.get(i));
            if (listeRessource.get(i).attrape) {
            }
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

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requeteHomer = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Homer'");
            PreparedStatement requeteMarge = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Marge'");
            PreparedStatement requeteBart = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Bart'");
            PreparedStatement requeteLisa = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Lisa'");
            ResultSet resultatHomer = requeteHomer.executeQuery();

            while (resultatHomer.next()) {
                scoreHomer = resultatHomer.getInt("score");
                if (scoreHomer == scoreFinDePartie) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                    System.out.println("Victoire");
                    FenetreDeJeu.fin("Homer");
                }

            }
            requeteHomer.close();

            ResultSet resultatMarge = requeteMarge.executeQuery();
            while (resultatMarge.next()) {
                scoreMarge = resultatMarge.getInt("score");
                if (scoreMarge == this.scoreFinDePartie) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                    System.out.println("Victoire");
                    System.out.println("Marge");
                    FenetreDeJeu.fin("Marge");
                }

            }
            requeteMarge.close();

            ResultSet resultatBart = requeteBart.executeQuery();
            while (resultatBart.next()) {
                scoreBart = resultatBart.getInt("score");
                if (scoreBart == scoreFinDePartie) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                    System.out.println("Victoire");
                    FenetreDeJeu.fin("Bart");
                }

            }
            requeteBart.close();

            ResultSet resultatLisa = requeteLisa.executeQuery();
            while (resultatLisa.next()) {
                scoreLisa = resultatLisa.getInt("score");
                if (scoreLisa == scoreFinDePartie) {
                    musiqueFond.stop();
                    musiqueFond.setName("victorySound.mp3");
                    musiqueFond.play();
                    System.out.println("Victoire");
                    FenetreDeJeu.fin("Lisa");
                }

            }
            requeteLisa.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
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

    public void rendu(Graphics2D contexte) throws IOException, InterruptedException {

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
