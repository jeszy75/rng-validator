package hu.unideb.inf.validator;

import javax.xml.transform.stream.StreamSource;

import javax.xml.XMLConstants;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXParseException;

public class RNGValidator {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.printf("java %s schema instance%n", RNGValidator.class.getName());
            System.exit(1);
        }
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.RELAXNG_NS_URI, com.thaiopensource.relaxng.jaxp.XMLSyntaxSchemaFactory.class.getName(), null);
            sf.setErrorHandler(new SimpleErrorHandler(true));

            Schema schema = sf.newSchema(new StreamSource(args[0]));
            StreamSource instance = new StreamSource(args[1]);

            Validator validator = schema.newValidator();
            validator.setErrorHandler(new SimpleErrorHandler(false));
            validator.validate(instance);
        } catch (SAXParseException e) {
            // Already handled by the error handler
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
