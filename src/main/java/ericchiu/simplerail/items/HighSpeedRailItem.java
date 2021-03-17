package ericchiu.simplerail.items;

import ericchiu.simplerail.registry.Blocks;
import ericchiu.simplerail.tabs.TabRail;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class HighSpeedRailItem extends BlockItem {

	public HighSpeedRailItem() {
		super(
				Blocks.HIGH_SPEED_RAIL_OBJ.get(),
				new Item.Properties().tab(TabRail.TAB));
	}

}
