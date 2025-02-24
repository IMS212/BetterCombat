package net.bettercombat.client;

import com.mojang.logging.LogUtils;
import dev.kosmx.playerAnim.core.data.KeyframeAnimation;
import dev.kosmx.playerAnim.core.data.gson.AnimationSerializing;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimationRegistry {
    static final Logger LOGGER = LogUtils.getLogger();
    public static Map<String, KeyframeAnimation> animations = new HashMap<>();

    public static void load(ResourceManager resourceManager) {
        var dataFolder = "attack_animations";
        resourceManager.findResources(dataFolder, fileName -> fileName.getPath().endsWith(".json")).forEach((identifier, resource) -> {
            try {
                List<KeyframeAnimation> readAnimations = AnimationSerializing.deserializeAnimation(resource.getInputStream());
                KeyframeAnimation animation = readAnimations.get(0);

                var id = identifier
                        .toString()
                        .replace(dataFolder + "/", "");
                id = id.substring(0, id.lastIndexOf('.'));
                AnimationRegistry.animations.put(id, animation);
            } catch (Exception e) {
                LOGGER.error("Failed to load animation " + identifier.toString());
                e.printStackTrace();
            }
        });
    }
}
