package model.response;

public class Response {
    private boolean isOk;
    private String message;

    public Response(boolean isOk, String message) {
        this.isOk = isOk;
        this.message = message;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
