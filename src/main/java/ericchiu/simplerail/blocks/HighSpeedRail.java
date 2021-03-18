package ericchiu.simplerail.blocks;

import ericchiu.simplerail.tabs.TabRail;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class HighSpeedRail extends PoweredRailBlock {
	
	public final BlockItem blockItem;

	public HighSpeedRail() {
		super(AbstractBlock.Properties.of(Material.METAL)
		.strength(0.7f)
		.harvestLevel(0)
		.harvestTool(ToolType.PICKAXE)
		.sound(SoundType.METAL)
		.noCollission(),
		true);
		blockItem = new BlockItem(this, new Item.Properties().tab(TabRail.TAB));
	}

}
