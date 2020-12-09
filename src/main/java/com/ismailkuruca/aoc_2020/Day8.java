package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day8");
        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        int acc = 0;
        int reg = 0;
        List<Integer> prev = new ArrayList<>();
        while (true) {
            if (prev.contains(reg)) {
                break;
            }
            prev.add(reg);
            final String[] instr = input.get(reg).split(" ");
            final String type = instr[0];
            final Integer arg = Integer.parseInt(instr[1]);

            if (type.equals("jmp")) {
                reg += arg;
                continue;
            }
            if (type.equals("acc")) {
                acc += arg;
            }
            reg++;
        }
        System.out.println(acc);
    }

    private static void part2(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final List<String> copy = new ArrayList<>(input);
            final String[] instr = copy.get(i).split(" ");
            final String type = instr[0];
            final int arg = Integer.parseInt(instr[1]);

            if (type.equals("jmp")) {
                copy.set(i, "nop " + arg);
            } else if (type.equals("nop")) {
                copy.set(i, "jmp " + arg);
            }

            try {
                System.out.println(run(copy));
            } catch (Exception e) {
            }
        }
    }

    private static int run(List<String> input) {
        int acc = 0;
        int reg = 0;
        List<Integer> prev = new ArrayList<>();
        while (true) {
            if (prev.contains(reg)) {
                throw new RuntimeException();
            }
            if (reg == input.size()) {
                return acc;
            }
            prev.add(reg);
            final String[] instr = input.get(reg).split(" ");
            final String type = instr[0];
            final Integer arg = Integer.parseInt(instr[1]);

            if (type.equals("jmp")) {
                reg += arg;
                continue;
            }
            if (type.equals("acc")) {
                acc += arg;
            }
            reg++;
        }
    }
}
