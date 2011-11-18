package es.uclm.inf_cr.alarcos.desglosa_web.exception;

public class CompanyNotFoundException extends Exception {
    private static final long serialVersionUID = -3163560420744910914L;

    public CompanyNotFoundException() {
        super();
    }

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
