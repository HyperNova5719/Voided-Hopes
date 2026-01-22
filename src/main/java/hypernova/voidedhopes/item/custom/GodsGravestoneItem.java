package hypernova.voidedhopes.item.custom;

import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class GodsGravestoneItem extends SwordItem {
    private static final GodsGravestoneMaterial INSTANCE = new GodsGravestoneMaterial();

    public GodsGravestoneItem(Settings settings) {
        super(INSTANCE, 5, -2.8f, settings);
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
