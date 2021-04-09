package ericchiu.simplerail.properties;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.state.Property;

public class LongProperty extends Property<Long> {

  private final ImmutableSet<Long> values;

  protected LongProperty(String name, long min, long max) {
    super(name, Long.class);
    if (min < 0) {
      throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
    } else if (max <= min) {
      throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
    } else {
      Set<Long> set = Sets.newHashSet();

      for (long i = min; i <= max; ++i) {
        set.add(i);
      }

      this.values = ImmutableSet.copyOf(set);
    }
  }

  public Collection<Long> getPossibleValues() {
    return this.values;
  }

  public boolean equals(Object p_equals_1_) {
    if (this == p_equals_1_) {
      return true;
    } else if (p_equals_1_ instanceof LongProperty && super.equals(p_equals_1_)) {
      LongProperty longproperty = (LongProperty) p_equals_1_;
      return this.values.equals(longproperty.values);
    } else {
      return false;
    }
  }

  public int generateHashCode() {
    return 31 * super.generateHashCode() + this.values.hashCode();
  }

  public static LongProperty create(String name, long min, long max) {
    return new LongProperty(name, min, max);
  }

  public Optional<Long> getValue(String name) {
    try {
      Long longNum = Long.valueOf(name);
      return this.values.contains(longNum) ? Optional.of(longNum) : Optional.empty();
    } catch (NumberFormatException numberformatexception) {
      return Optional.empty();
    }
  }

  public String getName(Long value) {
    return value.toString();
  }

}
