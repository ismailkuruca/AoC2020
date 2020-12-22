package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

public class Day21 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day21");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final Set<String> allergens = new HashSet<>();
        final Set<String> ingredients = new HashSet<>();
        final Map<String, Integer> occurrences = new HashMap<>();
        for (String recipe : input) {
            final String[] split = recipe.split(" \\(contains ");
            for (String ingredient : split[0].split(" ")) {
                ingredients.add(ingredient);
                occurrences.putIfAbsent(ingredient, 0);
                occurrences.put(ingredient, occurrences.get(ingredient) + 1);
            }
            Arrays.stream(split[1].replace(")", "").trim().split(","))
                    .map(String::trim)
                    .forEach(allergens::add);
        }

        final Map<String, Set<String>> possibility = new HashMap<>();
        allergens.forEach(s -> possibility.put(s, new HashSet<>(ingredients)));
        for (String recipe : input) {
            final String[] split = recipe.split(" \\(contains ");
            search(ingredients, possibility, split);
        }
        System.out.println(count(ingredients, occurrences, possibility));
    }

    private static void search(Set<String> ingredients, Map<String, Set<String>> possibility, String[] split) {
        List<String> ingrs = Arrays.asList(split[0].split(" "));
        Arrays.stream(split[1].replace(")", "").trim().split(", "))
                .forEach(allergen -> {
                    for (String ingredient : ingredients) {
                        if (!ingrs.contains(ingredient)) {
                            possibility.get(allergen).remove(ingredient);
                        }
                    }
                });
    }

    private static int count(Set<String> ingredients, Map<String, Integer> counts, Map<String, Set<String>> possibility) {
        int count = 0;
        boolean done = false;
        for (String ingredient : ingredients) {
            for (Set<String> poss : possibility.values()) {
                if (poss.contains(ingredient)) {
                    done = true;
                    break;
                }
            }
            if (!done) {
                count += counts.get(ingredient);
            } else {
                done = false;
            }
        }
        return count;
    }

    private static void part2(List<String> input) {
        final Set<String> allergens = new HashSet<>();
        final Set<String> ingredients = new HashSet<>();
        for (String line : input) {
            final String[] split = line.split(" \\(contains ");
            ingredients.addAll(Arrays.asList(split[0].split(" ")));
            Arrays.stream(split[1].replace(")", "").trim().split(", "))
                    .map(String::trim)
                    .forEach(allergens::add);
        }

        final Map<String, Set<String>> possibilities = new HashMap<>();
        allergens.forEach(s -> possibilities.put(s, new HashSet<>()));
        for (String line : input) {
            String[] recipe = line.split(" \\(contains ");
            search(ingredients, possibilities, recipe);
        }

        final Set<String> processed = new HashSet<>();
        while (processed.size() < allergens.size()) {
            for (String allergen : allergens) {
                if (possibilities.get(allergen).size() == 1 && !processed.contains(allergen)) {
                    processed.add(allergen);
                    String possibility = possibilities.get(allergen).iterator().next();
                    for (String allergen2 : allergens) {
                        if (!allergen.equals(allergen2)) {
                            possibilities.get(allergen2).remove(possibility);
                        }
                    }
                }
            }
        }
        StringBuilder part2 = new StringBuilder();
        final String result = allergens.stream().sorted().reduce((s, s2) -> String.join(",", s2)).get();

        for (String a : allergens.stream().sorted().collect(Collectors.toList())) {
            part2.append(",").append(possibilities.get(a).iterator().next());
        }
        System.out.println(result);
        System.out.println(part2.substring(1));
    }

}
