package hkspoilerviewer.gui;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public final class Log {
  private static final List<String> messages = new ArrayList<>();

  public static synchronized void log(String msg) {
    messages.add(msg);
  }

  public static synchronized void log(Iterable<String> msgs) {
    msgs.forEach(messages::add);
  }

  private static synchronized OutputStream newOutputStream() {
    return new OutputStream() {
      private StringBuilder sb = new StringBuilder();
      private final List<String> lines = new ArrayList<>();

      @Override
      public void write(int b) {
        char ch = (char) b;
        if (ch == '\n') {
          lines.add(sb.toString());
          sb = new StringBuilder();
          return;
        }

        sb.append(ch);
      }

      @Override
      public void flush() {
        String last = sb.toString();
        sb = new StringBuilder();
        if (!last.isEmpty()) {
          lines.add(last);
        }

        log(lines);
        lines.clear();
      }
    };
  }

  public static synchronized void log(Throwable t) {
    try (OutputStream os = newOutputStream(); PrintStream ps = new PrintStream(os)) {
      t.printStackTrace(ps);
    } catch (IOException ignore) {
    }
  }

  public static synchronized List<String> readAll() {
    return new ArrayList<>(messages);
  }
}
