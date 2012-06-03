/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;

/**
 *
 * @author Tuomas
 */
public class Vaaliliitto {
    
    private int vlnro;
    private String nimi;
    private int aanimaara;
    private int vaalirengas;
    
    public Vaaliliitto(int i, String s, int a, int v) {
        vlnro = i;
        nimi = s;
        aanimaara = a;
        vaalirengas = v;
    }
    
    public int getVlnro() {
        return vlnro;
    }
    
    public void setVlnro(int a) {
        vlnro = a;
    }
    
    public int getAanimaara() {
        return aanimaara;
    }
    
    public void setAanimaara(int a) {
        aanimaara = a;
    }
    
    public int getVaalirengas() {
        return vaalirengas;
    }
    
    public void setVaalirengas(int a) {
        vaalirengas = a;
    }
    
    public String getNimi() {
        return nimi;
    }
    
    public void setNimi(String a) {
        nimi = a;
    }
}
