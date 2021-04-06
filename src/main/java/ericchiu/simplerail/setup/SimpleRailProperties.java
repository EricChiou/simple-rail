package ericchiu.simplerail.setup;

import ericchiu.simplerail.constants.Properties;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public class SimpleRailProperties {

  public static final BooleanProperty REVERSE = BooleanProperty.create(Properties.REVERSE_PROPERTY);
  public static final BooleanProperty NEED_POWER = BooleanProperty.create(Properties.NEED_POWER_PROPERTY);
  public static final BooleanProperty USE_POWER = BooleanProperty.create(Properties.USE_POWER_PROPERTY);
  public static final EnumProperty<Level> LEVEL = EnumProperty.create(Properties.LEVEL_PROPERTY, Level.class);
  public static final EnumProperty<Direction> DIRECTION = EnumProperty.create(Properties.DIRECTION_PROPERTY,
      Direction.class);

  public static final void setup() {
  }

  public static enum Level implements IStringSerializable {
    ONE(1), //
    TWO(2), //
    THREE(3), //
    FOUR(4), //
    FIVE(5);

    private final Integer level;

    private Level(Integer level) {
      this.level = level;
    }

    public String toString() {
      return this.level.toString();
    }

    public String getSerializedName() {
      return this.level.toString();
    }
  }

}
