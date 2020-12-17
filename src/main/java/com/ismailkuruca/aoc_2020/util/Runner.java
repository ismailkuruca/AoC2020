package com.ismailkuruca.aoc_2020.util;

import com.ismailkuruca.aoc_2020.*;
import org.apache.commons.lang3.time.StopWatch;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Runner {

    private static List days = asList(
            new Day1(),
            new Day2(),
            new Day3(),
            new Day4(),
            new Day5(),
            new Day6(),
            new Day7(),
            new Day8(),
            new Day9(),
            new Day10(),
            new Day11(),
            new Day12(),
            new Day13(),
            new Day14(),
            new Day15(),
            new Day16()
    );

    public static void main(String[] args) {

        final List<Long> runtimes = new ArrayList<>();

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Day1.main(null);
        stopWatch.stop();
        runtimes.add(stopWatch.getNanoTime());


        final StopWatch stopWatch2 = new StopWatch();
        stopWatch2.start();
        Day2.main(null);
        stopWatch2.stop();
        runtimes.add(stopWatch2.getNanoTime());

        final StopWatch stopWatch3 = new StopWatch();
        stopWatch3.start();
        Day3.main(null);
        stopWatch3.stop();
        runtimes.add(stopWatch3.getNanoTime());

        final StopWatch stopWatch4 = new StopWatch();
        stopWatch4.start();
        Day4.main(null);
        stopWatch4.stop();
        runtimes.add(stopWatch4.getNanoTime());

        final StopWatch stopWatch5 = new StopWatch();
        stopWatch5.start();
        Day5.main(null);
        stopWatch5.stop();
        runtimes.add(stopWatch5.getNanoTime());

        final StopWatch stopWatch6 = new StopWatch();
        stopWatch6.start();
        Day6.main(null);
        stopWatch6.stop();
        runtimes.add(stopWatch6.getNanoTime());

        final StopWatch stopWatch7 = new StopWatch();
        stopWatch7.start();
        Day7.main(null);
        stopWatch7.stop();
        runtimes.add(stopWatch7.getNanoTime());

        final StopWatch stopWatch8 = new StopWatch();
        stopWatch8.start();
        Day8.main(null);
        stopWatch8.stop();
        runtimes.add(stopWatch8.getNanoTime());

        final StopWatch stopWatch9 = new StopWatch();
        stopWatch9.start();
        Day9.main(null);
        stopWatch9.stop();
        runtimes.add(stopWatch9.getNanoTime());

        final StopWatch stopWatch10 = new StopWatch();
        stopWatch10.start();
        Day10.main(null);
        stopWatch10.stop();
        runtimes.add(stopWatch10.getNanoTime());

        final StopWatch stopWatch11 = new StopWatch();
        stopWatch11.start();
        Day11.main(null);
        stopWatch11.stop();
        runtimes.add(stopWatch11.getNanoTime());

        final StopWatch stopWatch12 = new StopWatch();
        stopWatch12.start();
        Day12.main(null);
        stopWatch12.stop();
        runtimes.add(stopWatch12.getNanoTime());

        final StopWatch stopWatch13 = new StopWatch();
        stopWatch13.start();
        Day13.main(null);
        stopWatch13.stop();
        runtimes.add(stopWatch13.getNanoTime());

        final StopWatch stopWatch14 = new StopWatch();
        stopWatch14.start();
        Day14.main(null);
        stopWatch14.stop();
        runtimes.add(stopWatch14.getNanoTime());

        final StopWatch stopWatch15 = new StopWatch();
        stopWatch15.start();
        Day15.main(null);
        stopWatch15.stop();
        runtimes.add(stopWatch15.getNanoTime());

        final StopWatch stopWatch16 = new StopWatch();
        stopWatch16.start();
        Day16.main(null);
        stopWatch16.stop();
        runtimes.add(stopWatch16.getNanoTime());

        for (int i = 0; i < runtimes.size(); i++) {
            System.out.println("Day " + (i + 1) + " -> " + runtimes.get(i) /1000 + " us, " + runtimes.get(i)/1000000 + " ms");
        }
    }
}
