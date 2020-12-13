package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;

public class Day12 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day12");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        int direction = 0, x = 0, y = 0;
        for (String line : input) {
            char inst = line.toCharArray()[0];
            int val = Integer.parseInt(line.substring(1));

            switch (inst) {
                case 'L': {
                    direction -= val;
                    direction += 360;
                    direction %= 360;
                    break;
                }
                case 'R': {
                    direction += val;
                    direction += 360;
                    direction %= 360;
                    break;
                }
                case 'N': {
                    y += val;
                    break;
                }
                case 'E': {
                    x += val;
                    break;
                }
                case 'S': {
                    y -= val;
                    break;
                }
                case 'W': {
                    x -= val;
                    break;
                }
                case 'F': {
                    if (direction == 0) {
                        x += val;
                    } else if (direction == 90) {
                        y -= val;
                    } else if (direction == 180) {
                        x -= val;
                    } else if (direction == 270) {
                        y += val;
                    }
                    break;
                }
            }
        }

        System.out.println(Math.abs(x) + Math.abs(y));
    }

    private static void part2(List<String> input) {
        int direction = 0, x = 0, y = 0, wx = 10, wy = 1;
        for (String line : input) {
            char inst = line.toCharArray()[0];
            int val = Integer.parseInt(line.substring(1));

            switch (inst) {
                case 'L': {
                    for (int i = 0; i < val / 90; i++) {
                        int temp = wx;
                        wx = wy * -1;
                        wy = temp;
                    }
                    break;
                }
                case 'R': {
                    for (int i = 0; i < val / 90; i++) {
                        int temp = wy;
                        wy = wx * -1;
                        wx = temp;
                    }
                    break;
                }
                case 'N': {
                    wy += val;
                    break;
                }
                case 'E': {
                    wx += val;
                    break;
                }
                case 'S': {
                    wy -= val;
                    break;
                }
                case 'W': {
                    wx -= val;
                    break;
                }
                case 'F': {
                    x = x + wx * val;
                    y = y + wy * val;
                    break;
                }
            }
        }

        System.out.println(Math.abs(x) + Math.abs(y));
    }
}
