package appvaloresdataextractor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
    ResultSet rs = null;
    public Boolean SetData(String Qry){
        Boolean result = false;
        Connection con = null;
        Statement statement = null;        
       
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(DB.GetConnectionString());
            statement = con.createStatement();            
            result = statement.execute(Qry);                 
        }catch(Exception ex){
            myLog.e("Error al Insertar Datos a BD, SQL query: " + Qry + "( " + ex.toString() + ")");
        }        
        finally {        
            if (statement != null) try { statement.close(); } catch(Exception ex) {}
            if (con != null) try { con.close(); } catch(Exception ex) {}           
        }
        return result;        
    }
            
            
    public Boolean UpdateData(String Qry){
        Boolean result = false;
        Connection con = null;
        Statement statement = null;        
       
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(DB.GetConnectionString());
            statement = con.createStatement();            
            result = statement.execute(Qry);                 
        }catch(Exception ex){
            myLog.e("Error al actualizar BD, SQL query: " + Qry + "( " + ex.toString() + ")");
        }        
        finally {        
            if (statement != null) try { statement.close(); } catch(Exception ex) {}
            if (con != null) try { con.close(); } catch(Exception ex) {}
        }
        return result;        
    }
    public Boolean VerificarExisteRegistro(String Qry){
        Boolean result = false;
        Connection con = null;
        Statement statement = null;               
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(DB.GetConnectionString());
            statement = con.createStatement();
            
            rs = statement.executeQuery(Qry);     
    
            if (rs.next()){
                result = true;                
                myLog.d("[VerificarExisteRegistro]   Se encontro registro, reg.: " + rs.getString(1) + " , query: [" + Qry + "]");
            }else {
                myLog.d("[VerificarExisteRegistro]   No se ha encontrado registros, query: [" + Qry + "]");
            }             
        }catch(Exception ex){
            AppValoresDataExtractor.sendMsg = true;
            myLog.e("[VerificarExisteRegistro]   Error al Verificar existencia de registro en BD, query: " + Qry + "; Exception: ( " + ex.toString() + ")");
        }
        finally {        
            if (statement != null) try { statement.close(); } catch(Exception ex) {}
            if (con != null) try { con.close(); } catch(Exception ex) {}
        }
        return result;        
    }
    
    //Ejecuta un SELECT y devuelve el Resultset con los resultados
    public ResultSet GetData(String Qry) 
            throws SQLException, ClassNotFoundException
    {        
        Connection con = null;
        Statement statement = null;               
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(DB.GetConnectionString());
            statement = con.createStatement();
            
            rs = statement.executeQuery(Qry);       
       }
       catch(SQLException sqlex)
       {
           myLog.e("Error al intentar obtener informacion desde BD, query: " + Qry + " ; Exception: [" + sqlex.getMessage()+"]");
            sqlex.printStackTrace();
       AppValoresDataExtractor.sendMsg = true;
       }
       finally {        
           if (statement != null) try { statement.close(); } catch(Exception ex) {}
           if (con != null) try { con.close(); } catch(Exception ex) {}
       }
       return rs;
    } 
}
