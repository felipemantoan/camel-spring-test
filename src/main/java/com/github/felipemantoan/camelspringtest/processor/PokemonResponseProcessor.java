package com.github.felipemantoan.camelspringtest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.codec.json.Jackson2JsonEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.felipemantoan.camelspringtest.model.Pokemon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PokemonResponseProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Started PokemonResponseProcessor");
        var pokemon = new Pokemon(
            exchange.getProperty("id", Integer.class),
            exchange.getProperty("name", String.class),
            exchange.getProperty("height", Integer.class),
            exchange.getProperty("weight", Integer.class),
            exchange.getProperty("baseHappiness", Integer.class),
            exchange.getProperty("captureRate", Integer.class)
        );

        log.info("{}", pokemon);
        var mapper = new ObjectMapper();
        
        exchange.getMessage().setBody(mapper.writeValueAsString(pokemon));
        log.info("Finished PokemonResponseProcessor");
    }
    
}
