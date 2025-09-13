package com.rynzier.taketotheskies.item.custom;


import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class WingsItem extends Item implements Equipable {


    public WingsItem(EquipmentSlot itemSlot, Properties properties) {

        super(properties);
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
        if (entity instanceof Player player) {
            if (slotId == 38) {
                entity.moveRelative(0.1f, new Vec3(0, 1, 0));
            }
        }
    }
}
