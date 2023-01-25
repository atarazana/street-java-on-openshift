
package com.redhat.fruit.gateway.beans;

public class Config {

    public Config() {
    }

    public Config(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    private String name;

    private Status status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
