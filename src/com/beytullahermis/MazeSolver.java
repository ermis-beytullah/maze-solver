package com.beytullahermis;

import java.io.*;

public class MazeSolver {

    private char[][] newMaze;

    private int height;
    private int width;
    private int start_X;
    private int start_Y;
    private int end_X;
    private int end_Y;

    public MazeSolver(String inputName) {

        String fileName;
        fileName = inputName;

        buildMaze(fileName);
        newFormat();

        if(solve(start_X, start_Y)) {
            printMaze();
        } else {
            System.out.println("This maze does not have a solution.");
        }
    }

    private void buildMaze(String file) {

        BufferedReader bufferedReader;
        String line;
        int count = 1;
        int heightCounter = 0;

        try {
            bufferedReader = new BufferedReader(new FileReader(file));

            while((line = bufferedReader.readLine()) != null) {
                switch (count) {
                    case (1):
                        width = Integer.parseInt(line.substring(0, line.indexOf(' ')));
                        height = Integer.parseInt((line.substring(line.indexOf(' ') + 1)));
                        newMaze = new char[height][width];
                        break;

                    case (2):
                        start_Y = Character.getNumericValue(line.charAt(0));
                        start_X = Character.getNumericValue(line.charAt(2));
                        break;

                    case (3):
                        end_Y = Integer.parseInt(line.substring(0, line.indexOf(' ')));
                        end_X = Integer.parseInt((line.substring(line.indexOf(' ') + 1)));
                        break;

                    default:
                        int counter = 0;

                        for (int i = 0; i < line.length(); i++){
                            if(line.charAt(i) != ' '){
                                newMaze[heightCounter][counter] = line.charAt(i);
                                counter++;
                            }
                        }
                        heightCounter++;
                        break;
                }
                count++;
            }
        }
        catch(Exception ex) {
            ex.getLocalizedMessage();
        }
    }

    private void newFormat() {

        // Start position marked with S
        newMaze[start_X][start_Y] = 'S';

        // End position marked with E
        newMaze[end_X][end_Y] = 'E';

        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {

                // Walls are marked with #
                if(newMaze[i][j] == '1') {
                    newMaze[i][j] = '#';
                }

                // Passages marked with ' '
                if(newMaze[i][j] == '0') {
                    newMaze[i][j] = ' ';
                }
            }
        }
    }

    private boolean solve(int i, int j) {

        if (newMaze[i][j] == '#') {
            return false;
        }

        if (newMaze[i][j] == 'X') {
            return false;
        }

        if (newMaze[i][j] == 'E') {
            return true;
        }

        newMaze[i][j] = 'X';

        // Moving in North direction
        if ((solve(i , j + 1))) {
            return true;
        }

        // Moving in East direction
        if ((solve(i + 1, j))) {
            return true;
        }

        // Moving in South direction
        if ((solve(i, j - 1))) {
            return true;
        }

        // Moving in West direction
        if ((solve(i - 1 , j))) {
            return true;
        }

        newMaze[i][j] = ' ';
        return false;
    }

    private void printMaze() {
        newMaze[start_X][start_Y] = 'S';
        for(int i = 0; i < newMaze.length; i++) {
            System.out.println(newMaze[i]);
        }
    }
}
