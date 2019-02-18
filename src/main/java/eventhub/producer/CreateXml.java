package eventhub.producer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.sun.tools.classfile.Attribute;

import java.io.StringWriter;

/**
 * Class to create an XML for distribution 
 * Fakes creation of a mortgage listing
 */
public class CreateXml {

   public String createXml(int i){
   
      String xmlString="";
      try {
         DocumentBuilderFactory dbFactory =
         DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         Document doc = dBuilder.newDocument();

         
         // root element
         Element rootElement = doc.createElement("mortgage");
        // rootElement.setNodeValue(Integer.toString(i));
         doc.appendChild(rootElement);

         Attr attribute = doc.createAttribute("id");
         attribute.setValue(Integer.toString(i));
         rootElement.setAttributeNode(attribute);

         // home element
         Element home = doc.createElement("home");
         rootElement.appendChild(home);

                 // setting attribute to element
         Attr attr = doc.createAttribute("type");
         attr.setValue("RowHouse");
         home.setAttributeNode(attr);

         // contract element
         Element contract = doc.createElement("contract");
         Attr attrType = doc.createAttribute("type");
         attrType.setValue("annuity");
         contract.setAttributeNode(attrType);
         contract.appendChild(doc.createTextNode("Fixed interest For 10 Years"));
         home.appendChild(contract);

         Element contractFixed = doc.createElement("contract");
         Attr attrType1 = doc.createAttribute("type");
         attrType1.setValue("30 Years");
         contractFixed.setAttributeNode(attrType1);
         contractFixed.appendChild(doc.createTextNode("Fixed interest throughout"));
         home.appendChild(contractFixed);
         Transformer transformer = TransformerFactory.newInstance().newTransformer();
         StringWriter sw = new StringWriter();
         StreamResult sr = new StreamResult(sw);
         Source source = new DOMSource(doc);
         transformer.transform(source, sr);
         xmlString=sw.toString();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return xmlString;
   }
}