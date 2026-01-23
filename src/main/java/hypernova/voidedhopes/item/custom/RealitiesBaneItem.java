package hypernova.voidedhopes.item.custom;

import hypernova.voidedhopes.VoidedHopes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;

public class RealitiesBaneItem extends SwordItem {
    private static final RealitiesBaneMaterial INSTANCE = new RealitiesBaneMaterial();

    public RealitiesBaneItem(Settings settings) {
        super(INSTANCE, 3, -2.4f, settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        Text name = super.getName(stack);
        return Text.literal("").append(name).setStyle(name.getStyle().withFont((VoidedHopes.FONT_2)));
    }

    public static class RealitiesBaneMaterial implements ToolMaterial {
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
