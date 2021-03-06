package ericchiu.simplerail.block;

import java.util.Random;

import ericchiu.simplerail.constants.Config;
import ericchiu.simplerail.setup.SimpleRailProperties;
import ericchiu.simplerail.tileentity.SignalTimerTileEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
// import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.TickPriority;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class SignalTimerBlock extends RedstoneBlock {

  public static final IntegerProperty LEVEL = SimpleRailProperties.LEVEL;
  public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

  public SignalTimerBlock() {
    super(AbstractBlock.Properties //
        .of(Material.METAL) //
        .strength(5.0F, 6.0F) //
        .sound(SoundType.METAL) //
        .harvestTool(ToolType.PICKAXE));
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
    if (state.getValue(POWERED)) {
      serverWorld.setBlock(pos, state.setValue(POWERED, false), 3);
    } else {
      serverWorld.setBlock(pos, state.setValue(POWERED, true), 3);
      serverWorld.getBlockTicks().scheduleTick(pos, this, Config.SIGNAL_DURATION_TIKCS, TickPriority.VERY_HIGH);
    }
  }

  @Override
  public int getSignal(BlockState state, IBlockReader reader, BlockPos pos, Direction direction) {
    int lv = state.getValue(LEVEL);
    if (lv > 0 && state.getValue(POWERED)) {
      return 15;
    }

    return 0;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new SignalTimerTileEntity();
  }

}
