package Simpsons;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
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
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import outils.SingletonJDBC;

/**
 * Exemple de fenetre de jeu en utilisant uniquement des commandes
 *
 */
public class FenetreDeJeu extends JFrame implements ActionListener, KeyListener {

    private BufferedImage framebuffer;
    private Graphics2D contexte;
    static JLabel jLabel1;
    private Jeu jeu;
    private Timer timer;
    static int scoreHomer;
    static int scoreMarge;
    static int scoreBart;
    static int scoreLisa;
    static JLabel scoredeHomer = new JLabel();
    static JLabel scoredeMarge = new JLabel();
    static JLabel scoredeBart = new JLabel();
    static JLabel scoredeLisa = new JLabel();
    static JLabel titre = new JLabel();

    public FenetreDeJeu() throws SQLException {
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
        this.timer = new Timer(150, this);
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
        } catch (InterruptedException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.jeu.rendu(contexte);
        } catch (IOException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FenetreDeJeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.jLabel1.repaint();
        try {

            Connection connexion = SingletonJDBC.getInstance().getConnection();

            PreparedStatement requeteHomer = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Homer'");
            PreparedStatement requeteMarge = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Marge'");
            PreparedStatement requeteBart = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Bart'");
            PreparedStatement requeteLisa = connexion.prepareStatement("SELECT score FROM Avatar WHERE idavatar='Lisa'");
            ResultSet resultatHomer = requeteHomer.executeQuery();

            while (resultatHomer.next()) {
                scoreHomer = resultatHomer.getInt("score");

            }
            requeteHomer.close();

            ResultSet resultatMarge = requeteMarge.executeQuery();
            while (resultatMarge.next()) {
                scoreMarge = resultatMarge.getInt("score");

            }
            requeteMarge.close();

            ResultSet resultatBart = requeteBart.executeQuery();
            while (resultatBart.next()) {
                scoreBart = resultatBart.getInt("score");

            }
            requeteBart.close();

            ResultSet resultatLisa = requeteLisa.executeQuery();
            while (resultatLisa.next()) {
                scoreLisa = resultatLisa.getInt("score");

            }
            requeteLisa.close();
            scoredeHomer.setText("Score de Homer : " + String.valueOf(scoreHomer));
            scoredeMarge.setText("Score de Marge : " + String.valueOf(scoreMarge));
            scoredeBart.setText("Score de Bart : " + String.valueOf(scoreBart));
            scoredeLisa.setText("Score de Lisa : " + String.valueOf(scoreLisa));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

    public int getScoreHomer() {
        return scoreHomer;
    }

    public int getScoreMarge() {
        return scoreMarge;
    }

    public int getScoreBart() {
        return scoreBart;
    }

    public int getScoreLisa() {
        return scoreLisa;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        //SCORE//

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        frame.setSize(160, 145);
        frame.setLocation(1600, 0);
        frame.setLayout(new FlowLayout());

        titre.setText("Panneau des scores");
        JLabel espace = new JLabel();
        panel.add(titre);
        panel.add(espace);
        panel.add(scoredeHomer);
        panel.add(scoredeMarge);
        panel.add(scoredeBart);
        panel.add(scoredeLisa);
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        FenetreDeJeu fenetre = new FenetreDeJeu();
        fenetre.setVisible(true);

    }

    public static void fin(String id) throws InterruptedException {
        JFrame framefin = new JFrame();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        framefin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framefin.setResizable(false);
        framefin.setSize(500, 500);
        framefin.setLocation(800, 480);
        JLabel labelfin = new JLabel("La partie est terminée !", SwingConstants.CENTER);
        JLabel gagnant = new JLabel("Le gagnant / la gagnante est " + id, SwingConstants.CENTER);
        Font font = new Font("Arial", Font.BOLD, 15);
        labelfin.setFont(font);
        gagnant.setFont(font);
        labelfin.setSize(250, 250);
        labelfin.setLocation(760, 480);
        labelfin.setVisible(true);
        panel.add(labelfin);
        panel.add(gagnant);
        framefin.getContentPane().add(panel);
        framefin.setVisible(true);       
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            statement.executeUpdate("DELETE FROM Avatar;");
            statement.executeUpdate("DELETE FROM Base;");
            statement.executeUpdate("DELETE FROM Ressource;");

            statement.close();
            connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        jLabel1.wait();

    }

}
