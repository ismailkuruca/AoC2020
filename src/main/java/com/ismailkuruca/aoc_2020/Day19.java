package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day19");

        Map<Integer, String> ruleset = new HashMap<>();
        List<String> values = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            final String in = input.get(i);
            if (!in.contains(":")) {
                values.add(in);
                continue;
            }
            final String[] split = in.split(":");
            final int rule = Integer.parseInt(split[0]);
            final String content = split[1].trim();
            ruleset.putIfAbsent(rule, content);
        }

        part1(ruleset, values);
        part2(ruleset, values);
    }

    private static void part1(Map<Integer, String> ruleset, List<String> values) {
        String zero = ruleset.get(0);
        String prev = "";
        while (!prev.equals(zero)) {
            prev = zero;
            String temp = "";
            for (String s : zero.split(" ")) {
                if (s.matches("[0-9]+")) {
                    temp += "( " + ruleset.get(Integer.parseInt(s)) + " )";
                } else {
                    temp += s + " ";
                }
            }
            zero = temp;
        }
        String regex = "^" + zero.replaceAll("[ \"]", "") + "$";

        int match = 0;
        for (String value : values) {
            if (value.matches(regex)) {
                match++;
            }
        }

        System.out.println(match);
    }

    private static void part2(Map<Integer, String> ruleset, List<String> values) {
        ruleset.put(8, "42 | 42 8");
        ruleset.put(11, "42 31 | 42 11 31");

        String zero = ruleset.get(0);

        int prev = 0;
        while (true) {
            String temp = "";
            for (String s : zero.split(" ")) {
                if (s.matches("[0-9]+")) {
                    temp += "( " + ruleset.get(Integer.parseInt(s)) + " )";
                } else {
                    temp += s + " ";
                }
            }
            zero = temp;
            String regex = "^" + zero.replaceAll("([ \"])|42|31", "") + "$";

            int match = 0;
            for (String value : values) {
                if (value.matches(regex)) {
                    match++;
                }
            }

            if (match > 0 && match == prev) {
                System.out.println(match);
                break;
            }

            prev = match;
        }
    }
}
