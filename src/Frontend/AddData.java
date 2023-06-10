package Frontend;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import Backend.DataBase;
import DataModels.TraverseData;
import Logic.Calculation;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class AddData extends JPanel{
    Connection c;
    ArrayList<TraverseData> data=new ArrayList<TraverseData>();
    JFrame jframe;
    DataBase db;
    //given data
    double initialbearing=0.0;
    Container container;
    JTextField station;
    JTextField observedangle;
    JTextField distance;
    JTextField bearing;
    JButton calculate;
    JButton adddata;
    JButton compute;
    JLabel bearinglabel;
    //label to display message
    JLabel label;
    JLabel instructions;
    JTextField initial_easting;
    JTextField initial_northing;
    JLabel label_easting;
    JLabel label_northing;
    //value to count no of rows in table
    int count=0;
    String[] columns={"station","ObservedAngle","Distance"};
    DefaultTableModel model=new DefaultTableModel(columns,0);

    public AddData(Connection c,JFrame jframe,Container container,DataBase db)
    {
        this.c=c;
        this.jframe=jframe;
        this.container=container;
        this.db=db;
        container.removeAll();
        container.revalidate();

        station=new JTextField(20);
    
        observedangle=new JTextField(20);
        distance=new JTextField(20);
        bearing=new JTextField(20);
        //instructions
        instructions=new JLabel("Enter observed angle in degree,minute,second format");
        container.add(instructions);
        //station input
        JLabel stationlabel=new JLabel("Station");
        container.add(stationlabel);
        container.add(station);

        //initial easting
        label_easting=new JLabel("Initial Easting");
        initial_easting=new JTextField(20);
        container.add(label_easting);
        container.add(initial_easting);

        //initial northing
        label_northing=new JLabel("Initial Northing");
        initial_northing=new JTextField(20);
        container.add(label_northing);
        container.add(initial_northing);

        //observed angle input
        JLabel observedanglelabel=new JLabel("Observed angle");
        container.add(observedanglelabel);
        container.add(observedangle);
        //distance input
        JLabel distancelabel=new JLabel("Distance");
        container.add(distancelabel);
        container.add(distance);
        //bearing input
        bearinglabel=new JLabel("Bearing");
        container.add(bearinglabel);
        container.add(bearing);
        //button canculate
        calculate=new JButton("Calculate");
        container.add(calculate);
        calculate.addActionListener(new CalculateActionListener());

        //button add data
        adddata=new JButton("Add data");
        adddata.addActionListener(new AddActionListener());
        container.add(adddata);

        //label for initial bearing
        if(initialbearing==0.0)
        {
        label=new JLabel("Enter known station values");
        container.add(label);
        }
        //table
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        container.add(scrollPane);
        //at last
        container.setLayout(getLayout());
        jframe.repaint();

        //table
    }



    class AddActionListener implements ActionListener
{   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(initialbearing==0.0)
        {
            String[] values=bearing.getText().split(",");
            double bearingvalue=Calculation.convert_intoDegree(values);
            initialbearing=bearingvalue;
        }
        container.remove(bearing);
        container.remove(bearinglabel);
        container.remove(label);
        container.revalidate();
        container.repaint();

        if(data.size()==0)
        {
            //getting values from textfields
           String[] values= observedangle.getText().split(",");
           double observedangle=Calculation.convert_intoDegree(values);
           TraverseData obj= new TraverseData(Integer.parseInt(station.getText()),observedangle,Double.parseDouble(distance.getText()));
        //    System.out.println("initial bearing"+initialbearing);
           obj.setBearing(initialbearing);
           obj.setEasting(Double.parseDouble(initial_easting.getText()));
           obj.setNorthing(Double.parseDouble(initial_northing.getText()));
           obj.setDel_easting(Calculation.calculate_delEasting(obj.getDistance(),obj.getBearing()));
           obj.setDel_northing(Calculation.calculate_delNorthing(obj.getDistance(),obj.getBearing()));
           data.add(obj);
        //removing fields
        container.remove(bearing);
        container.remove(bearinglabel);
        container.remove(label);
        container.remove(initial_easting);
        container.remove(initial_northing);
        container.remove(label_easting);
        container.remove(label_northing);
        container.revalidate();
        container.repaint();
        }
        else
        {
            String[] values= observedangle.getText().split(",");
            double observedangle=Calculation.convert_intoDegree(values);
            TraverseData ob= new TraverseData(Integer.parseInt(station.getText()),observedangle,Double.parseDouble(distance.getText()));
            data.add(ob);
        }
        for(int i=count;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            // if(x.getBearing()==null)
            // {
            // x.setBearing(Calculation.CalculateBearing(x.getObservedAngle(),initialbearing));
            // }
            Object[] row={x.getStation(),Calculation.get_DMSformat(x.getObservedAngle()),x.getDistance()};
            model.addRow(row);
            count++;
        }


        // throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    
}

class CalculateActionListener implements ActionListener
{   
    @Override
    public void actionPerformed(ActionEvent e) {
        //setting bearing,del_easting and del_northing for individual data
        for(int i=1;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            TraverseData x1=data.get(i-1);
            x.setBearing(Calculation.CalculateBearing(x.getObservedAngle(),x1.getBearing()));
            x.setDel_easting(Calculation.calculate_delEasting(x.getDistance(),x.getBearing()));
            x.setDel_northing(Calculation.calculate_delNorthing(x.getDistance(),x.getBearing()));
            System.out.println("del easting"+x.getDel_easting()+"del northing"+x.getDel_northing());


        }


        //calculating correction values
        Calculation.calculate_correctionEasting(data);
        Calculation.calculate_correctionNorthing(data);
        Calculation.calculate_Easting(data);
        Calculation.calculate_Northing(data);


        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            x.setSn(i+1);
        }
        //inserting data into database
        ViewTable viewtable=new ViewTable(c,jframe,container,"",data,db);

        }
    }

}
