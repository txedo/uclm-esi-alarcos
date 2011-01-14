package persistence;


import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


public class XMLAgent {
	
	public static String getXMLFilename (String tag) throws ConfigurationException {
		XMLConfiguration resources = new XMLConfiguration(IConfiguration.resources);
		return resources.getString(tag);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> void marshal(String filename, Class<E> c, Object ob) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(c);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal((E)ob, new File (filename));
        //marshaller.marshal((E)ob, System.out);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E unmarshal (String filename, Class<E> c) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
		Object result = null;
		JAXBContext jc = JAXBContext.newInstance(c);
		Unmarshaller unmarshaller = jc.createUnmarshaller();
		try {
			result = (E)unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
		} catch (JAXBException e) {
			marshal(filename, c, c.newInstance());
		}
		return (E)unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
	}
	
}
