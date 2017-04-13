    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Datos;

/**
 *
 * @author ANGEL ABARO
 */
public class dcliente extends dpersona{
   private String codigo_cliente;

    public dcliente() {
    }

    public dcliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }

    // get and set
    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public void setCodigo_cliente(String codigo_cliente) {
        this.codigo_cliente = codigo_cliente;
    }
   
    
   
}
