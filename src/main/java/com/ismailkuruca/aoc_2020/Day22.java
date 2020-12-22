package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day22 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day22");
        List<Integer> player1 = input.subList(1, 26).stream().map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> player2 = input.subList(28, input.size()).stream().map(Integer::parseInt).collect(Collectors.toList());

        part1(new ArrayList<>(player1), new ArrayList<>(player2));
        part2(new ArrayList<>(player1), new ArrayList<>(player2));
    }

    private static void part1(List<Integer> player1, List<Integer> player2) {
        while (!player1.isEmpty() && !player2.isEmpty()) {
            final Integer p1 = player1.remove(0);
            final Integer p2 = player2.remove(0);

            if (p1 > p2) {
                player1.add(p1);
                player1.add(p2);
            } else {
                player2.add(p2);
                player2.add(p1);
            }
        }
        int score = 0;
        int j = 1;
        List<Integer> outcome = player1.isEmpty() ? player2 : player1;
        for (int i = outcome.size() - 1; i >= 0; i--) {
            score += outcome.get(i) * j;
            j++;
        }
        System.out.println(score);
    }

    private static boolean play(List<Integer> player1, List<Integer> player2) {
        Map<String, String> prev = new HashMap<>();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            final String p1t = player1.stream().map(Object::toString).collect(Collectors.joining(","));
            final String p2t = player2.stream().map(Object::toString).collect(Collectors.joining(","));
            if (prev.containsKey(p1t) || prev.containsValue(p2t)) {
                return true;
            }
            prev.put(p1t, p2t);

            final Integer p1 = player1.remove(0);
            final Integer p2 = player2.remove(0);
            boolean outcome;
            if (player1.size() < p1 || player2.size() < p2) {
                outcome = p1 > p2;
            } else {
                outcome = play(new ArrayList<>(player1.subList(0, p1)), new ArrayList<>(player2.subList(0, p2)));
            }

            if (outcome) {
                player1.add(p1);
                player1.add(p2);
            } else {
                player2.add(p2);
                player2.add(p1);
            }

        }
        return player2.isEmpty();
    }

    private static void part2(List<Integer> player1, List<Integer> player2) {
        final boolean play = play(player1, player2);
        List<Integer> outcome = play ? player1 : player2;
        int score = 0;
        int j = 1;
        for (int i = outcome.size() - 1; i >= 0; i--) {
            score += outcome.get(i) * j;
            j++;
        }
        System.out.println(score);
    }
}
