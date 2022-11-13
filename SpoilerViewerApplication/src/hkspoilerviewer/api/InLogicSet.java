package hkspoilerviewer.api;

import java.util.HashSet;
import java.util.Set;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class InLogicSet {
  public abstract ImmutableSet<Integer> obtainedItemPlacements();

  public abstract ImmutableSet<Integer> inLogicItemPlacements();

  public abstract ImmutableSet<Integer> obtainedTransitionPlacements();

  public abstract ImmutableSet<Integer> inLogicTransitionPlacements();

  public final InLogicSet apply(Iterable<InLogicSetDelta> deltas) {
    Set<Integer> obtainedItemPlacements = new HashSet<>();
    Set<Integer> inLogicItemPlacements = new HashSet<>();
    Set<Integer> obtainedTransitionPlacements = new HashSet<>();
    Set<Integer> inLogicTransitionPlacements = new HashSet<>();

    for (InLogicSetDelta delta : deltas) {
      obtainedItemPlacements.addAll(delta.newlyObtainedItemPlacements());
      inLogicItemPlacements.addAll(delta.newlyInLogicItemPlacements());
      obtainedTransitionPlacements.addAll(delta.newlyObtainedTransitionPlacements());
      inLogicTransitionPlacements.addAll(delta.newlyInLogicTransitionPlacements());
    }

    return create(obtainedItemPlacements, inLogicItemPlacements, obtainedTransitionPlacements, inLogicTransitionPlacements);
  }

  public static InLogicSet create(Set<Integer> obtainedItemPlacements,
      Set<Integer> inLogicItemPlacements, Set<Integer> obtainedTransitionPlacements,
      Set<Integer> inLogicTransitionPlacements) {
    return new AutoValue_InLogicSet(ImmutableSet.copyOf(obtainedItemPlacements),
        ImmutableSet.copyOf(inLogicItemPlacements),
        ImmutableSet.copyOf(obtainedTransitionPlacements),
        ImmutableSet.copyOf(inLogicTransitionPlacements));
  }
}
