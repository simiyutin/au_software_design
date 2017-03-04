package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Cat extends Command {

    public static final String NAME = "cat";

    public Cat(List<String> args, Environment env) {
        super(args, env);
    }

    @Override
    public Stream run(Stream stream) {

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
            Path file = Paths.get(filename);
            try {
                List<String> lines = Files.readAllLines(file);
                lines.forEach(res::write);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
