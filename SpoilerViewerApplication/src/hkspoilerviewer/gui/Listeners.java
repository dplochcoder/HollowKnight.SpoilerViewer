package hkspoilerviewer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public final class Listeners {
  private Listeners() {}

  public static ActionListener newActionListener(Consumer<ActionEvent> action) {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        try {
          action.accept(event);
        } catch (Exception e) {
          Log.log(e);
        }
      }
    };
  }
}
