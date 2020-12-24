package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;
import org.javatuples.Pair;

import java.util.*;

import static java.util.Arrays.asList;

public class Day24 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day24");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final Map<Pair<Integer, Integer>, Boolean> hex = new HashMap<>();

        for (String line : input) {
            final char[] chars = line.toCharArray();
            int x = 0, y = 0;
            for (int i = 0; i < chars.length; i++) {
                final char aChar = chars[i];
                final String coor;
                if ((aChar == 's' || aChar == 'n') && i < chars.length + 1) {
                    coor = chars[i] + "" + chars[i + 1];
                    i++;
                } else {
                    coor = chars[i] + "";
                }
                switch (coor) {
                    case "se":
                        x -= 1;
                        y += 1;
                        break;
                    case "sw":
                        x -= 1;
                        break;
                    case "ne":
                        x += 1;
                        break;
                    case "nw":
                        x += 1;
                        y -= 1;
                        break;
                    case "e":
                        y += 1;
                        break;
                    case "w":
                        y -= 1;
                        break;
                }
            }
            if (hex.containsKey(Pair.with(x, y))) {
                hex.put(Pair.with(x, y), !hex.get(Pair.with(x, y)));
            } else {
                hex.put(Pair.with(x, y), false);
            }
        }


        int count = 0;
        for (Boolean value : hex.values()) {
            if (!value) count++;
        }

        System.out.println(count);
    }

    private static void part2(List<String> input) {
        final Map<Pair<Integer, Integer>, Boolean> hex = new HashMap<>();

        for (String line : input) {
            final char[] chars = line.toCharArray();
            int x = 0, y = 0;
            for (int i = 0; i < chars.length; i++) {
                final char aChar = chars[i];
                final String coor;
                if ((aChar == 's' || aChar == 'n') && i < chars.length + 1) {
                    coor = chars[i] + "" + chars[i + 1];
                    i++;
                } else {
                    coor = chars[i] + "";
                }
                switch (coor) {
                    case "se":
                        x -= 1;
                        y += 1;
                        break;
                    case "sw":
                        x -= 1;
                        break;
                    case "ne":
                        x += 1;
                        break;
                    case "nw":
                        x += 1;
                        y -= 1;
                        break;
                    case "e":
                        y += 1;
                        break;
                    case "w":
                        y -= 1;
                        break;
                }
            }
            if (hex.containsKey(Pair.with(x, y))) {
                hex.put(Pair.with(x, y), !hex.get(Pair.with(x, y)));
            } else {
                hex.put(Pair.with(x, y), false);
            }
        }

        Set<Pair<Integer, Integer>> blackTiles = new HashSet<>();
        for (Map.Entry<Pair<Integer, Integer>, Boolean> entry : hex.entrySet()) {
            if (!entry.getValue()) {
                blackTiles.add(entry.getKey());
            }
        }

        for (int i = 0; i < 100; i++) {
            final Map<Pair<Integer, Integer>, Integer> blackNeighborCount = new HashMap<>();
            for (Pair<Integer, Integer> pair : blackTiles) {
                blackNeighborCount.put(pair, 0);
            }
            for (Pair<Integer, Integer> pair : blackTiles) {
                final Pair<Integer, Integer> east = Pair.with(pair.getValue0(), pair.getValue1() + 1);
                final Pair<Integer, Integer> west = Pair.with(pair.getValue0(), pair.getValue1() - 1);
                final Pair<Integer, Integer> northEast = Pair.with(pair.getValue0() + 1, pair.getValue1());
                final Pair<Integer, Integer> northWest = Pair.with(pair.getValue0() + 1, pair.getValue1() - 1);
                final Pair<Integer, Integer> southEast = Pair.with(pair.getValue0() - 1, pair.getValue1() + 1);
                final Pair<Integer, Integer> southWest = Pair.with(pair.getValue0() - 1, pair.getValue1());
                final List<Pair<Integer, Integer>> neighborList = asList(east, west, northEast, northWest, southEast, southWest);
                for (Pair<Integer, Integer> coordinate : neighborList) {
                    if (blackNeighborCount.containsKey(coordinate)) {
                        blackNeighborCount.put(coordinate, blackNeighborCount.get(coordinate) + 1);
                    } else {
                        blackNeighborCount.put(coordinate, 1);
                    }
                }
            }
            final Set<Pair<Integer, Integer>> next = new HashSet<>();
            Set<Pair<Integer, Integer>> finalBlackTiles = blackTiles;
            blackNeighborCount.forEach((coordinate, value) -> {
                int count = blackNeighborCount.get(coordinate);
                if (finalBlackTiles.contains(coordinate) && (count == 1 || count == 2)) {
                    next.add(coordinate);
                } else if (count == 2) {
                    next.add(coordinate);

                }
            });
            System.out.println(i + " " + blackTiles.size());
            blackTiles = next;
        }

        System.out.println(blackTiles.size());
    }

}
