
package com.redhat.fruit.gateway.beans;

public class Status {

    private String status;

    private Check[] checks = new Check[0];

    public Status() {
    }
    
    public Status(String status, Check[] checks) {
        this.status = status;
        this.checks = checks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Check[] getChecks() {
        return checks;
    }

    public void setChecks(Check[] checks) {
        this.checks = checks;
    }
}
