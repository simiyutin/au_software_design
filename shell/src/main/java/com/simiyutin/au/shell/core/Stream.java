package com.simiyutin.au.shell.core;

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
     * Read one line from stream
     * @return next line in stream or null if stream is empty
     */
    public String read() {
        return vals.poll();
    }

    /**
     * Write one line to stream
     * @param val line to add to stream
     */
    public void write(String val) {
        vals.offer(val);
    }

    /**
     * Check if stream has more lines to read
     * @return true if stream has at least one line to read
     */
    public boolean hasNext() {
        return vals.peek() != null;
    }

    /**
     * String representation of a stream object
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
