/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**, TableModelListener
 *, TableModelListener
 * @author QiD
 */
public class EhdokasAsettelu extends JInternalFrame implements TableModelListener, Observer{
    

    private Malli malli;
    private EhdokasAsettelunKuuntelija kontrolli;
  //  private Kirjautuminen kirjaudu;

    
    public EhdokasAsettelu(EhdokasAsetteluTaulu t, Malli m) {
        super("Ehdokasasettelu", true, true, true, true);
        taulumalli = t;
        malli = m;

        initComponents();
        
    /*    malli.ehdokasListanPaivitys();
        malli.vaaliLiittojenPaivitys();*/
        for (int i = 0; i < malli.montakoVaaliliittoa(); i++) {
            liittoMalli.addElement(malli.kerroVaaliliitto(i));            
        }
        for (int i = 0; i < malli.montakoVaalirengasta(); i++) {
            rengasMalli.addElement(malli.kerroVaalirengas(i));            
        }
        malli.addObserver(this);
    }
     
    private void initComponents() {
        
        taulu = new JTable(taulumalli);
        taulumalli.addTableModelListener(this);
        
        kontrolli = new EhdokasAsettelunKuuntelija(malli, this);
        
    /*    mBar = new JMenuBar();
        lisaa = new JMenu("Lisää");
     
        lisaaVL = new JMenuItem("Uusi vaaliliitto");
        lisaaVR = new JMenuItem("Uusi vaalirengas");
        this.setJMenuBar(mBar);*/
        
        vasenLaatikko = Box.createVerticalBox();
        oikeaLaatikko = Box.createVerticalBox();
        
        vasen = new JPanel(new BorderLayout());
        oikea = new JPanel(new BorderLayout());
        
        lisaaVaaliliitto = new JButton("Lisää vaaliliitto");
        lisaaVaaliliitto.addActionListener(kontrolli);
        
        lisaaVaalirengas = new JButton("Lisää vaalirengas");
        lisaaVaalirengas.addActionListener(kontrolli);
        
        poistaEhdokas = new JButton("Poista ehdokas");
        poistaVaaliliitto = new JButton("Poista vaaliliitto");
        poistaVaalirengas = new JButton("Poista vaalirengas");
        
        poistaEhdokas.addActionListener(kontrolli);
        poistaVaaliliitto.addActionListener(kontrolli);
        poistaVaalirengas.addActionListener(kontrolli);
        
    /*    mBar.add(lisaa);
       // lisaa.add(lisaaKaavio);
        lisaa.add(lisaaVL);
        lisaa.add(lisaaVR);*/
        
        
        
    //    lisaaVL.addActionListener(kontrolli);
      //  lisaaVR.addActionListener(kontrolli);
        
        
      //  oikea.setLayout(new BoxLayout(oikea, BoxLayout.Y_AXIS));
        
        ennakkoAanestys = new JButton("Suorita ennakkoäänestys");
        ennakkoAanestys.addActionListener(kontrolli);
        
        ehdokasNro = new JTextField(10);
        ehdokasNro.setBorder(BorderFactory.createTitledBorder("Ehdokkaan numero"));
        
        ehdokasNimi = new JTextField(10);
        ehdokasNimi.setBorder(BorderFactory.createTitledBorder("Ehdokkaan nimi"));
        
        lisaaEhdokas = new JButton("Lisää ehdokas");
        lisaaEhdokas.addActionListener(kontrolli);
        
        lisaaRengasNappi = new JButton("Lisää ehdokas vaalirenkaaseen*");
        lisaaRengasNappi.addActionListener(kontrolli);
                
        lisaaVaaliliittoRenkaaseen = new JButton("Lisää vaaliiliitto vaalirenkaaseen");
        lisaaVaaliliittoRenkaaseen.addActionListener(kontrolli);
        
        mahdollisuus = new JLabel("*Mahdollista vain, jos ehdokas ei kuulu vaaliliittoon.");
        mahdollisuus2 = new JLabel(" Muussa tapauksesssa liitä vaaliliitto kyseiseen vaalirenkaaseen.");        
        
        lisaaLiittoNappi = new JButton("Lisää ehdokas vaaliliittoon");
        lisaaLiittoNappi.addActionListener(kontrolli);
        
        liittoMalli = new DefaultComboBoxModel();
        vaaliLiitot = new JComboBox(liittoMalli);
        vaaliLiitot.setBorder(BorderFactory.createTitledBorder("Vaaliliitot"));
        
        rengasMalli = new DefaultComboBoxModel();
        vaaliRenkaat = new JComboBox(rengasMalli);       
        
        vasenLaatikko.add(lisaaVaaliliitto);
        vasenLaatikko.add(lisaaVaalirengas);
        vasenLaatikko.add(Box.createVerticalStrut(30));
        vasenLaatikko.add(poistaEhdokas);
        vasenLaatikko.add(poistaVaaliliitto);
        vasenLaatikko.add(poistaVaalirengas);
        
        oikeaLaatikko.add(vaaliLiitot);
        oikeaLaatikko.add(lisaaLiittoNappi);
        oikeaLaatikko.add(Box.createVerticalStrut(30));
        oikeaLaatikko.add(vaaliRenkaat);
        oikeaLaatikko.add(lisaaRengasNappi);
        oikeaLaatikko.add(mahdollisuus);
        oikeaLaatikko.add(mahdollisuus2);
        oikeaLaatikko.add(Box.createVerticalStrut(30));
        oikeaLaatikko.add(lisaaVaaliliittoRenkaaseen);
        oikeaLaatikko.add(Box.createVerticalStrut(50));
        oikeaLaatikko.add(ennakkoAanestys);
        oikeaLaatikko.add(Box.createVerticalStrut(80));
        oikeaLaatikko.add(ehdokasNro);
        oikeaLaatikko.add(ehdokasNimi);
        oikeaLaatikko.add(lisaaEhdokas);
        
        oikea.add(oikeaLaatikko);
        vasen.add(vasenLaatikko);
        
        JScrollPane jp = new JScrollPane(taulu);
        
        taulu.setAutoCreateRowSorter(rootPaneCheckingEnabled);
        
        this.getContentPane().setLayout(new BorderLayout());
        
     //   this.getContentPane().add(mBar, BorderLayout.PAGE_START);
        this.getContentPane().add(vasen, BorderLayout.LINE_START);
        this.getContentPane().add(jp, BorderLayout.CENTER);
        this.getContentPane().add(oikea, BorderLayout.LINE_END);

        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.pack();
    }
    
    public Object ehdokasLisaystaPainettu() {
        return lisaaEhdokas;
    }
        
    public Object ennakkoAanestysNappiaPainettu() {
        return ennakkoAanestys;
    }   
        
    public String kerroEhdokasnumero() {
        return ehdokasNro.getText();
    }
    
    public String kerroEhdokkaanNimi() {
        return ehdokasNimi.getText();
    }   
    
    public int kerroValittuRivi() {
        return taulu.getSelectedRow();
    }
    
    public int kerroValittuVaaliliitto() {
        return vaaliLiitot.getSelectedIndex();
    }
    
    public String kerroValitunLiitonNimi() {
        return (String)vaaliLiitot.getSelectedItem();
    }
    
    public int kerroValittuVaalirengas() {
        return vaaliRenkaat.getSelectedIndex();
    }
    
    public String kerroValitunRenkaanNimi() {
        return (String)vaaliRenkaat.getSelectedItem();
    }
    
    public void lisaaLiitto(String str) {
        liittoMalli.addElement(str);
        System.out.print(liittoMalli.getSize());
    }
    
    public Object lisaaRengasNappiaPainettu() {
        return lisaaRengasNappi;
    }
    
    public Object lisaaLiittoNappiaPainettu() {
        return lisaaLiittoNappi;
    }

    public Object lisaaVaaliliittopainettu() {
        return lisaaVaaliliitto;
    }
    
    public Object lisaaVaalirengaspainettu() {
        return lisaaVaalirengas;
    }
    
    public Object lisaaVaaliliittoVaalirenkaaseenPainettu() {
        return lisaaVaaliliittoRenkaaseen;
    }
    
    public Object poistaEhdokasPainettu() {
        return poistaEhdokas;
    }
    
    public Object poistaVaaliliittoPainettu() {
        return poistaVaaliliitto;
    }
    
    public Object poistaVaalirengasPainettu() {
        return poistaVaalirengas;
    }
    
    public void tyhjennaEhdokkaanlisaysKentat() {
        ehdokasNimi.setText("");
        ehdokasNro.setText("");
    }
    
   /* public Malli kysyMallinTiedot() {
        return malli.kerroMalli();
    }
    */
   // public void paivitaTaulu() {
    //   malli.ehdokasListanPaivitys();
  //  }

    @Override
    public void tableChanged(TableModelEvent e) {
     //   taulumalli.update(malli, e);
        System.out.println("tablechanged");
    }

    @Override
    public void update(Observable o, Object arg) {
        
        if (((String)arg).equals("liitto")) {
            System.out.println("tännekin");
            liittoMalli.removeAllElements();
            
            for (int i = 0; i < malli.montakoVaaliliittoa(); i++) {
                liittoMalli.addElement(malli.kerroVaaliliitto(i));            
            }
        }
    
        if (((String)arg).equals("rengas")) {
            rengasMalli.removeAllElements();
            
            for (int i = 0; i < malli.montakoVaalirengasta(); i++) {
                rengasMalli.addElement(malli.kerroVaalirengas(i));            
            }   
        }
    }    
    
    private JTable taulu;
    private EhdokasAsetteluTaulu taulumalli;
    private JButton lisaaEhdokas;
    
    private JButton ennakkoAanestys;
    
    private JLabel mahdollisuus;
    private JLabel mahdollisuus2;
    private JMenuBar mBar;
    private JMenu lisaa;
    private JButton lisaaVaaliliittoRenkaaseen;
    private JMenuItem lisaaVL;
    private JMenuItem lisaaVR;
    private JButton lisaaRengasNappi;
    private JButton lisaaLiittoNappi;
    private Box oikeaLaatikko;
    private Box vasenLaatikko;
    
    private JButton poistaEhdokas;
    private JButton poistaVaaliliitto;
    private JButton poistaVaalirengas;
    
    private JButton lisaaVaalirengas;
    private JButton lisaaVaaliliitto;
    
    private JComboBox vaaliLiitot;
    private JComboBox vaaliRenkaat;
    
    private DefaultComboBoxModel liittoMalli;
    private DefaultComboBoxModel rengasMalli;
    
    private JTextField ehdokasNro;
    private JTextField ehdokasNimi;
    private JPanel oikea;
    private JPanel vasen;
}
