
package em.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class InvosTableMod extends AbstractTableModel {
    private ArrayList<Invo> invos;
    private String[] pillars = {"No.", "Date", "Customer", "Total"};
    
    public InvosTableMod(ArrayList<Invo> invos) {
        this.invos = invos;
    }
    
    @Override
    public int getRowCount() {
        return invos.size();
    }

    @Override
    public int getColumnCount() {
        return pillars.length;
    }

    @Override
    public String getColumnName(int pillar) {
        return pillars[pillar];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int pillarIndex) {
        Invo invo = invos.get(rowIndex);
        
        switch (pillarIndex) {
            case 0: return invo.getNo();
            case 1: return invo.getDate();
            case 2: return invo.getClient();
            case 3: return invo.getInvoTotal();
            default : return "";
        }
    }
}
