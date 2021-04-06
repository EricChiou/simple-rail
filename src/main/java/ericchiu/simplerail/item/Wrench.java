package ericchiu.simplerail.item;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.link.LinkageManager;
import ericchiu.simplerail.setup.SimpleRailProperties;
import ericchiu.simplerail.setup.SimpleRailTags;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
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
    BlockState state = world.getBlockState(pos);

    ServerWorld serverWorld = (ServerWorld) world;
    BlockPos firstPos = pos;
    BlockPos secondPos = pos;
    Direction direction = context.getHorizontalDirection();
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

    List<AbstractMinecartEntity> carts = new ArrayList<AbstractMinecartEntity>();
    carts.addAll(world.getEntitiesOfClass(AbstractMinecartEntity.class, new AxisAlignedBB(firstPos)));
    carts.addAll(world.getEntitiesOfClass(AbstractMinecartEntity.class, new AxisAlignedBB(secondPos)));
    System.out.println("11111 useOn");
    System.out.println(carts);

    LocomotiveCartEntity locomotiveCart = null;
    for (AbstractMinecartEntity cart : carts) {
      UUID locomotiveUuid = LinkageManager.getLocomotiveUuid(cart.getUUID());
      if (locomotiveUuid != null) {
        locomotiveCart = (LocomotiveCartEntity) serverWorld.getEntity(locomotiveUuid);
      }
    }

    if (locomotiveCart != null) {
      for (AbstractMinecartEntity cart : carts) {
        if (LinkageManager.checkCartLinkable(cart.getUUID())) {
          locomotiveCart.linkNewCart(cart);
          world.addParticle(ParticleTypes.SMOKE, //
              loc.x, loc.y, loc.z, //
              0.0D, 0.0D, 0.0D);
          world.playSound(context.getPlayer(), pos, SoundEvents.CHAIN_HIT, SoundCategory.VOICE, 0, 0);
        }
      }
    }

    if (carts.size() <= 0 && state.is(SimpleRailTags.RAILS)) {
      if (state.hasProperty(SimpleRailProperties.REVERSE)) {
        boolean reverse = state.getValue(SimpleRailProperties.REVERSE);
        world.setBlock(pos, state.setValue(SimpleRailProperties.REVERSE, !reverse), 3);
      }

      return ActionResultType.SUCCESS;
    }

    return ActionResultType.FAIL;
  }

}
