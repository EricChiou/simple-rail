package ericchiu.simplerail.item;

import java.util.List;
import java.util.UUID;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.link.LinkageManager;
import ericchiu.simplerail.setup.SimpleRailProperties;
import ericchiu.simplerail.setup.SimpleRailTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class Wrench extends Item {

  public Wrench() {
    super(new Item.Properties() //
        .addToolType(ToolType.HOE, 0) //
        .rarity(Rarity.COMMON) //
        .fireResistant() //
        .stacksTo(1) //
        .tab(Rail.TAB));
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    if (world.isClientSide) {
      return ActionResultType.SUCCESS;
    }

    Vector3d loc = context.getClickLocation();
    BlockPos pos = context.getClickedPos();

    BlockPos firstPos = pos;
    BlockPos secondPos = this.getSecondPos(context.getHorizontalDirection(), pos, loc);

    List<AbstractMinecartEntity> firstPosCarts = this.getCartsByPos(world, firstPos);
    List<AbstractMinecartEntity> secondPosCarts = this.getCartsByPos(world, secondPos);

    ServerWorld serverWorld = (ServerWorld) world;
    LocomotiveCartEntity locomotive = this.getLocomotive(serverWorld, firstPosCarts);
    if (locomotive == null) {
      locomotive = this.getLocomotive(serverWorld, secondPosCarts);
    }

    if (locomotive != null) {
      this.linkCarts(context, locomotive, firstPosCarts);
      this.linkCarts(context, locomotive, secondPosCarts);
    }

    BlockState state = world.getBlockState(pos);
    if (firstPosCarts.size() <= 0 && state.is(SimpleRailTags.RAILS)) {
      this.changeReverse(world, state, pos);
    }

    if (state.is(SimpleRailTags.MACHINES)) {
      this.changeFacing(world, state, pos);
    }

    return ActionResultType.CONSUME;
  }

  private void changeReverse(World world, BlockState state, BlockPos pos) {
    if (state.hasProperty(SimpleRailProperties.REVERSE)) {
      boolean reverse = state.getValue(SimpleRailProperties.REVERSE);
      world.setBlock(pos, state.setValue(SimpleRailProperties.REVERSE, !reverse), 3);
    }
  }

  private void changeFacing(World world, BlockState state, BlockPos pos) {
    if (state.hasProperty(DirectionalBlock.FACING)) {
      Direction direction = state.getValue(DirectionalBlock.FACING);
      System.out.println("11111 changeFacing");
      System.out.println(direction);

      if (direction.equals(Direction.EAST)) {
        world.setBlock(pos, state.setValue(DirectionalBlock.FACING, Direction.SOUTH), 3);
      } else if (direction.equals(Direction.WEST)) {
        world.setBlock(pos, state.setValue(DirectionalBlock.FACING, Direction.NORTH), 3);
      } else if (direction.equals(Direction.NORTH)) {
        world.setBlock(pos, state.setValue(DirectionalBlock.FACING, Direction.EAST), 3);
      } else if (direction.equals(Direction.SOUTH)) {
        world.setBlock(pos, state.setValue(DirectionalBlock.FACING, Direction.WEST), 3);
      }
    }
  }

  private BlockPos getSecondPos(Direction direction, BlockPos pos, Vector3d loc) {
    BlockPos secondPos = pos;

    if (direction.equals(Direction.EAST) || direction.equals(Direction.WEST)) {
      double decimal = loc.z - pos.getZ();
      if (decimal < 0.5D) {
        secondPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
      } else if (decimal > 0.5D) {
        secondPos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
      }
    } else if (direction.equals(Direction.NORTH) || direction.equals(Direction.SOUTH)) {
      double decimal = loc.x - pos.getX();
      if (decimal < 0.5D) {
        secondPos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
      } else if (decimal > 0.5D) {
        secondPos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
      }
    }

    return secondPos;
  }

  private List<AbstractMinecartEntity> getCartsByPos(World world, BlockPos pos) {
    return world.getEntitiesOfClass(AbstractMinecartEntity.class, new AxisAlignedBB(pos));
  }

  private LocomotiveCartEntity getLocomotive(ServerWorld serverWorld, List<AbstractMinecartEntity> carts) {
    for (AbstractMinecartEntity cart : carts) {
      UUID locomotiveUuid = LinkageManager.getLocomotiveUuid(cart.getUUID());
      if (locomotiveUuid != null) {
        LocomotiveCartEntity locomotive = (LocomotiveCartEntity) serverWorld.getEntity(locomotiveUuid);
        if (locomotive != null) {
          return locomotive;
        }
      }
    }

    return null;
  }

  private void linkCarts(ItemUseContext context, LocomotiveCartEntity locomotive, List<AbstractMinecartEntity> carts) {
    World world = context.getLevel();
    Vector3d loc = context.getClickLocation();
    BlockPos pos = context.getClickedPos();
    PlayerEntity player = context.getPlayer();

    for (AbstractMinecartEntity cart : carts) {
      locomotive.linkNewCart((ServerWorld) world, cart);
      world.addParticle(ParticleTypes.SMOKE, //
          loc.x, loc.y, loc.z, //
          0.0D, 0.0D, 0.0D);
      world.playSound(player, pos, SoundEvents.CHAIN_HIT, SoundCategory.VOICE, 3F, 3F);
    }
  }

}
