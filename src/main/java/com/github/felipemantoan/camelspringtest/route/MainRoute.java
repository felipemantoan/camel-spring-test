package com.github.felipemantoan.camelspringtest.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MainRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        from("timer:foo").to("log:bar");
    }
    
}
