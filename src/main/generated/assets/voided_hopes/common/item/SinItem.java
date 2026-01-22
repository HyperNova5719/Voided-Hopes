package eya.hyper.ats.common.item;

import eya.hyper.ats.AllTheSins;
import nazario.nicosgraves.api.SoulboundItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

public class SinItem extends Item implements SoulboundItem {
    public SinItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((AllTheSins.FONT)));
    }

    @Override
    public boolean isRetained(ItemStack itemStack, PlayerEntity playerEntity, World world) {
        return true;
    }
}
