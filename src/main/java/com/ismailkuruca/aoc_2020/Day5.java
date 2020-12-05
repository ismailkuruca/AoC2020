package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day5");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final List<Integer> seatIDs = getSeatIDs(input);
        System.out.println(seatIDs.stream().max(Comparator.comparingInt(o -> o)));
    }

    private static void part2(List<String> input) {
        final List<Integer> integers = getSeatIDs(input);

        for (int i = 0; i < integers.size(); i++) {
            if (integers.get(i + 1) - integers.get(i) != 1) {
                System.out.println(integers.get(i) + +1);
            }
        }
    }

    private static List<Integer> getSeatIDs(List<String> input) {
        final List<Integer> integers = new ArrayList<>();
        for (String s : input) {
            final String rows = s.substring(0, 7);
            final String cols = s.substring(7, 10);

            final char[] rowChars = rows.toCharArray();
            for (int i = 0; i < rowChars.length; i++) {
                if (rowChars[i] == 'F') {
                    rowChars[i] = '0';
                } else {
                    rowChars[i] = '1';
                }
            }

            final int r = Integer.parseInt(String.valueOf(rowChars), 2);

            final char[] colChars = cols.toCharArray();
            for (int i = 0; i < colChars.length; i++) {
                if (colChars[i] == 'L') {
                    colChars[i] = '0';
                } else {
                    colChars[i] = '1';
                }
            }

            final int c = Integer.parseInt(String.valueOf(colChars), 2);

            final int id = r * 8 + c;

            integers.add(id);
        }
        integers.sort(Comparator.comparingInt(Integer::intValue));
        return integers;
    }
}