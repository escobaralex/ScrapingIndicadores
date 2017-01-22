package appvaloresdataextractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry; 

public class ServiceValores {
    public static Boolean ExistenValoresParaDia(String day){
        Boolean result = false;
        String Qry = " SELECT ID FROM VALORDIA WHERE FECHA = '" + day + "'";
        DBHelper dbH = new DBHelper();
        result = dbH.VerificarExisteRegistro(Qry);
        return result;
    }
    public static Boolean ExistenValoresParaMes(String mes, String anno){
        Boolean result = false;
        String Qry = " SELECT uta,utm,ipc,imacec FROM ValorMensual WHERE mes = '" + mes + "' AND anno = '" + anno + "'";        
        DBHelper dbH = new DBHelper();
        result = dbH.VerificarExisteRegistro(Qry);
        return result;
    }
    public static Boolean ExistenValoresParaPeriodo(String mes, String anno){
        Boolean result = false;
        String Qry = " SELECT Indicador,mesInicial,mesFinal,valor FROM ValorPeriodo WHERE " + mes + " BETWEEN mesInicial AND mesFinal AND anno = '" + anno + "'";        
        DBHelper dbH = new DBHelper();
        result = dbH.VerificarExisteRegistro(Qry);
        return result;
    }
    public static Boolean ExisteUF(String dia){
        Boolean result = false;        
        String Qry = " SELECT Valor FROM UF WHERE Fecha =  '" + dia + "'";        
        DBHelper dbH = new DBHelper();
        result = dbH.VerificarExisteRegistro(Qry);
        return result;
    }
    public static Boolean ProcesarValores(Boolean existValDia,
            Boolean existValMen, Boolean existValPer,Boolean existUF){
        Map<String, String> data = null;
        //Levantar archivo XML parametros
        PropiedadIndicador prInd = new PropiedadIndicador();
        List<PropiedadIndicador> config = prInd.GetConfig(
                existValDia,existValMen,existValPer,existUF);
        //ordenar por fuente        
        config = prInd.Ordenar(config);
        //Por cada fuente hacer un request
        Fuente fte = new Fuente();
        List<Fuente> fuentes = fte.GetFuentes();
        // por cada valor de la fuente, verificar forma de obtener la data (entre by ID or select)        
        HttpHelper hh = new HttpHelper();
        
        // Obtiene data desde paginas web
        config = hh.GetDataHttp(fuentes, config);
        // Pasa los indicadores a un Map< nombre, valor >
        data = GetDataMap(config);
        myLog.d("Almacenar: "+"Guardar en base de datos");        
        return DB.SaveValoresDiaToDB(data);
    }
    
    private static Map<String, String> GetDataMap(List<PropiedadIndicador> indicadores){
        Map<String , String> result = new HashMap<String,String>();
        for(int i = 0; i < indicadores.size(); i++){
            result.put(indicadores.get(i).getNombre(),indicadores.get(i).getValor().toString());
        }    
        return result;
    }
    private static void printResult(Map<String, String> data) {
        for (Entry<String, String> e: data.entrySet()) {
        System.out.println("["+e.getKey() + "=" + e.getValue()+"]");
        }        
    }   
    public static Boolean VerificarValoresDia() {
        return false;
    }
    public static Boolean ValoresDiaVerificados() {
        return false;
    }
}
