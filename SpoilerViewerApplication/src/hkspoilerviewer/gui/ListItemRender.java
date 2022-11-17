package hkspoilerviewer.gui;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ListItemRender {
  public abstract String text();

  public abstract boolean faded();

  public abstract boolean bold();

  public abstract boolean italics();

  public static ListItemRender create(String text, boolean faded, boolean bold, boolean italics) {
    return new AutoValue_ListItemRender(text, faded, bold, italics);
  }
}
