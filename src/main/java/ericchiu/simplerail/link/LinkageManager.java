package ericchiu.simplerail.link;

import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import ericchiu.simplerail.constants.I18n;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.INBTType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.storage.WorldSavedData;

public class LinkageManager {

  public class SaveData extends WorldSavedData {
    private static final String DATA_NAME = I18n.TRAIN_DATA_NAME;

    public CompoundNBT data = new CompoundNBT();

    public SaveData() {
      super(DATA_NAME);
    }

    @Override
    public void load(CompoundNBT nbt) {
      this.data = nbt.getCompound(DATA_NAME);
    }

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
      nbt.put(DATA_NAME, this.data);
      return nbt;
    }

    // public static ObsidianWorldSavedData get(World world) {
    //   if (!(worldIn instanceof ServerWorld)) {
    //     throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
    //   }

    //   ServerWorld world = worldIn.getServer().getWorld(DimensionType.OVERWORLD);
    //   /***
    //    * 如果你需要每个纬度都有一个自己的World Saved Data。 用 ServerWorld world = (ServerWorld)world;
    //    * 代替上面那句。
    //    */
    //   DimensionSavedDataManager storage = world.getSavedData();
    //   return storage.getOrCreate(() -> {
    //     return new ObsidianWorldSavedData();
    //   }, NAME);
    // }

  }

  public class Train implements INBT {

    public ArrayList<AbstractMinecartEntity> cartList = new ArrayList<AbstractMinecartEntity>();
    public LinkedList<BlockPos> posList = new LinkedList<BlockPos>();

    @Override
    public void write(DataOutput output) throws IOException {
    }

    @Override
    public byte getId() {
      return 0;
    }

    @Override
    public INBTType<?> getType() {
      return null;
    }

    @Override
    public INBT copy() {
      return null;
    }

    @Override
    public ITextComponent getPrettyDisplay(String indentation, int indentDepth) {
      return new StringTextComponent(indentation + indentDepth);
    }

  }

}
