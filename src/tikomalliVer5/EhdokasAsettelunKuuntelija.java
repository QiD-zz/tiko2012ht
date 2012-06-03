/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import tikoht.*;

/**
 *
 * @author QiD
 */
public class EhdokasAsettelunKuuntelija implements ActionListener {
    
    private Malli malli;
    private EhdokasAsettelu ikkuna;
    private Kirjautuminen kirjaudu;
    private String liittoNimi;
    
    private String opnroRegex = "^\\d\\d\\d\\d\\d";
    private String rengasNimi;
    
    public EhdokasAsettelunKuuntelija(Malli m, EhdokasAsettelu i) {
        malli = m;
        ikkuna = i;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    /*     try {
        if (e.getSource().equals(ikkuna.nappi())) {
            ikkuna.lisaaLiitto("juu");
            System.out.print("täällä");
        }
        } catch (NullPointerException ex) {  }
        System.out.print(e.getActionCommand());*/

 //       try {
            if (e.getSource().equals(ikkuna.ehdokasLisaystaPainettu())) {           
                if (ikkuna.kerroEhdokasnumero().matches(opnroRegex) ) {                       
                    malli.lisaaEhdokas(ikkuna.kerroEhdokkaanNimi(), Integer.parseInt(ikkuna.kerroEhdokasnumero()));                         
                    malli.ehdokasListanPaivitys();
                    ikkuna.tyhjennaEhdokkaanlisaysKentat();
                }       
            }
     //   } catch (NullPointerException ex) {  }
        
     //    try {
      
             if (e.getSource().equals(ikkuna.lisaaVaaliliittopainettu())) {
       
                  liittoNimi = JOptionPane.showInputDialog(ikkuna, "Anna uuden vaaliliiton nimi", null);
                 
                 if (liittoNimi != null) {

                     malli.lisaaVaaliliitto(liittoNimi);
                 }
      
             }
             
             if (e.getSource().equals(ikkuna.lisaaVaalirengaspainettu())) {
       
                  rengasNimi = JOptionPane.showInputDialog(ikkuna, "Anna uuden vaalirenkaan nimi", null);
                 
                 if (rengasNimi != null) {

                     malli.lisaaVaalirengas(rengasNimi);
                 }
      
             }
             
             if (e.getSource().equals(ikkuna.lisaaLiittoNappiaPainettu())) {
                 try {
                     int indeksi = ikkuna.kerroValittuRivi();
                 int vlNumero = malli.kerroVaaliliitto(ikkuna.kerroValitunLiitonNimi());
                 if (malli.kuuluukoEhdokasVaalirenkaaseen(indeksi)) {
                     JOptionPane.showMessageDialog(ikkuna, "Ehdokas kuuluu jo vaalirenkaaseen, joten hän ei voi kuulua itsekseen vaaliliittoon.\n"
                                 + "Vihje: lisää vaaliliitto vaalirenkaaseen liittääksesi\n"
                                 + " tämän ehdokkaan kyseiseen vaaliliittoon", null, JOptionPane.WARNING_MESSAGE);
                 } else {
                     malli.lisaaEhdokasVaaliliittoon(indeksi, vlNumero);
                 }
                 
                 } catch (NullPointerException ex) {
                    ex.printStackTrace(System.out);
                 }
                 
                 
             }
             
             if (e.getSource().equals(ikkuna.lisaaRengasNappiaPainettu())) {
                 try {
                     int indeksi = ikkuna.kerroValittuRivi();
                     int vlNumero = malli.kerroVaalirengas(ikkuna.kerroValitunRenkaanNimi());
                     if (malli.kuuluukoEhdokasVaaliliittoon(indeksi)) {
                         JOptionPane.showMessageDialog(ikkuna, "Ehdokas kuuluu jo vaaliliittoon, joten hän ei voi kuulua itsekseen vaalirenkaaseen.\n"
                                 + "Vihje: lisää ehdokkaan vaaliliitto vaalirenkaaseen liittääksesi\n"
                                 + " tämän ehdokkaan kyseiseen vaalirenkaaseen", null, JOptionPane.WARNING_MESSAGE);
                     } else {
                         malli.lisaaEhdokasVaalirenkaaseen(indeksi, vlNumero);
                     }
                     
                 } catch (NullPointerException ex) {
                     System.out.println(ex.getMessage());
                 }
                 
                 
             }
             
             if (e.getSource().equals(ikkuna.ennakkoAanestysNappiaPainettu())) {
                // try {
                     Tikoht tht = new Tikoht(malli);
                     tht.ennakkoAanestys();
                     malli.ehdokasListanPaivitys();
            //     } catch (SQLException ex) {
              //       System.out.println(ex.getMessage());
              //   }
                 
                 
             }
             
             if (e.getSource().equals(ikkuna.lisaaVaaliliittoVaalirenkaaseenPainettu())) {
                 int valittuLiittoIndeksi = ikkuna.kerroValittuVaaliliitto();
                 int valittuRengasIndeksi = ikkuna.kerroValittuVaalirengas();
                 
                 if (valittuRengasIndeksi != -1 && valittuLiittoIndeksi != -1) {
                     malli.lisaaVaaliliittoVaalirenkaaseen(malli.kerroVaaliliitto((ikkuna.kerroValitunLiitonNimi())), malli.kerroVaalirengas(ikkuna.kerroValitunRenkaanNimi()));
                 } else {
                     JOptionPane.showMessageDialog(ikkuna, "Vaaliliitto tai vaalirengas ei ollut valittuna", "Varoitus", JOptionPane.WARNING_MESSAGE);
                 }               
             }
             
             if (e.getSource().equals(ikkuna.poistaEhdokasPainettu())) {
                 String opnro = JOptionPane.showInputDialog(ikkuna,"Anna ehdokkaan opiskelijanumero","Poista ehdokas", JOptionPane.INFORMATION_MESSAGE);
                 if (opnro != null) {
                     malli.poistaEhdokas(opnro);
                 }
                
             }
             
             if (e.getSource().equals(ikkuna.poistaVaaliliittoPainettu())) {
                 String nimi = JOptionPane.showInputDialog(ikkuna,"Anna vaaliliiton nimi","Poista vaaliliitto", JOptionPane.INFORMATION_MESSAGE);
                 if (nimi != null) {
                     malli.poistaVaaliliitto(nimi);
                 }                
             }
             
             if (e.getSource().equals(ikkuna.poistaVaalirengasPainettu())) {
                 String nimi = JOptionPane.showInputDialog(ikkuna,"Anna vaalirenkaan nimi","Poista vaaliliitto", JOptionPane.INFORMATION_MESSAGE);
                 if (nimi != null) {
                     malli.poistaVaalirengas(nimi);
                 } 
             }
             
     //   } catch (NullPointerException ex) { System.out.print("täälläkö?"); }
    }
}
