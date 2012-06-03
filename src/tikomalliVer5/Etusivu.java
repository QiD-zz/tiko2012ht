/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.*;
import java.awt.dnd.DropTarget;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author QiD
 */
public class Etusivu extends JFrame implements Observer{
    
    private JMenuBar mBar;
    private JMenu kirjautuminen;
    private JMenu vaaliToiminnot;
    private JMenu tulosValikko;
    private JMenu lisaa;
    private JMenu poista;
    private JMenuItem kirjaudu;
    private JMenuItem lopeta;
    private JMenuItem ehdokkaat;
    private JMenuItem aanestys;
    private JMenuItem luoKaavio;
    private JMenuItem lisaaAanestyspaikka;
    private JMenuItem tulosIkkunanAvaus;
    private JMenuItem aanestyspaikkaTulokset;
    private JMenuItem lisaaXmlEhdokkaita;
    private JMenuItem poistaKaavio;
    private JDesktopPane desktop;
 /*   private JButton kirjaudu;
    private JButton ehdokkaat;
    private JButton aanestys;
    private JButton luoKaavio;
    private JDesktopPane desktop;
    
    private JPanel yla;
    private JPanel ala;*/
    private EhdokasAsettelu ehdokasasetteluIkkuna;
    private AanestyspaikanLisays apLisays;
    private Aanestys aanestysIkkuna;
    private AanestyspaikkaTiedot paikkaTietoIkkuna;
    private Tulosikkuna tulosIkkuna;
    
    private EtusivunKuuntelija kuuntelija;
    private Malli malli;
    private JMenuItem lisaaXmlVaalipiiri;
    private JMenuItem lisaaXmlVaalirengas;
    
    public Etusivu(Malli m) {
        super("Tikon harjoitustyö. Etusivu.");
                
        malli = m;
        malli.addObserver(this);
        
        initComponents();
        
    }
    
    
    
    private void initComponents() {
        mBar = new JMenuBar();
        kirjautuminen = new JMenu("Kirjaudu");
        vaaliToiminnot = new JMenu("Vaalitoiminnot");
        tulosValikko = new JMenu("Tulokset");
        lisaa = new JMenu("Lue tiedostosta");
        poista = new JMenu("Poista");
        
        kirjaudu = new JMenuItem("Kirjaudu sisään");
        lopeta = new JMenuItem("Lopeta");
        ehdokkaat = new JMenuItem("Ehdokasasettelu");
        aanestys = new JMenuItem("Äänestä");
        luoKaavio = new JMenuItem("Luo uusi kaavio");
        tulosIkkunanAvaus = new JMenuItem("Vaalitulokset");
        aanestyspaikkaTulokset = new JMenuItem("Äänet äänestyspaikoittain");
        lisaaAanestyspaikka = new JMenuItem("Lisää äänestyspaikka");
        lisaaXmlEhdokkaita = new JMenuItem("Lisää ehdokkaita XML-tiedostosta");
        lisaaXmlVaalipiiri = new JMenuItem("Lisää Vaalirenkaita XML-tiedostosta");
        lisaaXmlVaalirengas = new JMenuItem("Lisää Vaalirenkaita XML-tiedostosta");
        poistaKaavio = new JMenuItem("Poista kaavio");
       // Nämä liittyvät internal frame-kokeiluun, joka ei ole nyt käytössä
        desktop = new JDesktopPane();
        desktop.setPreferredSize(new Dimension(1200, 900));
        desktop.setBackground(Color.gray);       
        
        vaaliToiminnot.add(luoKaavio);
        vaaliToiminnot.add(ehdokkaat);
        vaaliToiminnot.add(aanestys);
        vaaliToiminnot.add(lisaaAanestyspaikka);
        kirjautuminen.add(kirjaudu);
        kirjautuminen.add(lopeta);
        tulosValikko.add(tulosIkkunanAvaus);
        tulosValikko.add(aanestyspaikkaTulokset);
        lisaa.add(lisaaXmlEhdokkaita);
        lisaa.add(lisaaXmlVaalipiiri);
        lisaa.add(lisaaXmlVaalirengas);
        poista.add(poistaKaavio);
        mBar.add(kirjautuminen);
        mBar.add(vaaliToiminnot);
        mBar.add(tulosValikko);
        mBar.add(lisaa);
        mBar.add(poista);
        this.setJMenuBar(mBar);
        
        kuuntelija = new EtusivunKuuntelija(malli, this);
        
        kirjaudu.addActionListener(kuuntelija);
        ehdokkaat.addActionListener(kuuntelija);
        luoKaavio.addActionListener(kuuntelija);
        lisaaAanestyspaikka.addActionListener(kuuntelija);
        lopeta.addActionListener(kuuntelija);
        aanestys.addActionListener(kuuntelija);
        tulosIkkunanAvaus.addActionListener(kuuntelija);
        aanestyspaikkaTulokset.addActionListener(kuuntelija);
        lisaaXmlEhdokkaita.addActionListener(kuuntelija);
        lisaaXmlVaalipiiri.addActionListener(kuuntelija);
        lisaaXmlVaalirengas.addActionListener(kuuntelija);
        poistaKaavio.addActionListener(kuuntelija);
        
        vaaliToiminnot.setEnabled(false);
        tulosValikko.setEnabled(false);
        lisaa.setEnabled(false);
        poista.setEnabled(false);
        
        ehdokkaat.setEnabled(false);
        aanestys.setEnabled(false);
        luoKaavio.setEnabled(false);
        lisaaAanestyspaikka.setEnabled(false);
        tulosIkkunanAvaus.setEnabled(false);
        aanestyspaikkaTulokset.setEnabled(false);

        
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(desktop, BorderLayout.CENTER);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.pack();
    }
    
    public void lisaaIkkunaTyopydalle(EhdokasAsettelu i) {
        if (ehdokasasetteluIkkuna == null) {
            ehdokasasetteluIkkuna = i;
            desktop.add(ehdokasasetteluIkkuna);
        }       
    }

    public void lisaaIkkunaTyopydalle(AanestyspaikanLisays apl) {
        if (apLisays == null) {
            apLisays = apl;
            desktop.add(apLisays);
        }       
    }
    
    public void lisaaIkkunaTyopydalle(Aanestys a) {
        if (aanestysIkkuna == null) {
            aanestysIkkuna = a;
            desktop.add(aanestysIkkuna);
        }       
    }
    
    public void lisaaIkkunaTyopydalle(Tulosikkuna a) {
        if (tulosIkkuna == null) {
            tulosIkkuna = a;
            desktop.add(tulosIkkuna);
        }       
    }
    
    public void lisaaIkkunaTyopydalle(AanestyspaikkaTiedot a) {
        if (paikkaTietoIkkuna == null) {
            paikkaTietoIkkuna = a;
            desktop.add(paikkaTietoIkkuna);
        }       
    }
    
    public Object kirjautumistaPainettu() {
        return kirjaudu;
    }
    
    public Object ehdokkaitaPainettu() {
        return ehdokkaat;
    }
    
    public Object luoKaaviotaPainettu() {
        return luoKaavio;
    }
    
    public Object lisaaAanestyspaikkaPainettu() {
        return lisaaAanestyspaikka;
    }
    
    public Object aanestysPainettu() {
        return aanestys;
    }
    
    public Object lopetaPainettu() {
        return lopeta;
    }
    
    public Object tuloksiaPainettu() {
        return tulosIkkunanAvaus;
    }
    
    public Object poistaKaavioPainettu() {
        return poistaKaavio;
    }
    
    public Object lisaaXmlEhdokkaitaPainettu() {
        return lisaaXmlEhdokkaita;
    }
    
    public Object lisaaXmlVaaliliittoaPainettu() {
        return lisaaXmlVaalipiiri;
    }
    
    public Object lisaaXmlVaalirenkaitaPainettu() {
        return lisaaXmlVaalirengas;
    }
    
    public Object AanestyspaikkaAanetPainettu() {
        return aanestyspaikkaTulokset;
    }
    
    public void kirjauduttu(String str) {
        if (str.equals("kaavio on")) {
            luoKaavio.setEnabled(false);
            ehdokkaat.setEnabled(true);
            aanestys.setEnabled(true);
            lisaaAanestyspaikka.setEnabled(true);
            tulosIkkunanAvaus.setEnabled(true);
            vaaliToiminnot.setEnabled(true);
            tulosValikko.setEnabled(true);
            aanestyspaikkaTulokset.setEnabled(true);
            lisaaXmlEhdokkaita.setEnabled(true);
            lisaa.setEnabled(true);
            poista.setEnabled(true);
        }
        
        if (str.equals("ei kaaviota")) {
            luoKaavio.setEnabled(true);
            vaaliToiminnot.setEnabled(true);
        }            
    }
    
    @Override
    public void update(Observable o, Object arg) {
        if (((String)arg).equals("kaavio on")) {
            kirjauduttu((String)arg);
        }
        
         if (((String)arg).equals("ei kaaviota")) {
            kirjauduttu((String)arg);
        }
    }

    public void asetaKaaviottomuusTilaan() {
        ehdokkaat.setEnabled(false);
            aanestys.setEnabled(false);
            lisaaAanestyspaikka.setEnabled(false);
            tulosIkkunanAvaus.setEnabled(false);
            vaaliToiminnot.setEnabled(false);
            tulosValikko.setEnabled(false);
            aanestyspaikkaTulokset.setEnabled(true);
            lisaaXmlEhdokkaita.setEnabled(false);
            lisaa.setEnabled(false);
            poista.setEnabled(false);
       
            luoKaavio.setEnabled(true);
            vaaliToiminnot.setEnabled(true);
    }

}
