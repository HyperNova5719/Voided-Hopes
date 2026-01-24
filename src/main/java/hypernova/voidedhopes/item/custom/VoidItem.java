package hypernova.voidedhopes.item.custom;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class VoidItem extends Item{
        public VoidItem(Item.Settings settings) {
            super(settings);
        }

        @Override
        public Text getName(ItemStack stack) {
            Text name = super.getName(stack);
            return Text.literal("").append(name).setStyle(name.getStyle().withFont((VoidedHopes.FONT)));
        }
    }