package ericchiu.simplerail.block;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class OnewayRail extends PoweredRailBlock {

  public static final BooleanProperty REVERSE = SimpleRailProperties.REVERSE;

  public final BlockItem blockItem;

  public OnewayRail() {
    super(AbstractBlock.Properties.of(Material.METAL).strength(0.7f).harvestLevel(0).harvestTool(ToolType.PICKAXE)
        .sound(SoundType.METAL).noCollission(), true);

    this.registerDefaultState(this.stateDefinition.any().setValue(REVERSE, Boolean.valueOf(false)));

    blockItem = new BlockItem(this, new Item.Properties().tab(Rail.TAB));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(REVERSE);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    return CommonConfig.INSTANCE.highSpeedRailMaxSpeed.get().floatValue();
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    boolean reverse = state.getValue(SimpleRailProperties.REVERSE);

    if (powered) {
      if (reverse) {
        if (shape.equals(RailShape.NORTH_SOUTH)) {
          cart.setDeltaMovement(0, 0, 0.4D);
        } else {
          cart.setDeltaMovement(-0.4D, 0, 0);
        }
      } else {
        if (shape.equals(RailShape.NORTH_SOUTH)) {
          cart.setDeltaMovement(0, 0, -0.4D);
        } else {
          cart.setDeltaMovement(0.4D, 0, 0);
        }
      }
    }
  }

}
