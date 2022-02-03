package net.bdew.technobauble;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandler;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class Caps {
    public static Capability<ICurio> CURIO = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static Capability<IEnergyStorage> ENERGY = CapabilityManager.get(new CapabilityToken<>() {
    });
    public static Capability<IItemHandler> ITEM_HANDLER = CapabilityManager.get(new CapabilityToken<>() {
    });
}
