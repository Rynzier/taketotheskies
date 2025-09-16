package com.rynzier.taketotheskies.item.custom;


import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class WingsItem extends Item implements Equipable {
    public float flightTime;
    public float remainingFlightTime;
    public float flightSpeed;
    public float glideStrength;
    public ResourceLocation wingModel;

    public WingsItem(ResourceLocation inModel, float flySpeed, float glidePower, float flightDuration, EquipmentSlot itemSlot, Properties properties) {
        super(properties);
        flightTime = flightDuration;
        remainingFlightTime = flightDuration;
        flightSpeed = flySpeed;
        glideStrength = glidePower;
        wingModel = inModel;
    }


    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.BODY;
    }

    @Override
    public boolean canEquip(ItemStack stack, EquipmentSlot armorType, LivingEntity entity) {
        if (armorType == EquipmentSlot.CHEST) {
            return true;
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (entity instanceof LocalPlayer localPlayer) {
            if (slotId == 38) {
                Vec3 playerDelta = entity.getDeltaMovement();
                if (localPlayer.input.jumping && remainingFlightTime > 0) {
                    entity.moveRelative(flightSpeed, new Vec3(0, 1, 0));
                    remainingFlightTime -= 1.0f;
                } else if (localPlayer.input.jumping && playerDelta.y < -glideStrength) {
                    //entity.moveRelative(0.05f, new Vec3(0, 1, 0));

                    entity.setDeltaMovement(playerDelta.x,-glideStrength,playerDelta.z );
                }

                if (localPlayer.onGround()) {
                    remainingFlightTime = flightTime;
                }
            }
        }

        if (entity instanceof Player player) {
            if (!level.isClientSide()) {
                player.fallDistance = 0.0f;
            }
        }
    }


}
