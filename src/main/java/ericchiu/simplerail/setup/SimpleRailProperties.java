package ericchiu.simplerail.setup;

import ericchiu.simplerail.constants.Properties;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;

public class SimpleRailProperties {

  public static final BooleanProperty REVERSE = BooleanProperty.create(Properties.REVERSE_PROPERTY);
  public static final BooleanProperty NEED_POWER = BooleanProperty.create(Properties.NEED_POWER_PROPERTY);
  public static final BooleanProperty USE_POWER = BooleanProperty.create(Properties.USE_POWER_PROPERTY);
  public static final EnumProperty<RainBow> RAINBOW = EnumProperty.create(Properties.RAINBOW_PROPERTY, RainBow.class);

  public static final void setup() {
  }

  public enum RainBow implements IStringSerializable {
    RED("red"), //
    ORANGE("orange"), //
    YELLOW("yellow"), //
    GREEN("green"), //
    BLUE("blue"), //
    INDIGO("indigo"), //
    VIOLET("violet");

    private final String name;

    private RainBow(String color) {
      this.name = color;
    }

    public String toString() {
      return this.name;
    }

    public String getSerializedName() {
      return this.name;
    }
  }

}
