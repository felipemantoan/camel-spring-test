package com.github.felipemantoan.camelspringtest.processor;

import java.util.HashMap;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PokemonAggregationStrategyProcessor implements AggregationStrategy {
    
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {

        throw new UnsupportedOperationException("Unimplemented method 'aggregate'");
    }    
}
