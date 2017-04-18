package com.simiyutin.au.shell.commands;

import com.simiyutin.au.shell.core.exceptions.CommandExecutionException;
import com.simiyutin.au.shell.core.Command;
import com.simiyutin.au.shell.core.Environment;
import com.simiyutin.au.shell.core.Stream;

import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Reads file from parameter to stdout or reflects stdin to stdout
 */
public class Cat extends Command {

    public static final String NAME = "cat";

    public Cat(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) throws CommandExecutionException {

        if (stream.hasNext()) {
            return stream;
        }

        Stream res = new Stream();
        if (args.isEmpty()) {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println(sc.nextLine());
            }
        } else {
            String filename = args.get(0);
            Path userDir = Paths.get(env.get("user.dir"));

            Path file = userDir.resolve(filename);
            try {
                List<String> lines = Files.readAllLines(file);
                lines.forEach(res::write);
            } catch (MalformedInputException e) {
                throw new CommandExecutionException("Cannot determine file encoding");
            } catch (IOException e) {
                throw new CommandExecutionException("Error while reading file: " + e.toString());
            }
        }
        return res;
    }
}
