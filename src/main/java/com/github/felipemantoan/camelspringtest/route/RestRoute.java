package com.github.felipemantoan.camelspringtest.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import static org.apache.camel.model.rest.RestParamType.path;

@Component
public class RestRoute extends RouteBuilder {

    @Autowired
    private Environment env;
    
    @Value("${camel.servlet.mapping.context-path}")
    private String contextPath;

    @Override
    public void configure() throws Exception {

        restConfiguration()
            .component("servlet")
            .bindingMode(RestBindingMode.json)
            .dataFormatProperty("prettyPrint", "true")
            .enableCORS(true)
            .port(env.getProperty("server.port", "8080"))
            .contextPath(contextPath.substring(0, contextPath.length() - 2))
            // turn on openapi api-doc
            .apiContextPath("/api-doc")
            .apiProperty("api.title", "User API")
            .apiProperty("api.version", "1.0.0");

        rest("/pokemon").description("User REST service")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .get("/{pokemon}").id("idRoutePokemonInfoByNameRest")
                .param()
                    .name("pokemon")
                    .type(path)
                    .description("The name of the pokemon")
                    .dataType("string")
                .endParam()
            .bindingMode(RestBindingMode.json)
            .to("direct:foo");
    }
    
}
