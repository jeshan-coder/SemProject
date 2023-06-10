package Backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import DataModels.LoginModel;
import DataModels.TraverseData;
public class DataBase {
    Connection c;
    LoginModel loginmodel;
    public DataBase(LoginModel loginmodel)
    {
        this.loginmodel=loginmodel;
    }

    public DataBase()
    {

    }
    public Connection setConnection()
    {
        try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres",loginmodel.getUsername(),loginmodel.getPassword());
            return c;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver not found");
            e.printStackTrace();
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Connection not established");
            e.printStackTrace();
        }
        return null;
    }


    public ArrayList<String> getTables(Connection c)
    {   
        ArrayList <String> tables=new ArrayList<String>();
        try{
        String query="SELECT cast(table_name as varchar ) FROM information_schema.tables where table_schema='public';";
        ResultSet rs=c.createStatement().executeQuery(query);
        // int nooftables=0;
        while(rs.next())
        {
            tables.add(rs.getString(1));

        }
        return tables;
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error in fetching tables");
        }

        return null;

    }

    public void createTable(Connection c,String name)
    {
        String query="CREATE TABLE "+name+"(sn SERIAL PRIMARY KEY,station INTEGER,observedAngle DOUBLE PRECISION,Bearing DOUBLE PRECISION,distance DOUBLE PRECISION,del_easting DOUBLE PRECISION,corr_easting DOUBLE PRECISION,del_northing DOUBLE PRECISION,corr_northing DOUBLE PRECISION,easting DOUBLE PRECISION,northing DOUBLE PRECISION);";
        try{

            boolean rs=c.createStatement().execute(query);
            System.out.println("boolen value "+rs);
            if(rs==false)
            {
                JOptionPane.showMessageDialog(null, "Table created");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Table not created");
            }
        }
        catch(PSQLException e)
        {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        catch(SQLException e)
        {
            // System.out.println(e.toString());
            JOptionPane.showMessageDialog(null,e.getMessage());
        }

        
    }





    public ArrayList<TraverseData> openTable(Connection c,String name)
    {
        int sn;
        Integer station;
        Double observedAngle;
        Double Bearing;
        Double distance;
        Double del_easting;
        Double corr_easting;
        Double del_northing;
        Double corr_northing;
        Double easting;
        Double northing;
        ArrayList<TraverseData> data=new ArrayList<TraverseData>();
        String query="SELECT * FROM "+name+";";
        try{
            ResultSet rs=c.createStatement().executeQuery(query);
            int totaldata=0;
            while(rs.next())
            {
                totaldata++;
            }

            for (int i=1;i<=totaldata;i++)
            {
                query="SELECT * FROM "+name+" WHERE sn="+i+";";
                rs=c.createStatement().executeQuery(query);
                while(rs.next())
                {
                    sn=rs.getInt(1);
                    station=rs.getInt(2);
                    observedAngle=rs.getDouble(3);
                    Bearing=rs.getDouble(4);
                    distance=rs.getDouble(5);
                    del_easting=rs.getDouble(6);
                    corr_easting=rs.getDouble(7);
                    del_northing=rs.getDouble(8);
                    corr_northing=rs.getDouble(9);
                    easting=rs.getDouble(10);
                    northing=rs.getDouble(11);
                    TraverseData traverseData=new TraverseData(sn, station, observedAngle, Bearing, distance, del_easting, corr_easting, del_northing, corr_northing, easting, northing);
                    data.add(traverseData);
                }
            }
            return data;

        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error in fetching tables");
        }
        return null;
    }


    public void insertData(Connection c,String name,TraverseData data)
    {
        String query="INSERT INTO "+name+"(station,observedAngle,Bearing,distance,del_easting,corr_easting,del_northing,corr_northing,easting,northing) VALUES("+data.getStation()+","+data.getObservedAngle()+","+data.getBearing()+","+data.getDistance()+","+data.getDel_easting()+","+data.getCorr_easting()+","+data.getDel_northing()+","+data.getCorr_northing()+","+data.getEasting()+","+data.getNorthing()+");";
        try{
            boolean rs=c.createStatement().execute(query);
            if(rs==false)
            {
                JOptionPane.showMessageDialog(null, "Data inserted");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Data not inserted");
            }
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error in inserting data");
        }
    }
}
