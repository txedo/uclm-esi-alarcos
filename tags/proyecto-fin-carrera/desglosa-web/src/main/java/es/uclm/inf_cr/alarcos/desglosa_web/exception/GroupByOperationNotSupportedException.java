package es.uclm.inf_cr.alarcos.desglosa_web.exception;

public class GroupByOperationNotSupportedException extends Exception {
    private static final long serialVersionUID = 1293839525986463014L;

    public GroupByOperationNotSupportedException() {
        super();
    }

    public GroupByOperationNotSupportedException(String message) {
        super(message);
    }

}
