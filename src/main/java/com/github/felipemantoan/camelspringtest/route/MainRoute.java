package com.github.felipemantoan.camelspringtest.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder{

    private String backend = "https://webhook.site";

    @Override
    public void configure() throws Exception {

        from("direct:foo").routeId("idFoo")
            .removeHeaders("CamelHttp*")
            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("POST"))
            .setHeader(Exchange.HTTP_BASE_URI, constant(backend))
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setBody(constant("{\"hello\": \"io\" }"))
            .to(backend + "/3d388a93-df80-467c-af70-5ff15cc1e19d")
            .removeHeaders("CamelHttp*")
            .log("end ${body}")
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
            .end();
    }
    
}
