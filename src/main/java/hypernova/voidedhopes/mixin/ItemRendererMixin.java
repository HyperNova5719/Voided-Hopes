package hypernova.voidedhopes.mixin;

import hypernova.voidedhopes.VoidedHopes;
import hypernova.voidedhopes.item.ModItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin
{
    private static final ModelIdentifier GODS_GRAVESTONE_BEEG;

    @Shadow
    @Final
    private ItemModels models;

    @Shadow public abstract BakedModel getModel(ItemStack stack, @Nullable World world, @Nullable LivingEntity entity, int seed);

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    public BakedModel onRenderItem(BakedModel value, ItemStack stack, ModelTransformationMode mode)
    {
        if(stack.isOf(hypernova.voidedhopes.item.ModItems.GODS_GRAVESTONE)) {
            if (mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformationMode.FIRST_PERSON_RIGHT_HAND) {
                return models.getModelManager().getModel(GODS_GRAVESTONE_BEEG);
            }
            if (mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || mode == ModelTransformationMode.FIRST_PERSON_LEFT_HAND) {
                return models.getModelManager().getModel(GODS_GRAVESTONE_BEEG);
            }
            if (mode == ModelTransformationMode.FIXED) {
                return models.getModelManager().getModel(GODS_GRAVESTONE_BEEG);
            }
        }

        return value;
    }

    static {
        GODS_GRAVESTONE_BEEG = new ModelIdentifier(VoidedHopes.id("gods_gravestone_beeg"), "inventory");
    }
}