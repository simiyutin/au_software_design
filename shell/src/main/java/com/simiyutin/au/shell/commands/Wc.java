package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Counts lines, words and bytes in given file or input stream
 */
public class Wc extends Command {

    public static final String NAME = "wc";

    public Wc(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {
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
        Path userDir = Paths.get(env.get("user.dir"));
        Path file = userDir.resolve(fileName);
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
