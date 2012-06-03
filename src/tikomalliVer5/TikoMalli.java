/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author QiD
 */
public class TikoMalli {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        Malli m = new Malli();
        Etusivu e = new Etusivu(m);
        
        //asetaan ikkuna keskelle ruutua.
        Keskittaja.keskita(e);
        

        e.setVisible(true);
       
    }
}
