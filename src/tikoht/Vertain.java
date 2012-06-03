/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikoht;
import java.util.Comparator;
/**
 *
 * @author Tuomas
 */
public class Vertain implements Comparator{
    
   public Vertain()  {
       
   }
    
   /* verrataan ehdokkaiden vertauslukuja */
   public int compare (Object a, Object b) {
       
        Ehdokas eka = (Ehdokas) a;
        Ehdokas toka = (Ehdokas) b;
        int avertaus = eka.getVertausluku();
        int bvertaus = toka.getVertausluku();
	int tulos = 0;
        if (avertaus < bvertaus)
            tulos = -1;
        else if (avertaus > bvertaus)
            tulos = 1;    
	return 1;
    }
}
