package model;

/**
 * Created by Hugo on 08/10/2017.
 */

public class GPS {
    private int idUsuario, idEmpresa;
    private double latitude,longetude;


    public GPS() {

    }

    public GPS(int usr, int empresa, double latitude, double longetude) {
        this.idUsuario = usr;
        this.idEmpresa = empresa;
        this.latitude = latitude;
        this.longetude = longetude;
    }

    public int getUsr() {
        return idUsuario;
    }

    public int getEmpresa() {
        return idEmpresa;
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

    public void setEmpresa(int id) {
        idEmpresa = id;
    }

    public void setLatitude(double lat) {
        latitude = lat;
    }

    public void setLongitude(double lon) {
        longetude = lon;
    }

}
