package appvaloresdataextractor;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Element;

public class Fuente {
    private Integer id;
    private String url;
    private Boolean isHttps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsHttps() {
        return isHttps;
    }

    public void setIsHttps(Boolean isHttps) {
        this.isHttps = isHttps;
    }
        
    public List<Fuente> GetFuentes() {
        List<Fuente> result = new ArrayList<Fuente>();
        org.w3c.dom.Document dom;        
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;
        
        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        try
        {
            db = dbf.newDocumentBuilder();
            dom = db.parse("c:/AppValores/config.xml");
            org.w3c.dom.Element rootElement = dom.getDocumentElement();
            org.w3c.dom.Node node = rootElement.getElementsByTagName("FUENTES").item(0);
            org.w3c.dom.NodeList nodeList = node.getChildNodes();
            if(nodeList != null && nodeList.getLength()>0)
            {                
                for(int i=0;i<nodeList.getLength();i++)
                {
                    Fuente fte = new Fuente();
                    //DE CADA INDICADOR TOMO EL NOMBRE
                    try{
                        fte.setId(Integer.parseInt(((Element)(nodeList.item(i))).getAttribute("id")));
                        fte.setUrl(String.valueOf(((Element)(nodeList.item(i))).getFirstChild().getNodeValue()));
                        fte.setIsHttps(Boolean.parseBoolean(((Element)(nodeList.item(i))).getAttribute("https")));
                        result.add(fte);                    
                    }catch(Exception ex){
                    }                    
                }
            }
        }
        catch(Exception ex) {}
        return result;
    }
}
