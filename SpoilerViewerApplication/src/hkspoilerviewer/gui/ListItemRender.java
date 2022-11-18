package hkspoilerviewer.gui;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ListItemRender {
  public abstract String text();

  public abstract boolean faded();

  public abstract boolean bold();

  public abstract boolean italics();

  public abstract Builder toBuilder();

  public static Builder builder(String text) {
    return new AutoValue_ListItemRender.Builder().setText(text).setFaded(false).setBold(false)
        .setItalics(false);
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setText(String text);

    public abstract Builder setFaded(boolean faded);

    public abstract Builder setBold(boolean bold);

    public abstract Builder setItalics(boolean italics);

    public abstract ListItemRender build();
  }
}
