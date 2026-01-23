package eya.hyper.ats.common.item;

import eya.hyper.ats.AllTheSins;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;

public class GodsGravestoneItem extends SwordItem {
    public GodsGravestoneItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((AllTheSins.FONT)));
    }
}
