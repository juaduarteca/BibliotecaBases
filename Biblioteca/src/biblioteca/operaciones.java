/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biblioteca;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Julian Andres Duarte
 */
public class operaciones {
    Connection conexion = null;
    String pass = "1234";
    String user = "postgres";
    public void conectarme(){
        try {
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Prueba", user, pass);
        } catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    public void ingreso (String script){
        PreparedStatement p= null;
        conectarme();
        try{
            p= conexion.prepareStatement(script);
            p.executeUpdate();
            JOptionPane.showMessageDialog(null, "Con exito");
        } catch (Exception ex){
            JOptionPane.showMessageDialog(null, "Se Presento un inconveniente ");
        }
        
    }
    public void consultar (JTable tabla, String script){
        Statement codigoSQL= null;
        ResultSet resultados = null;
        
        try{
            DefaultTableModel t = new DefaultTableModel();
            tabla.setModel(t);
            
            conectarme();
            
            codigoSQL = conexion.createStatement();
            resultados = codigoSQL.executeQuery(script);
            ResultSetMetaData datos = resultados.getMetaData();
            
            int numeroColumna = datos.getColumnCount();
            
            for(int i=1;i<=numeroColumna; i++){
                t.addColumn(datos.getColumnLabel(i));
            }
            
            while(resultados.next()){
                Object[] f = new Object[numeroColumna];
                for (int i = 0; i < numeroColumna; i++) {
                    f[i]=resultados.getObject(i+1);
                }
                t.addRow(f);
            }
            
            codigoSQL.close();
            resultados.close();
            conexion.close();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    }
