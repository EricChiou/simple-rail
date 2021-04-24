package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.constants.I18n;
import ericchiu.simplerail.item.LocomotiveCart;
import ericchiu.simplerail.item.Wrench;
import ericchiu.simplerail.itemgroup.Rail;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Items {

	private static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS,
			SimpleRail.MOD_ID);

	// items
	public static final RegistryObject<Item> WRENCH = REGISTER.register(I18n.ITEM_WRENCH, () -> new Wrench());
	public static final RegistryObject<Item> LOCOMOTIVE_CART = REGISTER.register(I18n.ITEM_LOCOMOTIVE_CART,
			() -> new LocomotiveCart());

	// block items
	// rail
	public static final RegistryObject<Item> HIGH_SPEED_RAIL = REGISTER.register(I18n.BLOCK_HIGH_SPEED_RAIL,
			() -> new BlockItem(Blocks.HIGH_SPEED_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> HOLDING_RAIL = REGISTER.register(I18n.BLOCK_HOLDING_RAIL,
			() -> new BlockItem(Blocks.HOLDING_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> ONEWAY_RAIL = REGISTER.register(I18n.BLOCK_ONEWAY_RAIL,
			() -> new BlockItem(Blocks.ONEWAY_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> EJECT_RAIL = REGISTER.register(I18n.BLOCK_EJECT_RAIL,
			() -> new BlockItem(Blocks.EJECT_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> DESTORY_RAIL = REGISTER.register(I18n.BLOCK_DESTORY_RAIL,
			() -> new BlockItem(Blocks.DESTORY_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> TIMER_HOLDING_RAIL = REGISTER.register(I18n.BLOCK_TIMER_HOLDING_RAIL,
			() -> new BlockItem(Blocks.TIMER_HOLDING_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> CROSS_RAIL = REGISTER.register(I18n.BLOCK_CROSS_RAIL,
			() -> new BlockItem(Blocks.CROSS_RAIL.get(), new Item.Properties().tab(Rail.TAB)));
	// machine
	public static final RegistryObject<Item> TRAIN_DISPENSER = REGISTER.register(I18n.BLOCK_TRAIN_DISPENSER,
			() -> new BlockItem(Blocks.TRAIN_DISPENSER.get(), new Item.Properties().tab(Rail.TAB)));
	public static final RegistryObject<Item> SIGNAL_TIMER = REGISTER.register(I18n.BLOCK_SIGNAL_TIMER,
			() -> new BlockItem(Blocks.SIGNAL_TIMER.get(), new Item.Properties().tab(Rail.TAB)));

	public static void register(IEventBus bus) {
		REGISTER.register(bus);
	}

}
