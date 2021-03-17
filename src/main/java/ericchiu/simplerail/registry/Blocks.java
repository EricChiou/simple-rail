package ericchiu.simplerail.registry;

import ericchiu.simplerail.SimpleRail;
import ericchiu.simplerail.blocks.HighSpeedRail;
import ericchiu.simplerail.constants.I18n;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Blocks {
	
	public static final DeferredRegister<Block> REGISTER =
			DeferredRegister.create(ForgeRegistries.BLOCKS, SimpleRail.MOD_ID);
		
	public static final RegistryObject<Block> HIGH_SPEED_RAIL_OBJ =
			REGISTER.register(I18n.BLOCK_HIGH_SPEED_RAIL, () -> new HighSpeedRail());

}
