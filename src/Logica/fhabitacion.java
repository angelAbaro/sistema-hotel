/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import Datos.dhabitacion;
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
public class fhabitacion {
    // creo una insta ncia a mi cadena de conexxion
    private conexion mysql=new conexion();
    private Connection cn=mysql.conectar();
    private String sSQL="";
    public Integer totalregistros;
    
    // creo una funcion de tipo defaulttablemodel para mostrar los registros de la base de datos
    public DefaultTableModel mostrar(String buscar){
        DefaultTableModel modelo;
        
       // declaro dos vectores uno para guardar los Títulos de la columnas de tipo string
       // aqui almaceno uno a uno los titulos
        String [] titulos={"ID","Número","piso","Descripción","Características","Precio","Estado","Tipo habitación"};
        // otro array para almacenar ya no los titulos sino los regitros de cada uno de esos titulos
        // se llama registro el vector y lo inicializo en blanco, y le digo que tendra 8 indices
        String [] registro = new String[8]; 
        // inicializo mi variable totalderegistros
        totalregistros=0;
        // depus a modelo agrego los titulos que ya tengo ,,, envio null al inicio y ledigo que vaya guardand los titulos
        modelo=new DefaultTableModel(null,titulos);
        //armo mi instruxion SQL que obtendra todos los registros de la tabla habitacion
        sSQL="select * from habitacion where piso like '%" + buscar + "%' order by idhabitacion";
        
        try {
            Statement st=cn.createStatement();
            ResultSet rs=st.executeQuery(sSQL);
            
            //recorro uno a uno los registros con while
            while (rs.next()) {      
                //ahora si almaceno en mi vector registro todos los registros obtenidos de mi variable rs que es un resultset
                registro [0]=rs.getString("idhabitacion");
                registro [1]=rs.getString("numero");
                registro [2]=rs.getString("piso");
                registro [3]=rs.getString("descripcion");
                registro [4]=rs.getString("caracteristicas");
                registro [5]=rs.getString("precio_diario");
                registro [6]=rs.getString("estado");
                registro [7]=rs.getString("tipo_habitacion");
                
                totalregistros=totalregistros+1;
                // a manera de fila agrego mi vector registro a la variable modelo
                modelo.addRow(registro);
            }
            return modelo;
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return null;
        }
        
    }
    
    public boolean insertar(dhabitacion dts){
        
        sSQL="insert into habitacion (numero,piso,descripcion,caracteristicas,precio_diario,estado,tipo_habitacion)"+
                "values(?,?,?,?,?,?,?)";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst=cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            
            //n almacena el resultado de la ejecucion del statement
            int n=pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n!=0) {
                return true;               
            }else {
                return false;
            }
            
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
        
    }
    public boolean editar(dhabitacion dts){
        // modifico mi variable sSQL
        sSQL="update habitacion set numero=?,piso=?,descripcion=?,caracteristicas=?,precio_diario=?,estado=?,tipo_habitacion=?" +
                " where idhabitacion=?";
                    try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst=cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setString(1, dts.getNumero());
            pst.setString(2, dts.getPiso());
            pst.setString(3, dts.getDescripcion());
            pst.setString(4, dts.getCaracteristicas());
            pst.setDouble(5, dts.getPrecio_diario());
            pst.setString(6, dts.getEstado());
            pst.setString(7, dts.getTipo_habitacion());
            pst.setInt(8, dts.getIdhabitacion());
            
            //n almacena el resultado de la ejecucion del statement
            int n=pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n!=0) {
                return true;               
            }else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
        
    }
    public boolean eliminar(dhabitacion dts){
        sSQL="delete from habitacion where idhabitacion=?";
        try {
            //voy a preparar la consulta / me conecto con la variable "cn" y preparo el statement en este caso es la variable sSQL
            PreparedStatement pst=cn.prepareStatement(sSQL);
            //ahora si envio uno a ino a todos los valores a mi  preparestatement este caso es el pst
            pst.setInt(1, dts.getIdhabitacion());
            
            //n almacena el resultado de la ejecucion del statement
            int n=pst.executeUpdate();
            //declaro la condicion para ver si cumple o no la insercion de registros en mi tabla
            if (n!=0) {
                return true;               
            }else {
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, e);
            return false;
        }
        
    }
}
