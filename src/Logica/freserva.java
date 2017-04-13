/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.dhabitacion;
import Datos.dproducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ANGEL ABARO
 */
public class freserva {

    // creo una insta ncia a mi cadena de conexxion
    private conexion mysql = new conexion();
    private Connection cn = mysql.conectar();
    private String sSQL = "";
    public Integer totalregistros;

    // creo una funcion de tipo defaulttablemodel para mostrar los registros de la base de datos
    public DefaultTableModel mostrar(String buscar) {
        DefaultTableModel modelo;

        // declaro dos vectores uno para guardar los Títulos de la columnas de tipo string
        // aqui almaceno uno a uno los titulos
        String[] titulos = {"ID", "Idhabitacion", "Número", "Idcliente","Cliente","Idtrabajador","Trabajador","Tipo Reserva","Fecha_Reserva","Fecha Ingreso","Fecha Salida","Costo","Estado"};
        // otro array para almacenar ya no los titulos sino los regitros de cada uno de esos titulos
        // se llama registro el vector y lo inicializo en blanco, y le digo que tendra 8 indices
        String[] registro = new String[13];
        // inicializo mi variable totalderegistros
        totalregistros = 0;
        // depus a modelo agrego los titulos que ya tengo ,,, envio null al inicio y ledigo que vaya guardand los titulos
        modelo = new DefaultTableModel(null, titulos);
        //armo mi instruxion SQL que obtendra todos los registros de la tabla habitacion
        sSQL = "select r.idreserva, r.idhabitaion, h.numero, r.idcliente,"
                + " (select nombre from persona where idpersona=r.idcliente) as clienten, "
                + " (select apaterno from persona where idpersona=r.idcliente) as clienteap,"
                + " r.idtrabajador, (select nombre from persona where idpersona=r.idtrabajador) as trabajadorn,"
                + " (select apaterno from persona where idpersona=r.idcliente) as trabajadorap,"
                + " r.tipo_reserva, r.fecha_reserva, r.fecha_ingresa, r.fecha_salida,"
                + " r.costo_alojamiento, r.estado from reserva r inner join habitacion h on r.idhabitacion=h.idhabitacion where fecha_reserva like '%" + buscar + "%' order by idreserva";

        try {
            Statement st = cn.createStatement();
            ResultSet rs = st.executeQuery(sSQL);

            //recorro uno a uno los registros con while
            while (rs.next()) {
                //ahora si almaceno en mi vector registro todos los registros obtenidos de mi variable rs que es un resultset
                registro[0] = rs.getString("idproducto");
                registro[1] = rs.getString("nombre");
                registro[2] = rs.getString("descripcion");
                registro[3] = rs.getString("unidad_medida");
                registro[4] = rs.getString("precio_venta");
                registro[5] = rs.getString("precio_venta");
                registro[6] = rs.getString("precio_venta");
                registro[7] = rs.getString("precio_venta");
                registro[8] = rs.getString("precio_venta");
                registro[9] = rs.getString("precio_venta");
                registro[10] = rs.getString("precio_venta");
                registro[11] = rs.getString("precio_venta");
                registro[12] = rs.getString("precio_venta");

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

    public boolean insertar(dproducto dts) {

        sSQL = "insert into producto (nombre,descripcion,unidad_medida,precio_venta)"
                + "values(?,?,?,?)";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUnidad_medida());
            pst.setDouble(4, dts.getPrecio_venta());
           
            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }

    public boolean editar(dproducto dts) {
        // modifico mi variable sSQL
        sSQL = "update producto set nombre=?,descripcion=?,unidad_medida=?,precio_venta=?"
                + " where idproducto=?";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNombre());
            pst.setString(2, dts.getDescripcion());
            pst.setString(3, dts.getUnidad_medida());
            pst.setDouble(4, dts.getPrecio_venta());
            
            
            pst.setInt(5, dts.getIdproducto());

            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }

    public boolean eliminar(dproducto dts) {
        sSQL = "delete from producto where idproducto=?";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst = cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setInt(1, dts.getIdproducto());

            //n almacena el resultado de la ejecucion del statement
            int n = pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n != 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }

    }
}
