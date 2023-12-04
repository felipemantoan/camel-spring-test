package com.github.felipemantoan.camelspringtest.route;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder {

    private String backend = "https://webhook.site/e8f8f8f4-1c78-46f3-9271-a131e4a9cb77";

    @Override
    public void configure() throws Exception {

        from("direct:foo").routeId("idFoo")
            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .process(p -> {
                HashMap<String, String> body = new HashMap<String, String>();
                body.put("step", "1");
                p.getMessage().setBody(body);
            })
            .marshal().json(JsonLibrary.Jackson)
            .to(backend)
            .log("${body}")
            // .unmarshal().json(JsonLibrary.Jackson)
            .process(p -> {
                HashMap<String, String> body = new HashMap<String, String>();
                body.put("step", "2");
                p.getMessage().setBody(body);
            })
            .setHeader(Exchange.HTTP_METHOD, constant("PATCH"))
            .marshal().json(JsonLibrary.Jackson)
            .multicast().parallelProcessing()
                .to(backend)
                .log("${body}")
                .to(backend)
                .log("${body}")
            .end()
            .unmarshal().json(JsonLibrary.Jackson)
            .removeHeaders("*")
            .log("end ${body}");
    }
    
}
