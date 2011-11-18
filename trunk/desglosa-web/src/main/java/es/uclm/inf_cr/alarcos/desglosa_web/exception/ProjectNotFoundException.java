package es.uclm.inf_cr.alarcos.desglosa_web.exception;

public class ProjectNotFoundException extends Exception {
    private static final long serialVersionUID = -9170738280519561881L;

    public ProjectNotFoundException() {
        super();
    }
    
    public ProjectNotFoundException(String message) {
        super(message);
    }

}
