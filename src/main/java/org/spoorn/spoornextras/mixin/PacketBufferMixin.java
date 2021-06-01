package org.spoorn.spoornextras.mixin;

import net.minecraft.network.PacketBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.logging.Logger;

/**
 * Increases max string size for packet buffers.
 */
@Mixin(PacketBuffer.class)
public abstract class PacketBufferMixin {

    @Shadow
    public abstract PacketBuffer writeString(String string, int maxLength);

    private static final int MAX_STRING_SIZE = 16777216;

    @Redirect(method = "writeString(Ljava/lang/String;)Lnet/minecraft/network/PacketBuffer;", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/PacketBuffer;writeString(Ljava/lang/String;I)Lnet/minecraft/network/PacketBuffer;"))
    public PacketBuffer replaceWriteStringMaxSize(PacketBuffer packetBuffer, String string, int maxLength) {
        return this.writeString(string, MAX_STRING_SIZE);
    }

    @ModifyVariable(method = "readString(I)Ljava/lang/String;", at = @At(value = "HEAD"), ordinal = 0)
    public int modifyReadStringMaxSize(int maxLength) {
        return MAX_STRING_SIZE;
    }
}
