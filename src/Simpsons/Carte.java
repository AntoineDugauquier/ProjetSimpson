/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Simpsons;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Exemple de classe carte
 *
 * @author guillaume.laurent
 */
public class Carte {

    private int largeur = 12;
    private int hauteur = 9;
    private int tailleTuile = 32;
    
    private int [][] decor = {
        {8,6,19,7,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,14,6,19,7,14,9},
    	{13,0,0,0,1,1,1,1,1,1,0,0,0,0,0,1,1,1,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,0,0,0,15},
    	{13,0,16,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,16,0,15},
    	{13,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,15},
    	{13,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,15},
    	{13,0,1,1,1,1,1,1,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,15},
    	{13,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,15},
    	{13,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,15},
    	{13,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,15},
    	{13,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,15},
    	{13,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15},
    	{13,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15},
    	{13,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0,15},
    	{13,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0,0,0,0,0,0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15},
    	{13,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,3,0,0,0,0,3,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,15},
    	{13,1,0,0,0,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,15},
    	{13,1,1,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,15},
    	{13,1,1,0,1,1,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,15},
    	{13,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,15},
    	{13,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,15},
    	{13,1,1,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,15},
    	{6,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,15},
    	{18,0,16,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,16,0,15},
    	{5,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,21,22,22,21,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,0,0,0,15},
    	{11,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,5,21,22,22,21,4,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,12,5,17,4,12,10}   	 
	};
    
    

    
    private BufferedImage tuileSolBasic;
    
    private BufferedImage tuileCouloirCoinR;
    private BufferedImage tuileCouloirCoin7;
    private BufferedImage tuileCouloirCoinJ;
    private BufferedImage tuileCouloirCoinL;
    
    private BufferedImage tuileCouloirCoinRPetit;
    private BufferedImage tuileCouloirCoin7Petit;
    private BufferedImage tuileCouloirCoinJPetit;
    private BufferedImage tuileCouloirCoinLPetit;
    
    private BufferedImage tuileCouloirBordH;
    private BufferedImage tuileCouloirBordD;
    private BufferedImage tuileCouloirBordB;
    private BufferedImage tuileCouloirBordG;
    
    private BufferedImage tuileSolNoir;
    private BufferedImage tuileSolCasse;
    private BufferedImage tuileSymboleNuc;
    
    private BufferedImage tuileExtincteur;
        
    private BufferedImage tuileFlecheH;
    private BufferedImage tuileFlecheD;
    private BufferedImage tuileFlecheB;
    private BufferedImage tuileFlecheG;
    
    private BufferedImage tuileDangerClair;
    private BufferedImage tuileDangerFonce;

    public Carte() {
        try {
            BufferedImage tileset = ImageIO.read(getClass().getResource("../images/tileSetNuclearLab.png"));
            tuileSolBasic = tileset.getSubimage(16*32, 16*32, tailleTuile, tailleTuile); //0
            tuileSolCasse = tileset.getSubimage(3*32, 15*32, tailleTuile, tailleTuile); //1
            tuileSolNoir = tileset.getSubimage(3*32, 3*32, tailleTuile, tailleTuile); //2
            
            tuileSymboleNuc = tileset.getSubimage(13*32, 15*32, tailleTuile, tailleTuile); //3
            
            tuileCouloirCoinR = tileset.getSubimage(2*32, 2*32, tailleTuile, tailleTuile); //4
            tuileCouloirCoin7 = tileset.getSubimage(4*32, 2*32, tailleTuile, tailleTuile); //5
            tuileCouloirCoinJ = tileset.getSubimage(4*32, 4*32, tailleTuile, tailleTuile); //6
            tuileCouloirCoinL = tileset.getSubimage(2*32, 4*32, tailleTuile, tailleTuile); //7
            
            tuileCouloirCoinRPetit = tileset.getSubimage(14*32, 7*32, tailleTuile, tailleTuile); //8
            tuileCouloirCoin7Petit = tileset.getSubimage(16*32, 7*32, tailleTuile, tailleTuile); //9
            tuileCouloirCoinJPetit = tileset.getSubimage(13*32, 11*32, tailleTuile, tailleTuile); //10
            tuileCouloirCoinLPetit = tileset.getSubimage(11*32, 11*32, tailleTuile, tailleTuile); //11
            
            tuileCouloirBordH = tileset.getSubimage(3*32, 2*32, tailleTuile, tailleTuile); //12
            tuileCouloirBordD = tileset.getSubimage(4*32, 3*32, tailleTuile, tailleTuile); //13
            tuileCouloirBordB = tileset.getSubimage(3*32, 4*32, tailleTuile, tailleTuile); //14
            tuileCouloirBordG = tileset.getSubimage(2*32, 3*32, tailleTuile, tailleTuile); //15
            
            tuileExtincteur = tileset.getSubimage(3*32, 16*32, tailleTuile, tailleTuile); //16
            
            tuileFlecheH = tileset.getSubimage(10*32, 16*32, tailleTuile, tailleTuile); //17
            tuileFlecheD = tileset.getSubimage(9*32, 16*32, tailleTuile, tailleTuile); //18
            tuileFlecheB = tileset.getSubimage(10*32, 15*32, tailleTuile, tailleTuile); //19
            tuileFlecheG = tileset.getSubimage(9*32, 15*32, tailleTuile, tailleTuile); //20
            
            tuileDangerClair = tileset.getSubimage(11*32, 16*32, tailleTuile, tailleTuile); //21
            tuileDangerFonce = tileset.getSubimage(11*32, 15*32, tailleTuile, tailleTuile); //22
            
        } catch (IOException ex) {
            Logger.getLogger(Carte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void miseAJour() {

    }

    public void rendu(Graphics2D contexte) {
        
        // Remplir l'image entière selon la matrice
        for (int i = 0 ; i<50 ; i++){
            for (int j = 0 ; j<30 ; j++){
                int valeur = decor[j][i] ;
                if (valeur==0){
                    contexte.drawImage(tuileSolBasic, i*32, j*32, null);
                }
                if (valeur==1){
                    contexte.drawImage(tuileSolCasse, i*32, j*32, null);
                }
                if (valeur==2){
                    contexte.drawImage(tuileSolNoir, i*32, j*32, null);
                }
                if (valeur==3){
                    contexte.drawImage(tuileSymboleNuc, i*32, j*32, null);
                }
                if (valeur==4){
                    contexte.drawImage(tuileCouloirCoinR, i*32, j*32, null);
                }
                if (valeur==5){
                    contexte.drawImage(tuileCouloirCoin7, i*32, j*32, null);
                }
                if (valeur==6){
                    contexte.drawImage(tuileCouloirCoinJ, i*32, j*32, null);
                }
                if (valeur==7){
                    contexte.drawImage(tuileCouloirCoinL, i*32, j*32, null);
                }
                if (valeur==8){
                    contexte.drawImage(tuileCouloirCoinRPetit, i*32, j*32, null);
                }
                if (valeur==9){
                    contexte.drawImage(tuileCouloirCoin7Petit, i*32, j*32, null);
                }
                if (valeur==10){
                    contexte.drawImage(tuileCouloirCoinJPetit, i*32, j*32, null);
                }
                if (valeur==11){
                    contexte.drawImage(tuileCouloirCoinLPetit, i*32, j*32, null);
                }
                if (valeur==12){
                    contexte.drawImage(tuileCouloirBordH, i*32, j*32, null);
                }
                if (valeur==13){
                    contexte.drawImage(tuileCouloirBordD, i*32, j*32, null);
                }
                if (valeur==14){
                    contexte.drawImage(tuileCouloirBordB, i*32, j*32, null);
                }
                if (valeur==15){
                    contexte.drawImage(tuileCouloirBordG, i*32, j*32, null);
                }
                if (valeur==16){
                    contexte.drawImage(tuileExtincteur, i*32, j*32, null);
                }
                if (valeur==17){
                    contexte.drawImage(tuileFlecheH, i*32, j*32, null);
                }
                if (valeur==18){
                    contexte.drawImage(tuileFlecheD, i*32, j*32, null);
                }
                if (valeur==19){
                    contexte.drawImage(tuileFlecheB, i*32, j*32, null);
                }
                if (valeur==20){
                    contexte.drawImage(tuileFlecheG, i*32, j*32, null);
                }
                if (valeur==21){
                    contexte.drawImage(tuileDangerClair, i*32, j*32, null);
                }
                if (valeur==22){
                    contexte.drawImage(tuileDangerFonce, i*32, j*32, null);
                }
            }
       
        }
        
        
        
        
        
    }
    
    public boolean accessible (int x, int y){
        List <Integer> listeNumAccessible = Arrays.asList(0,16,17,18,19,20,21,22);
        
        int valeur = decor[y][x];
        
        if (listeNumAccessible.contains(valeur)){
            return true;
        }
        return false;
    }

}
