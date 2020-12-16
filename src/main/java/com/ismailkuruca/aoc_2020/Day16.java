package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.*;

public class Day16 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day16");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        List<List<Range>> rules = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final String name = input.get(i).split(":")[1];
            final String[] ranges = input.get(i).split(":")[1].split("or");
            final List<Range> rule = new ArrayList<>();
            rule.add(new Range(Integer.parseInt(ranges[0].trim().split("-")[0]), Integer.parseInt(ranges[0].trim().split("-")[1]), name));
            rule.add(new Range(Integer.parseInt(ranges[1].trim().split("-")[0]), Integer.parseInt(ranges[1].trim().split("-")[1]), name));
            rules.add(rule);
        }

        int errorRate = 0;

        for (int i = 25; i < input.size(); i++) {
            final String[] in = input.get(i).split(",");
            boolean validTicket = true;
            for (String s : in) {
                final int val = Integer.parseInt(s);
                boolean valid = false;
                for (List<Range> rule : rules) {
                    for (Range range : rule) {
                        if (range.in(val)) {
                            valid = true;
                            break;
                        }
                    }
                }
                if (!valid) errorRate += val;
                validTicket = validTicket && valid;
            }

        }
        System.out.println(errorRate);
    }

    private static void part2(List<String> input) {
        List<List<Range>> rules = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            final String name = input.get(i).split(":")[0].trim();
            final String[] ranges = input.get(i).split(":")[1].split("or");
            final List<Range> rule = new ArrayList<>();
            rule.add(new Range(Integer.parseInt(ranges[0].trim().split("-")[0]), Integer.parseInt(ranges[0].trim().split("-")[1]), name));
            rule.add(new Range(Integer.parseInt(ranges[1].trim().split("-")[0]), Integer.parseInt(ranges[1].trim().split("-")[1]), name));
            rules.add(rule);
        }

        int errorRate = 0;
        final List<String> valids = new ArrayList<>();

        for (int i = 25; i < input.size(); i++) {
            final String[] in = input.get(i).split(",");
            boolean validTicket = true;
            for (String s : in) {
                final int val = Integer.parseInt(s);
                boolean valid = false;
                for (List<Range> rule : rules) {
                    for (Range range : rule) {
                        if (range.in(val)) {
                            valid = true;
                            break;
                        }
                    }
                }
                if (!valid) errorRate += val;
                validTicket = validTicket && valid;
            }
            if (validTicket) {
                valids.add(input.get(i));
            }

        }

        Map<Integer, Set<String>> possibilities = new HashMap<>();
        Map<Integer, Set<String>> impossibilities = new HashMap<>();

        for (int i = 0; i < valids.size(); i++) {
            final String[] values = valids.get(i).split(",");
            for (int j = 0; j < values.length; j++) {
                possibilities.putIfAbsent(j, new HashSet<>());
                impossibilities.putIfAbsent(j, new HashSet<>());
                final int value = Integer.parseInt(values[j]);

                for (List<Range> rule : rules) {
                    if (rule.get(0).in(value) || rule.get(1).in(value)) {
                        possibilities.get(j).add(rule.get(0).name);
                    } else {
                        impossibilities.get(j).add(rule.get(0).name);
                    }
                }
            }
        }

        for (Integer integer : possibilities.keySet()) {
            final Set<String> impos = impossibilities.get(integer);
            final Set<String> pos = possibilities.get(integer);
            pos.removeAll(impos);
        }

        Map<Integer, String> cols = new HashMap<>();

        for (int k = 0; k < possibilities.size(); k++) {
            for (Integer integer : possibilities.keySet()) {
                if (possibilities.get(integer).size() == 1) {
                    final String name = possibilities.get(integer).iterator().next();
                    cols.putIfAbsent(integer, name);

                    for (Integer integer1 : possibilities.keySet()) {
                        final Set<String> set = possibilities.get(integer1);
                        if (set.size() != 1) {
                            set.remove(name);
                        }
                    }
                }
            }
        }
        long val = 1;
        for (Integer integer : cols.keySet()) {
            if (cols.get(integer).startsWith("depart")) {
                int row = Integer.parseInt(input.get(22).split(",")[integer]);
                System.out.println(cols.get(integer) + " " + row);
                val *= row;
            }
        }
        System.out.println(val);
    }


}

class Range {
    int x;
    int y;
    String name;

    public Range(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public boolean in(int z) {
        return z >= x && z <= y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
