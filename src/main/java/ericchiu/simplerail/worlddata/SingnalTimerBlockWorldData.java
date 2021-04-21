package ericchiu.simplerail.worlddata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.block.SingnalTimerBlock;
import ericchiu.simplerail.constants.WorldData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class SingnalTimerBlockWorldData extends WorldSavedData {
  private static final String NAME = SimpleRail.MOD_ID + "_" + WorldData.SINGNAL_TIMER_BLOCK;
  private static final String SAVE_TIME_NAME = SimpleRail.MOD_ID + "_" + WorldData.SINGNAL_TIMER_BLOCK_SAVE_TIME_NAME;

  private Map<BlockPos, Long> timerData = new ConcurrentHashMap<BlockPos, Long>();
  private ServerWorld serverWorld = null;

  public SingnalTimerBlockWorldData(ServerWorld serverWorld) {
    super(NAME);
    this.serverWorld = serverWorld;
  }

  public SingnalTimerBlockWorldData(String name) {
    super(name);
  }

  public static SingnalTimerBlockWorldData get(ServerWorld serverWorld) {
    DimensionSavedDataManager storage = serverWorld.getDataStorage();

    SingnalTimerBlockWorldData data = storage.get(() -> new SingnalTimerBlockWorldData(serverWorld), NAME);
    if (data == null) {
      data = new SingnalTimerBlockWorldData(serverWorld);
      storage.set(data);
    }

    return data;
  }

  @Override
  public void load(CompoundNBT nbt) {
    Long delayTime = System.currentTimeMillis() - nbt.getLong(SAVE_TIME_NAME);

    for (String key : nbt.getAllKeys()) {
      try {
        String[] coor = key.split(",");
        if (coor.length >= 3) {
          BlockPos pos = new BlockPos(Integer.valueOf(coor[0]), Integer.valueOf(coor[1]), Integer.valueOf(coor[2]));

          Long triggerTime = delayTime + nbt.getLong(key);

          this.timerData.put(pos, triggerTime);
        }
      } catch (Exception e) {
      }
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT nbt) {
    this.checkData();
    for (BlockPos pos : this.timerData.keySet()) {
      Long triggerTime = this.timerData.get(pos);
      String timeDataKey = pos.getX() + "," + pos.getY() + "," + pos.getZ();
      nbt.putLong(timeDataKey, triggerTime);
    }

    nbt.putLong(SAVE_TIME_NAME, System.currentTimeMillis());
    return nbt;
  }

  public Long getTriggerTime(BlockPos pos) {
    return this.timerData.get(pos);
  }

  public void setTriggerTime(BlockPos pos, Long triggerTime) {
    this.timerData.put(pos, triggerTime);
    setDirty();
  }

  public void removeTriggerTime(BlockPos pos) {
    this.timerData.remove(pos);
    setDirty();
  }

  private void checkData() {
    for (BlockPos pos : this.timerData.keySet()) {
      if (!(this.serverWorld.getBlockState(pos).getBlock() instanceof SingnalTimerBlock)) {
        this.timerData.remove(pos);
      }
    }
  }

}
