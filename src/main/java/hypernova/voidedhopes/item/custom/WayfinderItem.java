package hypernova.voidedhopes.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class WayfinderItem extends Item {
    public WayfinderItem(Settings settings) {
        super(settings);
    }

    public static boolean isOpen(ItemStack stack) {
        return stack.getOrCreateNbt().getBoolean("open");
    }

    public void incrementType(ItemStack stack, boolean sneaking) {
        if (sneaking) {
            NbtCompound compound = stack.getOrCreateNbt();
            compound.putBoolean("open", !compound.getBoolean("open"));
        }
    }
}
