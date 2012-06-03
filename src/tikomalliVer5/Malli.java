/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import tikoht.Ehdokas;
import tikoht.Tikoht;

/**
 *
 * @author QiD
 */
public class Malli extends Observable {
    
    private LinkedList ehdokkaat;
    private LinkedList ehdokasAsettelunEhdokkaat;
    private LinkedList tulosEhdokkaat;
    private LinkedList< String > riviTiedot;
  //  private String[] ehdokasAsettelunSarakeTiedot = {"Ehdokasnumero","Nimi","Äänimäärä","Oppilasnumero","Vaaliliitto","Vaalirengas"};
    private String[] ehdokasAsettelunSarakeTiedot = {"Ehdokasnumero","Nimi","Oppilasnumero","Vaaliliitto","Vaalirengas"};
    private String[] tulosSarakeTiedot = {"Ehdokasnumero","Nimi","Oppilasnumero","Vaaliliitto","Vaalirengas", "Äänimäärä", "Vertailuluku"};
    private SqlKyselyt sqlKyselija;
    private SqlLuontilauseet sqlLuoja;
    
    private LinkedList vaalirenkaat;
    private LinkedList vaaliliitot;
    private String kaavio = "tikoht_kk_tr";
    private EhdokasAsetteluTaulu taulu;
    private TulosTaulu tulosTaulu;
    
    public Malli() {
        vaaliliitot = new LinkedList();
        vaalirenkaat = new LinkedList();
        ehdokkaat = new LinkedList();
        ehdokasAsettelunEhdokkaat = new LinkedList();
        tulosEhdokkaat = new LinkedList();
        riviTiedot = new LinkedList();
        taulu = new EhdokasAsetteluTaulu(this);
        tulosTaulu = new TulosTaulu(this);
        sqlLuoja = new SqlLuontilauseet(this);
        sqlKyselija = new SqlKyselyt(this);
    }
    
    public void ehdokasListanPaivitys() {

            try {
                LinkedList temp = sqlKyselija.kerroEhdokasTiedot(); 
                ListIterator i = temp.listIterator();      
                ehdokkaat.clear();
                ehdokasAsettelunEhdokkaat.clear();
                while (i.hasNext()) {        
                    ehdokkaat.addLast(i.next());                
                }
                for (int j = 0; j < ehdokkaat.size(); j++) {
                    LinkedList temp2 = (LinkedList)ehdokkaat.get(j);
                    LinkedList temp3 = new LinkedList();
                    temp3.add(temp2.get(0));
                    temp3.add(temp2.get(1));
                    temp3.add(temp2.get(3));
                    temp3.add(temp2.get(4));
                    temp3.add(temp2.get(5));
                    ehdokasAsettelunEhdokkaat.add(temp3);
                    
                }
                setChanged();    
                notifyObservers("paivitys");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
   
    }
    
    public void tulosListanPaivitys() {
        tulosEhdokkaat.clear();
        Tikoht tht = new Tikoht(this);
        tht.laskeTulos();
        
       
            try {
                LinkedList ehdokkaatEhdokkaina = sqlKyselija.kerroEhdokasTiedot();
                for (int j = 0; j < ehdokkaatEhdokkaina.size(); j++) {
                    LinkedList temp = new LinkedList();
                    LinkedList tempEhdokas = (LinkedList)ehdokkaatEhdokkaina.get(j);
                  //  Ehdokas tempEhd = (Ehdokas)ehdokkaatEhdokkaina.get(i);
                    temp.addLast(tempEhdokas.getFirst());
                    temp.addLast(tempEhdokas.get(1));
                    temp.addLast(tempEhdokas.get(3));
                    temp.addLast(tempEhdokas.get(4));
                    temp.addLast(tempEhdokas.get(5));
                    temp.addLast(tempEhdokas.get(2));
                    temp.addLast(tempEhdokas.get(6));
                    tulosEhdokkaat.addLast(temp);
                    
                }
                
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            
        
        
        setChanged();    
        notifyObservers("paivitys");
    }

    
    public void luoKaavio() throws SQLException {

        sqlLuoja.luoKaavio(kaavio);
        sqlLuoja.luoAanestyspaikka();
        sqlLuoja.luoAanestaja();
        sqlLuoja.luoVaalirengas();
        sqlLuoja.luoVaaliliitto();
        sqlLuoja.luoEhdokas();  
        sqlLuoja.luoEhdokaspaikka();
    }
    
    public String kerroVaaliliitto(int i) {
        return (String)vaaliliitot.get(i);
    }
    
    public int montakoVaaliliittoa() {
        return vaaliliitot.size();       
    }
    
    public int montakoVaalirengasta() {
        return vaalirenkaat.size();
    }
    
    public String kerroVaalirengas(int i) {
        return (String)vaalirenkaat.get(i);
    }
       
    
    public Object kerroEhdokasTaulunArvo(int rowIndex, int columnIndex) {
        LinkedList temp = (LinkedList)ehdokasAsettelunEhdokkaat.get(rowIndex);
        return temp.get(columnIndex);
    }
    
    public Object kerroTulosTaulunArvo(int rowIndex, int columnIndex) {
        LinkedList temp = (LinkedList)tulosEhdokkaat.get(rowIndex);
        return temp.get(columnIndex);
    }
    
    public LinkedList getEhdokasLista() {
        return ehdokasAsettelunEhdokkaat;
    }
    
    public int kerroEhdokkaidenLkm() {
        return ehdokkaat.size();
    }
    
    public int kerroSarakeLkm() {

        return ehdokasAsettelunSarakeTiedot.length;
    }
    
    public String kerroSarakkeenNimi(int index) {
 
        return ehdokasAsettelunSarakeTiedot[index];
    }
    
    public int kerroTulosSarakeLkm() {
        return tulosSarakeTiedot.length;
    }

    public String kerroTulosSarakkeenNimi(int columnIndex) {
        return tulosSarakeTiedot[columnIndex];
    }
    
    public String kerroKaavio() {
        return "tikoht_kk_tr";
    }
    
    public Malli kerroMalli() {
        return this;
    }
    
    public EhdokasAsetteluTaulu kerroTaulu() {
        return taulu;
    }
    
    public TulosTaulu kerroTulostaulu() {
        return tulosTaulu;
    }
    
    public void kirjautuminenOk() {
        if (onkoKaaviota()) {
            ehdokasListanPaivitys();
            vaaliLiittojenPaivitys();
            vaaliRenkaidenPaivitys();
            
            setChanged();  
            notifyObservers("kaavio on");
        } else {
            setChanged();  
            //Tämän kun laittaa päälle ja "ei kaaviota" pois niin pääse tarkastamaan mestoja.
          //  notifyObservers("kaavio on");
            notifyObservers("ei kaaviota");
        }
        
    }
    
    public void lisaaEhdokas(String nimi, int opnro) {
        try {
            System.out.println(sqlKyselija.lisaaEhdokas(nimi, opnro));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void lisaaVaaliliitto(String liitto) {
        try {  
            sqlKyselija.lisaaVaaliliitto(liitto);
        } catch (SQLException e) {
            System.out.println(e.getMessage());        
        }
        vaaliliitot.addLast(liitto);
        setChanged();
        notifyObservers("liitto");
    }
    
    public void lisaaVaalirengas(String rengas) {
        try {  
            sqlKyselija.lisaaVaalirengas(rengas);
        } catch (SQLException e) {
            System.out.println(e.getMessage());        
        }
        vaalirenkaat.addLast(rengas);
        setChanged();
        notifyObservers("rengas");
    }
    
    public void lisaaEhdokasVaalirenkaaseen(int indeksi, int vaalirengasId) {
        LinkedList temp = (LinkedList)ehdokkaat.get(indeksi);
      //  int ehdokas = Integer.parseInt((String)temp.get(3));
        Integer ehdokas = (Integer)temp.get(3);
        try {
            sqlKyselija.lisaaEhdokasVaalirenkaaseen(ehdokas, vaalirengasId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        ehdokasListanPaivitys();

    }
    
    public void lisaaEhdokasVaaliliittoon(int indeksi, int vaaliliittoId) {
        LinkedList temp = (LinkedList)ehdokkaat.get(indeksi);
//        int ehdokas = Integer.parseInt((String)temp.get(3));
        Integer ehdokas = (Integer)temp.get(3);
        int rengasTunnus = sqlKyselija.kuuluukoLiittoRenkaaseen(vaaliliittoId);
        
        try {
            if (rengasTunnus > 0) {
                sqlKyselija.lisaaEhdokasVaalirenkaaseen(ehdokas, rengasTunnus);
            }
            sqlKyselija.lisaaEhdokasVaaliliittoon(ehdokas, vaaliliittoId);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        ehdokasListanPaivitys();

    }
    
    public void lisaaRivi(LinkedList rivi) {
        ehdokkaat.add(rivi);
        setChanged();
        notifyObservers(rivi);
    }
    
    public void lisaaSarake(String str) {
        riviTiedot.add(str);
        for (int i = 0; i < ehdokkaat.size(); i++) {
            LinkedList temp = (LinkedList)ehdokkaat.get(i);
            temp.add(null);
            ehdokkaat.set(i, temp);
            
        }
        setChanged();
        notifyObservers(str);
    }
    
     
    public void maaritaNimi(String str) {
        SqlYhteydet.vaihdaNimi(str);
    }
    
    public void maaritaSalasana(char[] passu) {
        SqlYhteydet.vaihdaSalasana(passu);
    }
    
    public void maaritaPalvelin(String str) {
        SqlYhteydet.vaihdaPalvelin(str);
    }
    
    public boolean onkoAanestanyt(int opnro) {
        try {
           return sqlKyselija.onkoAanestanyt(opnro);          
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
        return false;
    }
    
    public boolean onkoKaaviota() {
        
        if (sqlKyselija.setSearchPathTo()) {
            return true;
        } else {
            return false;        
        }
     /*   try {
            ; 
        } catch (SQLException | NullPointerException ex) {
            return false;
        }
        return true;*/
    }
    
    public boolean otaYhteys() {
        try {
            return SqlYhteydet.otaYhteytta();       
        } catch (SQLException ex) {  
            return false;        
        }        
    }
    
    public void vaaliLiittojenPaivitys() {
        boolean yhteysOn = otaYhteys();
        if (yhteysOn) {
            try {
                LinkedList temp = sqlKyselija.kerroVaaliliittoTiedot();        
                ListIterator i = temp.listIterator();      
                vaaliliitot.clear();                    
                while (i.hasNext()) {        
                    vaaliliitot.addLast(i.next());          
                 //   System.out.println("haloo");           
                }     
                setChanged();    
                notifyObservers("liitto");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }       
    }
    
    public void vaaliRenkaidenPaivitys() {
        boolean yhteysOn = otaYhteys();
        if (yhteysOn) {
            try {
                LinkedList temp = sqlKyselija.kerroVaalirengasTiedot();        
                ListIterator i = temp.listIterator();      
                vaalirenkaat.clear();                    
                while (i.hasNext()) {        
                    vaalirenkaat.addLast(i.next());          
                  //  System.out.println((String)(vaalirenkaat.getLast()));           
                }     
                setChanged();    
                notifyObservers("rengas");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }       
    }

    public void lisaaAanestyspaikka(String nimi, String osoite) {
        try {
            sqlKyselija.lisaaAanestyspaikka(nimi, osoite);
        } catch (SQLException ex) {
           System.out.println(ex.getMessage());
        }
    }

    public LinkedList getAanestyspaikat() {
        try {
            return sqlKyselija.kerroAanestysPaikat();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public int kerroAanestysPaikkaTunnus(String paikkakunta) {
        try {
            return sqlKyselija.kerroAanestysPaikka(paikkakunta);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return -1;
        }
    }

    public int kerroEhdokkaanOpnro(int ehdokasNro) {
        try {
            return sqlKyselija.kerroEhdokkaanOpiskelijanumero(ehdokasNro);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return -1;
        }
    }

    public void tuhoaKaavio() {
        try {
            sqlLuoja.tuhoaKaavio();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public String kerroEhdokkaanNimi(int opnro) {
        try {
            return sqlKyselija.kerroEhdokkaanNimi(opnro);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
        }
    }
    
    public int kerroVaaliliitto(String nimi) {
        return sqlKyselija.kerroVaaliliitonTunnus(nimi);
    }
    
    public int kerroVaalirengas(String nimi) {
        return sqlKyselija.kerroVaalirenkaanTunnus(nimi);
    }

    public void lisaaParsittuEhdokas(int onro, String nimi, String vaaliliitto, String vaalirengas) {
        try {
            sqlKyselija.lisaaEhdokas(nimi, onro);
            if (!vaaliliitto.equals("null")) {
                for (int i = 0; i < vaaliliitot.size(); i++) {
                    if (vaaliliitot.get(i).equals(vaaliliitto)) {
                        sqlKyselija.lisaaEhdokasVaaliliittoon(onro, sqlKyselija.kerroVaaliliitonTunnus((String)vaaliliitot.get(i)));
                    }
                    
                }
            }
            if (!vaalirengas.equals("null")) {
                for (int i = 0; i < vaalirenkaat.size(); i++) {
                    if (vaalirenkaat.get(i).equals(vaalirengas)) {
                        sqlKyselija.lisaaEhdokasVaalirenkaaseen(onro, sqlKyselija.kerroVaalirenkaanTunnus((String)vaalirenkaat.get(i)));
                    }
                }
            }      
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void lisaaParsittuVaalirengas(String nimi) {
        try {
            sqlKyselija.lisaaVaalirengas(nimi);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
    
    public void lisaaParsittuVaaliliitto(String nimi, String vaalirengas) {
        try {
            sqlKyselija.lisaaVaaliliitto(nimi);
            for (int i = 0; i < vaalirenkaat.size(); i++) {
                String rengas = (String)vaalirenkaat.get(i);
                if (rengas.equals(vaalirengas)) {
                    sqlKyselija.lisaaVaaliliittoVaalirenkaaseen(sqlKyselija.kerroVaaliliitonTunnus(nimi),
                            sqlKyselija.kerroVaalirenkaanTunnus(vaalirengas));
                }
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Malli.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void lisaaVaaliliittoVaalirenkaaseen(int vaaliliitto, int vaalirengas) {
        try {
            sqlKyselija.lisaaVaaliliittoVaalirenkaaseen(vaaliliitto, vaalirengas);
            for (int i = 0; i < ehdokkaat.size(); i++) {
                LinkedList temp = (LinkedList)ehdokkaat.get(i);
                if (temp.get(4).equals(sqlKyselija.kerroVaaliliitonNimi(vaaliliitto))) {
                    sqlKyselija.lisaaEhdokasVaalirenkaaseen((Integer)temp.get(3), vaalirengas);
                }
                if (temp.get(5).equals(sqlKyselija.kerroVaalirenkaanNimi(vaalirengas))) {
                    sqlKyselija.lisaaEhdokasVaaliliittoon((Integer)temp.get(3), vaaliliitto);
                }
            }
            ehdokasListanPaivitys();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public boolean kuuluukoEhdokasVaaliliittoon(int i) {
        LinkedList temp = (LinkedList)ehdokkaat.get(i);
        try {
            Integer arvo = (Integer)temp.get(4);
            if (arvo == 0) {
                return false;
            } else return true;
        } catch (ClassCastException ex) {
            return true;
        }
        
    }

    public void poistaEhdokas(String opnro) {
     /*  for (int i = 0; i < ehdokkaat.size(); i++) {
            LinkedList temp = (LinkedList)ehdokkaat.get(i);
            if (temp.get(3) == opnro) {
                if (temp.get(5))
            }
            
        }*/
        try {
            sqlKyselija.poistaEhdokas(opnro);
            ehdokasListanPaivitys();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Ehdokasta ei voitu poistaa, koska hänelle on jo annettu ääniä.", "Virhe", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace(System.out);
        }
    }

    public void poistaVaaliliitto(String liittoNimi) {
        for (int i = 0; i < ehdokkaat.size(); i++) {
            LinkedList temp = (LinkedList)ehdokkaat.get(i);
            System.out.println(temp.get(4));
            String nimiTaulusta = "";
            try {
             nimiTaulusta = (String)temp.get(4);
            } catch (ClassCastException ex) {
                nimiTaulusta = "0";
            }
            System.out.println((temp.get(4)==nimiTaulusta));
            if (nimiTaulusta.equals(liittoNimi)) {
                System.out.println("mitä tää on: "+temp.get(3));
                sqlKyselija.poistaEhdokasVaaliliitosta((Integer)temp.get(3));
            }           
        }
        sqlKyselija.poistaVaaliliitto(liittoNimi);
        ehdokasListanPaivitys();
        vaaliLiittojenPaivitys();
    }

    public void poistaVaalirengas(String rengasNimi) {
        for (int i = 0; i < ehdokkaat.size(); i++) {
            LinkedList temp = (LinkedList)ehdokkaat.get(i);
            System.out.println(temp.get(5));
            String nimiTaulusta = "";
            try {
             nimiTaulusta = (String)temp.get(5);
            } catch (ClassCastException ex) {
                nimiTaulusta = "0";
            }
            System.out.println((temp.get(5)==rengasNimi));
            if (nimiTaulusta.equals(rengasNimi)) {
                System.out.println("mitä tää on: "+temp.get(3));
                sqlKyselija.poistaEhdokasVaalirenkaasta((Integer)temp.get(3));
            }           
        }
        for (int i = 0; i < vaaliliitot.size(); i++) {
            String tempVaaliliitonNimi = (String)vaaliliitot.get(i);
            int liitonVaalirengasId = sqlKyselija.mihinVaalirenkaaseenLiittokuuluu(tempVaaliliitonNimi);
            int vaalirenkaanTunnus = sqlKyselija.kerroVaalirenkaanTunnus(rengasNimi);
            if (liitonVaalirengasId == vaalirenkaanTunnus) {
                sqlKyselija.poistaLiittoVaalirenkaasta(tempVaaliliitonNimi);
            }
            
        }
        sqlKyselija.poistaVaalirengas(rengasNimi);
        ehdokasListanPaivitys();
        vaaliLiittojenPaivitys();
        vaaliRenkaidenPaivitys();
    }

    boolean kuuluukoEhdokasVaalirenkaaseen(int i) {
        LinkedList temp = (LinkedList)ehdokkaat.get(i);
        try {
            Integer arvo = (Integer)temp.get(5);
            if (arvo == 0) {
                return false;
            } else return true;
        } catch (ClassCastException ex) {
            return true;
        }
    }


    public LinkedList haeAanetAanestyspaikoittain(int ehdokkaanOpNro) {
        try {
            return sqlKyselija.haeEhdokkaanAanetAaanestyspaikoittain(ehdokkaanOpNro);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return null;
            
        }
    }

}
