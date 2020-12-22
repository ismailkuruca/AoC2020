package com.ismailkuruca.aoc_2020;

import com.ismailkuruca.aoc_2020.util.FileUtil;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleGraph;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.reverse;

public class Day20 {
    public static int gridSize = 12;

    public static void main(String[] args) {
        final List<String> input = FileUtil.readFile("day20");

        part1(input);
//        part2(input,1951, 3079);
        part2(input, 3539, 2693); // fix to find corners instead of hardcoding
    }

    static class Tile {
        String[][] content;
        String[][] manipulated;
        int id;
        List<String> edges;
        int rotations;
        boolean processed = false;
        int gridSize = 10;

        public Tile(String[][] content, int id) {
            this.id = id;
            this.content = content;
            this.manipulated = content;
            calculateEdges(content);
        }

        private void calculateEdges(String[][] content) {
            final String edge1 = String.join("", content[0]);
            final String edge2 = String.join("", Arrays.stream(content).map(o -> o[9]).toArray(String[]::new));
            final String edge3 = String.join("", content[9]);
            final String edge4 = String.join("", Arrays.stream(content).map(o -> o[0]).toArray(String[]::new));
            this.edges = asList(
                    edge1, edge2, edge3, edge4, reverse(edge1), reverse(edge2), reverse(edge3), reverse(edge4)
            );
        }

        void rotate(int amount) {
            if (processed) return;
            System.out.println("Rotating " + id + " by " + amount);
            if (amount != 0) {
                for (int i = 0; i < amount; i++) {
                    rotateClockWise();
                }
            }
            rotations += amount;
            calculateEdges(this.manipulated);
        }

        void flip(int direction) {
            if (processed) return;
            System.out.println("Flipping " + id);
            final String[][] flipped = new String[gridSize][gridSize];

            for (int i = 0; i < flipped.length; i++) {
                for (int j = 0; j < flipped.length; j++) {
                    if (direction == 1) {
                        flipped[i][j] = manipulated[i][gridSize - 1 - j];
                    } else {
                        flipped[i][j] = manipulated[gridSize - 1 - i][j];
                    }

                }
            }
            rotations += 2;
            manipulated = flipped;
            calculateEdges(manipulated);
        }

        public void print() {
            for (String[] strings : manipulated) {
                for (String string : strings) {
                    System.out.print(string);
                }
                System.out.println();
            }
            System.out.println();
        }

        public void rotateClockWise() {
            int size = manipulated.length;
            String[][] copy = new String[size][size];

            for (int i = 0; i < size; ++i)
                for (int j = 0; j < size; ++j)
                    copy[i][j] = manipulated[size - j - 1][i];

            this.manipulated = copy;
            calculateEdges(this.manipulated);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return id == tile.id &&
                    Arrays.equals(content, tile.content) &&
                    Objects.equals(edges, tile.edges);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(id, edges);
            result = 31 * result + Arrays.hashCode(content);
            return result;
        }
    }

    private static List<Tile> tiles(List<String> input) {
        Map<Integer, String[][]> tiles = new HashMap<>();

        int temp = 0;
        int current = 0;
        for (String in : input) {
            if (in.trim().equals("")) {
                temp = 0;
                continue;
            }

            if (in.startsWith("Tile")) {
                final int tile = Integer.parseInt(in.split(" ")[1].replaceFirst(":", ""));
                tiles.put(Integer.parseInt(in.split(" ")[1].replaceFirst(":", "")), new String[10][10]);
                current = tile;
                continue;
            }

            for (int i = 0; i < in.toCharArray().length; i++) {
                tiles.get(current)[temp][i] = in.toCharArray()[i] + "";
            }
            temp++;
        }

        final ArrayList<Tile> tileList = new ArrayList<>();
        tiles.forEach((integer, strings) -> tileList.add(new Tile(strings, integer)));
        gridSize = (int) Math.sqrt(tileList.size());
        return tileList;
    }

    private static int part1(List<String> input) {
        final List<Tile> tileList = tiles(input);

        Map<String, Integer> edgeMap = new HashMap<>();
        Map<Tile, Integer> occ = new HashMap<>();
        for (Tile tile : tileList) {
            for (String edge : tile.edges) {
                edgeMap.putIfAbsent(edge, 0);
                edgeMap.put(edge, edgeMap.get(edge) + 1);
            }
            for (Tile tile1 : tileList) {
                if (tile.id == tile1.id) continue;
                occ.putIfAbsent(tile, 0);
                occ.put(tile, occ.get(tile) + (Collections.disjoint(tile.edges, tile1.edges) ? 0 : 1));
            }
        }

        // 3709, 3539, 1549, 2693
        AtomicLong value = new AtomicLong(1L);
        AtomicInteger cornerTile = new AtomicInteger();
        occ.forEach((tile, integer) -> {
            if (integer == 2) {
                value.updateAndGet(v -> v * tile.id);
                cornerTile.set(tile.id);
            }
        });
        System.out.println(value);
        return cornerTile.get();
    }

    private static void part2(List<String> input, int leftCorner, int rightCorner) {
        final List<Tile> tiles = tiles(input);
        final int[][] board = new int[gridSize][gridSize];

        Set<Tile> processedTiles = new HashSet<>();
        Set<Tile> pendingTiles = new HashSet<>(tiles);

        Graph<Integer, SimpleEdge> g = new SimpleGraph<>(SimpleEdge.class);

        while (processedTiles.size() < gridSize * gridSize) {
            final Tile currentTile = pendingTiles.iterator().next();
            pendingTiles.remove(currentTile);
            for (Tile tile : tiles) {
                if (!Collections.disjoint(currentTile.edges, tile.edges) && currentTile.id != tile.id) {
                    for (int i = 0; i < currentTile.edges.size(); i++) {
                        for (int j = 0; j < tile.edges.size(); j++) {
                            if (currentTile.edges.get(i).equals(tile.edges.get(j))) {
                                System.out.println("Edge " + i + " of tile " + currentTile.id + " = " + " Edge " + j + " of tile " + tile.id);
                                g.addVertex(currentTile.id);
                                g.addVertex(tile.id);
                                g.addEdge(currentTile.id, tile.id);
                            }
                        }
                    }
                }
            }
            processedTiles.add(currentTile);
        }

        DijkstraShortestPath<Integer, SimpleEdge> dijkstraShortestPath
                = new DijkstraShortestPath<>(g);
        List<Integer> shortestPath = dijkstraShortestPath.getPath(leftCorner, rightCorner).getVertexList();

        for (int i = 0; i < gridSize; i++) {
            board[0][i] = shortestPath.get(i);
        }

        for (int i = 1; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                final int top = board[i - 1][j];
                final Set<Integer> neighbors = g.edgesOf(top).stream()
                        .map(simpleEdge -> asList((int) simpleEdge.getSource(), (int) simpleEdge.getTarget()))
                        .flatMap(Collection::stream)
                        .collect(Collectors.toSet());
                for (int[] ints : board) {
                    for (int anInt : ints) {
                        neighbors.remove(anInt);
                    }
                }
                board[i][j] = neighbors.iterator().next();
            }
        }

        final List<Tile> raw = Arrays.stream(board)
                .flatMap(ints -> Arrays.stream(ints).mapToObj(value -> tiles.stream().filter(tile -> tile.id == value).findFirst().get()))
                .collect(Collectors.toList());
        Tile first = raw.get(0);
        if (first.id == 1951) first.flip(1); // test input
        first.processed = true;
        first.print();

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int finalI = i;
                int finalJ = j;
                final Tile current = tiles.stream().filter(tile -> tile.id == board[finalI][finalJ]).findFirst().get();
                if (current.processed) continue;
                final Tile previous;
                boolean left = false;
                if (j == 0) { //look up
                    previous = tiles.stream().filter(tile -> tile.id == board[finalI - 1][finalJ]).findFirst().get();
                } else { //look left
                    left = true;
                    previous = tiles.stream().filter(tile -> tile.id == board[finalI][finalJ - 1]).findFirst().get();
                }
                boolean cont = true;
                for (int k = 0; k < current.edges.size() && cont; k++) {
                    String edge;
                    if (left) {
                        edge = previous.edges.get(1);
                    } else {
                        edge = previous.edges.get(2);
                    }
                    if (current.edges.get(k).equals(edge)) {
                        System.out.println("Edge " + k + " of tile " + current.id + " = " + " Edge of tile " + previous.id);
                        if (left) {
                            switch (k) {
                                case 0:
                                    current.flip(1);
                                    current.rotate(3);
                                    break;
                                case 1:
                                    current.flip(1);
                                    break;
                                case 2:
                                    current.rotate(1);
                                    break;
                                case 3:
                                    break;
                                case 4:
                                    current.rotate(3);
                                    break;
                                case 5:
                                    current.rotate(2);
                                    break;
                                case 6:
                                    current.flip(1);
                                    current.rotate(1);
                                    break;
                                case 7:
                                    current.flip(1);
                                    current.rotate(2);
                                    break;
                            }
                        } else {
                            switch (k) {
                                case 0:
                                    break;
                                case 1:
                                    current.rotate(3);
                                    break;
                                case 2:
                                    current.flip(1);
                                    current.rotate(2);
                                    break;
                                case 3:
                                    current.rotate(1);
                                    current.flip(1);
                                    break;
                                case 4:
                                    current.flip(1);
                                    break;
                                case 5:
                                    current.flip(1);
                                    current.rotate(1);
                                    break;
                                case 6:
                                    current.rotate(2);
                                    break;
                                case 7:
                                    current.rotate(1);
                                    break;
                            }
                        }

                        current.processed = true;
                        cont = false;
                        System.out.println();
                    }
                }
            }
        }

        final StringBuffer[] stringBuffers = new StringBuffer[gridSize * 10];
        for (int i = 0; i < gridSize * 10; i++) {
            stringBuffers[i] = new StringBuffer();
        }

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int finalI = i;
                int finalJ = j;
                final Tile tile1 = tiles.stream().filter(tile -> tile.id == board[finalI][finalJ]).findFirst().get();
                for (int x = 0; x < tile1.manipulated.length; x++) {
                    stringBuffers[i * 10 + x].append(String.join("", tile1.manipulated[x]));
                }

            }
        }
        for (StringBuffer stringBuffer : stringBuffers) {
            System.out.println(stringBuffer.toString());
        }
        System.out.println();

        final String[][] sea = new String[gridSize * 8][gridSize * 8];
        int row;
        int col = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                int finalI = i;
                int finalJ = j;
                String[][] grid = tiles.stream().filter(tile -> tile.id == board[finalI][finalJ]).findFirst().get().manipulated;
                row = i * 8;
                col = j * 8;
                for (int r = 1; r <= grid.length - 2; r++) {
                    for (int c = 1; c <= grid[0].length - 2; c++) {
                        sea[row][col] = grid[r][c];
                        col++;
                    }
                    row++;
                    col = j * 8;
                }
            }
        }

        String monster = "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   ";

        final Tile seas = new Tile(sea, 0);
        seas.gridSize = gridSize * 8;
        seas.print();
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.flip(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);
        seas.rotate(1);
        markSeaMonster(seas.manipulated);


        System.out.println("part2 " + roughness(seas));
    }

    private static int roughness(Tile seas) {
        int count = 0;
        String[][] finalState = seas.manipulated;
        for (String[] strings : finalState)
            for (String s : strings) {
                count += s.equals("#") ? 1 : 0;
            }
        return count;
    }

    private static void markSeaMonster(String[][] tile) {
        for (int row = 0; row < tile.length; row++) {
            for (int col = 0; col < tile[0].length; col++) {
                if (col + 19 < tile[0].length && row + 2 < tile.length)
                    if (tile[row][col + 18].equals("#")
                            && tile[row + 1][col].equals("#")
                            && tile[row + 2][col + 1].equals("#")
                            && tile[row + 2][col + 4].equals("#")
                            && tile[row + 1][col + 5].equals("#")
                            && tile[row + 1][col + 6].equals("#")
                            && tile[row + 2][col + 7].equals("#")
                            && tile[row + 2][col + 10].equals("#")
                            && tile[row + 1][col + 11].equals("#")
                            && tile[row + 1][col + 12].equals("#")
                            && tile[row + 2][col + 13].equals("#")
                            && tile[row + 2][col + 16].equals("#")
                            && tile[row + 1][col + 17].equals("#")
                            && tile[row + 1][col + 18].equals("#")
                            && tile[row + 1][col + 19].equals("#")) {

                        tile[row][col + 18] = "O";
                        tile[row + 1][col] = "O";
                        tile[row + 2][col + 1] = "O";
                        tile[row + 2][col + 4] = "O";
                        tile[row + 1][col + 5] = "O";
                        tile[row + 1][col + 6] = "O";
                        tile[row + 2][col + 7] = "O";
                        tile[row + 2][col + 10] = "O";
                        tile[row + 1][col + 11] = "O";
                        tile[row + 1][col + 12] = "O";
                        tile[row + 2][col + 13] = "O";
                        tile[row + 2][col + 16] = "O";
                        tile[row + 1][col + 17] = "O";
                        tile[row + 1][col + 18] = "O";
                        tile[row + 1][col + 19] = "O";
                    }
            }
        }

    }
}

