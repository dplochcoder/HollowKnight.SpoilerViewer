package hkspoilerviewer.api;

public abstract class TransitionPlacement {
  public abstract int placementId();

  public abstract TransitionLocation location();

  public abstract TransitionItem transition();
}
