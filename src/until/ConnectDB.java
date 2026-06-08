package until;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
    private Connection con;

    public ConnectDB(){
        String url = "jdbc:sqlserver://HIEUDEPTRAI123:1433;databaseName=QuanLyDiemSinhVien_V2;encrypt=false;trustServerCertificate=true;";
        String user="sa";
        String pass="123456789";

        try{
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("Ket noi thanh cong");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            System.out.println("Ket noi that bai");
        }
    }

    public Connection getCon() {
        return con;
    }

    public static void main(String[] args) {
        ConnectDB  c= new ConnectDB();
    }
}
