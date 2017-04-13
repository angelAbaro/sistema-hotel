/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author ANGEL ABARO
 */
public class conexion {
    //declarando variables y asignando valores
    public String db="basereserva";
    public String url="jdbc:mysql://localhost/"+db;
    public String user="root";
    public String pass="";
//constructor
    public conexion() {
    }
    
    //funcion para conectarse a la base de datos
    public Connection conectar(){
        Connection link=null;
        
        //capturador de errores trycatch
        try {
            // cargo el driver de la conexion
            Class.forName("org.gjt.mm.mysql.Driver");
            // a la variable link le digo que cree un enlace a la db
            // ya tengo un enlace a la db
            link=DriverManager.getConnection(this.url, this.user, this.pass);
            
        } catch (ClassNotFoundException | SQLException e) {
            //mediante un option pane me mustre el posible erro que aparesca
            JOptionPane.showConfirmDialog(null, e);
        }
        // al final tengo que devolver la cadena de conexion
        return link;  
    }
}
