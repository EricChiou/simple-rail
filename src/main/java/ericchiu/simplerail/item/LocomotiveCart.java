package ericchiu.simplerail.item;

import ericchiu.simplerail.entity.LocomotiveCartEntity;
import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.setup.SimpleRailTags;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity.Type;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.MinecartItem;
import net.minecraft.item.Rarity;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LocomotiveCart extends MinecartItem {

  public LocomotiveCart() {
    super( //
        Type.FURNACE, //
        new Item.Properties(). //
            rarity(Rarity.UNCOMMON). //
            fireResistant(). //
            stacksTo(64). //
            tab(Rail.TAB));
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    BlockState blockstate = world.getBlockState(blockpos);

    if (!blockstate.is(SimpleRailTags.RAILS) && !blockstate.is(BlockTags.RAILS)) {
      return ActionResultType.FAIL;
    }

    ItemStack itemstack = context.getItemInHand();
    itemstack.shrink(1);

    if (world.isClientSide) {
      return ActionResultType.sidedSuccess(world.isClientSide);
    }

    RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock
        ? ((AbstractRailBlock) blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null)
        : RailShape.NORTH_SOUTH;
    double deltaY = railshape.isAscending() ? 0.5d : 0.0d;

    LocomotiveCartEntity locomotiveCartEntity = new LocomotiveCartEntity(world, blockpos.getX() + 0.5d,
        blockpos.getY() + 0.0625d + deltaY, blockpos.getZ() + 0.5d);

    if (itemstack.hasCustomHoverName()) {
      locomotiveCartEntity.setCustomName(itemstack.getHoverName());
    }

    world.addFreshEntity(locomotiveCartEntity);

    return ActionResultType.sidedSuccess(world.isClientSide);
  }

}
