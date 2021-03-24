package ericchiu.simplerail.setup;

import ericchiu.simplerail.constants.Properties;
import net.minecraft.state.BooleanProperty;

public class SimpleRailProperties {

  public static final BooleanProperty REVERSE = BooleanProperty.create(Properties.REVERSE_PROPERTY);
  public static final BooleanProperty NEED_POWER = BooleanProperty.create(Properties.NEED_POWER_PROPERTY);
  public static final BooleanProperty USE_POWER = BooleanProperty.create(Properties.USE_POWER_PROPERTY);

  public static final void setup() {
  }

}
