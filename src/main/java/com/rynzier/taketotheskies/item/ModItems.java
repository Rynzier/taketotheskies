package com.rynzier.taketotheskies.item;

import com.rynzier.taketotheskies.TakeToTheSkies;
import com.rynzier.taketotheskies.item.custom.WingsItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TakeToTheSkies.MODID);

    // Register items here
    public static final DeferredItem<WingsItem> TEST_WINGS = ITEMS.register("test_wings",
            () -> new WingsItem(EquipmentSlot.BODY, new WingsItem.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
