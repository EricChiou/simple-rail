package ericchiu.simplerail.tileentity;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.constants.Config;
import ericchiu.simplerail.registry.TileEntities;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.TickPriority;
import net.minecraft.world.World;

public class SingnalTimerTileEntity extends TileEntity implements ITickableTileEntity {

  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;

  private static final int LV1 = CommonConfig.INSTANCE.singnalTimerBlockLv1.get();
  private static final int LV2 = CommonConfig.INSTANCE.singnalTimerBlockLv2.get();
  private static final int LV3 = CommonConfig.INSTANCE.singnalTimerBlockLv3.get();
  private static final int LV4 = CommonConfig.INSTANCE.singnalTimerBlockLv4.get();
  private static final int LV5 = CommonConfig.INSTANCE.singnalTimerBlockLv5.get();
  private static final int LV6 = CommonConfig.INSTANCE.singnalTimerBlockLv6.get();
  private static final int LV7 = CommonConfig.INSTANCE.singnalTimerBlockLv7.get();
  private static final int LV8 = CommonConfig.INSTANCE.singnalTimerBlockLv8.get();
  private static final int LV9 = CommonConfig.INSTANCE.singnalTimerBlockLv9.get();

  public SingnalTimerTileEntity(TileEntityType<?> type) {
    super(type);
  }

  public SingnalTimerTileEntity() {
    this(TileEntities.SINGNAL_TIMER);
  }

  @Override
  public void tick() {
    World world = this.getLevel();
    BlockState state = this.getBlockState();
    int lv = state.getValue(LEVEL);

    if (lv > 0) {
      int skipTicksAmount = this.calSkipTicksAmount(lv);
      world.getBlockTicks().scheduleTick(this.getBlockPos(), this.getBlockState().getBlock(), skipTicksAmount,
          TickPriority.VERY_HIGH);
    }
  }

  private int calSkipTicksAmount(int lv) {
    int skipTicksAmount = 0;
    switch (lv) {
    case 1:
      skipTicksAmount = LV1 * Config.TICKS_PER_SECONDS;
      break;
    case 2:
      skipTicksAmount = LV2 * Config.TICKS_PER_SECONDS;
      break;
    case 3:
      skipTicksAmount = LV3 * Config.TICKS_PER_SECONDS;
      break;
    case 4:
      skipTicksAmount = LV4 * Config.TICKS_PER_SECONDS;
      break;
    case 5:
      skipTicksAmount = LV5 * Config.TICKS_PER_SECONDS;
      break;
    case 6:
      skipTicksAmount = LV6 * Config.TICKS_PER_SECONDS;
      break;
    case 7:
      skipTicksAmount = LV7 * Config.TICKS_PER_SECONDS;
      break;
    case 8:
      skipTicksAmount = LV8 * Config.TICKS_PER_SECONDS;
      break;
    case 9:
      skipTicksAmount = LV9 * Config.TICKS_PER_SECONDS;
      break;
    }

    return skipTicksAmount - 10;
  }

}
