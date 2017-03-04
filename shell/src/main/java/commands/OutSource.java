package commands;

import shell.Command;
import shell.Environment;
import shell.Stream;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class OutSource extends Command {

    private String commandName;

    public OutSource(String commandName, List<String> args, Environment env) {
        super(args, env);
        this.commandName = commandName;
    }

    @Override
    public Stream run(Stream stream) {
        String command = commandName + " " + args.stream().collect(Collectors.joining(" "));
        Stream output = new Stream();
        try {
            Process process = Runtime.getRuntime().exec(command);
            write(process, stream);
            output = read(process);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return output;
    }

    private void write(Process process, Stream stream) throws IOException {
        Writer handle = new PrintWriter(process.getOutputStream());
        final int LINE_FEED = 10;
        while (stream.hasNext()) {
            handle.write(stream.read());
            handle.write(LINE_FEED);
        }
        handle.close();
    }

    private Stream read(Process process) throws IOException, InterruptedException {
        Stream output = new Stream();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            output.write(line);
        }
        process.waitFor();
        return output;
    }
}
