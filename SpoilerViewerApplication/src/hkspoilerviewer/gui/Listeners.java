package hkspoilerviewer.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public final class Listeners {
  private Listeners() {}

  private static <T> void invoke(Consumer<T> action, T event) {
    try {
      action.accept(event);
    } catch (Exception e) {
      Log.log(e);
    }
  }

  public static ActionListener newActionListener(Consumer<ActionEvent> action) {
    return new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent event) {
        invoke(action, event);
      }
    };
  }

  public static ListDataListener newListDataListener(Consumer<ListDataEvent> action) {
    return new ListDataListener() {
      @Override
      public void intervalRemoved(ListDataEvent event) {
        invoke(action, event);
      }

      @Override
      public void intervalAdded(ListDataEvent event) {
        invoke(action, event);
      }

      @Override
      public void contentsChanged(ListDataEvent event) {
        invoke(action, event);
      }
    };
  }
}
