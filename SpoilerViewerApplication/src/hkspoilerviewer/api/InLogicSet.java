package hkspoilerviewer.api;

import java.util.HashSet;
import java.util.Set;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableSet;

@AutoValue
public abstract class InLogicSet {
  public abstract ImmutableSet<Integer> obtainedPlacements();

  public abstract ImmutableSet<Integer> inLogicPlacements();

  public final InLogicSet apply(Iterable<InLogicSetDelta> deltas) {
    Set<Integer> obtainedPlacements = new HashSet<>();
    Set<Integer> inLogicPlacements = new HashSet<>();

    for (InLogicSetDelta delta : deltas) {
      obtainedPlacements.addAll(delta.newlyObtainedPlacements());
      inLogicPlacements.addAll(delta.newlyInLogicPlacements());
    }

    return create(obtainedPlacements, inLogicPlacements);
  }

  public static InLogicSet create(Set<Integer> obtainedPlacements, Set<Integer> inLogicPlacements) {
    return new AutoValue_InLogicSet(ImmutableSet.copyOf(obtainedPlacements),
        ImmutableSet.copyOf(inLogicPlacements));
  }
}
