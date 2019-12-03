package br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions;


public class InvalidFieldException extends Exception {

    private String field;

    private String reason;

    public InvalidFieldException(final String field, final String reason) {
        super(field + ": " + reason);
        this.field = field;
        this.reason = reason;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
