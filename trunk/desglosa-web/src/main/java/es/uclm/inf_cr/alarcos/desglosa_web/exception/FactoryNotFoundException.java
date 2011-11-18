package es.uclm.inf_cr.alarcos.desglosa_web.exception;

public class FactoryNotFoundException extends Exception {
    private static final long serialVersionUID = 1439309768665418669L;

    public FactoryNotFoundException() {
        super();
    }

    public FactoryNotFoundException(String message) {
        super(message);
    }
}
