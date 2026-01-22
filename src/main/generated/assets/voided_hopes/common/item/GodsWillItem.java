package eya.hyper.ats.common.item;

import eya.hyper.ats.AllTheSins;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class GodsWillItem extends Item {
    public GodsWillItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((AllTheSins.FONT)));
    }
}
