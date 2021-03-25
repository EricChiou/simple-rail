package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.item.LocomotiveCart;
import ericchiu.simplerail.item.Wrench;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {

	public static final Wrench WRENCH = new Wrench();
	public static final LocomotiveCart LOCOMOTIVE_CART = new LocomotiveCart();

	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS,
			SimpleRail.MOD_ID);

	public static void register(IEventBus bus) {
		// items
		REGISTER.register(I18n.ITEM_WRENCH, () -> WRENCH);
		REGISTER.register(I18n.ITEM_LOCOMOTIVE_CART, () -> LOCOMOTIVE_CART);

		// block items
		REGISTER.register(I18n.BLOCK_HIGH_SPEED_RAIL, () -> Blocks.HIGH_SPEED_RAIL.blockItem);
		REGISTER.register(I18n.BLOCK_HOLDING_RAIL, () -> Blocks.HOLDING_RAIL.blockItem);
		REGISTER.register(I18n.BLOCK_ONEWAY_RAIL, () -> Blocks.ONEWAY_RAIL.blockItem);
		REGISTER.register(I18n.BLOCK_EJECT_RAIL, () -> Blocks.EJECT_RAIL.blockItem);

		REGISTER.register(bus);
	}

}
