package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


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
                    arr = remove(arr);
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

    public static String[] add(String[] arr) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description: ");
        String description = null;
        boolean validDescription = false;
        while (!validDescription) {
            description = scanner.nextLine();
            if (description.isEmpty()) {
                System.out.println("input something in yours task description: ");
            } else validDescription = true;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = null;
        Date date = null;
        boolean validDate = false;
        while (!validDate) {
            System.out.println("Please add task due data in format yyyy-MM-dd:");
            String inputDate = scanner.nextLine();
            try {
                dateFormat.setLenient(false);
                date = dateFormat.parse(inputDate);
                formattedDate = dateFormat.format(date);
                validDate = true;
            } catch (ParseException e) {
                System.out.println("invalid data input. Try again!");
            }
        }
        boolean importance = false;
        boolean validImportance = false;
        while (!validImportance) {
            System.out.println("Is your task is important: true/false");
            String input = scanner.nextLine().toLowerCase(Locale.ROOT);
            if (input.equals("true") || input.equals("false")) {
                importance = Boolean.parseBoolean(input);
                validImportance = true;
            } else {
                System.out.println("incorrect value. Please, input true or false");
            }
        }
        String result = description + ", " + formattedDate + ", " + importance;
        arr = Arrays.copyOf(arr, arr.length + 1);
        arr[arr.length - 1] = result;
        return arr;
    }

    public static void list(String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(i + ": " + arr[i]);
        }
    }

    public static String[] remove(String[] arr) {
        System.out.println("Input an ordinal number that you want remove: ");
        Scanner scanner1 = new Scanner(System.in);
        while (true) {
            String line = scanner1.nextLine();
            if (NumberUtils.isParsable(line) && Integer.parseInt(line) > -1) {
                try {
                    arr = ArrayUtils.remove(arr, Integer.parseInt(line));
                    System.out.println("Successfully deleted!");
                    System.out.println(" ");
                    return arr;
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Chose correct ordinal number of list.");
                }
            } else System.out.println("Incorrect argument passed. Please give number greater or equal 0");
        }
    }

    public static void uploadData(String[] arr) {
        Path path = Paths.get("C:\\Users\\Arkadii\\IdeaProjects\\Warsztat1\\tasks.csv");
        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(arr));
        try {
            Files.write(path, arrayList);
        } catch (IOException e) {
            System.out.println("You cant write file.");
        }
    }
}