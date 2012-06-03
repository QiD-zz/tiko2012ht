/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.sql.Statement;
/**
 *
 * @author QiD
 */
public class SqlYhteydet {
    
    private static final String AJURI = "org.postgresql.Driver";
    private static final String PROTOKOLLA = "jdbc:postgresql:";
    
    private static final int PORTTI = 5432;

    private static String palvelin = "localhost";
    private static String nimi;
    private static String passu;
    
    private static Connection con;
    private static Statement asetaPolku;
    
    public static boolean setSearchPathTo()  {

        String kysely = "SET search_path to tikoht_kk_tr";
        
        try {
             if (con == null || con.isClosed()) {
                con = SqlYhteydet.muodostaYhteys();
            }
            asetaPolku = con.createStatement();         
            asetaPolku.executeUpdate(kysely);
        } catch (NullPointerException ex ) {
            System.out.println(ex.getMessage());
            return false;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
    
    private static Connection yhteysMetodi() {
        try {        
                Class.forName(AJURI);      
            } catch (ClassNotFoundException poikkeus) {       
                System.out.println("Ajurin lataaminen ei onnistunut. Lopetetaan ohjelman suoritus.");          
                return null;       
            }        
            try {  

                return con = DriverManager.getConnection(PROTOKOLLA + "//" + palvelin + ":" + PORTTI + "/" + "testi", nimi, passu);
            } catch (SQLException ex) {         
                System.out.println(ex.getMessage());           
                return null;   
            }

    }
    
    public static Connection muodostaYhteys() throws SQLException {

        if (con == null) {
            con = yhteysMetodi(); 
            return con;
        } else if (con.isClosed()) {
            con =  yhteysMetodi();
            return con;
        } else return con;           
    }
    
    public static boolean onkoYhteytta() throws SQLException {
        if (con == null || con.isClosed()) {
            return false;
        } else {
            return true;
        }
    }
    
    public static void vaihdaNimi(String str) {
        nimi = str;
    }
    
    
    public static void vaihdaSalasana(char[] passuTaulu) {
        String temp = "";
        for (int i = 0; i < passuTaulu.length; i++) {
            temp += passuTaulu[i];           
        }
        passu = temp;

    }
    
    public static void vaihdaPalvelin(String str) {
        palvelin = str;
    }

    public static boolean otaYhteytta() throws SQLException {
        if (!onkoYhteytta()) {
            con = muodostaYhteys();
            return true;
        } else return false;
    }
}
