
package util;

import generated.gear.Gear;
import java.io.File;
import java.io.IOException;
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
    
}
