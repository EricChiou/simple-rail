package ericchiu.simplerail.tag;

import java.nio.file.Path;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.registry.Blocks;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockTagsProvider<T> extends TagsProvider<Block> {

  protected BlockTagsProvider(DataGenerator dataGenerator, Registry<Block> registry,
      ExistingFileHelper existingFileHelper) {
    super(dataGenerator, registry, SimpleRail.MOD_ID, existingFileHelper);
  }

  @Override
  public String getName() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected void addTags() {
    // TODO Auto-generated method stub

  }

  @Override
  protected Path getPath(ResourceLocation p_200431_1_) {
    // TODO Auto-generated method stub
    return null;
  }

}
