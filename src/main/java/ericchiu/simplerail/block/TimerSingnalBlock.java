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
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public class TimerSingnalBlock extends RedstoneBlock {

  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;

  private static final int LV1 = CommonConfig.INSTANCE.timerSingnalBlockLv1.get();
  private static final int LV2 = CommonConfig.INSTANCE.timerSingnalBlockLv2.get();
  private static final int LV3 = CommonConfig.INSTANCE.timerSingnalBlockLv3.get();
  private static final int LV4 = CommonConfig.INSTANCE.timerSingnalBlockLv4.get();
  private static final int LV5 = CommonConfig.INSTANCE.timerSingnalBlockLv5.get();
  private static final int LV6 = CommonConfig.INSTANCE.timerSingnalBlockLv6.get();
  private static final int LV7 = CommonConfig.INSTANCE.timerSingnalBlockLv7.get();
  private static final int LV8 = CommonConfig.INSTANCE.timerSingnalBlockLv8.get();
  private static final int LV9 = CommonConfig.INSTANCE.timerSingnalBlockLv9.get();

  public TimerSingnalBlock() {
    super(AbstractBlock.Properties.of(Material.METAL, MaterialColor.FIRE).requiresCorrectToolForDrops()
        .strength(5.0F, 6.0F).sound(SoundType.METAL));
    this.registerDefaultState(this.stateDefinition.any().setValue(LEVEL, 0));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(LEVEL);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public void tick(BlockState p_225534_1_, ServerWorld p_225534_2_, BlockPos p_225534_3_, Random p_225534_4_) {
    // TODO Auto-generated method stub
    super.tick(p_225534_1_, p_225534_2_, p_225534_3_, p_225534_4_);
  }

}
