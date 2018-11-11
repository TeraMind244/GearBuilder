
package util;

import gear.builder.ListGearSet;
import gear.search.SearchGearView;
import gear.generated.Gear;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLUtil {
    
    public static boolean validateXMLBeforeSaveToDatabase(Gear gear, String schemaPath) {
        try {
            String xml = marshall(gear);
            
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaPath));
            Validator validator = schema.newValidator();
            
            InputSource inputFile = new InputSource(new StringReader(xml));
            validator.validate(new SAXSource(inputFile));
            
            System.out.println("Validate: " + gear.getGearName());
            return true;
        } catch (SAXException | IOException ex) {
            System.out.println("Validate falied: Gear: " + gear.getGearName());
            System.out.println("Cause: " + ex.getMessage());
        }
        return false;
    }
    
    public static String marshall(Gear gear) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(Gear.class);
            Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(gear, sw);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }
    
    public static String marshall(SearchGearView gearView) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(SearchGearView.class);
            Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(gearView, sw);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }
    
    public static String marshall(ListGearSet listGearSet) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext jaxbCtx = JAXBContext.newInstance(ListGearSet.class);
            Marshaller marshaller = jaxbCtx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
            marshaller.marshal(listGearSet, sw);
        } catch (JAXBException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sw.toString();
    }
    
    public static String getXSLFileAsString(String path) {
        FileInputStream is = null;
        InputStreamReader ir = null;
        BufferedReader br = null;
        
        try {
            is = new FileInputStream(path);
            ir = new InputStreamReader(is, "UTF-8");
            br = new BufferedReader(ir);
            String line = "";
            StringBuilder sb = new StringBuilder("");
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (ir != null) {
                try {
                    ir.close();
                } catch (IOException ex) {
                    Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ex) {
                    Logger.getLogger(XMLUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return "";
    }
    
}
