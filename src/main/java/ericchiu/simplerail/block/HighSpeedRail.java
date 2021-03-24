package ericchiu.simplerail.block;

import ericchiu.simplerail.config.CommonConfig;
import ericchiu.simplerail.itemgroup.Rail;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.PoweredRailBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class HighSpeedRail extends PoweredRailBlock {

	public final BlockItem blockItem;

	public HighSpeedRail() {
		super(AbstractBlock.Properties //
				.of(Material.METAL) //
				.strength(0.7f). //
				harvestLevel(0). //
				harvestTool(ToolType.PICKAXE). //
				sound(SoundType.METAL). //
				noCollission(), //
				true);

		blockItem = new BlockItem(this, new Item.Properties().tab(Rail.TAB));
	}

	@Override
	public float getRailMaxSpeed(BlockState state, World world, BlockPos pos, AbstractMinecartEntity cart) {
		return CommonConfig.INSTANCE.highSpeedRailMaxSpeed.get().floatValue();
	}

	@Override
	public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
		return false;
	}

}
