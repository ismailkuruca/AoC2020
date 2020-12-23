package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Day23 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day23");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final List<Integer> list = Arrays.stream(input.get(0).split("")).map(Integer::parseInt).collect(Collectors.toList());

        LinkedList prev = null;
        final Map<Integer, LinkedList> all = new HashMap<>();

        int currentIndex = -1;
        int highest = 9;
        for (int value : list) {
            LinkedList node = new LinkedList(value);
            if (prev == null) {
                currentIndex = value;
            } else {
                prev.insert(node);
            }
            prev = node;
            all.put(value, node);
        }

        for (int i = 0; i < 100; i++) {
            final LinkedList current = all.get(currentIndex);
            final List<LinkedList> removed = asList(current.next.remove(), current.next.remove(), current.next.remove());
            int destinationIndex = currentIndex - 1 == 0 ? highest : currentIndex - 1;

            LinkedList destination = all.get(destinationIndex);
            while (removed.contains(destination)) {
                destinationIndex = destinationIndex - 1 == 0 ? highest : destinationIndex - 1;
                destination = all.get(destinationIndex);
            }
            Collections.reverse(removed);
            removed.forEach(destination::insert);
            currentIndex = current.next.value;
        }

        LinkedList cup = all.get(1).next;
        final StringBuilder output = new StringBuilder();
        while (!cup.value.equals(1)) {
            output.append(cup.value);
            cup = cup.next;
        }
        System.out.println(output);

    }

    private static void part2(List<String> input) {
        final List<Integer> list = Arrays.stream(input.get(0).split("")).map(Integer::parseInt).collect(Collectors.toList());

        LinkedList prev = null;
        final Map<Integer, LinkedList> all = new HashMap<>();

        int currentIndex = -1;
        int highest;

        for (int value : list) {
            final LinkedList node = new LinkedList(value);
            if (prev == null) {
                currentIndex = value;
            } else {
                prev.insert(node);
            }
            prev = node;
            all.put(value, node);
        }
        for (int i = 10; i <= 1000000; i++) {
            final LinkedList node = new LinkedList(i);
            prev.insert(node);
            prev = node;
            all.put(i, node);
        }
        highest = all.keySet().stream().max(Integer::compare).get();

        for (int i = 0; i < 10000000; i++) {
            final LinkedList current = all.get(currentIndex);
            final List<LinkedList> removed = asList(current.next.remove(), current.next.remove(), current.next.remove());

            int destinationIndex = currentIndex - 1;
            if (destinationIndex < 1) {
                destinationIndex = highest;
            }
            LinkedList destination = all.get(destinationIndex);
            while (removed.contains(destination)) {
                destinationIndex = destinationIndex - 1 == 0 ? highest : destinationIndex - 1;
                destination = all.get(destinationIndex);
            }
            Collections.reverse(removed);
            removed.forEach(destination::insert);
            currentIndex = current.next.value;
        }

        final LinkedList cup = all.get(1).next;
        System.out.println((long) cup.value * (long) cup.next.value);
    }
}
