package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 {
    public static void main(String[] args) {
        final List<Long> input = FileUtil.readFile("day9").stream().map(Long::parseLong).collect(Collectors.toList());
        part1(input);
        part2(input);
    }

    private static void part1(List<Long> input) {
        int preamble = 25;

        for (int i = preamble; i < input.size(); i++) {
            final Long number = input.get(i);
            if(!check(number, input.subList(i - preamble, i))) {
                System.out.println(number);
            }
        }

    }

    private static boolean check(Long number, List<Long> subList) {
        boolean valid = false;
        for (Long aLong : subList) {
            for (Long aLong1 : subList) {
                if (aLong + aLong1 == number) {
                    valid = true;
                    break;
                }
            }
        }
        return  valid;
    }

    private static void part2(List<Long> input) {
        int preamble = 25;

        for (int i = preamble; i < input.size(); i++) {
            final Long number = input.get(i);
            if(!check(number, input.subList(i - preamble, i))) {
                findContigous(number, input);
            }
        }
    }

    private static void findContigous(Long number, List<Long> input) {
        long inter = 0;
        int iter = 0;
        for (int i = iter; i < input.size(); i++) {
            inter += input.get(i);
            if(inter == number) {
                System.out.println(input.get(i + 1));
                System.out.println(input.get(iter + 1));

                System.out.println(input.get(i) + input.get(iter + 1));

                final long l = Collections.min(input.subList(iter + 1, i)) + Collections.max(input.subList(iter+ 1, i));
                System.err.println(l);
                break;
            }
            if (inter > number) {
                i = ++iter;
                inter = 0;
            }
        }
    }


}
