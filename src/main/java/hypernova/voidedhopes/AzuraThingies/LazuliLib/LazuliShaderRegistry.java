package hypernova.voidedhopes.LazuliLib;
/** Handles registration of shaders and post processors. */

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.Window;
import net.minecraft.util.Identifier;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LazuliShaderRegistry {

    private static final Map<String, Shader> SHADER_MAP = new HashMap<>();

    private static int resX ;
    private static int resY;

    /**
     * Registers a core shader and stores it in SHADER_MAP.
     */
    public static void registerShader(String name, String nameSpace, VertexFormat format) {
        Identifier shaderId = Identifier.of(nameSpace, name);

        CoreShaderRegistrationCallback.EVENT.register(ctx -> {
            ctx.register(shaderId, format, shaderProgram -> {
                SHADER_MAP.put(name, shaderProgram);
                Lazuli_Lib_Client.LOGGER.info("Shader '{}' registered!", name);
            });
        });
    }
    public static Shader getShader(String name) {
        return SHADER_MAP.get(name);
    }

}
