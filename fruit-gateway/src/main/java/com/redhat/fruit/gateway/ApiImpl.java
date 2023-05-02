package com.redhat.fruit.gateway;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.NotSupportedException;
import javax.ws.rs.core.Response;

import com.redhat.fruit.gateway.beans.Check;
import com.redhat.fruit.gateway.beans.Config;
import com.redhat.fruit.gateway.beans.Fruit;
import com.redhat.fruit.gateway.beans.Status;
import com.redhat.fruit.gateway.restclient.FruitService;

import io.micrometer.core.instrument.MeterRegistry;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

public class ApiImpl implements ApiResource {

    Logger logger = Logger.getLogger(ApiImpl.class);

    @ConfigProperty(name = "config.service.name", defaultValue = "fruit-gateway-svc")
    String configServiceName;

    @ConfigProperty(name = "fruit.welcome-message", defaultValue = "Welcome")
    String welcome;
    
    @Inject
    @RestClient
    FruitService fruitService;
    
    @Override
    public List<Fruit> listAll() {
        logger.debug("eventsGetAll was called!");
        return fruitService.listAll();
    }

    @Override
    public Fruit findById(Integer id) {
        return fruitService.findById(id);
    }

    @Override
    public Response create(Fruit fruit) {
        return fruitService.create(fruit);
    }

    @Override
    public Response update(Integer id, Fruit fruit) {
        return fruitService.update(id, fruit);
    }

    @Override
    public Config configGet() throws NotSupportedException {

        Check[] checks = new Check[1];
        String status = "OPERATIONAL";
        try {
            Status fruitServiceStatus = fruitService.health();
            logger.info(String.format("FruitService status: %s",fruitServiceStatus.getStatus()));
            String fruitServiceName = fruitService.serviceName();
            logger.info(String.format("FruitService name: %s",fruitServiceName));
            if (!"UP".equals(fruitServiceStatus.getStatus())) {
                status = "DEGRADED";
                checks[0] = new Check(fruitServiceName, "DOWN");
            } else {
                checks[0] = new Check(fruitServiceName, "UP");
            }
        } catch (Exception e) {
            logger.error(e, e);
            status = "DEGRADED";
            checks = new Check[1];
            checks[0] = new Check("backend-service", "DOWN");
        }
        
        return new Config(configServiceName, new Status(status, checks));
    }
}