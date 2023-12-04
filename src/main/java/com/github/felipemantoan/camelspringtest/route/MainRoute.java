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
            .marshal().json(JsonLibrary.Jackson)
            .to(backend + "/3d388a93-df80-467c-af70-5ff15cc1e19d")
            .unmarshal().json(JsonLibrary.Jackson)
            .removeHeaders("*")
            .log("end ${body}");
    }
    
}
