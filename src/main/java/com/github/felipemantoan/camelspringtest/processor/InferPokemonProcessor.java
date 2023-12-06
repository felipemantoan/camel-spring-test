package com.github.felipemantoan.camelspringtest.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InferPokemonProcessor implements Processor {
    
    @Override
    public void process(Exchange exchange) throws Exception {
        var arrayNode = exchange.getMessage().getBody(ArrayNode.class);
        ArrayNode locations = null;

        for (var node : arrayNode) {
            if (node instanceof ArrayNode) {
                locations = mapLocations((ArrayNode) node);
                continue;
            }

            if (node instanceof ObjectNode) {
                exchange.setProperty("color", node.get("color").get("name"));
                exchange.setProperty("baseHappiness", node.get("base_happiness"));
                exchange.setProperty("captureRate", node.get("capture_rate"));
                continue;
            }
        }

        exchange.setProperty("locations", locations);
    }

    private ArrayNode mapLocations(ArrayNode locations) {
        var mappedLocations = new ArrayNode(null);

        locations.forEach(item -> { 
            mappedLocations.add(item.get("location_area").get("name"));
        });

        return mappedLocations;
    }
}
