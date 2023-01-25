
package com.redhat.fruit.gateway.beans;

public class Check {


    public Check() {
    }

    public Check(String name, String status) {
        this.name = name;
        this.status = status;
    }

    private String name;

    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
