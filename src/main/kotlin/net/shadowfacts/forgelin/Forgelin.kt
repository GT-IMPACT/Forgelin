package net.shadowfacts.forgelin

import cpw.mods.fml.common.FMLCommonHandler
import cpw.mods.fml.common.Loader
import cpw.mods.fml.common.Mod
import cpw.mods.fml.common.event.FMLPreInitializationEvent
import net.shadowfacts.forgelin.coroutines.ClientTaskQueue
import net.shadowfacts.forgelin.coroutines.ServerTaskQueue

/**
 * @author shadowfacts
 */
// kotlin doesn't work with the current variable substitute system, so...
@Mod(
    modid = MODID,
    name = MODNAME,
    version = VERSION,
    acceptableRemoteVersions = "*",
    acceptedMinecraftVersions = "*",
    modLanguageAdapter = MODADAPTER
)
object Forgelin {

    @Mod.EventHandler
    fun onPreInit(event: FMLPreInitializationEvent) {
        Loader.instance().modList.forEach {
            ForgelinAutomaticEventSubscriber.subscribeAutomatic(it, event.asmData, FMLCommonHandler.instance().side)
        }

        FMLCommonHandler.instance().bus().register(ServerTaskQueue)
        FMLCommonHandler.instance().bus().register(ClientTaskQueue)
    }
}
