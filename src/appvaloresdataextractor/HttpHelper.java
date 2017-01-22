package appvaloresdataextractor;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpHelper {
    
    List<PropiedadIndicador> result = null;
    Document doc = null;
    public List<PropiedadIndicador> GetDataHttp(List<Fuente> fuentes,List<PropiedadIndicador> indicadores){
        result = new ArrayList<PropiedadIndicador>();               
        for(int i = 0; i < fuentes.size();i++){
            doc = null;
            try{
                if(fuentes.get(i).getIsHttps()){
                    HttpHelper.trustEveryone();
                }
                doc = Jsoup.connect(fuentes.get(i).getUrl()).userAgent("Mozilla").timeout(10000).get();
                for(int j = 0; j < indicadores.size();j++){
                    try{
                    if(indicadores.get(j).getOrigen()== fuentes.get(i).getId()){
                        String sVal = "";
                        if(indicadores.get(j).getIsById()){
                            sVal = doc.getElementById(indicadores.get(j).getSelector()).text();
                        }else{
                            sVal= doc.select(indicadores.get(j).getSelector()).get(indicadores.get(j).getIndice()).text();
                        }
                        //myLog.i(indicadores.get(j).getNombre() + "= " + sVal);
                        try{
                            // OBTIENE EL STRING REPLACE
                            String replace = indicadores.get(j).getReplace();
                            
                            // OBTIENE CADA REPLACE A EJECUTAR
                            String[] arrayReplace = replace.split("\\|");
                            // SI ES MAS DE UNO
                            if(arrayReplace!=null){
                                for(int k = 0; k < arrayReplace.length; k++){
                                    try{
                                        String sNew = "";
                                        String[] reem = arrayReplace[k].split(":");          
                                        
                                        String sOld = reem[0];
                                        if(reem.length > 1)
                                            sNew = reem[1];        
                                        sVal = sVal.replace(sOld,sNew);
                                    }catch(Exception ex){}
                                }
                            }else{
                                
                            }
                        }catch(Exception ex){ }
                        myLog.i(indicadores.get(j).getNombre() + "= " + sVal);
                        try{
                            indicadores.get(j).setValor(Double.valueOf(sVal));
                        }catch (Exception ex){
                            indicadores.get(j).setValor(Double.valueOf(sVal));
                        }
                        result.add(indicadores.get(j));                        
                    }
                    }catch(Exception ex){ }
                }                
            }catch(Exception ex){ }
        }
        return result;
    }
    
    public static void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier(){
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }});
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            myLog.e("Error en nuevo codigo: "+ e.getMessage());
        }
    }
    
    private void print_https_cert(HttpsURLConnection con) {
        if (con != null) {
            try {
                myLog.i("Response Code : " + String.valueOf(con.getResponseCode()).toString());
                myLog.i("Cipher Suite : " +  con.getCipherSuite().toString());
                Certificate[] certs = con.getServerCertificates();
                for (Certificate cert : certs) {
                    myLog.i("Cert Type : " + cert.getType());
                    myLog.i("Cert Hash Code : " + String.valueOf(cert.hashCode()));
                    myLog.i("Cert Key Algorithm : " + cert.getPublicKey().getAlgorithm());
                    myLog.i("Cert Key Format : " + cert.getPublicKey().getFormat());
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
