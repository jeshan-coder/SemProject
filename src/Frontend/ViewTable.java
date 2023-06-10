package Frontend;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Backend.DataBase;
import Backend.PrintTable;
import DataModels.TraverseData;
import Logic.Calculation;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
public class ViewTable extends JPanel{
    ArrayList<TraverseData> data;
    Connection c;
    JFrame jframe;
    JButton save;
    Container container;
    String table_name;
    DataBase db;
    public final static String[] columns={"sn","station","ObservedAngle","Bearings","Distance","del_easting","corr_easting","del_northing","corr_northing","easting","northing"};
    DefaultTableModel model=new DefaultTableModel(columns,0);
    public ViewTable(Connection c,JFrame jframe,Container container,String table_name,ArrayList<TraverseData> data,DataBase db)
    {
        this.data=data;
        this.c=c;
        this.jframe=jframe;
        this.db=db;
        this.container=container;
        this.table_name=table_name;
        container.removeAll();
        JButton print=new JButton("Print");
        print.addActionListener(new PrintTable(data));
       

        save=new JButton("Save");
        save.addActionListener(new SaveActionListener());
    /*--------------------------------------------------- */
    for(TraverseData x:data)
    {
        Object[] row={x.getSn(),x.getStation(),Calculation.get_DMSformat(x.getObservedAngle()),Calculation.get_DMSformat(x.getBearing()),Calculation.format_double(x.getDistance()),Calculation.format_double(x.getDel_easting()),Calculation.format_double(x.getCorr_easting()),Calculation.format_double(x.getDel_northing()),Calculation.format_double(x.getCorr_northing()),Calculation.format_double(x.getEasting()),Calculation.format_double(x.getNorthing())};
        model.addRow(row);
    }
    JTable table = new JTable(model);
    JScrollPane scrollPane = new JScrollPane(table);
    container.add(scrollPane);
    container.add(print);
    container.add(save);
    /*---------------------------------------------------------- */
        container.revalidate();
        container.setLayout(getLayout());
        jframe.repaint();
    }


    class SaveActionListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent arg0) {

            table_name=JOptionPane.showInputDialog("Enter the name of the table");
                db.createTable(c,table_name);
                for(TraverseData x:data)
                {
                    db.insertData(c, table_name, x);
                }
                JOptionPane.showMessageDialog(null, "Data saved");
        }
        
    }

}
