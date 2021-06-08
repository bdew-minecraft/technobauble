package net.bdew.technobauble;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.energy.IEnergyStorage;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class Caps {
    @CapabilityInject(ICurio.class)
    public static Capability<ICurio> CURIO;

    @CapabilityInject(IEnergyStorage.class)
    public static Capability<IEnergyStorage> ENERGY;
}
