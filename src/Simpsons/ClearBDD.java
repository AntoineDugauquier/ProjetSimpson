/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Simpsons;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import outils.SingletonJDBC;

/**
 *
 * @author ybordes
 */
public class ClearBDD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection connexion = SingletonJDBC.getInstance().getConnection();

            Statement statement = connexion.createStatement();

            statement.executeUpdate("DELETE FROM Avatar;");
            statement.executeUpdate("DELETE FROM Base;");
            statement.executeUpdate("DELETE FROM Ressource;");

            statement.close();
//                        connexion.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
