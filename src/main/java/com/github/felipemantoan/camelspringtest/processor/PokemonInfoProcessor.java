package com.github.felipemantoan.camelspringtest.processor;

import java.util.HashMap;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PokemonInfoProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("Started PokemonProcessor");
        log.info("Pokemon: {}", exchange.getProperty("pokemon"));

        var body = exchange.getMessage().getBody(HashMap.class);
        var locationAreaEncounters = body.get("location_area_encounters");
        var specie = ((HashMap) body.get("species")).get("url");
        
        exchange.setProperty("id", body.get("id"));
        exchange.setProperty("name", body.get("name"));
        exchange.setProperty("height", body.get("height"));
        exchange.setProperty("weight", body.get("weight"));
        exchange.setProperty("locationAreaEncounters", locationAreaEncounters);
        exchange.setProperty("specie", specie);

        log.info("Species Info: {}", specie);
        log.info("Location Area Encounters: {}", locationAreaEncounters);
        log.info("Finished PokemonProcessor");
    }
    
}
