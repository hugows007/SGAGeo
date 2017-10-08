package model;

/**
 * Created by Hugo on 08/10/2017.
 */

public class GPS {
    private int idUsuario;
    private double latitude,longetude;


    public GPS() {

    }

    public GPS(int usr, double latitude, double longetude) {
        this.idUsuario = usr;
        this.latitude = latitude;
        this.longetude = longetude;
    }

    public int getUsr() {
        return idUsuario;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longetude;
    }

    public void setUsr(int id) {
        idUsuario = id;
    }

    public void setLatitude(double lat) {
        latitude = lat;
    }

    public void setLongitude(double lon) {
        longetude = lon;
    }

}
