package ericchiu.simplerail.block;

import java.util.Random;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.server.ServerWorld;

public class SingnalTimerBlock extends RedstoneBlock {

  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;
  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  private static final int LV1 = CommonConfig.INSTANCE.singnalTimerBlockLv1.get();
  private static final int LV2 = CommonConfig.INSTANCE.singnalTimerBlockLv2.get();
  private static final int LV3 = CommonConfig.INSTANCE.singnalTimerBlockLv3.get();
  private static final int LV4 = CommonConfig.INSTANCE.singnalTimerBlockLv4.get();
  private static final int LV5 = CommonConfig.INSTANCE.singnalTimerBlockLv5.get();
  private static final int LV6 = CommonConfig.INSTANCE.singnalTimerBlockLv6.get();
  private static final int LV7 = CommonConfig.INSTANCE.singnalTimerBlockLv7.get();
  private static final int LV8 = CommonConfig.INSTANCE.singnalTimerBlockLv8.get();
  private static final int LV9 = CommonConfig.INSTANCE.singnalTimerBlockLv9.get();

  public SingnalTimerBlock() {
    super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.FIRE).requiresCorrectToolForDrops()
        .strength(5.0F, 6.0F).sound(SoundType.METAL));
    this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0).setValue(POWERED, false));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(LEVEL);
    builder.add(POWERED);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void tick(BlockState state, ServerWorld serverWorld, BlockPos pos, Random p_225534_4_) {
    System.out.println("11111 tick");
    // if (state.getValue(POWERED)) {
    //   System.out.println("11111 POWERED true");
    //   serverWorld.setBlock(pos, state.setValue(POWERED, false), 3);
    //   serverWorld.getBlockTicks().scheduleTick(pos, this, this.calTriggerTime(state), TickPriority.VERY_HIGH);
    // } else {
    //   System.out.println("11111 POWERED false");
    //   serverWorld.setBlock(pos, state.setValue(POWERED, true), 3);
    //   serverWorld.getBlockTicks().scheduleTick(pos, this, 1, TickPriority.VERY_HIGH);
    // }
  }

  @Override
  public int getSignal(BlockState state, IBlockReader reader, BlockPos pos, Direction direction) {
    int lv = state.getValue(LEVEL);
    System.out.println("11111");
    System.out.println(lv);
    System.out.println(state.getValue(POWERED));
    if (lv > 0 && state.getValue(POWERED)) {
      return 15;
    }

    return 0;
  }

  private int calTriggerTime(BlockState state) {
    int triggerTime = 0;
    switch (state.getValue(LEVEL)) {
    case 1:
      triggerTime = LV1;
      break;
    case 2:
      triggerTime = LV2;
      break;
    case 3:
      triggerTime = LV3;
      break;
    case 4:
      triggerTime = LV4;
      break;
    case 5:
      triggerTime = LV5;
      break;
    case 6:
      triggerTime = LV6;
      break;
    case 7:
      triggerTime = LV7;
      break;
    case 8:
      triggerTime = LV8;
      break;
    case 9:
      triggerTime = LV9;
      break;
    }

    return triggerTime;
  }

}
