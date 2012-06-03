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
import java.util.LinkedList;
import javax.swing.*;

/**
 *
 * @author QiD
 */
public class AanestyspaikkaTiedot extends JInternalFrame implements ActionListener{
    
    private Malli malli;
    private DefaultComboBoxModel komboMalliEhdokkaat;
    private JComboBox komboEhdokkaat;
    private JPanel ylaPalkki;
    private JButton haeAanet;
    private DefaultListModel tulosListanMalli;
    private JList tulosLista;
    private JScrollPane scrollPane;
    private Integer ehdokasNro;
    private int ehdokkaanOpNro;
    
    public AanestyspaikkaTiedot(Malli m) {
        
        
        super("Äänestyspaikkojen äänet", true, true, true, true);
        malli = m;
        
        komboMalliEhdokkaat = new DefaultComboBoxModel();
        LinkedList lista = malli.getEhdokasLista();

        for (int i = 0; i < lista.size(); i++) {
            LinkedList osaLista = new LinkedList();
            osaLista.add(((LinkedList)lista.get(i)).get(0));
            osaLista.add(((LinkedList)lista.get(i)).get(1));
            komboMalliEhdokkaat.addElement(osaLista);
        }
        
           
        komboEhdokkaat = new JComboBox(komboMalliEhdokkaat); 
        
        ylaPalkki = new JPanel(new FlowLayout());
        haeAanet = new JButton("Hae äänet");
        haeAanet.addActionListener(this);
        tulosListanMalli = new DefaultListModel();
       
        tulosLista = new JList(tulosListanMalli);
        tulosLista.setVisibleRowCount(6);
        scrollPane = new JScrollPane(tulosLista);
        //scrollPane.add(tulosLista);
        scrollPane.setPreferredSize(new Dimension(400,200));
        
        ylaPalkki.add(komboEhdokkaat);
       // ylaPalkki.add(komboAanestyspaikat);
        ylaPalkki.add(haeAanet);
        this.getContentPane().setLayout(new BorderLayout());
        this.getContentPane().add(ylaPalkki, BorderLayout.PAGE_START);
        this.getContentPane().add(scrollPane, BorderLayout.CENTER);
        this.setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
        
    }
    
     public void paivitaEhdokkaanTiedot() {
        LinkedList temp = (LinkedList)komboMalliEhdokkaat.getElementAt(komboEhdokkaat.getSelectedIndex());
        ehdokasNro = (Integer)temp.get(0);
        ehdokkaanOpNro = malli.kerroEhdokkaanOpnro(ehdokasNro);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paivitaEhdokkaanTiedot();
        tulosListanMalli.clear();
        String ehdokasNimi = "Ehdokkasta "+malli.kerroEhdokkaanNimi(ehdokkaanOpNro)+" on äänestetty seuraavasti:";
        tulosListanMalli.addElement(ehdokasNimi);
        LinkedList lista = malli.haeAanetAanestyspaikoittain(ehdokkaanOpNro);
        for (int i = 0; i < lista.size(); i++) {
            tulosListanMalli.addElement(lista.get(i));
            
        }

    }
    
}
