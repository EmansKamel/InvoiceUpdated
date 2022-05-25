
package em.sales.model;

public class Piece {
    private String item;
    private double fee;
    private int amount;
    private Invo invo;

    public Piece() {
    }

    public Piece(String item, double fee, int amount, Invo invo) {
        this.item = item;
        this.fee = fee;
        this.amount = amount;
        this.invo = invo;
    }

    public double getPieceTotal() {
        return fee * amount;
    }
    
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    @Override
    public String toString() {
        return "Line{" + "num=" + invo.getNo() + ", item=" + item + ", price=" + fee + ", count=" + amount + '}';
    }

    public Invo getInvo() {
        return invo;
    }
    
    public String getCSVs() {
        return invo.getNo() + "," + item + "," + fee + "," + amount;
    }
    
}
