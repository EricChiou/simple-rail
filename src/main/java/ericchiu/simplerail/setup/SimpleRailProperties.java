package ericchiu.simplerail.setup;

import ericchiu.simplerail.constants.Properties;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.Direction;

public class SimpleRailProperties {

  public static final BooleanProperty REVERSE = BooleanProperty.create(Properties.REVERSE_PROPERTY);
  public static final BooleanProperty NEED_POWER = BooleanProperty.create(Properties.NEED_POWER_PROPERTY);
  public static final BooleanProperty USE_POWER = BooleanProperty.create(Properties.USE_POWER_PROPERTY);
  public static final IntegerProperty LEVEL = IntegerProperty.create(Properties.LEVEL_PROPERTY, 0, 9);
  public static final EnumProperty<Direction> DIRECTION = EnumProperty.create(Properties.DIRECTION_PROPERTY,
      Direction.class);

  public static final void setup() {
  }

}
