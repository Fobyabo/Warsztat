package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class TaskManager {
    public static void main(String[] args) {
      String[] arr = loadingData();
          selectOption();
        while (true) {
          Scanner scanner = new Scanner(System.in);
          String line = scanner.nextLine();
          switch (line) {
              case ("add"):
                 arr = add(arr);
                  selectOption();
                  break;
              case ("remove"):
                 arr =  remove(arr);
                  selectOption();
                  break;
              case ("list"):
                  list(arr);
                  selectOption();
                  break;
          }
          if (line.equals("exit")) {
              uploadData(arr);
              System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);
              break;
          }
      }
    }

    public static void selectOption() {
        System.out.println(ConsoleColors.BLUE + "Please select the option:" + ConsoleColors.RESET);
        String[] options = new String[]{"add", "remove", "list", "exit"};
        for (int i = 0; i < options.length; i++) {
            System.out.println(options[i]);
        }
    }

    public static String[] loadingData() {
        Path path = Paths.get("C:\\Users\\Arkadii\\IdeaProjects\\Warsztat1\\tasks.csv");
        ArrayList<String> tasks = new ArrayList<>();
        try {
            tasks.addAll(Files.readAllLines(path));
        } catch (IOException e) {
        e.printStackTrace();
        }
        String[] result = new String[tasks.size()];
        for (int i = 0; i < result.length; i++) {
                result[i] = tasks.get(i);
        }
        return result;
    }

    public static String[] add(String[] arr){
        System.out.println("Please add task description: ");
        Scanner scanner = new Scanner(System.in);
        String line1 = scanner.nextLine();
        System.out.println("Please add task due data: ");
        String line2 = scanner.nextLine();
        System.out.println("Is your task is important: true/false");
        String line3 =scanner.nextLine();
        String result = line1 + " " + line2 + " " + line3;
            arr = Arrays.copyOf(arr, arr.length+1);
            arr[arr.length-1] = result;
            return arr;
    }

    public static void list(String [] arr){
        for (int i = 0; i < arr.length; i++) {
                System.out.println(i + ": " + arr[i]);
        }
    }

    public static String[] remove(String[] arr){
        System.out.println("Input an ordinal number that you want remove: ");
        Scanner scanner1 = new Scanner(System.in);
        while (true) {
            String line = scanner1.nextLine();
            if (NumberUtils.isParsable(line)&&Integer.parseInt(line)>-1) {
                try {
                    arr = ArrayUtils.remove(arr, Integer.parseInt(line));
                    System.out.println("Successfully deleted!");
                    System.out.println(" ");
                    return arr;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Chose correct ordinal number of list.");
                }
            }else System.out.println("Incorrect argument passed. Please give number greater or equal 0");
        }
    }
    public static void uploadData(String [] arr){
        Path path = Paths.get("C:\\Users\\Arkadii\\IdeaProjects\\Warsztat1\\tasks.csv");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
        try {
            Files.write(path, arrayList);
        }catch (IOException e){
            System.out.println("You cant write file.");
        }
    }
}