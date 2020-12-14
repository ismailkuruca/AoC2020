package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;

import static java.lang.Integer.parseInt;

public class Day13 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day13");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final int initial = parseInt(input.get(0));
        final Map<Integer, Integer> integers = new HashMap<>();
        final String[] buses = input.get(1).split(",");
        for (String s : buses) {
            if (s.equals("x")) {
                continue;
            }
            int bus = parseInt(s);
            int earliest = 0;
            while (earliest < initial) {
                earliest += bus;
            }
            integers.put(earliest - initial, bus);
        }
        final Integer min = Collections.min(integers.keySet());
        System.out.println(integers.get(min) * min);
    }

    private static void part2(List<String> input) {
        final List<Integer> x = new ArrayList<>();
        final List<Integer> y = new ArrayList<>();
        final String[] buses = input.get(1).split(",");
        for (int i = 0; i < buses.length; i++) {
            if (!buses[i].equals("x")) {
                final int val = parseInt(buses[i]);
                x.add(val);
                y.add(Math.floorMod(val - i, val));
            }
        }
        System.out.println(x);
        System.out.println(y);
        System.out.println(chineseRemainder(y.stream().mapToInt(i -> i).toArray(), x.stream().mapToInt(i -> i).toArray()));
    }

    // chinese remainder therom copy/pasta
    private static long chineseRemainder(int[] rem, int[] num) {
        long product = 1;
        for (int value : num) {
            product *= value;
        }

        long[] partialProduct = new long[num.length];
        long[] inverse = new long[num.length];
        long sum = 0;

        for (int i = 0; i < num.length; i++) {
            partialProduct[i] = product / num[i];//floor division
            inverse[i] = computeInverse(partialProduct[i], num[i]);
            sum += partialProduct[i] * inverse[i] * rem[i];
        }
        return sum % product;
    }

    private static long computeInverse(long a, long b) {
        long m = b, t, q;
        long x = 0, y = 1;

        if (b == 1)
            return 0;

        // Apply extended Euclid Algorithm
        while (a > 1) {
            // q is quotient
            q = a / b;
            t = b;

            // m is remainder now, process
            // same as euclid's algo
            b = a % b;
            a = t;
            t = x;
            x = y - q * x;
            y = t;
        }
        // Make x1 positive
        if (y < 0)
            y += m;

        return y;
    }
}
