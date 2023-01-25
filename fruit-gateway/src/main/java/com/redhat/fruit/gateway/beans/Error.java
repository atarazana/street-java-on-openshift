
package com.redhat.fruit.gateway.beans;


public class Error {

    private String message;
    private String code;

    
    public Error() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
