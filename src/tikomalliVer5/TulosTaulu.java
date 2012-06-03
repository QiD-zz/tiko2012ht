/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tikomalliVer5;

import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author QiD
 */
public class TulosTaulu extends AbstractTableModel implements Observer{
    
    private Malli malli;
  /*  private Ikkuna ikkuna;
    private LinkedList rivit;
    private LinkedList riviTiedot;*/
    
    public TulosTaulu(Malli m) {
        super();
        malli = m;
            
        malli.addObserver(this);
        
    }

    @Override
    public void update(Observable o, Object arg) {

        System.out.println("tulos update");

        fireTableChanged(null);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return malli.kerroTulosSarakkeenNimi(columnIndex);
    }

    @Override
    public int getRowCount() {
        return malli.kerroEhdokkaidenLkm();
    }

    @Override
    public int getColumnCount() {
        return malli.kerroTulosSarakeLkm();
    }    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object arvo = malli.kerroTulosTaulunArvo(rowIndex, columnIndex);
        if (arvo == (Object)0) {
            if (columnIndex == 5 || columnIndex == 6) {
                return arvo;
            } else {
                return null;
            }
            
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
