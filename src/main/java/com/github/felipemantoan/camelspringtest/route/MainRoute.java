package com.github.felipemantoan.camelspringtest.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder{

    private String backend = "https://webhook.site";

    @Override
    public void configure() throws Exception {

        from("direct:foo").routeId("idFoo")
            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.HTTP_BASE_URI, constant(backend))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .setBody(constant("{\"step\": \"1\"}"))
            .marshal().json(JsonLibrary.Jackson)
            .to(backend + "/e8f8f8f4-1c78-46f3-9271-a131e4a9cb77")
            .log("${body}")
            // .unmarshal().json(JsonLibrary.Jackson)
            .multicast().parallelProcessing()
                .to("https://webhook.site/e8f8f8f4-1c78-46f3-9271-a131e4a9cb77")
                .to("https://webhook.site/e8f8f8f4-1c78-46f3-9271-a131e4a9cb77")
            .end()
            .unmarshal().json(JsonLibrary.Jackson)
            .removeHeaders("*")
            .log("end ${body}");
    }
    
}
