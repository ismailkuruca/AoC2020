package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class Day4 {
    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day4");

        part1(input);
        part2(input);
    }

    private static List<Map<String, String>> extract(List<String> input) {
        List<Map<String, String>> passports = new ArrayList<>();
        Map<String, String> passport = new HashMap();
        for (int i = 0; i < input.size(); i++) {
            final String s = input.get(i);
            if (s.isEmpty()) {
                passports.add(passport);
                passport = new HashMap();
                continue;
            }
            for (String fields : s.split(" ")) {
                passport.put(fields.split(":")[0], fields.split(":")[1]);
            }
        }
        passports.add(passport);
        return passports;
    }

    private static void part1(List<String> input) {
        final List<Map<String, String>> passports = extract(input);

        int valid = 0;
        for (Map map : passports) {
            if (map.containsKey("byr") &&
                    map.containsKey("iyr") &&
                    map.containsKey("eyr") &&
                    map.containsKey("hgt") &&
                    map.containsKey("ecl") &&
                    map.containsKey("hcl") &&
                    map.containsKey("pid")) {
                valid++;
            }
        }
        System.out.println(valid);
    }

    private static void part2(List<String> input) {
        final List<Map<String, String>> passports = extract(input);
        int valid = 0;
        for (Map<String, String> map : passports) {
            if (map.containsKey("byr") && parseInt(map.get("byr")) <= 2002 && parseInt(map.get("byr")) >= 1920 &&
                    map.containsKey("iyr") && parseInt(map.get("iyr")) <= 2020 && parseInt(map.get("iyr")) >= 2010 &&
                    map.containsKey("eyr") && parseInt(map.get("eyr")) <= 2030 && parseInt(map.get("eyr")) >= 2020 &&
                    map.containsKey("hgt") && validHgt(map.get("hgt")) &&
                    map.containsKey("ecl") && validEcl(map.get("ecl")) &&
                    map.containsKey("hcl") && validColor(map.get("hcl")) &&
                    map.containsKey("pid") && validPid(map.get("pid"))) {
                valid++;
            }
        }
        System.out.println(valid);
    }

    private static boolean validPid(String pid) {
        try {
            return pid.length() == 9 && parseInt(pid) > 0;
        } catch (Exception e) {
            return false;
        }

    }

    private static boolean validEcl(String ecl) {
        return ecl.equals("amb") || ecl.equals("blu") || ecl.equals("brn") || ecl.equals("gry") || ecl.equals("grn") || ecl.equals("hzl") || ecl.equals("oth");
    }

    private static boolean validHgt(String hgt) {
        try {
            final String metric = hgt.charAt(hgt.length() - 2) + "" + hgt.charAt(hgt.length() - 1);
            final int size = parseInt(hgt.substring(0, hgt.length() - 2));
            if (metric.equals("cm")) {
                return size >= 150 && size <= 193;
            } else {
                return size >= 59 && size <= 76;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean validColor(String hcl) {
        return hcl.charAt(0) == '#' && Pattern.compile("[0-9a-f]{6}").matcher(hcl.substring(1)).matches();
    }
}

/*
byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)
 */
