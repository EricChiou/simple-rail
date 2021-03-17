package ericchiu.simplerail.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class HighSpeedRail extends PoweredRailBlock {

	public HighSpeedRail() {
		super(AbstractBlock.Properties.of(Material.METAL)
				.strength(5.0f, 6.0f)
				.harvestLevel(0)
				.harvestTool(ToolType.PICKAXE)
				.noCollission());
	}

}
