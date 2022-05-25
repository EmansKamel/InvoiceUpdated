
package em.sales.model;

import java.util.ArrayList;

public class Invo {
    private int no;
    private String date;
    private String client;
    private ArrayList<Piece> pieces;
    
    public Invo() {
    }

    public Invo(int no, String date, String client) {
        this.no = no;
        this.date = date;
        this.client = client;
    }

    public double getInvoTotal() {
        double sum = 0.0;
        for (Piece piece : getPieces()) {
            sum += piece.getPieceTotal();
        }
        return sum;
    }
    
    public ArrayList<Piece> getPieces() {
        if (pieces == null) {
            pieces = new ArrayList<>();
        }
        return pieces;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Invoice{" + "num=" + no + ", date=" + date + ", customer=" + client + '}';
    }
    
    public String getCSVs() {
        return no + "," + date + "," + client;
    }
    
}
