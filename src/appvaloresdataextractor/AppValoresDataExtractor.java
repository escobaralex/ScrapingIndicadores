package appvaloresdataextractor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AppValoresDataExtractor {
    public static Boolean sendMsg = false;
    public static Boolean existValDia = false;
    public static Boolean existValMen = false;
    public static Boolean existValPer = false;
    public static Boolean existUF = false;
    public static void main(String[] args) {
        
        myLog.i("--------  Inicio de Proceso   ---------");    
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaDate = new Date();        
        String fecha = formateador.format(fechaDate);
        
        // DEBUG
        //fecha = "2001/01/01";
        String mes = fecha.split("/")[1];
        String anno = fecha.split("/")[0];
        
        myLog.i("[Main]  Verificando si existen valores para el dia "+ fecha);        
        // Devuelven true si los valores existen
        existValDia = ServiceValores.ExistenValoresParaDia(fecha);
        existValMen = ServiceValores.ExistenValoresParaMes(mes,anno);
        existValPer = ServiceValores.ExistenValoresParaPeriodo(mes,anno);
        existUF = ServiceValores.ExisteUF(fecha);
        // Se niegan para que cuando no existan entre a buscarlos
        if(!existValDia || !existValMen || !existValPer || !existUF){
            myLog.i("[ProcesarValoresDiarios]  Inicio Procesar Valores para el dia "+ fecha);
            // Si se generara algun error, envia correo            
            sendMsg = !ServiceValores.ProcesarValores(!existValDia,!existValMen,!existValPer,!existUF);
        }else{
            myLog.i("[ExistValuesFor]  Existen valores para el dia "+ fecha);
            myLog.d("[VerificarValoresDia]  Verificar valores para el dia "+ fecha);
            if(!ServiceValores.VerificarValoresDia()){                
            }
        }
        myLog.i("--------  Fin de Proceso   ---------");
        myLog.s();
        if(sendMsg){
            MailHelper.sendMail();
        }
    }
    private Document print_content(HttpsURLConnection con) {
        Document doc = null;
        if (con != null) {
            try {
                doc = Jsoup.parse(con.getInputStream(),"UTF-8",con.getURL().toString());
                //InputStreamReader inputStream = new InputStreamReader(con.getInputStream(),"UTF-8");
                //String theString = IOUtils.toString(inputStream,);
                /*/String input;
                while ((input.append(br.readLine()) != null)) {
                }
                br.close();*/
                con.disconnect();
            } catch (IOException e) {
                myLog.e("Error: " + e.getMessage());
            }
        }
        return doc;
    }
    public static String GetDataAPI(){
        StringBuilder result = new StringBuilder();
        URL url = null;
        HttpURLConnection conn = null;
        try{
            url = new URL("http://ec2-52-11-162-191.us-west-2.compute.amazonaws.com/api/valores/get?fecha=2015-04-01");
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
 
            if (conn.getResponseCode() != 200) {
		throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());
            }
 
            BufferedReader br = new BufferedReader(new InputStreamReader(
		(conn.getInputStream())));
 
            String output;            
            while ((output = br.readLine()) != null) {
		result.append(output);
            }
        }catch (Exception ex) {       
            myLog.i("Error: al intentar obtener los datos desde el servidor");
        }
        return result.toString();
    }
}