package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;

public class Day14 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day14");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        Map<Long, Long> memory =
                new HashMap<>();
        long ormask = 0, andmask = 0;
        for (int i = 0; i < input.size(); i++) {
            final String ins = input.get(i);
            if (ins.startsWith("mask")) {
                ormask = Long.parseLong(ins.split("=")[1].trim().replaceAll("X", "0"), 2);
                andmask = Long.parseLong(ins.split("=")[1].trim().replaceAll("X", "1"), 2);
            } else {
                final long loc = Integer.parseInt(ins.split("=")[0].trim().split("]")[0].substring(4));
                final long val = Long.parseLong(ins.split("=")[1].trim());
                memory.put(loc, (val | ormask) & andmask);
            }
        }

        final Optional<Long> reduce = memory.values().stream().reduce(Long::sum);
        System.out.println(reduce.get());
    }

    private static void part2(List<String> input) {
        Map<Long, Long> memory =
                new HashMap<>();

        String mask = "";

        for (int i = 0; i < input.size(); i++) {
            List<String> poss = new ArrayList<>();
            final String ins = input.get(i);
            if (ins.startsWith("mask")) {
                mask = ins.split("=")[1].trim();
            } else {
                String paddedLoc = Integer.toBinaryString(Integer.parseInt(ins.split("=")[0].trim().split("]")[0].substring(4)));
                final long val = Long.parseLong(ins.split("=")[1].trim());

                while (paddedLoc.length() < 36) {
                    paddedLoc = "0" + paddedLoc;
                }
                final List<Integer> index = new ArrayList<>();
                for (int j = 0; j < mask.toCharArray().length; j++) {
                    if (mask.toCharArray()[j] == 'X') {
                        index.add(j);
                    } else if (mask.toCharArray()[j] == '1') {
                        paddedLoc = new StringBuilder(paddedLoc).replace(j, j + 1, mask.charAt(j) + "").toString();
                    }
                }

                poss.add(paddedLoc);

                for (Integer integer : index) {
                    final List<String> interm = new ArrayList<>();
                    for (String strings : poss) {
                        interm.add(new StringBuilder(strings).replace(integer, integer + 1, "0").toString());
                        interm.add(new StringBuilder(strings).replace(integer, integer + 1, "1").toString());
                    }
                    poss = interm;
                }

                for (String strings : poss) {
                    final long address = Long.parseLong(strings, 2);
                    memory.put(address, val);
                }

            }
        }

        final Optional<Long> reduce = memory.values().stream().reduce(Long::sum);
        System.out.println(reduce.get());
    }

}
