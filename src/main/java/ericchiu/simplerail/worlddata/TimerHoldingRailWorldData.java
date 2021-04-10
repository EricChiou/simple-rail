package ericchiu.simplerail.worlddata;

import java.util.Map;
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

  private Map<BlockPos, Long> goTime = new ConcurrentHashMap<BlockPos, Long>();
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

        this.goTime.put(pos, nbt.getLong(key) + delayTime);
      } catch (Exception e) {
      }
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT nbt) {
    for (BlockPos pos : this.goTime.keySet()) {
      String nbtKey = pos.getX() + "," + pos.getY() + "," + pos.getZ();
      nbt.putLong(nbtKey, this.goTime.get(pos));
    }
    nbt.putLong(SAVE_TIME_NAME, System.currentTimeMillis());

    this.checkData();

    return nbt;
  }

  public Long getGoTime(BlockPos pos) {
    Long goTime = this.goTime.get(pos);
    return goTime == null ? 0 : goTime;
  }

  public void setGoTime(BlockPos pos, Long goTime) {
    this.goTime.put(pos, goTime);
    setDirty();
  }

  public void removeGoTime(BlockPos pos) {
    this.goTime.remove(pos);
    setDirty();
  }

  private void checkData() {
    for (BlockPos pos : this.goTime.keySet()) {
      if (!(this.serverWorld.getBlockState(pos).getBlock() instanceof TimerHoldingRail)) {
        this.goTime.remove(pos);
      }
    }
  }

}
