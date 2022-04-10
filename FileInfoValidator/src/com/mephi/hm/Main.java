package com.mephi.hm;

import com.mephi.hm.exceptions.ValidatorException;
import com.mephi.hm.validators.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    static final private List<Validator> validators = List.of(new NameValidator(),
                                                        new YearValidator(),
                                                        new EmailValidator(),
                                                        new PhoneValidator());

    public static void checkFile(Scanner scanner) throws ValidatorException {
        ValidatorException badFileException = null;

        int lineCount = 0;
        while(scanner.hasNextLine()) {
            lineCount++;
            try {
                checkLine(scanner.nextLine(), lineCount);
            } catch (ValidatorException exceptionFromLine) {
                if (badFileException == null) {
                    badFileException = new ValidatorException("File has mistakes");
                }
                badFileException.addSuppressed(exceptionFromLine);
            }
        }

        if (badFileException != null) {
            throw badFileException;
        }
    }

    public static void checkLine(String fileLine, int lineCount) throws ValidatorException {
        ValidatorException badLineException = null;

        String[] elements = fileLine.split(", ");
        for (int i = 0; i < elements.length; i++) {
            try {
                validators.get(i).validate(elements[i]);
            } catch (ValidatorException exceptionFromElement) {
                if (badLineException == null) {
                    badLineException = new ValidatorException(Integer.toString(lineCount));
                }
                badLineException.addSuppressed(exceptionFromElement);
            }
        }

        if (badLineException != null) {
            throw badLineException;
        }
    }

    public static void printExceptionInfo(ValidatorException badFileException) {
        System.out.println(badFileException.getMessage());
        for (Throwable exceptionFromLine : badFileException.getSuppressed()) {
            System.out.print(exceptionFromLine.getMessage() + ": ");
            for (Throwable exceptionFromElement : exceptionFromLine.getSuppressed()) {
                System.out.print(exceptionFromElement.getMessage() + "; ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Path path = Paths.get("info.txt").toAbsolutePath();
        Scanner scanner;
        try {
            scanner = new Scanner(path);
        } catch (IOException exception) {
            System.out.println("Файла нет :(");
            System.out.println("Такого: " + path);
            return;
        }

        try {
            checkFile(scanner);
        } catch (ValidatorException badFileException) {
            printExceptionInfo(badFileException);
        }

        scanner.close();
    }
}
