package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Wc extends Command {

    public static final String NAME = "wc";

    public Wc(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) {
        if (!args.isEmpty()) {
            return handleFile();
        } else if (stream.hasNext()) {
            return handleInputStream(stream);
        } else {
            handleStdIn();
            return new Stream();
        }
    }

    private Stream handleFile() {
        String fileName = args.get(0);
        Path file = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(file);
            return parseLines(lines);
        } catch (IOException e) {
            e.printStackTrace();
            return new Stream();
        }
    }

    private Stream handleInputStream(Stream stream) {
        List<String> lines = new ArrayList<>();
        while (stream.hasNext()) {
            lines.add(stream.read());
        }
        return parseLines(lines);
    }

    private void handleStdIn() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            List<String> lines = Collections.singletonList(sc.nextLine());
            Stream result = parseLines(lines);
            System.out.println(result.read());
        }
    }

    private Stream parseLines(List<String> lines) {
        int linesCount = lines.size();
        char[] concatenated = lines.stream().collect(Collectors.joining(" ")).toCharArray();
        long wordCount = 0;
        for (char c : concatenated) {
            if (c == ' ') {
                wordCount++;
            }
        }
        wordCount++;

        long byteCount = concatenated.length;

        String result = String.format("    %d    %d    %d", linesCount, wordCount, byteCount);
        Stream stream = new Stream();
        stream.write(result);
        return stream;
    }
}
