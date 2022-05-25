package em.sales.controller;

import em.sales.model.Invo;
import em.sales.model.InvosTableMod;
import em.sales.model.Piece;
import em.sales.model.PiecesTableMod;
import em.sales.view.InvoDialog;
import em.sales.view.InvoFrame;
import em.sales.view.PieceDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Controller implements ActionListener, ListSelectionListener {

    private InvoFrame frame;
    private InvoDialog invoDialog;
    private PieceDialog pieceDialog;

    public Controller(InvoFrame shape) {
        this.frame = shape;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actCommand = e.getActionCommand();
        System.out.println("Action: " + actCommand);
        switch (actCommand) {
            case "Load File":
                loadFiles();                // load file dropdown menu
                break;
            case "Save File":
                saveFiles();                // save file dropdown menu
                break;
            case "Create New Invoice":
                createNewInvo();            // create new invo button
                break;
            case "Delete Invoice":
                deleteInvo();               // delete invo button
                break;
            case "Create New Item":
                createNewPiece();               // create new item button
                break;
            case "Delete Item":
                deletePiece();                  // delete item button
                break;
            case "createInvoiceCancel":
                createInvoCancel();             //create invo cancel button
                break;
            case "createInvoiceOK":
                createInvoOK();               //create new invo OK button
                break;
            case "createLineOK":
                createPieceOK();                //create new item OK button
                break;
            case "createLineCancel":
                createPieceCancel();            //cancel create new item
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selIndex = frame.getInvoiceTable().getSelectedRow();
        if (selIndex != -1) {
            System.out.println("You have selected row: " + selIndex);
            Invo curentInvo = frame.getInvos().get(selIndex);
            frame.getInvoiceNumLabel().setText("" + curentInvo.getNo());
            frame.getInvoiceDateLabel().setText(curentInvo.getDate());
            frame.getClientNameLabel().setText(curentInvo.getClient());
            frame.getInvoiceTotalLabel().setText("" + curentInvo.getInvoTotal());
            PiecesTableMod piecesTableMod = new PiecesTableMod(curentInvo.getPieces());
            frame.getLineTable().setModel(piecesTableMod);
            piecesTableMod.fireTableDataChanged();
        }
    }

    private void loadFiles() {
        JFileChooser fchoos = new JFileChooser();
        try {
            int data = fchoos.showOpenDialog(frame);
            if (data == JFileChooser.APPROVE_OPTION) {
                File clientFile = fchoos.getSelectedFile();
                Path clientPath = Paths.get(clientFile.getAbsolutePath());
                List<String> clientPieces = Files.readAllLines(clientPath);
                System.out.println("Invoices have been read");
                // 1,22-11-2020,Ali
                // 2,13-10-2021,Saleh
                // 3,09-01-2019,Ibrahim
                ArrayList<Invo> invosArray = new ArrayList<>();
                for (String clientPiece : clientPieces) {
                    String clientsValues [] = clientPiece.split(",");
                    int invoNo = Integer.parseInt(clientsValues[0]);
                    String invoDate = clientsValues[1];
                    String clientName = clientsValues[2];

                    Invo invo = new Invo(invoNo, invoDate, clientName);
                    invosArray.add(invo);
                }
                System.out.println("Check point");
                data = fchoos.showOpenDialog(frame);
                if (data == JFileChooser.APPROVE_OPTION) {
                    File pieceFile = fchoos.getSelectedFile();
                    Path piecePath = Paths.get(pieceFile.getAbsolutePath());
                    List<String> piecePieces = Files.readAllLines(piecePath);
                    System.out.println("Lines have been read");
                    for (String piecePiece : piecePieces) {
                        String lineParts[] = piecePiece.split(",");
                        int invoNo = Integer.parseInt(lineParts[0]);
                        String itemName = lineParts[1];
                        double itemFee = Double.parseDouble(lineParts[2]);
                        int numeration = Integer.parseInt(lineParts[3]);
                        Invo inv = null;
                        for (Invo invoice : invosArray) {
                            if (invoice.getNo() == invoNo) {
                                inv = invoice;
                                break;
                            }
                        }

                        Piece piece = new Piece(itemName, itemFee, numeration, inv);
                        inv.getPieces().add(piece);
                    }
                    System.out.println("Check point");
                }
                frame.setInvos(invosArray);
                InvosTableMod invosTableMod = new InvosTableMod(invosArray);
                frame.setInvoicesTableModel(invosTableMod);
                frame.getInvoiceTable().setModel(invosTableMod);
                frame.getInvoicesTableModel().fireTableDataChanged();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void saveFiles() {
        ArrayList<Invo> invos = frame.getInvos();
        String clients = "";
        String pieces = "";
        for (Invo invoice : invos) {
            String invCSV = invoice.getCSVs();
            clients += invCSV;
            clients += "\n";

            for (Piece line : invoice.getPieces()) {
                String lineCSV = line.getCSVs();
                pieces += lineCSV;
                pieces += "\n";
            }
        }
        System.out.println("Check point");
        try {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(clients);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(pieces);
                    lfw.flush();
                    lfw.close();
                }
            }
        } catch (Exception ex) {

        }
    }

    private void createNewInvo() {
        invoDialog = new InvoDialog(frame);
        invoDialog.setVisible(true);
    }

    private void deleteInvo() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvos().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewPiece() {
        pieceDialog = new PieceDialog(frame);
        pieceDialog.setVisible(true);
    }

    private void deletePiece() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            PiecesTableMod linesTableModel = (PiecesTableMod) frame.getLineTable().getModel();
            linesTableModel.getPieces().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoCancel() {
        invoDialog.setVisible(false);
        invoDialog.dispose();
        invoDialog = null;
    }

    private void createInvoOK() {
        String date = invoDialog.getInvDateField().getText();
        String customer = invoDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();

        Invo invoice = new Invo(num, date, customer);
        frame.getInvos().add(invoice);
        frame.getInvoicesTableModel().fireTableDataChanged();
        invoDialog.setVisible(false);
        invoDialog.dispose();
        invoDialog = null;
    }

    private void createPieceOK() {
        String item = pieceDialog.getItemNameField().getText();
        String countStr = pieceDialog.getItemCountField().getText();
        String priceStr = pieceDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            Invo invoice = frame.getInvos().get(selectedInvoice);
            Piece line = new Piece(item, price, count, invoice);
            invoice.getPieces().add(line);
            PiecesTableMod linesTableModel = (PiecesTableMod) frame.getLineTable().getModel();
            //linesTableModel.getLines().add(piece);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        pieceDialog.setVisible(false);
        pieceDialog.dispose();
        pieceDialog = null;
    }

    private void createPieceCancel() {
        pieceDialog.setVisible(false);
        pieceDialog.dispose();
        pieceDialog = null;
    }

}
