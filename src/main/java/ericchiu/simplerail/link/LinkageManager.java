package ericchiu.simplerail.link;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ericchiu.simplerail.constants.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class LinkageManager extends WorldSavedData {
  private static final String CART_DATA_NAME = "cart";

  private Map<UUID, ArrayList<AbstractMinecartEntity>> trains = new HashMap<UUID, ArrayList<AbstractMinecartEntity>>();
  private final World world;
  private final ServerWorld serverWorld;

  public LinkageManager(ServerWorld serverWorld, World world) {
    super(I18n.LINKAGE_DATA_NAME);
    this.serverWorld = serverWorld;
    this.world = world;
  }

  @Override
  public void load(CompoundNBT compound) {
    for (String key : compound.getAllKeys()) {
      ListNBT listNBT = (ListNBT) compound.get(key);
      for (INBT nbt : listNBT) {
        StringNBT stringNbt = (StringNBT) nbt;
        stringNbt.getAsString();
      }
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT compound) {
    return compound;
  }

  public static LinkageManager get(World world) {
    ServerWorld serverWorld = world.getServer().getLevel(world.dimension());
    DimensionSavedDataManager storage = serverWorld.getDataStorage();

    return storage.get(() -> {
      return new LinkageManager(serverWorld, world);
    }, I18n.LINKAGE_DATA_NAME);
  }

}
