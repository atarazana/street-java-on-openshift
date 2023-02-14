package com.redhat.fruit.gateway.restclient;

import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;
import org.jboss.logging.Logger;

import io.opentelemetry.api.trace.Span;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

@ApplicationScoped
public class RequestHeaderFactory implements ClientHeadersFactory {

    @Inject
    Logger log;

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> inHeaders,
                                                 MultivaluedMap<String, String> outHeaders) {
        
        
        Span span = Span.current(); 
        log.info("span: " + span.getSpanContext());

        log.info("inHeaders: " + inHeaders);

        // String version =  Optional.of(inHeaders.getFirst("x-version")).orElse(span.getSpanContext().get());
        // log.infof("Version Header: %s", version);
        String traceId = Optional.ofNullable(inHeaders.getFirst("x-b3-traceid")).orElse(span.getSpanContext().getTraceId());
        String spanId = Optional.ofNullable(inHeaders.getFirst("x-b3-spanid")).orElse(span.getSpanContext().getSpanId());
        // log.infof("Trace Header: %s", traceId);
        MultivaluedMap<String, String> result = new MultivaluedHashMap<>();
        result.add("X-B3-TraceId", traceId);
        result.add("X-B3-SpanId", spanId);
        result.add("X-B3-ParentSpanId", inHeaders.getFirst("x-b3-parentspanid"));

        result.add("x-owner", "carlos");

        log.info(result);
        return result;
    }
}
