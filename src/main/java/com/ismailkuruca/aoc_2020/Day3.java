package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;

public class Day3 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day3");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        System.out.println(traverse(input, 1, 3));
    }

    private static void part2(List<String> input) {
        System.out.println((long) traverse(input, 1, 1) * traverse(input, 1, 3) * traverse(input, 1, 5) * traverse(input, 1, 7) * traverse(input, 2, 1));

    }

    private static int traverse(List<String> input, int down, int right) {
        int posx = 0, posy = 0;
        int trees = 0;
        while (posy < input.size() - 1) {
            posx = (posx + right) % (input.get(0).length());
            posy += down;
            if (input.get(posy).charAt(posx) == '#') trees++;
        }
        return trees;
    }

}
