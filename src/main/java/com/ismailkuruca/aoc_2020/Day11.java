package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;

public class Day11 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day11");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        final char[][] board = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            final char[] chars = line.toCharArray();
            System.arraycopy(input.get(i).toCharArray(), 0, board[i], 0, chars.length);
        }

        char[][] state = copy(board);
        while (true) {
            boolean changed = false;
            final char[][] newState = copy(state);
            int cl = board[0].length;
            int rl = board.length;
            for (int i = 0; i < rl; i++) {
                for (int j = 0; j < cl; j++) {
                    int occupied = 0;
                    occupied += i != 0 && state[i - 1][j] == '#' ? 1 : 0;
                    occupied += i != 0 && j != 0 && state[i - 1][j - 1] == '#' ? 1 : 0;
                    occupied += i != 0 && j + 1 != cl && state[i - 1][j + 1] == '#' ? 1 : 0;

                    occupied += j != 0 && state[i][j - 1] == '#' ? 1 : 0;
                    occupied += j + 1 != cl && state[i][j + 1] == '#' ? 1 : 0;

                    occupied += i + 1 != rl && j != 0 && state[i + 1][j - 1] == '#' ? 1 : 0;
                    occupied += i + 1 != rl && state[i + 1][j] == '#' ? 1 : 0;
                    occupied += i + 1 != rl && j + 1 != cl && state[i + 1][j + 1] == '#' ? 1 : 0;
                    if (state[i][j] == 'L') {
                        if (occupied == 0) {
                            newState[i][j] = '#';
                            changed = true;
                        }

                    } else if (state[i][j] == '#') {
                        if (occupied >= 4) {
                            newState[i][j] = 'L';
                            changed = true;
                        }
                    }
                }
            }
            state = copy(newState);
            if (!changed) {
                System.out.println(count(newState));
                break;
            }
        }

    }

    private static char[][] copy(char[][] board) {
        char[][] myInt = new char[board.length][];
        for (int i = 0; i < board.length; i++) {
            char[] aMatrix = board[i];
            int aLength = aMatrix.length;
            myInt[i] = new char[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    private static int count(char[][] board) {
        int counter = 0;
        for (char[] chars : board) {
            for (char aChar : chars) {
                if (aChar == '#') {
                    counter++;
                }
            }
        }
        return counter;
    }

    private static void print(char[][] board) {
        for (char[] chars : board) {
            for (char aChar : chars) {
                System.out.print(aChar);
            }
            System.out.println();
        }
        System.out.println();
    }


    private static void part2(List<String> input) {
        final char[][] board = new char[input.size()][input.get(0).length()];

        for (int i = 0; i < input.size(); i++) {
            final String line = input.get(i);
            final char[] chars = line.toCharArray();
            System.arraycopy(input.get(i).toCharArray(), 0, board[i], 0, chars.length);
        }

        char[][] state = copy(board);
        while (true) {
            boolean changed = false;
            final char[][] newState = copy(state);
            int cl = board[0].length;
            int rl = board.length;
            for (int i = 0; i < rl; i++) {
                for (int j = 0; j < cl; j++) {
                    int occupied = 0;
                    occupied += check(state, i - 1, j - 1, -1, -1);
                    occupied += check(state, i - 1, j, -1, 0);
                    occupied += check(state, i - 1, j + 1, -1, 1);

                    occupied += check(state, i, j - 1, 0, -1);
                    occupied += check(state, i, j + 1, 0, 1);

                    occupied += check(state, i + 1, j - 1, 1, -1);
                    occupied += check(state, i + 1, j, 1, 0);
                    occupied += check(state, i + 1, j + 1, 1, 1);
                    if (state[i][j] == 'L') {
                        if (occupied == 0) {
                            newState[i][j] = '#';
                            changed = true;
                        }

                    } else if (state[i][j] == '#') {
                        if (occupied >= 5) {
                            newState[i][j] = 'L';
                            changed = true;
                        }
                    }
                }
            }
            state = copy(newState);
            if (!changed) {
                System.out.println(count(newState));
                break;
            }
        }
    }

    private static int check(char[][] board, int i, int j, int diffi, int diffj) {
        if (i < 0 || i == board.length || j < 0 || j == board[i].length || board[i][j] == 'L') return 0;
        if (board[i][j] == '#') return 1;
        return check(board, i + diffi, j + diffj, diffi, diffj);
    }
}
