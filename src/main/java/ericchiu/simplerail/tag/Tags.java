package ericchiu.simplerail.tag;

import ericchiu.simplerail.SimpleRail;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraft.util.ResourceLocation;

public class Tags {

  private static final ResourceLocation RAILS_RESOURCE = new ResourceLocation(SimpleRail.MOD_ID, "rails");

  public static final INamedTag<Block> RAILS = BlockTags.createOptional(RAILS_RESOURCE);

  public static final void setup() {
  }

}
