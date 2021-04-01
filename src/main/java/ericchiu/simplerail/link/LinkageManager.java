package ericchiu.simplerail.link;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ericchiu.simplerail.constants.I18n;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

public class LinkageManager extends WorldSavedData {

  private final ServerWorld serverWorld;
  private Map<UUID, ArrayList<AbstractMinecartEntity>> trains = new HashMap<UUID, ArrayList<AbstractMinecartEntity>>();

  public LinkageManager(ServerWorld serverWorld) {
    super(I18n.LINKAGE_DATA_NAME);
    this.serverWorld = serverWorld;
  }

  @Override
  public void load(CompoundNBT compound) {
    for (String key : compound.getAllKeys()) {
      ArrayList<AbstractMinecartEntity> train = new ArrayList<AbstractMinecartEntity>();

      for (INBT nbt : (ListNBT) compound.get(key)) {
        try {
          UUID cartUuid = UUID.fromString(((StringNBT) nbt).getAsString());
          AbstractMinecartEntity cart = (AbstractMinecartEntity) this.serverWorld.getEntity(cartUuid);
          if (cart != null) {
            train.add(cart);
          }
        } catch (Exception e) {
        }
      }

      this.trains.put(UUID.fromString(key), train);
    }
  }

  @Override
  public CompoundNBT save(CompoundNBT compound) {
    for (UUID locomotiveUuid : this.trains.keySet()) {
      if (this.serverWorld.getEntity(locomotiveUuid) != null && this.trains.get(locomotiveUuid).size() > 0) {
        ListNBT listNbt = new ListNBT();
        for (AbstractMinecartEntity cart : this.trains.get(locomotiveUuid)) {
          if (this.serverWorld.getEntity(cart.getUUID()) != null) {
            listNbt.add(StringNBT.valueOf(cart.getUUID().toString()));
          }
        }

        compound.put(locomotiveUuid.toString(), listNbt);
      }
    }

    return compound;
  }

  public ArrayList<AbstractMinecartEntity> getTrain(UUID uuid) {
    ArrayList<AbstractMinecartEntity> train = this.trains.get(uuid);
    return train == null ? new ArrayList<AbstractMinecartEntity>() : train;
  }

  public void updateTrain(UUID uuid, ArrayList<AbstractMinecartEntity> train) {
    this.trains.put(uuid, train);
    setDirty();
  }

  public boolean checkCartLinkable(UUID uuid) {
    for (UUID locomotiveUuid : this.trains.keySet()) {
      for (AbstractMinecartEntity cart : this.trains.get(locomotiveUuid)) {
        if (cart.getUUID().equals(uuid)) {
          return false;
        }
      }
    }

    return true;
  }

  public static LinkageManager get(World world) {
    ServerWorld serverWorld = world.getServer().getLevel(world.dimension());
    DimensionSavedDataManager storage = serverWorld.getDataStorage();
    LinkageManager linkageManager = storage.get(() -> new LinkageManager(serverWorld), I18n.LINKAGE_DATA_NAME);

    if (linkageManager == null) {
      linkageManager = new LinkageManager(serverWorld);
      storage.set(linkageManager);
    }

    return linkageManager;
  }

}
