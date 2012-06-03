/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author QiD
 */
public class Keskittaja {
    
    public static void keskita(Component c) {
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        c.setLocation((int)dim.getWidth()/2-c.getWidth()/2, (int)dim.getHeight()/2-c.getHeight()/2);
        
    }
    
}
