package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day7");
        part1(input);
        part2(input);
    }

    private static Map<String, List<String>> parseInput(List<String> input) {
        final Map<String, List<String>> map = new HashMap<>();
        input.forEach(line -> {
            final String[] contains = line.split("contain");
            final String parentString = contains[0].trim();
            final String childrenString = contains[1].trim();
            List<String> children = new ArrayList<>();
            if (!childrenString.contains("no")) {
                for (String c : childrenString.split(",")) {
                    String child = c;
                    if (child.endsWith(".")) {
                        child = child.substring(0, child.length() - 1);
                    }
                    if (child.endsWith("s")) {
                        child = child.substring(0, child.length() - 1);
                    }
                    children.add(child.trim());
                }
            }
            map.put(parentString.substring(0, parentString.length() - 1), children);
        });
        return map;
    }


    private static void part1(List<String> input) {
        final Map<String, List<String>> bagMap = parseInput(input);

        System.out.println(bagMap.entrySet()
                .stream()
                .filter(e -> e.getValue()
                        .stream()
                        .map(bag -> bag.substring(2))
                        .anyMatch(bag -> bag.endsWith("shiny gold bag") || check(bag, bagMap, "shiny gold bag")))
                .count());
    }

    private static void part2(List<String> input) {
        final Map<String, List<String>> map = parseInput(input);
        System.out.println(count(map, "shiny gold bag"));

    }

    private static int count(Map<String, List<String>> map, String color) {
        int count = 0;
        for (String s : map.get(color)) {
            int bags = Integer.parseInt(s.substring(0, 1));
            count += bags * count(map, s.substring(2).trim());
            count += bags;
        }
        return count;
    }

    private static boolean check(String bag, Map<String, List<String>> map, String parent) {
        return map.containsKey(bag) && map.get(bag)
                .stream()
                .map(childBag -> childBag.substring(2))
                .anyMatch(childBag -> childBag.endsWith(parent) || check(childBag, map, parent));
    }

}
