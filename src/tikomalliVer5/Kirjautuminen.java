/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.*;

/**
 *Ohjelman etusivu, jossa ensin kirjaudutaan ja sen jälkeen voi säätää
 * tietoja.
 * @author QiD
 */
public class Kirjautuminen extends JDialog implements ActionListener, KeyListener{
    

    //Ohjelman sisältö löytyy täältä
    private Malli malli;
    
    //kirjautumisikkunan luonti. Annetaan ikkunalle parametrina malli.

    public Kirjautuminen(Malli m) {       
        malli = m;
        initComponents();
        
    }

    //Etusivun ikkunan komponenttien luonti
    private void initComponents() {
        nimi = new JTextField();
        salasana = new JPasswordField();
        
        
        nimi.setBorder(BorderFactory.createTitledBorder("Kirjautumisnimi"));
        salasana.setBorder(BorderFactory.createTitledBorder("Salasana"));
        kirjaudu = new JButton("Kirjaudu sisään");
        kirjaudu.addActionListener(this);      
        kirjaudu.addKeyListener(this);
        
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        this.getContentPane().add(nimi);
        this.getContentPane().add(salasana);
   //     this.getContentPane().add(tallenna);
        this.getContentPane().add(kirjaudu);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(kirjaudu)) {
            malli.maaritaNimi(nimi.getText());
            malli.maaritaSalasana(salasana.getPassword());


            boolean tulikoYhteys = malli.otaYhteys();
        
            if (!tulikoYhteys) {
                JOptionPane.showMessageDialog(kirjaudu, "Kirjautuminen epäonnistui.");
            } else {
                malli.kirjautuminenOk();     

                this.dispose();
            }

        }

    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        kirjaudu.doClick();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

     // Etusivun ikkunan komponentteja
    private JTextField nimi;
    private JPasswordField salasana;
    private JButton kirjaudu;  

 //   private JCheckBox tallenna;
}
