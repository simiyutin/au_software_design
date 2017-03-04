package shell;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Entity to handle input and output of commands.
 */
public class Stream {

    private Queue<String> vals;

    public Stream() {
        this.vals = new ArrayDeque<>();
    }

    /**
     * @return next line in stream or null if stream is empty
     */
    public String read() {
        return vals.poll();
    }

    /**
     * @param val line to add to stream
     */
    public void write(String val) {
        vals.offer(val);
    }

    /**
     * @return true if stream has at least one line to read
     */
    public boolean hasNext() {
        return vals.peek() != null;
    }

    /**
     * @return concatenated lines in stream
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Queue<String> replacement = new ArrayDeque<>();
        while (hasNext()) {
            String line = read();
            sb.append('\n');
            sb.append(line);
            replacement.add(line);
        }
        vals = replacement;
        sb.delete(0, 1);
        return sb.toString();
    }
}
