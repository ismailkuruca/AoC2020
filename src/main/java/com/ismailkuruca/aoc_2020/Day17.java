package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;

public class Day17 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day17");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        String[][][] grid = new String[100][100][100];
        int offset = 50;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    grid[i][j][k] = ".";
                }
            }
        }

        for (int i = 0; i < input.size(); i++) {
            final String in = input.get(i);
            final char[] row = in.toCharArray();
            for (int j = 0; j < row.length; j++) {
                grid[offset][offset + i][offset + j] = row[j] + "";
            }
        }

        for (int iter = 0; iter < 6; iter++) {

            String[][][] copy = new String[100][100][100];
            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    for (int k = 0; k < 100; k++) {
                        copy[i][j][k] = grid[i][j][k];
                    }
                }
            }

            for (int i = 0; i < 100; i++) {
                for (int j = 0; j < 100; j++) {
                    for (int k = 0; k < 100; k++) {
                        final int activeNeighbors = getActiveNeighbors(grid, i, j, k);
                        if (grid[i][j][k].equals("#")) {
                            if (!(activeNeighbors == 2 || activeNeighbors == 3)) {
                                copy[i][j][k] = ".";
                            }
                        }
                        if (grid[i][j][k].equals(".")) {
                            if (activeNeighbors == 3) {
                                copy[i][j][k] = "#";
                            }
                        }
                    }
                }
            }
            grid = copy;
        }
        System.out.println(count(grid));

    }

    private static void part2(List<String> input) {
        String[][][][] grid = new String[40][40][40][40];
        int offset = 10;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                for (int k = 0; k < 40; k++) {
                    for (int l = 0; l < 40; l++) {
                        grid[i][j][k][l] = ".";
                    }
                }
            }
        }

        for (int i = 0; i < input.size(); i++) {
            final String in = input.get(i);
            final char[] row = in.toCharArray();
            for (int j = 0; j < row.length; j++) {
                grid[offset][offset][offset + i][offset + j] = row[j] + "";
            }
        }

        for (int iter = 0; iter < 6; iter++) {

            String[][][][] copy = new String[40][40][40][40];
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {
                    for (int k = 0; k < 40; k++) {
                        for (int l = 0; l < 40; l++) {
                            copy[i][j][k][l] = grid[i][j][k][l];
                        }

                    }
                }
            }
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {
                    for (int k = 0; k < 40; k++) {
                        for (int l = 0; l < 40; l++) {
                            final int activeNeighbors = getActiveNeighbors(grid, i, j, k, l);
                            if (grid[i][j][k][l].equals("#")) {
                                if (!(activeNeighbors == 2 || activeNeighbors == 3)) {
                                    copy[i][j][k][l] = ".";
                                }
                            }
                            if (grid[i][j][k][l].equals(".")) {
                                if (activeNeighbors == 3) {
                                    copy[i][j][k][l] = "#";
                                }
                            }
                        }

                    }
                }
            }
            grid = copy;
        }
        System.out.println(count(grid));
    }

    private static int getActiveNeighbors(String[][][] grid, int x, int y, int z) {
        int c = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    if (i == 0 && j == 0 && k == 0) continue;
                    c += getSafe(grid, x + i, y + j, z + k).equals("#") ? 1 : 0;
                }
            }
        }
        return c;

    }

    private static int getActiveNeighbors(String[][][][] grid, int x, int y, int z, int t) {
        int c = 0;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                for (int k = -1; k < 2; k++) {
                    for (int l = -1; l < 2; l++) {
                        if (i == 0 && j == 0 && k == 0 && l == 0) continue;
                        c += getSafe(grid, x + i, y + j, z + k, t + l).equals("#") ? 1 : 0;
                    }
                }
            }
        }
        return c;

    }

    private static String getSafe(String[][][] grid, int x, int y, int z) {
        try {
            return grid[x][y][z];
        } catch (Exception e) {
            return ".";
        }
    }

    private static String getSafe(String[][][][] grid, int x, int y, int z, int t) {
        try {
            return grid[x][y][z][t];
        } catch (Exception e) {
            return ".";
        }
    }

    private static int count(String[][][] grid) {
        int cou = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                for (int k = 0; k < 100; k++) {
                    if (grid[i][j][k].equals("#")) cou++;
                }
            }
        }

        return cou;
    }

    private static int count(String[][][][] grid) {
        int cou = 0;
        for (int i = 0; i < 40; i++) {
            for (int j = 0; j < 40; j++) {
                for (int k = 0; k < 40; k++) {
                    for (int t = 0; t < 40; t++) {
                        if (grid[i][j][k][t].equals("#")) cou++;
                    }
                }
            }
        }

        return cou;
    }


}
