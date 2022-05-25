
package em.sales.model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class PiecesTableMod extends AbstractTableModel {

    private ArrayList<Piece> pieces;
    private String[] pillars = {"No.", "Item Name", "Item Price", "Count", "Item Total"};

    public PiecesTableMod(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }
    
    
    @Override
    public int getRowCount() {
        return pieces.size();
    }

    @Override
    public int getColumnCount() {
        return pillars.length;
    }

    @Override
    public String getColumnName(int x) {
        return pillars[x];
    }
    
    @Override
    public Object getValueAt(int rowIndex, int pillarIndex) {
        Piece line = pieces.get(rowIndex);
        
        switch(pillarIndex) {
            case 0: return line.getInvo().getNo();
            case 1: return line.getItem();
            case 2: return line.getFee();
            case 3: return line.getAmount();
            case 4: return line.getPieceTotal();
            default : return "";
        }
    }
    
}
