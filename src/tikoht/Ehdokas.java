/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;
/**
 *
 * @author Tuomas
 */
public class Ehdokas {
    

    private int ehdokasNumero;
    private int vertausluku;
    private int aanimaara;
    private int opnro;
    private int vaaliliitto;
    private int vaalirengas;
    
    public Ehdokas(int opn, int anniLkm, int vertailu, int liitto, int rengas) {
        opnro = opn;
        aanimaara = anniLkm;
        vertausluku = vertailu;
        vaaliliitto = liitto;
        vaalirengas = rengas;
    }
    
    public void serEhdokasNumero(int i) {
        ehdokasNumero = i;
    }
    
    public int getEhdokasNumero() {
        return ehdokasNumero;
    }
    public void setVertausluku(int i) {
        vertausluku = i;
    }
    
    public void setAanimaara(int i) {
        aanimaara = i;
    }
    
    public void setOpnro(int i) {
        opnro = i;
    }
    
    public void setVaaliliitto(int i) {
        vaaliliitto = i;
    }
    
    public void setVaalirengas(int i) {
        vaalirengas = i;
    }
    
    public int getVertausluku() {
        return vertausluku;
    }
    
    public int getOpnro() {
        return opnro;
    }
    
    public int getAanimaara() {
        return aanimaara;
    }
    
    public int getVaaliliitto() {
        return vaaliliitto;
    }
    
    public int getVaalirengas() {
        return vaalirengas;
    }
}
