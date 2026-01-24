package hypernova.voidedhopes.item.custom;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;

public class GodsGravestoneItem extends SwordItem {
    private static final GodsGravestoneMaterial INSTANCE = new GodsGravestoneMaterial();

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((VoidedHopes.FONT_2)));
    }

    public GodsGravestoneItem(Settings settings) {
        super(INSTANCE, 6, -2.95f, settings);
    }

    public static class GodsGravestoneMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 0;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 0;
        }

        @Override
        public float getAttackDamage() {
            return 6.0F;
        }

        @Override
        public int getMiningLevel() {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return null;
        }
    }
}
