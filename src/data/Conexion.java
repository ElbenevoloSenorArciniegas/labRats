package data;

import java.sql.*;

public class Conexion {

    private static Connection cnx = null;

    private static int insertParams(int x1,int y1, int x2, int y2) throws SQLException {
        int id=-1;
        PreparedStatement consulta = obtener().prepareStatement(
               "SELECT `id` FROM `parametros` WHERE `x1` = ? AND `y1` = ? AND `x2` = ? AND `y2` = ?"
        );
        consulta.setInt(1, x1);
        consulta.setInt(2, y1);
        consulta.setInt(3, x2);
        consulta.setInt(4, y2);
        ResultSet res = consulta.executeQuery();
        if(res.next()) {
            id = res.getInt("id");
        }
        res.close();
        consulta.close();

        if(id==-1){
            consulta = obtener().prepareStatement(
                    "INSERT INTO `parametros`(`x1`, `y1`, `x2`, `y2`) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            consulta.setInt(1, x1);
            consulta.setInt(2, y1);
            consulta.setInt(3, x2);
            consulta.setInt(4, y2);
            consulta.executeUpdate();
            ResultSet rs = consulta.getGeneratedKeys();
            if(rs.next()){
                id = rs.getInt(1);
            }
            consulta.close();
        }
        return id;
    }

    public static String insert(int x1,int y1, int x2, int y2, int algoritmo,int sizeBusq,String busqueda,int sizeSol,String solucion){
        try {
            int idParams=insertParams(x1,y1,x2,y2);
            PreparedStatement consulta = obtener().prepareStatement(
                    "INSERT INTO `solucion`(`parametros`, `algoritmo`, `sizeBusq`, `busqueda`, `sizeSol`, `solucion`) "
                            +"VALUES (?,?,?,?,?,?)");
            consulta.setInt(1, idParams);
            consulta.setInt(2, algoritmo);
            consulta.setInt(3, sizeBusq);
            consulta.setString(4, busqueda);
            consulta.setInt(5, sizeSol);
            consulta.setString(6, solucion);
            consulta.executeUpdate();
            consulta.close();
            return armarJson(idParams,x1,y1,x2,y2,algoritmo,sizeBusq,busqueda,sizeSol,solucion);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "{\"msg\":\"Error en la inserción\"}";
        }
    }

    public static String listAll(){
        String rta="[";
        try {
            PreparedStatement consulta = obtener().prepareStatement(
                    "SELECT p.`id`, p.`x1`, p.`y1`, p.`x2`, p.`y2`, " +
                            "`algoritmo`, `sizeBusq`, `busqueda`, `sizeSol`, `solucion` " +
                            "FROM `solucion` s " +
                            "INNER JOIN `parametros` p on p.`id` = s.`parametros` " +
                            "WHERE 1");
            rta+=armarJson(consulta.executeQuery());
            consulta.close();
        } catch (SQLException e) {
            rta+=",";
            System.out.println(e.getMessage());
        }
        rta = rta.substring(0,rta.length()-1);
        rta += ']';
        return rta;
    }

    public static String select(int x1, int y1, int x2, int y2) {
        String rta="[";
        try {
            PreparedStatement consulta = obtener().prepareStatement(
                    "SELECT p.`id`, p.`x1`, p.`y1`, p.`x2`, p.`y2`, " +
                            "`algoritmo`, `sizeBusq`, `busqueda`, `sizeSol`, `solucion` " +
                            "FROM `solucion` s " +
                            "INNER JOIN `parametros` p on p.`id` = s.`parametros` " +
                            "WHERE p.`x1`=? AND p.`y1` =? AND p.`x2` =? AND p.`y2` =?");
            consulta.setInt(1, x1);
            consulta.setInt(2, y1);
            consulta.setInt(3, x2);
            consulta.setInt(4, y2);
            rta+=armarJson(consulta.executeQuery());
            consulta.close();
        } catch (SQLException e) {
            rta+=",";
            System.out.println(e.getMessage());
        }
        rta = rta.substring(0,rta.length()-1);
        rta += ']';
        return rta;
    }

    private static Connection obtener(){
        if(cnx==null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                cnx = DriverManager.getConnection("jdbc:mysql://190.65.26.3:3306/labrat", "anarchy", "elbenevoloseñorarciniegas");
                //cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/labrat", "root", "");
            } catch (SQLException | ClassNotFoundException ex) {
                System.out.println(ex);
            }
        }
        return cnx;
    }

    public static void cerrar(){
        try {
            cnx.close();
            cnx = null;
        } catch (SQLException  | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static String armarJson(ResultSet res) throws SQLException {
        String rta="";
        while(res.next()){
            rta+= armarJson(
                    res.getInt("id"),
                    res.getInt("x1"),
                    res.getInt("y1"),
                    res.getInt("x2"),
                    res.getInt("y2"),
                    res.getInt("algoritmo"),
                    res.getInt("sizeBusq"),
                    res.getString("busqueda"),
                    res.getInt("sizeSol"),
                    res.getString("solucion")
                    ) +",";
        }
        res.close();
        return rta;
    }

    private static String armarJson(int id,int x1,int y1, int x2, int y2, int algoritmo,int sizeBusq,String busqueda,int sizeSol,String solucion){
        return "{\"id\":"+id
                +",\"x1\":"+x1
                +",\"y1\":"+y1
                +",\"x2\":"+x2
                +",\"y2\":"+y2
                +",\"algoritmo\":"+algoritmo
                +",\"sizeBusq\":"+sizeBusq
                +",\"busqueda\":"+busqueda
                +",\"sizeSol\":"+sizeSol
                +",\"solucion\":"+solucion
                +"}";
    }
}
