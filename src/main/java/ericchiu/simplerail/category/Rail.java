package ericchiu.simplerail.category;

import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.registry.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Rail {

	public static final ItemGroup TAB = new ItemGroup(I18n.TAB_RAIL) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Blocks.HIGH_SPEED_RAIL.blockItem);
		}
	};
	
}
