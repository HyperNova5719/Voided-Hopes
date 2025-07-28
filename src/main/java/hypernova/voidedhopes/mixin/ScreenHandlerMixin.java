package hypernova.voidedhopes.mixin;

import hypernova.voidedhopes.item.custom.WayfinderItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ScreenHandler.class)
public class ScreenHandlerMixin {
    @Shadow
    @Final
    public DefaultedList<Slot> slots;

    @Inject(method = "internalOnSlotClick", at = @At("HEAD"), cancellable = true)
    private void charmed$toggleMode(int slotIndex, int button, SlotActionType actionType, PlayerEntity player, CallbackInfo ci) {
        if (slotIndex >= 0 && slotIndex < this.slots.size()) {
            if (button == 1) {
                Slot slot = this.slots.get(slotIndex);
                ItemStack stack = slot.getStack();
                if (stack.getItem() instanceof WayfinderItem wayfinder) {
                    boolean quickMove = actionType == SlotActionType.QUICK_MOVE;
                    wayfinder.incrementType(stack, quickMove);
                    // player.playSound(ModSoundEvents.SPRITE_OPEN, SoundCategory.PLAYERS, 0.9f, 1.5f);
                    ci.cancel();
                }
            }
        }
    }
}
