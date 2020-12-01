package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;
import java.util.stream.Collectors;

public class Day1 {
    public static void main(String[] args) {
        final List<String> day1 = FileUtil.readFile("day1");

        List<Integer> input = day1.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());


        part1(input);
    }

    private static void part1(List<Integer> input) {
        for (Integer integer : input) {
            for (Integer integer1 : input) {
                if (integer + integer1 == 2020) {
                    System.out.println(integer * integer1);
                }
            }
        }
    }

    private static void part2(List<Integer> input) {
    }

}
