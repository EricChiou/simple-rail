package ericchiu.simplerail.tileentity;

import java.util.UUID;

import ericchiu.simplerail.registry.TileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class TimerHoldingRailTileEntity extends TileEntity {
  private static final String SAVE_TIME_KEY = "save_time";
  private static final String CART_UUID_KEY = "cart_uuid";
  private static final String GO_TIME_KEY = "go_time";

  private UUID cartUuid = null;
  private Long goTime = null;

  public TimerHoldingRailTileEntity(TileEntityType<?> type) {
    super(type);
  }

  public TimerHoldingRailTileEntity() {
    this(TileEntities.TIMER_HOLDING_RAIL);
  }

  @Override
  public CompoundNBT save(CompoundNBT nbt) {
    nbt.putLong(SAVE_TIME_KEY, System.currentTimeMillis());
    if (this.cartUuid != null) {
      nbt.putUUID(CART_UUID_KEY, this.cartUuid);
    }
    if (this.goTime != null) {
      nbt.putLong(GO_TIME_KEY, this.goTime);
    }
    return super.save(nbt);
  }

  @Override
  public void load(BlockState state, CompoundNBT nbt) {
    Long saveTime = nbt.getLong(SAVE_TIME_KEY);
    this.cartUuid = nbt.getUUID(CART_UUID_KEY);
    this.goTime = nbt.getLong(GO_TIME_KEY) + (System.currentTimeMillis() - saveTime);
    super.load(state, nbt);
  }

  public void setCartUuid(UUID cartUuid) {
    this.cartUuid = cartUuid;
  }

  public void setGoTime(Long goTime) {
    this.goTime = goTime;
  }

  public UUID getCartUuid() {
    return this.cartUuid;
  }

  public Long getGoTime() {
    return this.goTime;
  }

}
