package DataModels;
public class TraverseData {
    // HashMap<Integer,HashMap<String,Double>> map=new HashMap<Integer,HashMap<String,Double>>();
    Integer sn;
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
    
    public TraverseData(Integer station,Double observedAngle,Double distance)
    {
        this.station=station;
        this.observedAngle=observedAngle;
        this.distance=distance;
    }
    public TraverseData(Integer sn,Integer station,Double observedAngle,Double Bearing,Double distance,Double del_easting,Double corr_easting,Double del_northing,Double corr_northing,Double easting,Double northing)
    {
        this.sn=sn;
        this.station=station;
        this.observedAngle=observedAngle;
        this.Bearing=Bearing;
        this.distance=distance;
        this.del_easting=del_easting;
        this.corr_easting=corr_easting;
        this.del_northing=del_northing;
        this.corr_northing=corr_northing;
        this.easting=easting;
        this.northing=northing;
    }

    //getter and setter methods
    public Integer getSn() {
        return sn;
    }

    public void setSn(Integer sn) {
        this.sn = sn;
    }

    public Integer getStation() {
        return station;
    }

    public void setStation(Integer station) {
        this.station = station;
    }

    public Double getObservedAngle() {
        return observedAngle;
    }

    public void setObservedAngle(Double observedAngle) {
        this.observedAngle = observedAngle;
    }

    public Double getBearing() {
        return Bearing;
    }

    public void setBearing(Double bearing) {
        Bearing = bearing;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }


    public Double getDel_easting() {
        return del_easting;
    }

    public void setDel_easting(Double del_easting) {
        this.del_easting = del_easting;
    }

    public Double getCorr_easting() {
        return corr_easting;
    }

    public void setCorr_easting(Double corr_easting) {
        this.corr_easting = corr_easting;
    }

    public Double getDel_northing() {
        return del_northing;
    }

    public void setDel_northing(Double del_northing) {
        this.del_northing = del_northing;
    }

    public Double getCorr_northing() {
        return corr_northing;
    }


    public void setCorr_northing(Double corr_northing) {
        this.corr_northing = corr_northing;
    }

    public Double getEasting() {
        return easting;
    }

    public void setEasting(Double easting) {
        this.easting = easting;
    }

    public Double getNorthing() {
        return northing;
    }

    public void setNorthing(Double northing) {
        this.northing = northing;
    }
    }
