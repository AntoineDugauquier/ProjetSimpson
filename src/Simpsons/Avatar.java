/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simpsons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import outils.OutilsJDBC;
import outils.SingletonJDBC;

/**
 * Exemple de classe avatar
 *
 */
public class Avatar {

    public BufferedImage sprite;
    protected int x, y;
    public boolean haut, bas, gauche, droite;
    public int coordx, coordy;
    public boolean porteObjet;
    public int score;
    public String identifiant;

    public Avatar(String identifiant) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/" + identifiant + ".png"));

        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.identifiant = identifiant;
        this.haut = false;
        this.bas = false;
        this.gauche = false;
        this.droite = false;
        
        if (this.identifiant.equals("Homer")) {
            this.coordx = 2;
            this.coordy = 1;
        }
        if (this.identifiant.equals("Marge")) {
            this.coordx = 46;
            this.coordy = 1;
        }
        if (this.identifiant.equals("Lisa")) {
            this.coordx = 46;
            this.coordy = 28;
        }
        if (this.identifiant.equals("Bart")) {
            this.coordx = 1;
            this.coordy = 27;
        }
        this.x = coordx * 32 + 9;
        this.y = coordy * 32 + 8;
        this.score = 0;
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            if (this.identifiant.equals("Homer")) {
                statement.executeUpdate("INSERT INTO Avatar(x, y, score, idavatar) VALUES (x ,y,0,'Homer')");
            }
            if (this.identifiant.equals("Marge")) {
                statement.executeUpdate("INSERT INTO Avatar(x, y, score, idavatar) VALUES (x ,y,0,'Marge')");
            }
            if (this.identifiant.equals("Lisa")) {
                statement.executeUpdate("INSERT INTO Avatar(x, y, score, idavatar) VALUES (x ,y,0,'Lisa')");
            }
            if (this.identifiant.equals("Bart")) {
                statement.executeUpdate("INSERT INTO Avatar(x, y, score, idavatar) VALUES (x ,y,0,'Bart')");
            }

            PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET x = ?, y = ? , score = ? WHERE idavatar = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, score);
            requete.setString(4, this.identifiant);
            requete.executeQuery();
            ResultSet resultat = statement.executeQuery("SELECT * FROM Avatar;");
            OutilsJDBC.afficherResultSet(resultat);

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setGauche(boolean gauche) {
        this.gauche = gauche;
    }

    public void setDroite(boolean droite) {
        this.droite = droite;
    }

    public void retourDepart() {
        if (this.identifiant.equals("Homer")) {
            this.coordx = 2;
            this.coordy = 1;
        }
        if (this.identifiant.equals("Marge")) {
            this.coordx = 46;
            this.coordy = 1;
        }
        if (this.identifiant.equals("Lisa")) {
            this.coordx = 46;
            this.coordy = 28;
        }
        if (this.identifiant.equals("Bart")) {
            this.coordx = 1;
            this.coordy = 27;
        }
        x = coordx * 32 + 9;
        y = coordy * 32 + 8;

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET x = ?, y = ? , score = ? WHERE idavatar = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, score);
            requete.setString(4, identifiant);
            requete.executeQuery();
            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    public void raccourciEntre() {
          this.coordx = 28;
          this.coordy = 2;
        
//        if (this.gauche) {
//            this.coordx = 29;
//            this.coordy = 2;
//        }
//        if (this.droite) {
//            this.coordx = 31;
//            this.coordy = 2;
//        }
//        if (this.haut) {
//            this.coordx = 30;
//            this.coordy = 1;
//        }
//        if (this.bas) {
//            this.coordx = 30;
//            this.coordy = 3;
//        }
        x = coordx * 32 + 9;
        y = coordy * 32 + 8;

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET x = ?, y = ? , score = ? WHERE idavatar = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, score);
            requete.setString(4, identifiant);
            requete.executeQuery();
            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
    
    
   public void raccourciSort() {
          this.coordx = 33;
          this.coordy = 26;
//        if (this.gauche) {
//            this.coordx = 29;
//            this.coordy = 8;
//        }
//        if (this.droite) {
//            this.coordx = 31;
//            this.coordy = 8;
//        }
//        if (this.haut) {
//            this.coordx = 30;
//            this.coordy = 7;
//        }
//        if (this.bas) {
//            this.coordx = 30;
//            this.coordy = 9;
//        }
        x = coordx * 32 + 9;
        y = coordy * 32 + 8;

        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET x = ?, y = ? , score = ? WHERE idavatar = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, score);
            requete.setString(4, identifiant);
            requete.executeQuery();
            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void setHaut(boolean haut) {
        this.haut = haut;
    }

    public void setBas(boolean bas) {
        this.bas = bas;
    
    }

    public BufferedImage getSprite() {
        return sprite;
    }


    public int getScore() {
        return score;
    }

    public void miseAJour() throws IOException {
        if (this.gauche) {
            coordx = coordx - 1;
            x = coordx * 32 + 9;
        }

        if (this.droite) {
            coordx = coordx + 1;
            x = coordx * 32 + 9;
        }

        if (this.haut) {
            coordy = coordy - 1;
            y = coordy * 32 + 8;
        }

        if (this.bas) {
            coordy = coordy + 1;
            y = coordy * 32 + 8;
        }     
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("UPDATE Avatar SET x = ?, y = ? , score = ? WHERE idavatar = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, score);
            requete.setString(4, identifiant);
            requete.executeQuery();
            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void rendu(Graphics2D contexte) throws IOException {
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("SELECT   x , y  , score,idavatar FROM Avatar");
            ResultSet resultat = requete.executeQuery();

            requete.executeQuery();

            while (resultat.next()) {

                String identifiant = resultat.getString("idavatar");
                int x = resultat.getInt("x");
                int y = resultat.getInt("y");
                int score = resultat.getInt("score");
                
                contexte.drawImage(ImageIO.read(getClass().getClassLoader().getResource("images/" + identifiant + ".png")), x,  y, null);
            }

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

}
