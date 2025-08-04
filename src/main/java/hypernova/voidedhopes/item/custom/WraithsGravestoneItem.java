package hypernova.voidedhopes.item.custom;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class WraithsGravestoneItem extends SwordItem {
    private static final WraithsGravestoneMaterial INSTANCE = new WraithsGravestoneMaterial();

    public WraithsGravestoneItem(Settings settings) {
        super(INSTANCE, 3, -3.2f, settings);
    }

    public static class WraithsGravestoneMaterial implements ToolMaterial {
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
