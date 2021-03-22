package ericchiu.simplerail.item;

import ericchiu.simplerail.category.Rail;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity.Type;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.MinecartItem;
import net.minecraft.item.Rarity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MineCart extends MinecartItem {

  public MineCart() {
    super(Type.CHEST, new Item.Properties().rarity(Rarity.COMMON).stacksTo(64).tab(Rail.TAB));
  }

  @Override
  public ActionResultType useOn(ItemUseContext context) {
    World world = context.getLevel();
    BlockPos blockpos = context.getClickedPos();
    BlockState blockstate = world.getBlockState(blockpos);

    System.out.println("MineCart useOn =========================");
    System.out.println(blockstate.is(BlockTags.RAILS));
    System.out.println("========================================");
    if (!blockstate.is(BlockTags.RAILS)) {
      return ActionResultType.FAIL;
    }

    return ActionResultType.SUCCESS;
  }

}
