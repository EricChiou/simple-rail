package ericchiu.simplerail.worlddata;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.block.TimerHoldingRail;
import ericchiu.simplerail.constants.I18n;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class TimerHoldingRailWorldData extends WorldSavedData {
  private static final String NAME = SimpleRail.MOD_ID + "_" + I18n.WORLD_DATA_TIMER_HOLDING_RAIL;
  private static final String SAVE_TIME_NAME = SimpleRail.MOD_ID + "_" + I18n.HOLDING_RAIL_SAVE_TIME_NAME;
  private static final String CART_UUID_KEY = I18n.HOLDING_DATA_CART_UUID_KEY;
  private static final String GO_TIME_KEY = I18n.HOLDING_DATA_GO_TIME_KEY;

  private Map<BlockPos, HoldingData> holdingData = new ConcurrentHashMap<BlockPos, HoldingData>();
  private ServerWorld serverWorld = null;

  public TimerHoldingRailWorldData(ServerWorld serverWorld) {
    super(NAME);
    this.serverWorld = serverWorld;
  }

  public TimerHoldingRailWorldData(String name) {
    super(name);
  }

  public static TimerHoldingRailWorldData get(ServerWorld serverWorld) {
    DimensionSavedDataManager storage = serverWorld.getDataStorage();

    TimerHoldingRailWorldData data = storage.get(() -> new TimerHoldingRailWorldData(serverWorld), NAME);
    if (data == null) {
      data = new TimerHoldingRailWorldData(serverWorld);
      storage.set(data);
    }

    return data;
  }

  @Override
  public void load(CompoundNBT nbt) {
    long delayTime = System.currentTimeMillis() - nbt.getLong(SAVE_TIME_NAME);

    for (String key : nbt.getAllKeys()) {
      try {
        String[] coor = key.split(",");
        BlockPos pos = new BlockPos(Integer.valueOf(coor[0]), Integer.valueOf(coor[1]), Integer.valueOf(coor[2]));

        CompoundNBT holdingDataNbt = (CompoundNBT) nbt.get(key);
        HoldingData holdingData = new HoldingData( //
            holdingDataNbt.getUUID(CART_UUID_KEY), //
            holdingDataNbt.getLong(GO_TIME_KEY) + delayTime);

        this.holdingData.put(pos, holdingData);
      } catch (Exception e) {
      }
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT nbt) {
    this.checkData();
    for (BlockPos pos : this.holdingData.keySet()) {
      HoldingData holdingData = this.holdingData.get(pos);
      CompoundNBT holdingDatNbt = new CompoundNBT();
      holdingDatNbt.putUUID(CART_UUID_KEY, holdingData.cartUuid);
      holdingDatNbt.putLong(GO_TIME_KEY, holdingData.goTime);

      String holdingDataKey = pos.getX() + "," + pos.getY() + "," + pos.getZ();
      nbt.put(holdingDataKey, holdingDatNbt);
    }

    nbt.putLong(SAVE_TIME_NAME, System.currentTimeMillis());
    return nbt;
  }

  public HoldingData getHoldingData(BlockPos pos) {
    return this.holdingData.get(pos);
  }

  public void setHoldingData(BlockPos pos, UUID cartUuid, long goTime) {
    this.holdingData.put(pos, new HoldingData(cartUuid, goTime));
    setDirty();
  }

  public void setHoldingData(BlockPos pos, HoldingData holdingData) {
    this.holdingData.put(pos, holdingData);
    setDirty();
  }

  public void removeHoldingData(BlockPos pos) {
    this.holdingData.remove(pos);
    setDirty();
  }

  private void checkData() {
    for (BlockPos pos : this.holdingData.keySet()) {
      if (!(this.serverWorld.getBlockState(pos).getBlock() instanceof TimerHoldingRail)) {
        this.holdingData.remove(pos);
      }
    }
  }

  public static class HoldingData {
    public final UUID cartUuid;
    public final long goTime;

    public HoldingData(UUID cartUuid, long goTime) {
      this.cartUuid = cartUuid;
      this.goTime = goTime;
    }
  }

}
