package ericchiu.simplerail.block;

import java.util.List;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.setup.SimpleRailProperties;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class EjectRail extends PoweredRailBlock {

  public static final BooleanProperty REVERSE = SimpleRailProperties.REVERSE;
  public static final BooleanProperty NEED_POWER = SimpleRailProperties.NEED_POWER;

  public final BlockItem blockItem;

  public EjectRail() {
    super(AbstractBlock.Properties //
        .of(Material.METAL) //
        .strength(0.7f). //
        harvestLevel(0). //
        harvestTool(ToolType.PICKAXE). //
        sound(SoundType.METAL). //
        noCollission(), //
        true);

    this.registerDefaultState(this.stateDefinition.any().setValue(REVERSE, false));

    blockItem = new BlockItem(this, new Item.Properties().tab(Rail.TAB));
  }

  @Override
  protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
    builder.add(REVERSE);
    builder.add(NEED_POWER);
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

    if (powered || !CommonConfig.INSTANCE.ejectRailNeedPower.get()) {
      List<Entity> passengers = cart.getPassengers();
      if (passengers.size() > 0) {
        cart.ejectPassengers();

        if (reverse) {
          if (shape.equals(RailShape.NORTH_SOUTH)) {
            passengers.forEach((passenger) -> {
              transportPassenger(pos, passenger, CommonConfig.INSTANCE.ejectRailTransportDistance.get(), 0, 0);
            });
          } else {
            passengers.forEach((passenger) -> {
              transportPassenger(pos, passenger, 0, 0, CommonConfig.INSTANCE.ejectRailTransportDistance.get());
            });
          }
        } else {
          if (shape.equals(RailShape.NORTH_SOUTH)) {
            passengers.forEach((passenger) -> {
              transportPassenger(pos, passenger, -CommonConfig.INSTANCE.ejectRailTransportDistance.get(), 0, 0);
            });
          } else {
            passengers.forEach((passenger) -> {
              transportPassenger(pos, passenger, 0, 0, -CommonConfig.INSTANCE.ejectRailTransportDistance.get());
            });
          }
        }
      }
    }
  }

  @Override
  public void onPlace(BlockState state, World world, BlockPos pos, BlockState originState, boolean bool) {
    BlockState newState = state.setValue(NEED_POWER, CommonConfig.INSTANCE.ejectRailNeedPower.get());
    super.onPlace(newState, world, pos, originState, bool);
  }

  private void transportPassenger(BlockPos pos, Entity passenger, double deltaX, double deltaY, double deltaZ) {
    passenger.setDeltaMovement(Vector3d.ZERO);
    passenger.moveTo(pos.getX() + deltaX, pos.getY() + deltaY, pos.getZ() + deltaZ);
  }

}
