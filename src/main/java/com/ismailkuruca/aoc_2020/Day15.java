package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day15 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day15");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final String inp = input.get(0);
        final List<Integer> integers = Arrays.stream(inp.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        final Map<Integer, Integer> memory = new HashMap<>();
        int previous, next, lastSpoken;
        for (int i = 0; i < integers.size() - 1; i++) {
            memory.put(integers.get(i), i);
        }
        while (true) {
            final int previousIndex = integers.size() - 1;
            previous = integers.get(previousIndex);
            lastSpoken = memory.getOrDefault(previous, -1);
            memory.put(previous, previousIndex);
            next = lastSpoken == -1 ? 0 : previousIndex - lastSpoken;
            integers.add(next);
            if (integers.size() == 2020) {
                System.out.println(next);
                break;
            }
        }
    }

    private static void part2(List<String> input) {
        final String inp = input.get(0);
        final List<Integer> integers = Arrays.stream(inp.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        final Map<Integer, Integer> memory = new HashMap<>();
        int previous, next, lastSpoken;
        for (int i = 0; i < integers.size() - 1; i++) {
            memory.put(integers.get(i), i);
        }
        while (true) {
            final int previousIndex = integers.size() - 1;
            previous = integers.get(previousIndex);
            lastSpoken = memory.getOrDefault(previous, -1);
            memory.put(previous, previousIndex);
            next = lastSpoken == -1 ? 0 : previousIndex - lastSpoken;
            integers.add(next);
            if (integers.size() == 30000000) {
                System.out.println(next);
                break;
            }
        }
    }

}
