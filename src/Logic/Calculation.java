package Logic;

import java.text.DecimalFormat;
import java.util.ArrayList;

import DataModels.TraverseData;
public class Calculation {
    TraverseData traverseData;
    ArrayList<TraverseData> traverseDataList;
    public Calculation(ArrayList<TraverseData> traverseDataList)
    {
        this.traverseDataList=traverseDataList;
    }
    {

    }


    public static double CalculateBearing(double observedAngle,double initialbearing)
    {
        double back_bearing;
        if(initialbearing>180)
        {
            back_bearing=initialbearing-180;
        }
        else
        {
            back_bearing=initialbearing+180;
        }

        double Bearing=back_bearing+observedAngle;
        if(Bearing>360)
        {
            Bearing=Bearing-360;
        }

        return Bearing;
    }


    public static double calculate_delEasting(double distance,double Bearing)
    {
        double del_easting=distance*Math.sin(Math.toRadians(Bearing));
        return del_easting;
    }



    public static double calculate_delNorthing(double distance,double Bearing)
    {
        double del_northing=distance*Math.cos(Math.toRadians(Bearing));
        return del_northing;
    }


    public static void calculate_correctionEasting(ArrayList<TraverseData> data)
    {
        double sum_of_delEasting=0;
        double perimeter=0;
        //calculating sum of delEasting
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            System.out.println("------del easting"+x.getDel_easting()+"del northing"+x.getDel_northing());
            sum_of_delEasting=sum_of_delEasting+x.getDel_easting();
            perimeter=perimeter+x.getDistance();
        }

        //use of boudies rule

        if(sum_of_delEasting<0)
        {
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
           double correction=((sum_of_delEasting*x.getDistance())/perimeter)*(-1);
           double corrected=correction+x.getDel_easting();
           x.setCorr_easting(corrected);
        }
    }
    else
    {
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
           double correction=(sum_of_delEasting*x.getDistance())/perimeter;
           double corrected=correction+x.getDel_easting();
           x.setCorr_easting(corrected);
        }
    }
}



    public static void calculate_correctionNorthing(ArrayList<TraverseData> data)

    {
        double sum_of_delNorthing=0;
        double perimeter=0;
        //calculating sum of delNorthing
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            sum_of_delNorthing=sum_of_delNorthing+x.getDel_northing();
            perimeter=perimeter+x.getDistance();
        }


        if(sum_of_delNorthing<0)
        {
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
           double correction=((sum_of_delNorthing*x.getDistance())/perimeter)*(-1);
           double corrected=correction+x.getDel_northing();
           x.setCorr_northing(corrected);
        }
    }
    else{
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
           double correction=(sum_of_delNorthing*x.getDistance())/perimeter;
           double corrected=correction+x.getDel_northing();
           x.setCorr_northing(corrected);
        }
    }

        //user of boudies rule
    }


    public static void calculate_Easting(ArrayList<TraverseData> data)
    {

        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            if(i!=0)
            {
                double easting=data.get(i-1).getCorr_easting()+data.get(i-1).getEasting();
                x.setEasting(easting);
            }
        }
        return;
    }


    public static void calculate_Northing(ArrayList<TraverseData> data)
    {

        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            if(i!=0)
            {
                double northing=data.get(i-1).getCorr_northing()+data.get(i-1).getNorthing();
                x.setNorthing(northing);
            }
        }

        return;
    }


    public static double calculate_closingError(ArrayList<TraverseData> data)
    {

        double sum_of_del_east=0;
        double sum_of_del_north=0;
        double closing_error=0;
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            sum_of_del_east=sum_of_del_east+x.getDel_easting();
            sum_of_del_north=sum_of_del_north+x.getDel_northing();
        }
        closing_error=Math.sqrt(Math.pow(sum_of_del_east,2)+Math.pow(sum_of_del_north,2));
        return closing_error;
    }


    public static String calculate_Accuracy(ArrayList<TraverseData> data)
    {

        double sum_of_del_east=0;
        double sum_of_del_north=0;
        double closing_error=0;
        double perimeter=0;
        for(int i=0;i<data.size();i++)
        {
            TraverseData x=data.get(i);
            sum_of_del_east=sum_of_del_east+x.getDel_easting();
            sum_of_del_north=sum_of_del_north+x.getDel_northing();
            perimeter=perimeter+x.getDistance();
        }
        closing_error=Math.sqrt(Math.pow(sum_of_del_east,2)+Math.pow(sum_of_del_north,2));

        double accuracy=perimeter/closing_error;
        DecimalFormat df = new DecimalFormat("#.###");
        accuracy = Double.parseDouble(df.format(accuracy));
        System.out.println("accuracy"+format_double(accuracy));
        System.out.println("perimeter"+perimeter);
        String accuracyString="1:"+accuracy;
        return accuracyString;
    }


    public static double convert_intoDegree(double degree,double minute,double second)
    {
        double degree_in_decimal=degree+(minute/60)+(second/3600);
        return degree_in_decimal;
    }


    public static double convert_intoDegree(String[] arr)
    {
        double degree=Double.parseDouble(arr[0]);
        double minute=Double.parseDouble(arr[1]);
        double second=Double.parseDouble(arr[2]);
        double degree_in_decimal=degree+(minute/60)+(second/3600);
        return degree_in_decimal;
    }

    public static String get_DMSformat(double degree)
    {
        double degree_int=(int)degree; //60.687 degree 
        double minute=(degree-degree_int)*60; //0.687*60=41.22 minute
        int minute_int=(int)minute;     //41 minute
        double second=(minute-minute_int)*60; //0.22*60=13.2 second
        DecimalFormat df = new DecimalFormat("#.####");
        second = Double.parseDouble(df.format(second));
        String DMSformat=degree_int+"°"+minute_int+"'"+second+"''"; //60°41'13.2''
        return DMSformat;
    }


    public static String format_double(double value)
    {
        DecimalFormat df = new DecimalFormat("#.####");
        value = Double.parseDouble(df.format(value));
        return Double.toString(value);
    }
}
