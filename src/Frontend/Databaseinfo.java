package Frontend;
import javax.swing.*;
import javax.xml.crypto.Data;

import Backend.DataBase;
import DataModels.TraverseData;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import Backend.DataBase;
public class Databaseinfo extends JPanel{
    ArrayList<String> tables;
    Container container;
    JFrame frame;
    JTextField tablename;
    JButton createtablebutton;
    JButton viewtablebutton;
    Connection c;
    DataBase db;
    public Databaseinfo(Container container,JFrame frame,Connection c,ArrayList<String> tables,DataBase db)
    {
        this.tables=tables;
        this.container=container;
        this.frame=frame;
        this.db=db;
        this.c=c;
        container.removeAll();
        tablename=new JTextField(20);
        createtablebutton=new JButton("new data");
        createtablebutton.addActionListener(new CreateTableListener(c,frame,container,db));
        // createtablebutton.addActionListener(new CreateTableListener(tablename,c));
        viewtablebutton= new JButton("View data");
        viewtablebutton.addActionListener(new ViewTableListener(tablename,c,frame,container,db));
        container.add(tablename);
        container.add(createtablebutton);
        container.add(viewtablebutton);
        if (tables.size()==0)
        {
            JLabel label=new JLabel("No tables found");
            container.add(label);
        }
        else{
        for(int i=0;i<tables.size();i++)
        {
            JLabel table=new JLabel(tables.get(i));
            container.add(table);
        }
    }
    container.revalidate();
    container.setLayout(getLayout());
    frame.repaint();
    }

}


class CreateTableListener implements ActionListener
{   Connection c;
    JFrame jframe;
    Container container;
    DataBase db;

    CreateTableListener(Connection c,JFrame jframe,Container container,DataBase db)
    {
        this.c=c;
        this.jframe=jframe;
        this.container=container;
        this.db=db;
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        AddData adddata=new AddData(c,jframe,container,db);
        // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}


// class CreateTableListener implements ActionListener
// {
//     JTextField tablename;
//     Connection c;
//     public CreateTableListener(JTextField tablename,Connection c)
//     {
//         this.tablename=tablename;
//         this.c=c;
//     }

//     @Override
//     public void actionPerformed(ActionEvent arg0) {
//         DataBase db=new DataBase();
//         db.createTable(c,tablename.getText());
//     }
    
// }


class ViewTableListener implements ActionListener
{
    JTextField tablename;
    Connection c;
    JFrame jframe;
    Container container;
    DataBase db;

    public ViewTableListener(JTextField tablename,Connection c,JFrame jframe,Container container,DataBase db)
    {
        this.tablename=tablename;
        this.c=c;
        this.jframe=jframe;
        this.container=container;
        this.db=db;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        DataBase db=new DataBase();
       ArrayList<TraverseData> data=db.openTable(c, tablename.getText());
       if(data==null)
       {
        return;
       }
         else{
          ViewTable viewtable=new ViewTable(c,jframe,container,tablename.getText(),data,db);
         }
    }
    
}