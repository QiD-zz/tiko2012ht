/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;

/**
 *
 * @author Tuomas
 */
public class Aanestaja {
    private int opnro;
    private int aanestyspaikka;
    
    public Aanestaja(int a, int b) {
        opnro = a;
        aanestyspaikka = b;
    }
    
    public int getOpnro() {
        return opnro;
    }
    
    public int getAanestyspaikka() {
        return aanestyspaikka;
    }
    
    public void setOpnro(int a) {
        opnro = a;
    }
    
    public void setAanestyspaikka(int a) {
        aanestyspaikka = a;
    }
}
