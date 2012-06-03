/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;

/**
 *
 * @author Tuomas
 */
public class AanestysPaikka {
    private int tunnus;
    private String osoite;
    private String nimi;
    private int aantenlkm;
    
    public AanestysPaikka(int a, String b, String c, int d) {
        tunnus = a;
        osoite = b;
        nimi = c;
        aantenlkm = d;
    }
    
    public int getTunnus() {
        return tunnus;
    }
    
    public void setTunnus(int a) {
        tunnus = a;
    }
    
    public int getAantenlkm() {
        return aantenlkm;
    }
    
    public void setAantenlkm(int a) {
        aantenlkm = a;
    }
    
    public String getOsoite() {
        return osoite;
    }
    
    public void setOsoite(String a) {
        osoite = a;
    }
    
    public int getNimi() {
        return aantenlkm;
    }
    
    public void setNimi(String a) {
        nimi = a;
    }
}
