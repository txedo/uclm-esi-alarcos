package es.uclm.inf_cr.alarcos.desglosa_web.util;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public final class XMLAgent {

    private XMLAgent() { }

    @SuppressWarnings("unchecked")
    public static <E> void marshal(final String filename, final Class<E> c, final Object ob) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(c);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal((E) ob, new File(filename));
    }

    @SuppressWarnings("unchecked")
    public static <E> E unmarshal(final String filename, final Class<E> c) throws JAXBException, IOException, InstantiationException, IllegalAccessException {
        @SuppressWarnings("unused")
        Object result = null;
        JAXBContext jc = JAXBContext.newInstance(c);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        try {
            result = (E) unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
        } catch (JAXBException e) {
            marshal(filename, c, c.newInstance());
        }
        return (E) unmarshaller.unmarshal(ResourceRetriever.getResourceAsStream(filename));
    }

}
