package ericchiu.simplerail.setup;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.Tags;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class SimpleRailTags {

  private static final ResourceLocation RAILS_RESOURCE = new ResourceLocation(SimpleRail.MOD_ID, Tags.RAILS_TAG);
  private static final ResourceLocation WRENCH_RESOURCE = new ResourceLocation(SimpleRail.MOD_ID, Tags.WRENCH_TAG);

  public static final INamedTag<Block> RAILS = BlockTags.createOptional(RAILS_RESOURCE);
  public static final INamedTag<Block> WRENCH = BlockTags.createOptional(WRENCH_RESOURCE);

  public static final void setup() {
  }

}
