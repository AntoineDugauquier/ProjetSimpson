package Simpsons;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import outils.SingletonJDBC;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    private JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;

    public FenetreDeJeu() {
        // initialisation de la fenetre
        this.setSize(1600, 960);
        this.setTitle("Simpson Partie");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(this);
        this.jLabel1 = new JLabel();
        this.jLabel1.setPreferredSize(new java.awt.Dimension(1600, 960));
        this.setContentPane(this.jLabel1);
        this.pack();

        // Creation du jeu
        this.jeu = new Jeu();
        // Creation du buffer pour l'affichage du jeu et recuperation du contexte graphique
        this.framebuffer = new BufferedImage(this.jLabel1.getWidth(), this.jLabel1.getHeight(), BufferedImage.TYPE_INT_ARGB);
        this.jLabel1.setIcon(new ImageIcon(framebuffer));
        this.contexte = this.framebuffer.createGraphics();

        // Creation du Timer qui appelle this.actionPerformed() tous les 40 ms
        this.timer = new Timer(100, this);
        this.timer.start();
      //SCORE//

    }

    // Methode appelee par le timer et qui effectue la boucle de jeu
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.jeu.miseAJour();
        } catch (IOException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.jeu.rendu(contexte);
        } catch (IOException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jLabel1.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // NOP
    }

    @Override
    public void keyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.setdemDroite(true);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.setdemGauche(true);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu.setdemHaut(true);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu.setdemBas(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent evt) {
        if (evt.getKeyCode() == evt.VK_RIGHT) {
            this.jeu.setdemDroite(false);
        }
        if (evt.getKeyCode() == evt.VK_LEFT) {
            this.jeu.setdemGauche(false);
        }
        if (evt.getKeyCode() == evt.VK_UP) {
            this.jeu.setdemHaut(false);
        }
        if (evt.getKeyCode() == evt.VK_DOWN) {
            this.jeu.setdemBas(false);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);
                //SCORE//
        JFrame frame = new JFrame("Score");
        frame.setSize(250, 250);
        frame.setLocation(1600, 0);
        frame.setLayout(null);
        frame.setVisible(true);
        JTextField scoredeHomer = new JTextField();
        JTextField scoredeMarge = new JTextField();
        JTextField scoredeBart = new JTextField();
        JTextField scoredeLisa = new JTextField();
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requeteHomer = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Homer'");
            PreparedStatement requeteMarge = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Marge'");
            PreparedStatement requeteBart = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Bart'");
            PreparedStatement requeteLisa = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Lisa'");
            ResultSet resultatHomer = requeteHomer.executeQuery();

            while (resultatHomer.next()) {
                int scoreHomer = resultatHomer.getInt("score");
                scoredeHomer.setText(String.valueOf(scoreHomer));
                
            }
            requeteHomer.close();
            
            ResultSet resultatMarge = requeteMarge.executeQuery();
            while (resultatMarge.next()) {
                int scoreMarge = resultatMarge.getInt("score");
                scoredeMarge.setText(String.valueOf(scoreMarge));
                
            }
            requeteMarge.close();
            
            ResultSet resultatBart = requeteBart.executeQuery();
            while (resultatBart.next()) {
                int scoreBart = resultatBart.getInt("score");
                scoredeBart.setText(String.valueOf(scoreBart));
                
            }
            requeteBart.close();
            
            ResultSet resultatLisa = requeteLisa.executeQuery();
            while (resultatLisa.next()) {
                int scoreLisa = resultatLisa.getInt("score");
                scoredeLisa.setText(String.valueOf(scoreLisa));
                
            }
            requeteLisa.close();

//            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        frame.add(scoredeHomer);
        frame.add(scoredeMarge);
        frame.add(scoredeBart);
        frame.add(scoredeLisa);
    }

}
