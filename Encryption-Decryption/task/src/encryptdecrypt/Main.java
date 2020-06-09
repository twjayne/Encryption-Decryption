package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class InputData {
    boolean isEncription = true;
    int key = 0;
    String data = "";
    File inFile;
    File outFile;
    AlgorithmType type = AlgorithmType.SHIFT;
}

enum AlgorithmType {
    SHIFT,
    UNICODE
}

abstract class EncryptionAlgorithm {

    public abstract char encryption(char c, int shift);
    public abstract char decryption(char c, int shift);
}

class shiftingAlgorithm extends EncryptionAlgorithm {
    static final String lowerCase = "abcdefghijklmnopqrstuvwxyz";
    static final String upperCase = lowerCase.toUpperCase();
    @Override
    public char encryption(char c, int shift) {
        int startPosition = lowerCase.indexOf(c);
        boolean isUpper = false;
        if (startPosition < 0) {
            startPosition = upperCase.indexOf(c);
            if (startPosition < 0) return c;
            isUpper = true;
        }
        int finalPosition = (startPosition + shift) % 26;
        return isUpper ? upperCase.charAt(finalPosition) : lowerCase.charAt(finalPosition);
    }

    @Override
    public char decryption(char c, int shift) {
        int startPosition = lowerCase.indexOf(c);
        boolean isUpper = false;
        if (startPosition < 0) {
            startPosition = upperCase.indexOf(c);
            if (startPosition < 0) return c;
            isUpper = true;
        }
        int finalPosition = (startPosition - shift + 26) % 26;
        return isUpper ? upperCase.charAt(finalPosition) : lowerCase.charAt(finalPosition);
    }
}

class UnicodeAlgorithm extends EncryptionAlgorithm {
    @Override
    public char encryption(char c, int shift) {
        return (char) (c + shift);
    }

    @Override
    public char decryption(char c, int shift) {
        return (char) (c - shift);
    }
}

class EncryptionAlgorithmFactory {
    public EncryptionAlgorithm getEncryptionAlgorithm(AlgorithmType type) {
        switch (type) {
            case SHIFT:
                return new shiftingAlgorithm();
            case UNICODE:
                return new UnicodeAlgorithm();
            default:
                return null;
        }
    }
}


public class Main {
    public static void main(String[] args) throws IOException {
        InputData input = new InputData();
        int i = 0;
        while (i < args.length) {
            switch (args[i++]) {
                case "-mode":
                    if (!args[i].equals("enc")) input.isEncription = false;
                    break;
                case "-key":
                    input.key = Integer.parseInt(args[i]);
                    break;
                case "-data":
                    input.data = args[i];
                    break;
                case "-in":
                    input.inFile = new File(args[i]);
                    Scanner sc = new Scanner(input.inFile);
                    StringBuilder data = new StringBuilder();
                    while (sc.hasNextLine()) {
                        data.append(sc.nextLine());
                    }
                    input.data = data.toString();
                    break;
                case "-out":
                    input.outFile = new File(args[i]);
                    break;
                case "-alg":
                    if (args[i].equals("unicode")) input.type = AlgorithmType.UNICODE;
            }
            i++;
        }
        EncryptionAlgorithmFactory algorithmFactory = new EncryptionAlgorithmFactory();
        EncryptionAlgorithm alg = algorithmFactory.getEncryptionAlgorithm(input.type);
        StringBuilder sb = new StringBuilder();
        for (char c : input.data.toCharArray()) {
            char output = input.isEncription ? alg.encryption(c, input.key) : alg.decryption(c, input.key);
            sb.append(output);
        }
        String out = sb.toString();
        System.out.println(out);
        if (input.outFile == null) System.out.println(out);
        else {
            FileWriter fileWriter = new FileWriter(input.outFile);
            fileWriter.write(out);
            fileWriter.close();
        }
    }
}
