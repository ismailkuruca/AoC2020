package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {
    public static void main(String[] args) {
        final List<Integer> input = FileUtil.readFile("day10").stream().map(Integer::parseInt).collect(Collectors.toList());

        input.add(0);
        input.sort(Comparator.comparingInt(o -> o));
        input.add(input.get(input.size() - 1) + 3);

        part1(input);
        part2(input);
    }

    private static void part1(List<Integer> input) {
        int prevJolt = 0;
        int diff1 = 0;
        int diff2 = 0;
        int diff3 = 0;

        for (final Integer integer : input) {
            final int diff = integer - prevJolt;
            if (diff == 1) diff1++;
            if (diff == 2) diff2++;
            if (diff == 3) diff3++;
            prevJolt += diff;
        }
        System.out.println(diff1 * diff3);
    }

    private static long[] counts;

    private static void part2(List<Integer> input) {
        counts = new long[input.size()];
        counts[0] = 1;
        for (int i = 1; i < counts.length; i++) {
            for (int j = 0; j < i; j++) {
                if (input.get(i) - input.get(j) <= 3) {
                    counts[i] += counts[j];
                }
            }
        }
        System.out.println(counts[counts.length - 1]);
    }
}
