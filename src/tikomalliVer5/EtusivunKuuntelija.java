/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import tikoht.Tikoht;
import tikoht.XMLParseri;

/**
 * Etusivun controller.
 * @author QiD
 */
public class EtusivunKuuntelija implements ActionListener{

    private Etusivu etusivu;
    private Malli malli;
    private Kirjautuminen kirjaudu = null;
    private EhdokasAsettelu ikkuna = null;
    private AanestyspaikanLisays apLisays = null;
    private Aanestys aanestys = null;
    private Tulosikkuna tulosIkkuna = null;
    private EhdokasAsetteluTaulu taulu;
    private TulosTaulu tulosTaulu;
    private AanestyspaikkaTiedot paikkatiedot;
    private JFileChooser avaaja;
    
    public EtusivunKuuntelija(Malli m, Etusivu e) {
        malli = m;
        etusivu = e;
        taulu = malli.kerroTaulu();
        tulosTaulu = malli.kerroTulostaulu();
        avaaja = new JFileChooser();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        /*
         * Kirjautumisnappulaa painettu. Luodaan uusi kirjautumisikkuna ja keskitetään
         * se Keskittäjän avulla. Jos ollaan jo luotu ikkuna laitetaan se vain esille.
         */
        if (e.getSource().equals(etusivu.kirjautumistaPainettu())) {
            if (kirjaudu == null) {
                kirjaudu = new Kirjautuminen(malli);
                Keskittaja.keskita(kirjaudu);
           //     kirjaudu.lataaKirjautumistiedot();
                kirjaudu.setVisible(true);
            } else {
                kirjaudu.setVisible(true);
            }
        }
        /*
         * Ehdokasikkunanappulaa. Luodaan uusi Ehdokasikkuna ja keskitetään
         * se Keskittäjän avulla. Jos ollaan jo luotu ikkuna laitetaan se vain esille.
         */
        if (e.getSource().equals(etusivu.ehdokkaitaPainettu())) {
            if (ikkuna == null) {

             //   taulu = new Taulu(malli);
                
                ikkuna = new EhdokasAsettelu(taulu, malli);
              //  Keskittaja.keskita(ikkuna);
                
                ikkuna.setVisible(true);
                etusivu.lisaaIkkunaTyopydalle(ikkuna);
                ikkuna.toFront();

            } else {
                ikkuna.setVisible(true);
                ikkuna.toFront();
            }
        }
        /*
         * Kaavionluontinappulaa painettu. Luodaan uusi kaavio, jos käyttäjä ei ole vielä kaaviota luonut.
         * Jos ollaan jo luotu kaavio laitetaan se vain esille.
         */
        if (e.getSource().equals(etusivu.luoKaaviotaPainettu())) {
            try {
              //  String nimi = JOptionPane.showInputDialog("Anna kaavion nimi.");
                malli.luoKaavio();
                JOptionPane.showMessageDialog(etusivu, "Tietokantakaavion TikoHt_KK_TR luominen onnistui.");
                malli.kirjautuminenOk();
                
            } catch (SQLException ex) {
                System.out.printf(ex.getMessage());
                JOptionPane.showMessageDialog(etusivu, "Kaavion luominen epäonnistui","Virhe", JOptionPane.WARNING_MESSAGE);
            }
        }
        
        if (e.getSource().equals(etusivu.lisaaAanestyspaikkaPainettu())) {
            if (apLisays == null) {
                apLisays = new AanestyspaikanLisays(malli);
                apLisays.setVisible(true);
                etusivu.lisaaIkkunaTyopydalle(apLisays);
                apLisays.toFront();
            } else {
                apLisays.setVisible(true);
                apLisays.toFront();
            }
        }
        
        if (e.getSource().equals(etusivu.aanestysPainettu())) {
            if (aanestys == null) {
                aanestys = new Aanestys(malli);
                aanestys.setVisible(true);
                etusivu.lisaaIkkunaTyopydalle(aanestys);
                aanestys.toFront();
            } else {
                aanestys.setVisible(true);
                aanestys.toFront();
            }
        }
        
        if (e.getSource().equals(etusivu.tuloksiaPainettu())) {
            if (tulosIkkuna == null) {
                malli.tulosListanPaivitys();
                tulosIkkuna = new Tulosikkuna(tulosTaulu, malli);
                tulosIkkuna.setVisible(true);
                etusivu.lisaaIkkunaTyopydalle(tulosIkkuna);
                tulosIkkuna.toFront();
            } else {
                malli.tulosListanPaivitys();
                tulosIkkuna.setVisible(true);
                tulosIkkuna.toFront();
            }
        }
        
        if (e.getSource().equals(etusivu.lisaaXmlEhdokkaitaPainettu())) {
            XMLParseri parsija = new XMLParseri(malli);
            
            int vastaus = avaaja.showOpenDialog(ikkuna);
            if (vastaus == 0) {
                parsija.luoEhdokas(avaaja.getSelectedFile().getPath());
            }
            
            malli.ehdokasListanPaivitys();
        }
        
        if (e.getSource().equals(etusivu.lisaaXmlVaaliliittoaPainettu())) {
            XMLParseri parsija = new XMLParseri(malli);
            
            int vastaus = avaaja.showOpenDialog(ikkuna);
            if (vastaus == 0) {
                try {
                parsija.luoVaaliliitto(avaaja.getSelectedFile().getPath());
                } catch (Exception egg) {
                    egg.printStackTrace(System.out);
                }
            }
            
            malli.vaaliLiittojenPaivitys();
        }
        
        if (e.getSource().equals(etusivu.lisaaXmlVaalirenkaitaPainettu())) {
            XMLParseri parsija = new XMLParseri(malli);
            
            int vastaus = avaaja.showOpenDialog(ikkuna);
            if (vastaus == 0) {
                try {
                parsija.luoVaalirengas(avaaja.getSelectedFile().getPath());
                } catch (Exception egg) {
                    egg.printStackTrace(System.out);
                }
            }
            
            malli.vaaliRenkaidenPaivitys();
        }
        
        if (e.getSource().equals(etusivu.AanestyspaikkaAanetPainettu())) {
            if (paikkatiedot == null) {
                paikkatiedot = new AanestyspaikkaTiedot(malli);
                paikkatiedot.setVisible(true);
                etusivu.lisaaIkkunaTyopydalle(paikkatiedot);
                paikkatiedot.toFront();
            } else {              
                paikkatiedot.setVisible(true);
                paikkatiedot.toFront();
            }
        }
        
        if (e.getSource().equals(etusivu.lopetaPainettu())) {
            etusivu.dispose();
        }
        
        if (e.getSource().equals(etusivu.poistaKaavioPainettu())) {
            int varmastiko = JOptionPane.showConfirmDialog(etusivu, "Haluatko varmasti poistaa kaavion? Painamalla Ok kaaVio tuhotaan"
                    + " lopullisesti.", "Varoitus", JOptionPane.WARNING_MESSAGE);
            if (varmastiko == 0) {
                System.out.println(" TUHOSIT   ");
                malli.tuhoaKaavio();
                etusivu.asetaKaaviottomuusTilaan();
            }
            
        }
        
    }
}
