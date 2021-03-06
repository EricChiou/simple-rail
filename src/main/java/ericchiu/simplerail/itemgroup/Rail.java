package ericchiu.simplerail.itemgroup;

import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.registry.Items;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Rail {

	public static final ItemGroup TAB = new ItemGroup(I18n.TAB_RAIL) {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(Items.HIGH_SPEED_RAIL.get());
		}
	};

}
