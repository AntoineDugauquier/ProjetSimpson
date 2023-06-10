/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Simpsons;

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
import java.util.Random;
import outils.OutilsJDBC;
import outils.SingletonJDBC;

/**
 *
 * @author ybordes
 */
public class Ressource {

    public BufferedImage sprite;
    public int coordX, coordY, x, y;
    public boolean attrape;
    public int identifiant;
    Random random = new Random();

    public Ressource(int identifiant) {
        try {
            this.sprite = ImageIO.read(getClass().getClassLoader().getResource("images/Uranium.png"));
        } catch (IOException ex) {
            Logger.getLogger(Avatar.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.coordX = (random.nextInt(28 - 22) + 22);
        this.coordY = (random.nextInt(18 - 12) + 12);
        this.x = 32 * coordX + 4;
        this.y = 32 * coordY + 4;

        this.identifiant = identifiant;        
        this.attrape = false;
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            if (this.identifiant == 1) {
                statement.executeUpdate("INSERT INTO Ressource (x, y,idressource) VALUES (0 ,0, 1)");
            }
            if (this.identifiant == 2) {
                statement.executeUpdate("INSERT INTO Ressource (x, y,idressource) VALUES (0 ,0, 2)");
            }
            PreparedStatement requete = connexion.prepareStatement("UPDATE Ressource SET x = ?, y = ? WHERE idressource = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, this.identifiant);
            requete.executeQuery();
            ResultSet resultat = statement.executeQuery("SELECT * FROM Ressource;");
            OutilsJDBC.afficherResultSet(resultat);

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public void modifiePosition() {
        this.coordX = (random.nextInt(28 - 22) + 22);
        this.coordY = (random.nextInt(18 - 12) + 12);
        this.x = 32 * coordX + 4;
        this.y = 32 * coordY + 4;
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();
           
            PreparedStatement requete = connexion.prepareStatement("UPDATE Ressource SET x = ?, y = ? WHERE idressource = ?");
            requete.setInt(1, x);
            requete.setInt(2, y);
            requete.setInt(3, this.identifiant);
            requete.executeQuery();
            ResultSet resultat = statement.executeQuery("SELECT * FROM Ressource;");
            OutilsJDBC.afficherResultSet(resultat);

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void miseAJour() throws IOException {

    }

    public void rendu(Graphics2D contexte) {
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            PreparedStatement requete = connexion.prepareStatement("SELECT   x , y  FROM Ressource WHERE idressource = ?");
            requete.setInt(1, this.identifiant);           
            
            ResultSet resultat = requete.executeQuery();

            requete.executeQuery();

            while (resultat.next()) {

                this.x = resultat.getInt("x");
                this.y = resultat.getInt("y");
            }

            statement.close();
            requete.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        contexte.drawImage(this.sprite, (int) x, (int) y, null);

    }

}
