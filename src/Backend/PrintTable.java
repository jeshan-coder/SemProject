package Backend;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import DataModels.TraverseData;
import Frontend.ViewTable;
import Logic.Calculation;
public class PrintTable implements ActionListener{
    ArrayList<TraverseData>data;
    String filepath;
    double closingerror;
    String accuracy;
    public PrintTable(ArrayList<TraverseData>data)
    {
        // String filepath=JOptionPane.showInputDialog("Enter the path of the file");
        filepath="D:\\table.pdf";
        this.data=data;
        closingerror=Calculation.calculate_closingError(data);
        accuracy=Calculation.calculate_Accuracy(data);
        System.out.println(closingerror);
        System.out.println(accuracy);

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        try{

            // Create a new document
            Document document = new Document(PageSize.A3);

            // Create a PDF writer instance
            PdfWriter.getInstance(document, new FileOutputStream(this.filepath));

            // Open the document
            document.open();

            // Add a title to the document
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, Font.BOLD);
            Paragraph title = new Paragraph("KATHMANDU UNIVERSITY", titleFont);
            Paragraph title1 = new Paragraph("DEPARTMENT OF GEOMATICS ENGINEERING", titleFont);
            Paragraph title2 = new Paragraph("Traverse Computation Form", titleFont);
            Paragraph space= new Paragraph(" ");
            Paragraph closingerror= new Paragraph("Closing Error: "+Calculation.format_double(this.closingerror));
            Paragraph accuracy= new Paragraph("Accuracy: "+this.accuracy);
            Paragraph credit= new Paragraph("Designed by");
            Paragraph credit1= new Paragraph("Jeshan Pokharel (41) \n Nischal Acharya (01) \n Sagar Pathak (41) \n Dip Kiran Tiwari (55)");

            title.setAlignment(Element.ALIGN_CENTER);
            title1.setAlignment(Element.ALIGN_CENTER);
            title2.setAlignment(Element.ALIGN_CENTER);
            credit.setAlignment(Element.ALIGN_RIGHT);
            credit1.setAlignment(Element.ALIGN_RIGHT);
            closingerror.setAlignment(Element.ALIGN_LEFT);
            accuracy.setAlignment(Element.ALIGN_LEFT);
            document.add(title);
            document.add(title1);
            document.add(title2);
            document.add(space);

            // Create a table with 11 columns
            PdfPTable table = new PdfPTable(11);
            table.setWidthPercentage(99.5f);

            // Add table headers
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,10);
            PdfPCell headerCell;
            for (int i = 0; i <11; i++) {
                headerCell = new PdfPCell(new Phrase(ViewTable.columns[i], headerFont));
                table.addCell(headerCell);
            }

            // Add table rows
            Font cellFont = FontFactory.getFont(FontFactory.HELVETICA,10);
            PdfPCell cell;
            for (int row = 0; row < data.size(); row++) {
                for (int col = 1; col <=11; col++) {
                    int value=col;
                    switch(value) {
                        case 1:
                        cell = new PdfPCell(new Phrase(data.get(row).getSn().toString(), cellFont));
                        
                          // code block
                          break;
                        case 3:
                        
                        cell = new PdfPCell(new Phrase(Calculation.get_DMSformat(data.get(row).getObservedAngle()), cellFont));
                          break;
                        
                        case 4:
                        cell = new PdfPCell(new Phrase(Calculation.get_DMSformat(data.get(row).getBearing()), cellFont));
                          break;
                        
                        case 5:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getDistance()), cellFont));
                          break;
                        
                        case 6:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getDel_easting()), cellFont));
                          break;
                        
                        case 7:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getCorr_easting()), cellFont));
                          break;
                        
                        case 8:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getDel_northing()), cellFont));
                          break;
                        
                        case 9:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getCorr_northing()), cellFont));
                          break;
                        
                        case 10:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getEasting()), cellFont));
                          break;
                        
                        case 11:
                        cell = new PdfPCell(new Phrase(Calculation.format_double(data.get(row).getNorthing()), cellFont));
                          break;
                        
                        case 2:
                        cell = new PdfPCell(new Phrase(data.get(row).getStation().toString(), cellFont));
                          break;
                        default:
                        cell = new PdfPCell(new Phrase(" ", cellFont));
                          // code block
                      }
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }
            }

            // Add the table to the document
            document.add(table);
            document.add(space);
            document.add(credit);
            document.add(credit1);
            document.add(closingerror);
            document.add(accuracy);

            // Close the document
            document.close();

            JOptionPane.showMessageDialog(null,"PDF created successfully.");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
