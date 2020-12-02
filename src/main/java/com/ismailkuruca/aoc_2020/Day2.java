package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        final List<String> day2 = FileUtil.readFile("day2");

        part1(day2);
        part2(day2);
    }

    private static void part1(List<String> input) {
        int valid = 0;
        for (String s : input) {
            final int min = Integer.parseInt(s.substring(0, s.lastIndexOf("-")));
            final int max = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.indexOf(" ")));
            final char ch = s.charAt(s.indexOf(":") - 1);

            final String pass = s.split(" ")[2];

            int actual = 0;
            for (char c : pass.toCharArray()) {
                if (ch == c) {
                    actual++;
                }
            }

            if (actual >= min && actual <= max) {
                valid++;
            }
        }

        System.out.println(valid);

    }

    private static void part2(List<String> input) {
        int valid = 0;
        for (String s : input) {
            final int first = Integer.parseInt(s.substring(0, s.lastIndexOf("-")));
            final int second = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.indexOf(" ")));
            final char ch = s.charAt(s.indexOf(":") - 1);

            final String pass = s.split(" ")[2];
            if ((pass.charAt(first - 1) == ch && pass.charAt(second - 1) != ch) || (pass.charAt(first - 1) != ch && pass.charAt(second - 1) == ch)) {
                valid++;
            }
        }

        System.out.println(valid);
    }

}
