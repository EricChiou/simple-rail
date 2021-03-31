package ericchiu.simplerail.link;

import java.util.ArrayList;
import java.util.LinkedList;

import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.storage.WorldSavedData;

public class LinkageManager {

  public class SaveData extends WorldSavedData {

    public SaveData(String name) {
      super(name);
    }

    @Override
    public void load(CompoundNBT p_76184_1_) {
      // TODO Auto-generated method stub
      
    }

    @Override
    public CompoundNBT save(CompoundNBT p_189551_1_) {
      // TODO Auto-generated method stub
      return null;
    }
    
  }

  public class Linkage {
    public ArrayList<AbstractMinecartEntity> cartList;
    public LinkedList<BlockPos> posList;
  }

}
