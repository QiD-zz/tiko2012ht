/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;
import tikoht.Tikoht;

/**
 *
 * @author QiD
 */
public class Aanestys extends JInternalFrame implements ActionListener {
    
    private Malli malli;
    private JTextField opnro;

    private DefaultComboBoxModel komboMalliEhdokkaat;
    private JComboBox komboEhdokkaat;
    private DefaultComboBoxModel komboMalliAanestyspaikat;
    private JComboBox komboAanestyspaikat;
    private JButton aanesta;
    private JTextField aanestajanOpnroKentta;
    
    private int ehdokasNro;
    private int ehdokkaanOpNro;
    private int aanestajanOpNro;
    private int aanestyspaikka;
    
    private String opnroRegex = "^\\d\\d\\d\\d\\d";
    //<>
    public Aanestys(Malli m) {
        super("Äänestys", false, true, false, true);
        malli = m;
        
        komboMalliEhdokkaat = new DefaultComboBoxModel();
        LinkedList lista = malli.getEhdokasLista();
       // LinkedList osaLista = new LinkedList();
     //   LinkedList varalista = new LinkedList();
        for (int i = 0; i < lista.size(); i++) {
          //  varalista.add(lista.get(i));
            LinkedList osaLista = new LinkedList();
            osaLista.add(((LinkedList)lista.get(i)).get(0));
            osaLista.add(((LinkedList)lista.get(i)).get(1));
          //  varalista.add(osaLista);
            komboMalliEhdokkaat.addElement(osaLista);
          //  osaLista.removeAll(osaLista);
        }
        
    /*    for (int i = 0; i < lista.size(); i++) {
            komboMalliEhdokkaat.addElement(lista.get(i));          
        }*/
        
        komboMalliAanestyspaikat = new DefaultComboBoxModel();
        LinkedList aanestysPaikat = malli.getAanestyspaikat();
        
         for (int i = 0; i < aanestysPaikat.size(); i++) {
         String nimi = (String)aanestysPaikat.get(i);
         System.out.println(i);
            komboMalliAanestyspaikat.addElement(nimi);
         
        }
         
        komboAanestyspaikat = new JComboBox(komboMalliAanestyspaikat);       
        komboEhdokkaat = new JComboBox(komboMalliEhdokkaat); 
        
        aanestajanOpnroKentta = new JTextField(15);
        aanestajanOpnroKentta.setBorder(BorderFactory.createTitledBorder("Opiskelijanumerosi"));
        
        aanesta = new JButton("Äänestä");
        aanesta.addActionListener(this);
        
        this.setPreferredSize(new Dimension(300, 120));
        
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));
        getContentPane().add(komboEhdokkaat);
        getContentPane().add(komboAanestyspaikat);
        getContentPane().add(aanestajanOpnroKentta);
        getContentPane().add(aanesta);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        pack();
    }
    
    public boolean kokeileParsiaOpnro() {
        try {
            aanestajanOpNro = Integer.parseInt(aanestajanOpnroKentta.getText());
            return true;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Antamasi opiskelijanumero ei kelvannut", "Virhe!", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    public void paivitaEhdokkaanTiedot() {
        LinkedList temp = (LinkedList)komboMalliEhdokkaat.getElementAt(komboEhdokkaat.getSelectedIndex());
        ehdokasNro = (Integer)temp.get(0);
        Object paikkakunta = komboMalliAanestyspaikat.getSelectedItem();
        aanestyspaikka = malli.kerroAanestysPaikkaTunnus((String)(paikkakunta));
        ehdokkaanOpNro = malli.kerroEhdokkaanOpnro(ehdokasNro);
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        boolean opNroOk = kokeileParsiaOpnro();
        
        
        paivitaEhdokkaanTiedot();
        System.out.println("AAnestyspaikka"+aanestyspaikka);
        if (!malli.onkoAanestanyt(aanestajanOpNro)) {
            if (opNroOk && aanestajanOpnroKentta.getText().matches(opnroRegex)) {
                System.out.println("Päästiinkö tänne?");
                Tikoht tht = new Tikoht(malli);
                tht.lisaaAani(ehdokasNro, ehdokkaanOpNro, aanestyspaikka, aanestajanOpNro);
            } 
        } else {
           JOptionPane.showMessageDialog(this, "Tällä opiskelijanumerolla on jo äänestettu", "Tarkista opiskelijanumero!", JOptionPane.ERROR_MESSAGE); 
        }
           
    }    
}
