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
import tikoht.Tikoht;

/**, TableModelListener
 *, TableModelListener
 * @author QiD
 */
public class Tulosikkuna extends JInternalFrame implements TableModelListener, Observer, ActionListener{
    

    private Malli malli;
    private EhdokasAsettelunKuuntelija kontrolli;
    private JTable taulu;
    private TulosTaulu taulumalli;
  //  private Kirjautuminen kirjaudu;

    
    public Tulosikkuna(TulosTaulu t, Malli m) {
        super("Tulosikkuna", true, true, true, true);
        taulumalli = t;
        malli = m;

        initComponents();
        
        malli.addObserver(this);
    }
     
    private void initComponents() {
        
        taulu = new JTable(taulumalli);
        taulumalli.addTableModelListener(this);
        
        JButton button = new JButton("Päivitä tuloslista");
        button.addActionListener(this);
        
        JScrollPane jp = new JScrollPane(taulu);
        
        taulu.setAutoCreateRowSorter(rootPaneCheckingEnabled);
        
        this.getContentPane().setLayout(new BorderLayout());
        
        this.getContentPane().add(jp, BorderLayout.CENTER);
        this.getContentPane().add(button, BorderLayout.PAGE_END);

        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.getContentPane().setPreferredSize(new Dimension(1000,750));
        this.pack();
    }
    

    @Override
    public void tableChanged(TableModelEvent e) {
     //   taulumalli.update(malli, e);
        System.out.println("tablechanged");
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      //  Tikoht tht = new Tikoht(malli);
        malli.tulosListanPaivitys();
                
    }
    
    
}
