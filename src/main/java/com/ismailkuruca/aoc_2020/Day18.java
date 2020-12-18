package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.List;
import java.util.Stack;

public class Day18 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day18");

        part1(input);
        part2(input);
    }

    private static void part1(List<String> input) {
        System.out.println(input.stream()
                .map(Day18::evaluate)
                .reduce(Long::sum));
    }

    private static void part2(List<String> input) {
        System.out.println(input.stream()
                .map(Day18::evaluate2)
                .reduce(Long::sum));
    }

    // Modified code from a sample infix calculator  https://www.geeksforgeeks.org/expression-evaluation/
    private static Long evaluate2(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Long> values = new Stack<>();

        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Long.parseLong(sbuf.toString()));
                i--;
            } else if (tokens[i] == '(')
                ops.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            } else if (tokens[i] == '+' || tokens[i] == '*') {
                while (!ops.empty() && hasPrecedence2(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                ops.push(tokens[i]);
            }
        }
        while (!ops.empty()) values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    private static Long evaluate(String expression) {
        char[] tokens = expression.toCharArray();
        Stack<Long> values = new Stack<>();

        Stack<Character> ops = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;
            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder sbuf = new StringBuilder();
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Long.parseLong(sbuf.toString()));
                i--;
            } else if (tokens[i] == '(')
                ops.push(tokens[i]);
            else if (tokens[i] == ')') {
                while (ops.peek() != '(')
                    values.push(applyOp(ops.pop(),
                            values.pop(),
                            values.pop()));
                ops.pop();
            } else if (tokens[i] == '+' || tokens[i] == '*') {
                while (!ops.empty() && hasPrecedence1(tokens[i], ops.peek()))
                    values.push(applyOp(ops.pop(), values.pop(), values.pop()));

                ops.push(tokens[i]);
            }
        }
        while (!ops.empty()) values.push(applyOp(ops.pop(), values.pop(), values.pop()));

        return Long.valueOf(values.pop());
    }

    private static boolean hasPrecedence1(char op1, char op2) {
        return op2 != '(' && op2 != ')';
    }

    private static boolean hasPrecedence2(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        return (op1 != '+') || (op2 != '*');
    }


    private static Long applyOp(char op, Long b, Long a) {
        switch (op) {
            case '+':
                return a + b;
            case '*':
                return a * b;
        }
        return 0L;
    }

}
