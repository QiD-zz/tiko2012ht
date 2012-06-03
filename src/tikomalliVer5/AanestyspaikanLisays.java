/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JTextField;

/**
 *
 * @author QiD
 */
public class AanestyspaikanLisays extends JInternalFrame implements ActionListener{
 
    private Malli malli;
    private JTextField nimi;
    private JTextField osoite;
    private JButton lisaa;
    
    public AanestyspaikanLisays(Malli m) {
        super("Äänestyspaikan lisäys", false, true, false, true);
        malli = m;
        nimi = new JTextField(16);
        osoite = new JTextField(16);
        nimi.setBorder(BorderFactory.createTitledBorder("Äänestyspaikan nimi"));
        osoite.setBorder(BorderFactory.createTitledBorder("Äänestyspaikan katuosoite"));
        lisaa = new JButton("Lisää äänestyspaikka tietokantaan");
        lisaa.addActionListener(this);
        
        this.setPreferredSize(new Dimension(400, 120));
        getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
        getContentPane().add(nimi);
        getContentPane().add(osoite);
        getContentPane().add(lisaa);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        malli.lisaaAanestyspaikka(nimi.getText(), osoite.getText());
        nimi.setText("");
        osoite.setText("");
    }
}
