package com.rynzier.taketotheskies;

import com.rynzier.taketotheskies.item.ModItems;
import com.rynzier.taketotheskies.render.WingsLayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(TakeToTheSkies.MODID)
public class TakeToTheSkies {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "taketotheskies";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public TakeToTheSkies(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);


        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (TaketotheSkies) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.addListener(this::onServerStarting);

        ModItems.register(modEventBus);

        modEventBus.addListener(this::addLayers);
        modEventBus.addListener(this::registerAdditional);
        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.TEST_WINGS);
        }
    }

    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    public void addLayers(EntityRenderersEvent.AddLayers renderEvent) {
        for (PlayerSkin.Model skin : renderEvent.getSkins()) {
            var renderer = renderEvent.getSkin(skin);
            if (renderer instanceof PlayerRenderer pRenderer) {
                pRenderer.addLayer(new WingsLayer(pRenderer));
            }
        }
    }

    public void registerAdditional(ModelEvent.RegisterAdditional event) {
        ModelResourceLocation wingsModel = new ModelResourceLocation(
            ResourceLocation.fromNamespaceAndPath(MODID, "block/wings"),
            "standalone"
        );
        event.register(wingsModel);
        WingsLayer.wingsModel = Minecraft.getInstance().getModelManager().getModel(wingsModel);
    }

    /*
    private static void onPlayerJump(InputEvent.InteractionKeyMappingTriggered event) {
        LivingEntity entity = event.getEntity();
        if (entity.getType() == EntityType.PLAYER) {
            var test = entity.getItemBySlot(EquipmentSlot.CHEST);
            Player p_test = Minecraft.getInstance().player;
            if (p_test != null) {
                p_test.createCommandSourceStack().sendChatMessage(new OutgoingChatMessage.Player(PlayerChatMessage.unsigned(p_test.getUUID(), "test")), false, ChatType.bind(ChatType.CHAT, p_test));
            }
        }
    }
    */
}
