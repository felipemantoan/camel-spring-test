package com.github.felipemantoan.camelspringtest.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PokemonAggregationStrategyProcessor implements AggregationStrategy {
    
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        if (oldExchange == null) {
            return newExchange;
        }
        
        var parser = new ObjectMapper();

        var bodyNewExchange = newExchange.getIn().getBody(String.class);
        var bodyOldExchange = oldExchange.getIn().getBody(String.class);
        
        try {
            var arrayNode = parser.createArrayNode();
            arrayNode.add(parser.readTree(bodyOldExchange));
            arrayNode.add(parser.readTree(bodyNewExchange));
            newExchange.getMessage().setBody(arrayNode);
        }
        catch (Exception e) {
            log.info(e.getMessage());
        }
        return newExchange;
    }
}
