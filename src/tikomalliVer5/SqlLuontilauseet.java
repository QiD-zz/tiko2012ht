/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.sql.*;

/**
 *
 * @author QiD
 */
public class SqlLuontilauseet {
    
    private Connection con;
    private Statement luoKaavio;
    private Statement luoVaalirengas;
    private Statement luoVaaliliitto;
    
    private Statement asetaPolku;
    
  //  private String kaavio;
    private Malli malli;
    private Statement luoEhdokas;
    private Statement luoAanestaja;
    private Statement luoAanestyspaikka;
    private Statement luoEhdokaspaikka;

    SqlLuontilauseet(Malli m) {
        malli = m;
    }
    
    private void setSearchPathTo() throws SQLException {

        String kysely = "SET search_path to "+malli.kerroKaavio();
        
        asetaPolku = con.createStatement();         
        asetaPolku.executeUpdate(kysely);
    }
    
    public void luoKaavio(String kaavio) throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
            
        }
        String kysely = "CREATE SCHEMA "+kaavio;

        luoKaavio = con.createStatement();
        int a = luoKaavio.executeUpdate(kysely);
    }
    
    public void luoVaalirengas() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        luoVaalirengas = con.createStatement();
        
        luoVaalirengas.executeUpdate("CREATE TABLE vaalirengas ("
                + "vaalirengastunnus serial NOT NULL,"
                + "nimi character varying,"
                + "aanimaara integer,"
                + "PRIMARY KEY (vaalirengastunnus))");
        
    }
    
    public void luoVaaliliitto() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
       luoVaaliliitto = con.createStatement();
        
       luoVaaliliitto.executeUpdate("CREATE TABLE vaaliliitto"
                +"(vaaliliittotunnus serial NOT NULL,"
                + "nimi character varying,"
                + "aanimaara integer,"
                + "vaalirengas integer,"
                + "PRIMARY KEY (vaaliliittotunnus),"
                + "FOREIGN KEY (vaalirengas) REFERENCES vaalirengas (vaalirengastunnus))");
    }
    
    public void luoEhdokas() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        luoEhdokas = con.createStatement();
        
        luoEhdokas.executeUpdate("CREATE TABLE ehdokas ("
                + "ehdokasNro integer,"
                + "nimi character varying,"
                + "aanimaara integer,"
                + "opnro integer NOT NULL,"
                + "vaaliliitto integer,"
                + "vaalirengas integer,"
                + "vertailuluku integer,"
                + "sija integer,"
                + "PRIMARY KEY (opnro),"
                + "FOREIGN KEY (vaaliliitto) REFERENCES vaaliliitto (vaaliliittotunnus),"
                + "FOREIGN KEY (vaalirengas) REFERENCES vaalirengas (vaalirengastunnus))");
    }
    
    public void luoAanestaja() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        luoAanestaja = con.createStatement();
        
        luoAanestaja.executeUpdate("CREATE TABLE aanestaja("
                + "opnro integer NOT NULL,"
                + "aanestyspaikka integer,"
                + "PRIMARY KEY (opnro),"
                + "FOREIGN KEY (aanestyspaikka) REFERENCES aanestyspaikka (aanestyspaikkatunnus))");
    }
    
    public void luoAanestyspaikka() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        luoAanestyspaikka = con.createStatement();
        
        luoAanestyspaikka.executeUpdate("CREATE TABLE aanestyspaikka("
                + "aanestyspaikkatunnus serial NOT NULL,"
                + "nimi character varying,"
                + "osoite character varying,"
                + "PRIMARY KEY (aanestyspaikkatunnus))");
    }
    
    public void luoEhdokaspaikka() throws SQLException {
        
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        setSearchPathTo();
        
        luoEhdokaspaikka = con.createStatement();

        luoEhdokaspaikka.executeUpdate("CREATE TABLE ehdokas_paikka("
                + "aanestyspaikkatunnus integer NOT NULL,"
                + "aanten_lkm integer,"
                + "opnro integer NOT NULL,"
                + "PRIMARY KEY (opnro, aanestyspaikkatunnus),"
                + "FOREIGN KEY (aanestyspaikkatunnus) REFERENCES aanestyspaikka (aanestyspaikkatunnus),"
                + "FOREIGN KEY (opnro) REFERENCES tikoht_kk_tr.ehdokas (opnro))");
    }

    public void tuhoaKaavio() throws SQLException {
        if (con == null || con.isClosed()) {
            con = SqlYhteydet.muodostaYhteys();
        }
        
        Statement tuhoa = con.createStatement();
        
        tuhoa.executeUpdate("DROP SCHEMA tikoht_kk_tr CASCADE");
    }
    
}


/*
 * CREATE TABLE tikoht_kk_tr.ehdokas_paikka
(
  aanestyspaikka integer NOT NULL,
  aanten_lkm integer,
  "opNro" integer NOT NULL,
  CONSTRAINT ehdokas_paikka_pkey PRIMARY KEY ("opNro", aanestyspaikka),
  CONSTRAINT ehdokas_paikka_aanestyspaikka_fkey FOREIGN KEY (aanestyspaikka)
      REFERENCES tikoht_kk_tr.aanestyspaikka (aanestyspaikkatunnus)
 * CREATE TABLE tiko.ehdokas
(
  "ehdokasNro" integer,
  nimi character varying,
  aanimaara integer,
  opnro integer NOT NULL,
  vaaliliitto integer,
  vaalirengas integer,
  CONSTRAINT ehdokas_pkey PRIMARY KEY (opnro),
  CONSTRAINT ehdokas_vaaliliitto_fkey FOREIGN KEY (vaaliliitto)
      REFERENCES tiko.vaaliliitto (vaaliliittotunnus) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT ehdokas_vaalirengas_fkey FOREIGN KEY (vaalirengas)
      REFERENCES tiko.vaalirengas (vaalirengastunnus) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
)*/