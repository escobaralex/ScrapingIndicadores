package appvaloresdataextractor;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
public class PropiedadIndicador {
    List<PropiedadIndicador> result = null;
    private String nombre;
    private Integer indice;
    private Integer origen;
    private Boolean isHttps;
    private Boolean isSelect;
    private Boolean isById;
    private String selector;
    private String variacion;
    private String replace;
    private Double valor;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getIndice() {
        return indice;
    }   
    public void setIndice(Integer indice) {
        this.indice = indice;
    }
    public String getSelector() {
        return selector;
    }
    public void setSelector(String selector) {
        this.selector = selector;
    }
    public Integer getOrigen() {
        return origen;
    }
    public void setOrigen(Integer origen) {
        this.origen = origen;
    }
    public Boolean getIsHttps() {
        return isHttps;
    }    
    public void setIsHttps(Boolean isHttps) {
        this.isHttps = isHttps;
    }
    public Boolean getIsSelect() {
        return isSelect;
    }
    public void setIsSelect(Boolean isSelect) {
        this.isSelect = isSelect;
    }
    public Boolean getIsById() {
        return isById;
    }
    public void setIsById(Boolean isById) {
        this.isById = isById;
    }
    public String getVariacion() {
        return variacion;
    }
    public void setVariacion(String variacion) {
        this.variacion = variacion;
    }
    public String getReplace() {
        return replace;
    }
    public void setReplace(String replace) {
        this.replace = replace;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public List<PropiedadIndicador> GetConfig(Boolean ifExistValDia,Boolean ifExistValMen,
                                                 Boolean ifExistValPer,Boolean ifExistUF) {
        result = new ArrayList<PropiedadIndicador>();
        org.w3c.dom.Document dom;
        PropiedadIndicador propInd = null;
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;        
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        try
        {
            db = dbf.newDocumentBuilder();
            dom = db.parse("c:/AppValores/config.xml");
            org.w3c.dom.Element rootElement = dom.getDocumentElement();            
            org.w3c.dom.Node IndNode =  rootElement.getElementsByTagName("INDICADORES").item(0);            
            org.w3c.dom.NodeList nodeList = IndNode.getChildNodes();
            if(nodeList != null && nodeList.getLength()>0)
            {
                //nodeList.
                //Recorro los distintos indicadores ej. UF                
                for(int i=0;i<nodeList.getLength();i++)
                {
                    propInd = new PropiedadIndicador();
                    //DE CADA INDICADOR TOMO EL NOMBRE
                    propInd.nombre = nodeList.item(i).getNodeName();                    
                    org.w3c.dom.NodeList nodeValueIndicador = nodeList.item(i).getChildNodes();                    
                    if(nodeValueIndicador != null && nodeValueIndicador.getLength()>0)
                    {
                        for(int j=0;j<nodeValueIndicador.getLength();j++)
                        {
                            String nodeName = nodeValueIndicador.item(j).getNodeName();
                            switch(nodeName){                                
                                case "ORIGEN":
                                    propInd.setOrigen(Integer.parseInt(nodeValueIndicador.item(j).getFirstChild().getNodeValue()));
                                break;
                                case "SELECTOR":
                                    propInd.setSelector(nodeValueIndicador.item(j).getFirstChild().getNodeValue());
                                    break;
                                case "IS_HTTPS":
                                    propInd.setIsHttps(Boolean.parseBoolean(nodeValueIndicador.item(j).getFirstChild().getNodeValue()));
                                    break;
                                case "IS_SELECT":
                                    propInd.setIsSelect(Boolean.parseBoolean(nodeValueIndicador.item(j).getFirstChild().getNodeValue()));
                                    break;
                                case "IS_BY_ID":
                                    propInd.setIsById(Boolean.parseBoolean(nodeValueIndicador.item(j).getFirstChild().getNodeValue()));
                                    break;
                                case "INDICE":
                                    propInd.setIndice(Integer.parseInt(nodeValueIndicador.item(j).getFirstChild().getNodeValue()));
                                    break;
                                case "VARIACION":
                                    propInd.setVariacion(nodeValueIndicador.item(j).getFirstChild().getNodeValue());
                                    break;
                                case "REPLACE":
                                    try{
                                        propInd.setReplace(nodeValueIndicador.item(j).getFirstChild().getNodeValue().toString());
                                    }catch (Exception ex){
                                        propInd.setReplace("");
                                    }
                                    break;
                            }
                        }
                        if( (ifExistValDia && propInd.getVariacion().equals("D") ) ||
                            (ifExistValMen && propInd.getVariacion().equals("M") ) ||
                            (ifExistValPer && propInd.getVariacion().equals("S") ) ||
                            (ifExistValPer && propInd.getVariacion().equals("T") ) ||                            
                            (ifExistUF && propInd.getVariacion().equals("UF") )){
                            result.add(propInd);
                        }
                    }                        
                }
            }
        }
        catch(Exception ex) {}        
        return result;
    }

    public List<PropiedadIndicador> Ordenar(List<PropiedadIndicador> config) {
        List<PropiedadIndicador> temp = new ArrayList<PropiedadIndicador>();
        int cont = 1;
        Boolean continuar = true;
        while(continuar){
            for(int i = 0; i<config.size(); i++){
                if(cont == config.get(i).getOrigen()){
                    temp.add(config.get(i));                    
                }
                if(temp.size()==config.size())
                    continuar = false;
            }
            cont++;
        }
        return temp;
    }
    
}
