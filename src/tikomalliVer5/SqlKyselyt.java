/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author QiD
 */
public class SqlKyselyt {
    private PreparedStatement poistaEhdokasVaaliliitosta;
    private PreparedStatement poistaVaaliliitto;
    private PreparedStatement poistaEhdokasVaalirenkaasta;
    private PreparedStatement poistaVaalirengas;
    private PreparedStatement mihinVaalirenkaaseenLiittoKuuluu;
    private PreparedStatement poistaLiittoVaalirenkaasta;
    private PreparedStatement kuuluukoLiittoRenkaaseen;
    private PreparedStatement vaaliLiitonTunnus;
    private PreparedStatement kerroVaalirenkaanTunnus;
    private PreparedStatement onkoJoAanestanyt;
    private PreparedStatement kerroEhdokkaanAanestysPaikat;
    private LinkedList ehdokkaanAaanienAanestysPaikkaLuettelo = new LinkedList();
   
    public SqlKyselyt(Malli m) {
        malli = m;
    }
    
 /*   private static final String AJURI = "org.postgresql.Driver";
    private static final String PROTOKOLLA = "jdbc:postgresql:";
    
    private static final int PORTTI = 5432;

    private String palvelin = "localhost";
    private String nimi;
    private String passu;*/
    
    private Malli malli;
    private Connection con = null;
    private LinkedList ehdokasLuettelo = new LinkedList();
    private LinkedList liittoLuettelo = new LinkedList();
    private LinkedList rengasLuettelo = new LinkedList();
    private LinkedList aanestysPaikkaLuettelo = new LinkedList();
    private int aanestysPaikanNimi;
    private int opNro;
    
    private Statement asetaPolku;
    
    private PreparedStatement lisaaEhdokas;
    private PreparedStatement lisaaVaaliliitto;
    private PreparedStatement lisaaVaalirengas;
    private PreparedStatement lisaaEhdokasVaaliliittoon;
    private PreparedStatement lisaaEhdokasVaalirenkaaseen;
    private PreparedStatement lisaaVaaliliittoVaalirenkaaseen;   
    private PreparedStatement lisaaAanestyspaikka;    
    private PreparedStatement onkoAanestanyt;
    private PreparedStatement kerroOpiskelijanumero;
    private PreparedStatement onkoJoEhdokkaana;
    private PreparedStatement kerroAanestysPaikat;
    private PreparedStatement kerroAanestysPaikka;
    private PreparedStatement poistaEhdokas;
    
  // Public SqlKyselyt(SqlYhteydet y)) {
       
  // }
     public boolean setSearchPathTo()  {

        String kysely = "SET search_path to "+malli.kerroKaavio();
        
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
            return false;
        }
        return true;
    }
    
    
    public int lisaaVaaliliitto(String liitto) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaVaaliliitto  = con.prepareStatement("INSERT INTO vaaliliitto (nimi) values ( ?)");
        lisaaVaaliliitto.setString(1, liitto);
        return lisaaVaaliliitto.executeUpdate();
    }
    
    public int lisaaVaalirengas(String rengas) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaVaalirengas  = con.prepareStatement("INSERT INTO vaalirengas (nimi) values ( ?)");
        lisaaVaalirengas.setString(1, rengas);
        return lisaaVaalirengas.executeUpdate();
    }
    
    public int lisaaEhdokas(String ehdokas, int opnro) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaEhdokas = con.prepareStatement("INSERT INTO ehdokas (nimi, opnro) values ( ?, ?)");
        lisaaEhdokas.setString(1, ehdokas);
        lisaaEhdokas.setInt(2, opnro);
        return lisaaEhdokas.executeUpdate();
    }
    
    
  /*  public boolean onkoEhdokkaana(int opnro) throws SQLException {
        if (con == null) {
            muodostaYhteys();
        }
        
        onkoAanestanyt = con.prepareStatement("SELECT opnro FROM tiko.aanestaja WHERE opnro = ?");
        onkoAanestanyt.setInt(1, opnro);
        ResultSet rs = onkoAanestanyt.executeQuery();
        if (rs.next()) {
            System.out.println("Ehdokas "+opnro+" on jo ehdokkaana.");
            return true;
        } else return false;
    }*/
    
    public String kerroEhdokkaanNimi(int opnro) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
       }
       
       setSearchPathTo(); 
       
       String opNimi = "";
       PreparedStatement kerroOpiskelijanNimi = con.prepareStatement("SELECT nimi FROM ehdokas WHERE opnro = ?");
       kerroOpiskelijanNimi.setInt(1, opnro);
       
       ResultSet rs = kerroOpiskelijanNimi.executeQuery();
       
       while (rs.next()) {
           opNimi = rs.getString(1);
                //    System.out.println(a+b+c+d+e+f);     
        }
        con.close();
        rs.close();
        return opNimi;
    }
    
    public int kerroEhdokkaanOpiskelijanumero(int ehdokasnumero) throws SQLException {
        
       if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
       }
       
       setSearchPathTo(); 
       kerroOpiskelijanumero = con.prepareStatement("SELECT opnro FROM ehdokas WHERE ehdokasnro = ?");
       kerroOpiskelijanumero.setInt(1, ehdokasnumero);
       ResultSet rs = kerroOpiskelijanumero.executeQuery();
       
       while (rs.next()) {
                   opNro = rs.getInt(1);
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
                return opNro;
    }
    
    public LinkedList kerroEhdokasTiedot() throws SQLException {
     try {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        ehdokasLuettelo.clear();
     } catch (SQLException e) {
         System.out.println("Yhteyttä ei voitu muodostaa.");
     }
     
     setSearchPathTo();

           // Vaihe 2: yhteyden ottaminen tietokantaan


    
        try {
            
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM ehdokas order by ehdokasnro asc");

                while (rs.next()) {
                    LinkedList riviolio = new LinkedList();
                    int a  = rs.getInt(1);
                    riviolio.addLast(a);
                    String b = rs.getString(2);
                    riviolio.addLast(b);
                    int c = rs.getInt(3);
                    riviolio.addLast(c);
                    int d = rs.getInt(4);
                    riviolio.addLast(d);
                    int e = rs.getInt(5);
                    if (e != 0) {
                        String nimi = kerroVaaliliitonNimi(e);
                        riviolio.addLast(nimi);
                    } else {
                        riviolio.addLast(e);
                    }
                    
                    int f = rs.getInt(6);
                    if (f != 0) {
                        String nimi = kerroVaalirenkaanNimi(f);
                        riviolio.addLast(nimi);
                    } else {
                        riviolio.addLast(f);
                    }
                    int g = rs.getInt(7);
                    riviolio.addLast(g);
                    int h = rs.getInt(8);
                    riviolio.addLast(h);
                 //   riviolio.addLast(f);
                    ehdokasLuettelo.addLast(riviolio);
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
   
    } catch (SQLException poikkeus) {

        // Vaihe 3.2: tähän toiminta mahdollisessa virhetilanteessa

        System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
    }       

    return ehdokasLuettelo;
 

    }
    
    public LinkedList kerroVaaliliittoTiedot() throws SQLException {
     try {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        liittoLuettelo.clear();
     } catch (SQLException e) {
         System.out.println("Yhteyttä ei voitu muodostaa.");
     }

     setSearchPathTo();

    
        try {
            Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nimi FROM vaaliliitto");

                while (rs.next()) {
                    liittoLuettelo.addLast(rs.getString(1));
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
            
            
    } catch (SQLException poikkeus) {

        // Vaihe 3.2: tähän toiminta mahdollisessa virhetilanteessa

        System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
    }       

    return liittoLuettelo;
 

    }
    
    public String kerroVaaliliitonNimi(int indeksi) {
        try {
            if (con == null || con.isClosed()) {
               con = SqlYhteydet.muodostaYhteys();
            }
        } catch (SQLException e) {
            System.out.println("Yhteyttä ei voitu muodostaa.");
        }
        setSearchPathTo();
        
        String liitonNimi = null;
        try {
            Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nimi FROM vaaliliitto WHERE vaaliliittotunnus = "+indeksi);
                
                while (rs.next()) {
                    liitonNimi = rs.getString(1);     
                }
                con.close();
                rs.close();
            
            
                
        } catch (SQLException ex) {
            
        }
        return liitonNimi;
    }
    
    public String kerroVaalirenkaanNimi(int indeksi) {
        try {
            if (con == null || con.isClosed()) {
               con = SqlYhteydet.muodostaYhteys();
            }
        } catch (SQLException e) {
            System.out.println("Yhteyttä ei voitu muodostaa.");
        }
        setSearchPathTo();
        
        String renkaanNimi = null;
        try {
            Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nimi FROM vaalirengas WHERE vaalirengastunnus = "+indeksi);
                
                while (rs.next()) {
                    renkaanNimi = rs.getString(1);     
                }
                con.close();
                rs.close();
            
            
                
        } catch (SQLException ex) {
            
        }
        return renkaanNimi;
    }
    
    public int kerroVaalirenkaanTunnus(String nimi) {
        try {
            if (con == null || con.isClosed()) {
               con = SqlYhteydet.muodostaYhteys();
            }
        } catch (SQLException e) {
            System.out.println("Yhteyttä ei voitu muodostaa.");
        }
        setSearchPathTo();
        
        int renkaanId = -1;
        try {
            kerroVaalirenkaanTunnus = con.prepareStatement("SELECT vaalirengastunnus FROM vaalirengas WHERE nimi = ?");
            kerroVaalirenkaanTunnus.setString(1, nimi);
                ResultSet rs = kerroVaalirenkaanTunnus.executeQuery();
                
                while (rs.next()) {
                    renkaanId = rs.getInt(1);     
                }
                con.close();
                rs.close();
            
            
                
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        return renkaanId;
    }
    
    public LinkedList kerroVaalirengasTiedot() throws SQLException {
     try {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        rengasLuettelo.clear();
     } catch (SQLException e) {
         System.out.println("Yhteyttä ei voitu muodostaa.");
     }

     setSearchPathTo();

    
        try {
            Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT nimi FROM vaalirengas");

                while (rs.next()) {
                    rengasLuettelo.addLast(rs.getString(1));
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
            
            
    } catch (SQLException poikkeus) {

        // Vaihe 3.2: tähän toiminta mahdollisessa virhetilanteessa

        System.out.println("Tapahtui seuraava virhe: " + poikkeus.getMessage());     
    }       

    return rengasLuettelo;
 

    }

    public void lisaaAanestyspaikka(String nimi, String osoite) throws SQLException {
       if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaAanestyspaikka = con.prepareStatement("INSERT INTO aanestyspaikka (nimi, osoite) values ( ?, ?)");
        lisaaAanestyspaikka.setString(1, nimi);
        lisaaAanestyspaikka.setString(2, osoite);
        lisaaAanestyspaikka.executeUpdate();
    }
    
    public void lisaaEhdokasVaaliliittoon(int opnro, int vaaliliittoId) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaEhdokasVaaliliittoon = con.prepareStatement("update ehdokas set vaaliliitto = ? WHERE opnro = ?");
        lisaaEhdokasVaaliliittoon.setInt(1, vaaliliittoId);
        lisaaEhdokasVaaliliittoon.setInt(2, opnro);
        lisaaEhdokasVaaliliittoon.executeUpdate();
    }
    
    public void lisaaEhdokasVaalirenkaaseen(int opnro, int vaalirengasId) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        lisaaEhdokasVaalirenkaaseen = con.prepareStatement("update ehdokas set vaalirengas = ? WHERE opnro = ?");
        lisaaEhdokasVaalirenkaaseen.setInt(1, vaalirengasId);
        lisaaEhdokasVaalirenkaaseen.setInt(2, opnro);
        lisaaEhdokasVaalirenkaaseen.executeUpdate();
    }
    
    public boolean onkoAanestanyt(int opnro) throws SQLException{
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        onkoJoAanestanyt = con.prepareStatement("SELECT opnro FROM aanestaja WHERE opnro = ?");
        onkoJoAanestanyt.setInt(1, opnro);
        ResultSet rs = onkoJoAanestanyt.executeQuery();
        if (!rs.next()) {
            System.out.println(rs.next());
            return false;
        } else return true;
    }

    public LinkedList kerroAanestysPaikat() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        kerroAanestysPaikat = con.prepareStatement("SELECT nimi FROM aanestyspaikka");
        
        
        ResultSet rs = kerroAanestysPaikat.executeQuery();

                while (rs.next()) {
                    aanestysPaikkaLuettelo.addLast(rs.getString(1));
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
                
                return aanestysPaikkaLuettelo;
    }
    
    public int kerroAanestysPaikka(String paikkakunta) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        kerroAanestysPaikka = con.prepareStatement("SELECT aanestyspaikkatunnus FROM aanestyspaikka WHERE nimi = ?");
        kerroAanestysPaikka.setString(1, paikkakunta);
        
        
        ResultSet rs = kerroAanestysPaikka.executeQuery();

                while (rs.next()) {
                    aanestysPaikanNimi = rs.getInt(1);
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
                
                return aanestysPaikanNimi;
    }

    public void lisaaVaaliliittoVaalirenkaaseen(int vaaliliitto, int vaalirengas) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        
        setSearchPathTo();
        
        lisaaVaaliliittoVaalirenkaaseen = con.prepareStatement("UPDATE vaaliliitto SET vaalirengas = ? WHERE vaaliliittotunnus = ?");
        lisaaVaaliliittoVaalirenkaaseen.setInt(1, vaalirengas);
        lisaaVaaliliittoVaalirenkaaseen.setInt(2, vaaliliitto);
        lisaaVaaliliittoVaalirenkaaseen.executeUpdate();
    }

    public void poistaEhdokas(String opnro) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        
        setSearchPathTo();
        Integer opNumero = Integer.parseInt(opnro);
        poistaEhdokas = con.prepareStatement("DELETE FROM ehdokas WHERE opnro = ?");
        poistaEhdokas.setInt(1, opNumero);
        poistaEhdokas.executeUpdate();
    }

    public void poistaEhdokasVaaliliitosta(int opnro) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            poistaEhdokasVaaliliitosta = con.prepareStatement("UPDATE ehdokas SET vaaliliitto = NULL WHERE opnro = ?");
            poistaEhdokasVaaliliitosta.setInt(1, opnro);
            poistaEhdokasVaaliliitosta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void poistaVaaliliitto(String nimi) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            poistaVaaliliitto = con.prepareStatement("DELETE FROM vaaliliitto WHERE nimi = ?");
            poistaVaaliliitto.setString(1, nimi);
            poistaVaaliliitto.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void poistaEhdokasVaalirenkaasta(Integer opnro) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            poistaEhdokasVaalirenkaasta = con.prepareStatement("UPDATE ehdokas SET vaalirengas = NULL WHERE opnro = ?");
            poistaEhdokasVaalirenkaasta.setInt(1, opnro);
            poistaEhdokasVaalirenkaasta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void poistaVaalirengas(String nimi) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            poistaVaalirengas = con.prepareStatement("DELETE FROM vaalirengas WHERE nimi = ?");
            poistaVaalirengas.setString(1, nimi);
            poistaVaalirengas.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public int mihinVaalirenkaaseenLiittokuuluu(String liitonNimi) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            mihinVaalirenkaaseenLiittoKuuluu = con.prepareStatement("SELECT vaalirengas FROM vaaliliitto WHERE nimi = ?");
            mihinVaalirenkaaseenLiittoKuuluu.setString(1, liitonNimi);
           ResultSet rs = mihinVaalirenkaaseenLiittoKuuluu.executeQuery();
           int rengasNumero = -1;
           while (rs.next()) {
               rengasNumero = rs.getInt(1);
           }
           
           return rengasNumero;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return -1;
        }
    }

    public void poistaLiittoVaalirenkaasta(String vaaliliittoNimi) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            poistaLiittoVaalirenkaasta = con.prepareStatement("UPDATE vaaliliitto SET vaalirengas = NULL WHERE nimi = ?");
            poistaLiittoVaalirenkaasta.setString(1, vaaliliittoNimi);
            poistaLiittoVaalirenkaasta.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public int kuuluukoLiittoRenkaaseen(int vaaliliittoId) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            kuuluukoLiittoRenkaaseen = con.prepareStatement("SELECT vaalirengas FROM vaaliliitto WHERE vaaliliittotunnus = ?");
            kuuluukoLiittoRenkaaseen.setInt(1, vaaliliittoId);
            ResultSet rs = kuuluukoLiittoRenkaaseen.executeQuery();
            
            int rengasNumero = -1;
            while (rs.next()) {
               rengasNumero = rs.getInt(1);
            }
            return rengasNumero;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return -1;
        }
    }

    public int kerroVaaliliitonTunnus(String nimi) {
        try {
            try {
                if (con == null || con.isClosed()) {
                    con = SqlYhteydet.muodostaYhteys();
                }
                
                setSearchPathTo();
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
            vaaliLiitonTunnus = con.prepareStatement("SELECT vaaliliittotunnus FROM vaaliliitto WHERE nimi = ?");
            vaaliLiitonTunnus.setString(1, nimi);
            ResultSet rs = vaaliLiitonTunnus.executeQuery();
            
            int liittoNumero = -1;
            while (rs.next()) {
               liittoNumero = rs.getInt(1);
            }
            return liittoNumero;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return -1;
        }
    }

    public LinkedList haeEhdokkaanAanetAaanestyspaikoittain(int ehdokkaanOpNro) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        ehdokkaanAaanienAanestysPaikkaLuettelo.clear();
    /*    kerroEhdokkaanAanestysPaikat = con.prepareStatement("SELECT e.nimi, ap.nimi, aanten_lkm"
                + " FROM aanestyspaikka ap, ehdokas e, ehdokas_paikka ep WHERE ep.opnro = e.opnro AND"
                + " ap.aanestyspaikkatunnus = ep.aanestyspaikkatunnus AND e.opnro = ?");*/
        
        kerroEhdokkaanAanestysPaikat = con.prepareStatement("SELECT ap.nimi, aanten_lkm"
                + " FROM aanestyspaikka ap, ehdokas e, ehdokas_paikka ep WHERE ep.opnro = e.opnro AND"
                + " ap.aanestyspaikkatunnus = ep.aanestyspaikkatunnus AND e.opnro = ?");
        
        kerroEhdokkaanAanestysPaikat.setInt(1, ehdokkaanOpNro);
        ResultSet rs = kerroEhdokkaanAanestysPaikat.executeQuery();

                while (rs.next()) {
                    String rivi = "Äänetyspaikka: "+rs.getString(1)+" Ääniä: "+rs.getInt(2);
                    ehdokkaanAaanienAanestysPaikkaLuettelo.addLast(rivi);
                //    ehdokkaanAaanienAanestysPaikkaLuettelo.addLast();
                 //   ehdokkaanAaanienAanestysPaikkaLuettelo.addLast();
                //    System.out.println(a+b+c+d+e+f);     
                }
                con.close();
                rs.close();
                
                return ehdokkaanAaanienAanestysPaikkaLuettelo;
    }
    
    
    
}
