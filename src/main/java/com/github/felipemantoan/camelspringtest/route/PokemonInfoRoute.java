package com.github.felipemantoan.camelspringtest.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.github.felipemantoan.camelspringtest.processor.PokemonInfoProcessor;
import com.github.felipemantoan.camelspringtest.processor.PokemonResponseProcessor;
import com.github.felipemantoan.camelspringtest.processor.InferPokemonProcessor;
import com.github.felipemantoan.camelspringtest.processor.PokemonAggregationStrategyProcessor;

@Component
public class PokemonInfoRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("direct:pokemon-info").routeId("idRoutePokemonInfo")
            .setProperty("pokemon", simple("${header.pokemon}"))
            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .toD("https://pokeapi.co/api/v2/pokemon/${exchangeProperty.pokemon}")
            .unmarshal().json(JsonLibrary.Jackson)
            .process(new PokemonInfoProcessor())
            .removeHeaders("*")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .multicast(new PokemonAggregationStrategyProcessor())
                .parallelAggregate()
                .parallelProcessing()
                .timeout(1000L)
                    .toD("${exchangeProperty.locationAreaEncounters}")
                    .toD("${exchangeProperty.specie}")
            .end()
            .process(new InferPokemonProcessor())
            .process(new PokemonResponseProcessor())
            .unmarshal().json(JsonLibrary.Jackson)
            .removeHeaders("*");
    }
    
}
