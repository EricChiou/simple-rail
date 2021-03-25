package ericchiu.simplerail.item;

import ericchiu.simplerail.itemgroup.Rail;
import ericchiu.simplerail.setup.SimpleRailProperties;
import ericchiu.simplerail.setup.SimpleRailTags;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.item.Rarity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
    BlockPos blockpos = context.getClickedPos();
    BlockState blockstate = world.getBlockState(blockpos);

    if (!blockstate.is(SimpleRailTags.RAILS)) {
      return ActionResultType.FAIL;
    }

    if (blockstate.hasProperty(SimpleRailProperties.REVERSE)) {
      boolean reverse = blockstate.getValue(SimpleRailProperties.REVERSE);
      world.setBlock(blockpos, blockstate.setValue(SimpleRailProperties.REVERSE, !reverse), 3);
    }

    return ActionResultType.SUCCESS;
  }

}
