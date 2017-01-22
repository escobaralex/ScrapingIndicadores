package appvaloresdataextractor;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.w3c.dom.Element;

public class DB {    
    static DBHelper dbH = null;
    public static final String GetConnectionString(){
        String result = new String();
        org.w3c.dom.Document dom;        
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;
        
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        try
        {
            db = dbf.newDocumentBuilder();
            dom = db.parse("c:/AppValores/config.xml");
            org.w3c.dom.Element rootElement = dom.getDocumentElement();
            org.w3c.dom.Node node = rootElement.getElementsByTagName("STR_CON").item(0).getLastChild();
            result = node.getNodeValue();            
        }
        catch(Exception ex) {}
        return result;        
        //return "jdbc:sqlserver://52.10.155.66:1433;" +  "DatabaseName=AppValores;user=sa;password=198476ers";        
    }
       
    public static Boolean SaveValoresDiaToDB(Map<String,String> data){
        Boolean result = false;
        dbH = new DBHelper();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaDate = new Date();
        String fecha = formateador.format(fechaDate);        
        if(data.containsKey("UF")){
            result = InsertUF(data,fecha);
        }
        if(result){
            if(data.containsKey("DOLAR")){
                result = InsertValDia(data,fecha);
            }
        }
        if(result){
            if(data.containsKey("UTM")){
                result = InsertValMensual(data,fecha);
            }
        }
        if(result){
            if(data.containsKey("PIB")){
                result = InsertValPeriodo(data,fecha);
            }
        }
        return result;        
    }    
    
    private static Boolean InsertUF(Map<String,String> data, String fecha){
        Boolean result = false;
        dbH = new DBHelper();
        String Qry = null;        
        try{
            Qry = data.get("UF");
            if(Qry != null){
                Qry = "INSERT INTO UF VALUES ('" + fecha + "'," + Qry + ")";
                try{
                    result = dbH.SetData(Qry);
                if(result){
                    myLog.d("[InsertUF]   Actualización de Valores realizada con Exito");
                    result = true;
                }else{
                    myLog.e("[InsertUF]   Error al actualizar Valores, la operación no se pudo realizar");
                }
                }catch(Exception ex){
                    myLog.e(ex.getMessage());
                }
            }
        }catch(Exception ex){            
            myLog.e("[InsertUF]   Error al obtener valor UF, fecha : " + fecha);
        }
        return result;
    }
    private static Boolean InsertValDia(Map<String,String> data, String fecha){
        Boolean result = false;
        String Qry = "";        
        Qry += " INSERT INTO VALORESDIA (Fecha,IPV,UTM,UTA,Dolar,Euro,Yen,Real,Peso,IPC,TPM,Imacec,PIB,Ipsa,Igpa,DowJones,Nasdaq) VALUES (";
        Qry += "'" + fecha + "',";    
        try{
            if(data.containsKey("IPV")){
                Qry += data.get("IPV") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("UTM")){
                Qry += data.get("UTM") + ",";
            }else
            {
                Qry += "Null,";
            }
            //REVISAR
            Qry += String.valueOf(Double.parseDouble(ClearValue(data.get("UTM")))* 12) + ",";
            
            if(data.containsKey("DOLAR")){
                Qry += data.get("DOLAR") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("EURO")){
                Qry += data.get("EURO") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("YEN")){
                Qry += data.get("YEN") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("REAL")){
                Qry += data.get("REAL") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("PESO")){
                Qry += data.get("PESO") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("IPC")){
                Qry += data.get("IPC") + ",";
            }else
            {
                Qry += "Null,";
            }           
            if(data.containsKey("TPM")){
                Qry += data.get("TPM") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("IMACEC")){
                Qry += data.get("IMACEC") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("PIB")){
                Qry += data.get("PIB") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("IPSA")){
                Qry += data.get("IPSA") + ",";
            }else
            {
                Qry += "Null,";
            }
            
            if(data.containsKey("IGPA")){
                Qry += data.get("IGPA") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("DOWJONES")){
                Qry += data.get("DOWJONES") + ",";
            }else
            {
                Qry += "Null,";
            }
            if(data.containsKey("NASDAQ")){
                Qry += data.get("NASDAQ") + ",";
            }else
            {
                Qry += "Null,";
            }
            
           
            result = dbH.SetData(Qry);
            if(result){
                myLog.d("[SaveValoresDiaToDB]   Actualización de Valores realizada con Exito");
            }else{
                myLog.e("[SaveValoresDiaToDB]   Error al actualizar Valores, la operación no se pudo realizar");
            }
        }catch(Exception ex){
            myLog.e(ex.getMessage());
        }       
        return result;
    }
    private static Boolean InsertValMensual(Map<String,String> data, String fecha){
        Boolean result = false;
        dbH = new DBHelper();
        String Qry = null;
        try{            
            if(Qry != null){
                Qry = "INSERT INTO VALMENSUAL VALUES ('" + fecha + "'," + Qry + ")";
                try{
                result = dbH.SetData(Qry);
                if(result){
                    myLog.d("[SaveValoresDiaToDB]   Actualización de Valores realizada con Exito");
                }else{
                    myLog.e("[SaveValoresDiaToDB]   Error al actualizar Valores, la operación no se pudo realizar");
                }
                }catch(Exception ex){
                    myLog.e(ex.getMessage());
                }
            }
        }catch(Exception ex){            
        }
        return result;               
    }
    private static Boolean InsertValPeriodo(Map<String,String> data, String fecha){
        Boolean result = false;
        dbH = new DBHelper();
        String Qry = null;
        try{
            Qry += ClearValue(data.get("UF"));
            if(Qry != null){
                Qry = "INSERT INTO UF VALUES ('" + fecha + "'," + Qry + ")";
                try{
                result = dbH.SetData(Qry);
                if(result){
                    myLog.d("[SaveValoresDiaToDB]   Actualización de Valores realizada con Exito");
                }else{
                    myLog.e("[SaveValoresDiaToDB]   Error al actualizar Valores, la operación no se pudo realizar");
                }
                }catch(Exception ex){
                    myLog.e(ex.getMessage());
                }
            }
        }catch(Exception ex){            
        }
        return result;
    }
    private static String ClearValue(String valor) {
        String result = "";
        result = valor.replace(".","");
        result = result.replace(",",".");
        /*result = result.replace("$","");
        result = result.replace("-","");
        result = result.replace("ND","0");
        result = result.replace("%","");*/        
        return result;
    }
}
/*try {
            //identity = Integer.parseInt("SELECT @@IDENTITY");
            result = true;
        } catch (Exception e) {
            myLog.e("Error al obtener Identity tabla ValoresDiarios, SQL query: " + sQuery + "( " + e.toString() + ")");
            identity = -1;
        }*/