/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.fruit.service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.redhat.fruit.service.exception.NotFoundException;
import com.redhat.fruit.service.exception.UnprocessableEntityException;
import com.redhat.fruit.service.exception.UnsupportedMediaTypeException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import io.micrometer.core.instrument.Metrics;

@RestController
@RequestMapping(value = "/")
public class FruitController {

    private static final String FORCED_INTERNAL_ERROR = "FORCED INTERNAL ERROR";

    private static final Logger LOG = LoggerFactory.getLogger(FruitController.class);
    
    private final FruitRepository repository;

    @Autowired
    private Tracer tracer;

    public FruitController(FruitRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(value = "/")
    public RedirectView welcome() {
        return new RedirectView("index.html");
    }
    
    @PostMapping("/")
    public Fruit processCloudEvent(
        @RequestHeader("ce-id") String id,
        @RequestHeader("ce-type") String type,
        @RequestHeader("ce-source") String source,
        @RequestHeader("ce-specversion") String specversion,
        @RequestHeader("ce-user") String user,
        @RequestHeader("content-type") String contentType,
        @RequestHeader("content-length") String contentLength,
        @RequestBody(required = false) Fruit fruit) {
        
        System.out.println("ce-id=" + id);
        System.out.println("ce-type=" + type);
        System.out.println("ce-source=" + source);
        System.out.println("ce-specversion=" + specversion);
    
        System.out.println("ce-user=" +user);
        System.out.println("content-type=" + contentType);
        System.out.println("content-length=" + contentLength);
        
        return post(fruit);
    }

    @GetMapping("/api/info/name")
    public String getServiceName() {
        Span newSpan = tracer.nextSpan().name("getServiceName").start();
        newSpan.end();
        return "fruit-service";
    }

    @GetMapping("/api/fruits/{id}")
    public Fruit get(@PathVariable("id") Integer id) {
        Span newSpan = tracer.nextSpan().name("getFruit").start();
        if (checkThrowErrors()) {
            throwInternalServerError();
        }

        // >>> Prometheus metric
        Metrics.counter("api.http.requests.total", "api", "inventory", "method", "GET", "endpoint", 
            "jkube/inventory/" + id).increment();
        // <<< Prometheus metric
        verifyFruitExists(id);

        timeOut();

        Fruit fruit = repository.findById(id).get();

        newSpan.end();

        return fruit;
    }

    @GetMapping("/api/fruits")
    public List<Fruit> getAll(@RequestHeader Map<String, String> headers) {
        Span newSpan = tracer.nextSpan().name("getAllFruits").start();
        headers.forEach((key, value) -> {
            LOG.info(String.format("Header '%s' = %s", key, value));
        });
        
        if (checkThrowErrors()) {
            throwInternalServerError();
        }

        // Prometheus metric
        Metrics.counter("api.http.requests.total", "api", "inventory", "method", "GET", "endpoint", 
        "/inventory").increment();
        // <<< Prometheus metric
        Spliterator<Fruit> fruits = repository.findAll()
                .spliterator();

        timeOut();

        List<Fruit> allFruits = StreamSupport
                                    .stream(fruits, false)
                                    .collect(Collectors.toList());

        newSpan.end();
        
        return allFruits;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/fruits")
    public Fruit post(@RequestBody(required = false) Fruit fruit) {
        if (checkThrowErrors()) {
            throwInternalServerError();
        }

        verifyCorrectPayload(fruit);

        timeOut();

        return repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/fruits/{id}")
    public Fruit put(@PathVariable("id") Integer id, @RequestBody(required = false) Fruit fruit) {
        if (checkThrowErrors()) {
            throwInternalServerError();
        }

        verifyFruitExists(id);
        verifyCorrectPayload(fruit);

        fruit.setId(id);

        timeOut();

        return repository.save(fruit);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/fruits/{id}")
    public void delete(@PathVariable("id") Integer id) {
        if (checkThrowErrors()) {
            throwInternalServerError();
        }
        
        verifyFruitExists(id);

        repository.deleteById(id);

        timeOut();
    }

    private void verifyFruitExists(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(String.format("Fruit with id=%d was not found", id));
        }
    }

    private void verifyCorrectPayload(Fruit fruit) {
        if (Objects.isNull(fruit)) {
            throw new UnsupportedMediaTypeException("Fruit cannot be null");
        }

        if (Objects.isNull(fruit.getName()) || fruit.getName().trim().length() == 0) {
            throw new UnprocessableEntityException("The name is required!");
        }

        if (!Objects.isNull(fruit.getId())) {
            throw new UnprocessableEntityException("Id field must be generated");
        }
    }

    private void throwInternalServerError() throws ResponseStatusException {
        LOG.error(FORCED_INTERNAL_ERROR);
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean checkThrowErrors() {
        return SetupController.getThrowErrors();
    }

    private void timeOut() {
        LOG.info("DELAY OF " + SetupController.getDelayInMilliseconds() + " WAS ADDED");
        try {
			TimeUnit.MILLISECONDS.sleep(SetupController.getDelayInMilliseconds());
		} catch (InterruptedException e) {
            // deepcode ignore catchingInterruptedExceptionWithoutInterrupt: NOT IMPORTANT
            LOG.error("Error while sleeping");
		}
    }
}
