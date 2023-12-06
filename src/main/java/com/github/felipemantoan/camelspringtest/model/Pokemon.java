package com.github.felipemantoan.camelspringtest.model;

import java.util.ArrayList;

/**
 * Pokemon
 */
public record Pokemon(Integer id, String name, Integer height, Integer weight, Integer baseHappiness, Integer captureRate, String color, ArrayList<?>locationAreaEncounters) { }
