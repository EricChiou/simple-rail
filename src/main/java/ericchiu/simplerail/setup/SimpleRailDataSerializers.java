package ericchiu.simplerail.setup;

import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.IDataSerializer;
import net.minecraft.util.IStringSerializable;

public class SimpleRailDataSerializers {

  public static final IDataSerializer<FacingDirection> FACING_DIRECTION = new IDataSerializer<FacingDirection>() {
    public void write(PacketBuffer buffer, FacingDirection facingDirection) {
      buffer.writeEnum(facingDirection);
    }

    public FacingDirection read(PacketBuffer buffer) {
      return buffer.readEnum(FacingDirection.class);
    }

    public FacingDirection copy(FacingDirection facingDirection) {
      return facingDirection;
    }
  };

  public static final void setup() {
    DataSerializers.registerSerializer(FACING_DIRECTION);
  }

  public enum FacingDirection implements IStringSerializable {
    EAST("east"), //
    WEST("west"), //
    NORTH("north"), //
    SOUTH("south"), //
    NORTH_EAST("north_east"), //
    NORTH_WEST("north_west"), //
    SOUTH_EAST("south_east"), //
    SOUTH_WEST("south_west");

    private final String name;

    private FacingDirection(String direction) {
      this.name = direction;
    }

    public String toString() {
      return this.name;
    }

    public String getSerializedName() {
      return this.name;
    }
  }

}
