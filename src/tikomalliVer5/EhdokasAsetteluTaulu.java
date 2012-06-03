/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**TableModel,
 * 
 * @author QiD
 */
public class EhdokasAsetteluTaulu extends AbstractTableModel implements Observer{
    
    private Malli malli;
  /*  private Ikkuna ikkuna;
    private LinkedList rivit;
    private LinkedList riviTiedot;*/
    
    public EhdokasAsetteluTaulu(Malli m) {
        super();
        malli = m;
     
        malli.addObserver(this);
        
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("ehdokas update");

        fireTableChanged(null);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return malli.kerroSarakkeenNimi(columnIndex);
    }

    @Override
    public int getRowCount() {
        return malli.kerroEhdokkaidenLkm();
    }

    @Override
    public int getColumnCount() {
        return malli.kerroSarakeLkm();
    }    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object arvo = malli.kerroEhdokasTaulunArvo(rowIndex, columnIndex);
        if (arvo == (Object)0) {
            return null;
        } else {
            return arvo;
        }
      /*  if (arvo == 0) {
            return null;
        } else {
            return arvo;
        }*/
     //   return malli.kerroArvo(rowIndex, columnIndex);

     //   LinkedList temp = (LinkedList)rivit.get(rowIndex);
       //  return temp.get(columnIndex);
    }
}
