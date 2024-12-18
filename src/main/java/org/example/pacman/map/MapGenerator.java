package org.example.pacman.map;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MapGenerator {
    static public void generate(String filePath) throws IOException {
        List<List<String>> map = new ArrayList<>();
        File file = new File(filePath);
        Scanner sc = new Scanner(file);
        int rowCounter = 0, colCounter = 0;
        while (sc.hasNextLine()) {
            colCounter = 0;
            List<String> tmpList = new ArrayList<>();
            String[] lineParsed = sc.nextLine().split(",");
            for (String tmpString : lineParsed) {
                tmpList.add(tmpString.trim());
                colCounter++;
            }
            rowCounter++;
            map.add(tmpList);
        }
        int[][] newMap = new int[rowCounter][colCounter];
        for (int i = 0; i < rowCounter; i++) {
            for (int j = 0; j < colCounter; j++) {
                if(map.get(i).get(j).equals("-1")){
                    newMap[i][j] = -1;
                }
                else if (map.get(i).get(j).equals("256")) {
                    newMap[i][j] = 256;
                }
                else {
                    if (j > 0 && map.get(i).get(j - 1).equals("256")) {
                        newMap[i][j] += 1;
                    }
                    if (j < colCounter - 1 && map.get(i).get(j + 1).equals("256")) {
                        newMap[i][j] += 2;
                    }
                    if (i > 0 && map.get(i - 1).get(j).equals("256")) {
                        newMap[i][j] += 4;
                    }
                    if (i < rowCounter - 1 && map.get(i + 1).get(j).equals("256")) {
                        newMap[i][j] += 8;
                    }
                    if ((int) (Math.random() * 100) % 10 == 0)
                        newMap[i][j] += 32;
                    else {
                        newMap[i][j] += 16;
                    }
                }
            }
        }
        try {
            FileWriter myWriter = new FileWriter("src\\main\\resources\\map.txt");
            for (int i = 0; i < rowCounter; i++) {
                for (int j = 0; j < colCounter; j++) {
                    myWriter.write(String.format("%3s", newMap[i][j]) + ",");
                }
                myWriter.write("\n");
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }
}
