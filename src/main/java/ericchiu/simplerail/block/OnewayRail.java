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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class OnewayRail extends PoweredRailBlock {

  public static final BooleanProperty REVERSE = SimpleRailProperties.REVERSE;
  public static final BooleanProperty NEED_POWER = SimpleRailProperties.NEED_POWER;
  public static final BooleanProperty USE_POWER = SimpleRailProperties.USE_POWER;

  public final BlockItem blockItem;

  public OnewayRail() {
    super(AbstractBlock.Properties //
        .of(Material.METAL) //
        .strength(0.7f). //
        harvestLevel(0). //
        harvestTool(ToolType.PICKAXE). //
        sound(SoundType.METAL). //
        noCollission(), //
        true);

    this.registerDefaultState(this.stateDefinition.any() //
        .setValue(REVERSE, false) //
        .setValue(NEED_POWER, CommonConfig.INSTANCE.onewayRailNeedPower.get()) //
        .setValue(USE_POWER, CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get()));

    blockItem = new BlockItem(this, new Item.Properties().tab(Rail.TAB));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(REVERSE);
    builder.add(NEED_POWER);
    builder.add(USE_POWER);
    super.createBlockStateDefinition(builder);
  }

  @Override
  public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    return CommonConfig.INSTANCE.highSpeedRailMaxSpeed.get().floatValue();
  }

  @Override
  public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
    return false;
  }

  @Override
  public void onMinecartPass(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
    RailShape shape = state.getValue(BlockStateProperties.RAIL_SHAPE_STRAIGHT);
    boolean powered = state.getValue(BlockStateProperties.POWERED);
    boolean reverse = state.getValue(SimpleRailProperties.REVERSE);

    if (CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get() //
        || powered //
        || !CommonConfig.INSTANCE.onewayRailNeedPower.get() //
    ) {
      if (reverse) {
        goForward(shape, cart);
      } else {
        goReverse(shape, cart);
      }
    }
  }

  @Override
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState originState, boolean bool) {
    BlockState newState = state //
        .setValue(NEED_POWER, CommonConfig.INSTANCE.onewayRailNeedPower.get())
        .setValue(USE_POWER, CommonConfig.INSTANCE.onewayRailUsePowerChangeDirection.get());

    super.onPlace(newState, world, pos, originState, bool);
  }

  private void goForward(RailShape shape, AbstractMinecartEntity cart) {
    if (shape.equals(RailShape.NORTH_SOUTH)) {
      cart.setDeltaMovement(0, 0, 0.4d);
    } else {
      cart.setDeltaMovement(-0.4d, 0, 0);
    }
  }

  private void goReverse(RailShape shape, AbstractMinecartEntity cart) {
    if (shape.equals(RailShape.NORTH_SOUTH)) {
      cart.setDeltaMovement(0, 0, -0.4d);
    } else {
      cart.setDeltaMovement(0.4d, 0, 0);
    }
  }

}
