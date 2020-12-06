package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;

public class Day6 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day6");

        part1(input);
        part2(input);
    }

    private static List<List<String>> extract(List<String> input) {
        List<List<String>> all = new ArrayList<>();
        Set<String> questions = new HashSet<>();
        for (int i = 0; i < input.size(); i++) {
            final String s = input.get(i);
            if (s.isEmpty()) {
                all.add(new ArrayList<>(questions));
                questions = new HashSet<>();
                continue;
            }
            for (char q : s.toCharArray()) {
                questions.add(String.valueOf(q));
            }
        }
        all.add(new ArrayList<>(questions));
        return all;
    }

    private static void part1(List<String> input) {
        final List<List<String>> extract = extract(input);
        int val = 0;
        for (int i = 0; i < extract.size(); i++) {
            val += extract.get(i).size();
        }
        System.out.println(val);
    }

    private static void part2(List<String> input) {
        Map<Character, Integer> questions = new HashMap<>();
        int persons = 0;
        int valid = 0;
        for (int i = 0; i < input.size(); i++) {
            final String s = input.get(i);
            if (s.isEmpty()) {
                int finalPersons = persons;
                valid += (int) questions.values().stream().filter(integer -> integer == finalPersons).count();
                questions = new HashMap<>();
                persons = 0;
                continue;
            }
            for (char q : s.toCharArray()) {
                questions.putIfAbsent(q, 0);
                questions.put(q, questions.get(q) + 1);
            }
            persons++;
        }
        int finalPersons1 = persons;
        valid += (int) questions.values().stream().filter(integer -> integer >= finalPersons1 - 1).count();
        System.out.println(valid);
    }
}