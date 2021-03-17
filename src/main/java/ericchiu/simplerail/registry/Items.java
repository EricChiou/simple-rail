package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.items.HighSpeedRailItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {
	
	public static final HighSpeedRailItem HIGH_SPEED_RAIL_ITEM = null;
	
	public static final DeferredRegister<Item> REGISTER =
			DeferredRegister.create(ForgeRegistries.ITEMS, SimpleRail.MOD_ID);
	
	// items
	
	// block items
	public static final RegistryObject<Item> HIGH_SPEED_RAIL_ITEM_OBJ =
			Items.REGISTER.register(I18n.BLOCK_ITEM_HIGH_SPEED_RAIL, () -> new HighSpeedRailItem());

}
