/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;

/**
 *
 * @author Tuomas
 */
public class Vaalirengas {
    
    
    private int vrnro;
    private String nimi;
    private int aanimaara;
    
    
    public Vaalirengas(int i, String s, int a) {
        vrnro = i;
        nimi = s;
        aanimaara = a;
        
    }
    
    public int getVrnro() {
        return vrnro;
    }
    
    public void setVrnro(int a) {
        vrnro = a;
    }
    
    public int getAanimaara() {
        return aanimaara;
    }
    
    public void setAanimaara(int a) {
        aanimaara = a;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(String a) {
        nimi = a;
    }
}

