
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Scanner;

public class ff {
    public static void main(String[] args) throws IOException {
        // put your code here
        String input = new String(Files.readAllBytes(Paths.get(args[0])));

        String[] sentences = input.split("\\? |\\. |! |\\n");
        double totalWords = 0;
        double characters = 0;
        double numberOfSyllables = 0;
        double numberOfPolysyllables = 0;

        for (String str : sentences) {
            totalWords += str.split(" ").length;
            for (String word : str.split(" ")) {
                int x = syllablesCount(word);
                numberOfSyllables += x;
                if (x > 2) {
                    numberOfPolysyllables++;
                }
            }
        }
        for (String s : input.split(" ")) {
            characters += s.length();
        }


        System.out.println("java Main in.txt\n" +
                "The text is:");
        System.out.println(input);

        System.out.println("Words: " + (int) totalWords);
        System.out.println("Sentences: " + sentences.length);
        System.out.println("Characters: " + (int) characters);
        System.out.println("Syllables: " + (int) numberOfSyllables);
        System.out.println("Polysyllables: " + (int) numberOfPolysyllables);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        Scanner scanner = new Scanner(System.in);
        String method = scanner.nextLine().strip();
        System.out.println();

        String[] values = {"","",""};
        if (method.equalsIgnoreCase("ari")) {
            values = automatedReadabilityIndex(characters, totalWords, sentences.length);
        } else if (method.equalsIgnoreCase("fk")) {
            values = fleschKincaidMethod(totalWords,sentences.length,numberOfSyllables);
        } else if (method.equalsIgnoreCase("smog")) {
            values = SMOGIndex(numberOfPolysyllables,sentences.length);
        } else if (method.equalsIgnoreCase("cl")) {
            values = colemanLiauIndex(characters,totalWords,sentences.length);
        } else {
            double sumAge = 0;
            values = automatedReadabilityIndex(characters, totalWords, sentences.length);
            sumAge += Double.parseDouble(values[2]);
            DecimalFormat df = new DecimalFormat("###.##");
            System.out.println(values[1].concat(": ")
                    + df.format(Double.valueOf(values[0])) + " (about " + values[2] + " year olds).");
            values = fleschKincaidMethod(totalWords,sentences.length,numberOfSyllables);
            sumAge += Double.parseDouble(values[2]);
            System.out.println(values[1].concat(": ")
                    + df.format(Double.valueOf(values[0])) + " (about " + values[2] + " year olds).");
            values = SMOGIndex(numberOfPolysyllables,sentences.length);
            sumAge += Double.parseDouble(values[2]);
            System.out.println(values[1].concat(": ")
                    + df.format(Double.valueOf(values[0])) + " (about " + values[2] + " year olds).");
            values = colemanLiauIndex(characters,totalWords,sentences.length);
            sumAge += Double.parseDouble(values[2]);
            System.out.println(values[1].concat(": ")
                    + df.format(Double.valueOf(values[0])) + " (about " + values[2] + " year olds).");
            double averageAge = sumAge / 4;
            System.out.println("This text should be understood by ".concat(String.valueOf(averageAge)).concat(" year olds."));
            return;
        }

        DecimalFormat df = new DecimalFormat("###.##");
        System.out.println(values[1].concat(": ")
                + df.format(Double.valueOf(values[0])) + " (about " + values[2] + " year olds).");
        System.out.println("This text should be understood by ".concat(values[2]).concat(" year olds."));

    }

    public static int syllablesCount(String word) {

        String str = word.toLowerCase();

        str = str.replaceAll("e\\b", "");
        str = str.replaceAll("[aeiouy][aeiouy]","a");
        int numberOfVowels = str.length() - str.replaceAll("[aeiouy]", "").length();

        if (numberOfVowels == 0) {
            return 1;
        } else {
            return numberOfVowels;
        }

    }

    public static String[] fleschKincaidMethod(double words, double sentences, double syllables) {
        double score =  0.39 * (words / sentences) + 11.8 * (syllables / words) - 15.59;
        String nameOfMethod = "Flesch–Kincaid readability tests";
        String age = getAge((int) Math.round(score));
        return new String[] {String.valueOf(score), nameOfMethod, age};

    }

    public static String[] SMOGIndex(double polysyllables, double sentences) {
        double score =  1.043 * (Math.sqrt(polysyllables * (30 / sentences))) + 3.1291;
        String nameOfMethod = "Simple Measure of Gobbledygook";
        String age = getAge((int) Math.round(score));
        return new String[] {String.valueOf(score), nameOfMethod, age};
    }

    public static String[] colemanLiauIndex(double characters, double words, double sentences) {
        double l = characters / words * 100;
        double s = sentences / words * 100;
        double score =  0.0588 * l - 0.296 * s - 15.8;
        String nameOfMethod = "Coleman–Liau index";
        String age = getAge((int) Math.round(score));
        return new String[] {String.valueOf(score), nameOfMethod, age};
    }

    public static String[] automatedReadabilityIndex(double characters, double words, double sentences) {
        double score =  4.71 * (characters/words) + 0.5 * (words/sentences) - 21.43;
        String nameOfMethod = "Automated Readability Index";
        String age = getAge((int) Math.round(score));
        return new String[] {String.valueOf(score), nameOfMethod, age};
    }

    public static String getAge(int score){
        String age;
        switch (score) {
            case 1:
                age = "6";
                break;
            case 2:
                age = "7";
                break;
            case 3:
                age = "9";
                break;
            case 4:
                age = "10";
                break;
            case 5:
                age = "11";
                break;
            case 6:
                age = "12";
                break;
            case 7:
                age = "13";
                break;
            case 8:
                age = "14";
                break;
            case 9:
                age = "15";
                break;
            case 10:
                age = "16";
                break;
            case 11:
                age = "17";
                break;
            case 12:
                age = "18";
                break;
            case 13:
                age = "24";
                break;
            case 14:
                age = "25";
                break;
            default:
                age = "none";
                break;
        }
        return age;
    }
}



