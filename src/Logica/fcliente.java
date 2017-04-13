/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.dcliente;
import Datos.dproducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jdk.nashorn.internal.parser.TokenType;

/**
 *
 * @author ANGEL ABARO
 */
public class fcliente {

    // creo una insta ncia a mi cadena de conexxion
    private conexion mysql = new conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    private String sSQL2 = "";
    public Integer totalregistros;

    // creo una funcion de tipo defaulttablemodel para mostrar los registros de la base de datos
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;

        // declaro dos vectores uno para guardar los Títulos de la columnas de tipo string
        // aqui almaceno uno a uno los titulos
        String[] titulos = {"ID", "Nombre", "Apaterno", "Amaterno", "Doc", "Numero Documento", "Direccion", "Telefono", "Email", "Código"};
        // otro array para almacenar ya no los titulos sino los regitros de cada uno de esos titulos
        // se llama registro el vector y lo inicializo en blanco, y le digo que tendra 8 indices
        String[] registro = new String[10];
        // inicializo mi variable totalderegistros
        totalregistros = 0;
        // depus a modelo agrego los titulos que ya tengo ,,, envio null al inicio y ledigo que vaya guardand los titulos
        modelo = new DefaultTableModel(null, titulos);
        //armo mi instruxion SQL que obtendra todos los registros de la tabla habitacion
        sSQL = "select p.idpersona, p.nombre, p.apaterno, p.amaterno, p.tipo_documento, p.num_documento, p.direccion, p.telefono, p.email, c.codigo_cliente from persona p inner join cliente c on p.idpersona=c.idpersona where num_documento like '%"
                + buscar + "%' order by idpersona desc";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            //recorro uno a uno los registros con while
            while (rs.next()) {
                //ahora si almaceno en mi vector registro todos los registros obtenidos de mi variable rs que es un resultset
                registro[0] = rs.getString("idpersona");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("apaterno");
                registro[3] = rs.getString("amaterno");
                registro[4] = rs.getString("tipo_documento");
                registro[5] = rs.getString("num_documento");
                registro[6] = rs.getString("direccion");
                registro[7] = rs.getString("telefono");
                registro[8] = rs.getString("email");
                registro[9] = rs.getString("codigo_cliente");

                totalregistros = totalregistros + 1;
                // a manera de fila agrego mi vector registro a la variable modelo
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }

    }

    public boolean insertar(dcliente dts) {

       sSQL = "insert into persona (nombre,apaterno,amaterno,tipo_documento,num_documento,direccion,telefono,email) values (?,?,?,?,?,?,?,?)";
       sSQL2 = "insert into cliente (idpersona,codigo_cliente) values ((select idpersona from persona order by idpersona desc limit 1),?)";


        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApaterno());
            pst.setString(3, dts.getAmaterno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());

            pst2.setString(1, dts.getCodigo_cliente());

            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }

    public boolean editar(dcliente dts) {
        // modifico mi variable sSQL
        sSQL = "update persona set nombre=?,apaterno=?,amaterno=?,tipo_documento=?,num_documento=?,"
                + " direccion=?,telefono=?,email=? where idpersona=?";

        sSQL2 = "update cliente set codigo_cliente=?"
                + " where idpersona=?";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getApaterno());
            pst.setString(3, dts.getAmaterno());
            pst.setString(4, dts.getTipo_documento());
            pst.setString(5, dts.getNum_documento());
            pst.setString(6, dts.getDireccion());
            pst.setString(7, dts.getTelefono());
            pst.setString(8, dts.getEmail());
            pst.setInt(9, dts.getIdpersona());

            pst2.setString(1, dts.getCodigo_cliente());
            pst2.setInt(2, dts.getIdpersona());

            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }

    public boolean eliminar(dcliente dts) {
        sSQL = "delete from cliente where idpersona=?";
        sSQL2 = "delete from persona where idpersona=?";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            PreparedStatement pst2 = cn.prepareStatement(sSQL2);

            pst.setInt(1, dts.getIdpersona());

            pst2.setInt(1, dts.getIdpersona());

            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                int n2 = pst2.executeUpdate();
                if (n2 != 0) {
                    return true;
                } else {
                    return false;
                }

            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }
}
