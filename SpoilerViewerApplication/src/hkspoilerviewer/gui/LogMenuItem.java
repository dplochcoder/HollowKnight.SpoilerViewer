package hkspoilerviewer.gui;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

public final class LogMenuItem extends JMenuItem {
  private static final long serialVersionUID = 1L;

  public LogMenuItem() {
    super("Logs");
    addActionListener(Listeners.newActionListener(e -> showLogsWindow()));
  }

  private void showLogsWindow() {
    JFrame popup = new JFrame("Logs");

    DefaultListModel<String> model = new DefaultListModel<>();
    model.addAll(Log.readAll());
    JList<String> list = new JList<>(model);
    JScrollPane scrollPane = new JScrollPane(list, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    popup.getContentPane().add(scrollPane);
    popup.pack();
    popup.setVisible(true);
  }
}
