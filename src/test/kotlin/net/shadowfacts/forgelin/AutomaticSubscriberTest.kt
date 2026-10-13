package net.shadowfacts.forgelin

import cpw.mods.fml.common.discovery.ASMDataTable.ASMData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertNotNull
import org.junit.Test

class AutomaticSubscriberTest {
    @EventBusSubscriber
    private object AnnotatedSubscriber

    private fun data(className: String, modId: String? = null): ASMData =
        ASMData(null, "", className, className, mutableMapOf<String, Any>().apply {
            if (modId != null) put("modid", modId)
        })

    @Test
    fun annotationIsPresentOnClass() {
        assertNotNull(AnnotatedSubscriber::class.java.getAnnotation(EventBusSubscriber::class.java))
    }

    @Test
    fun explicitOwnerTakesPriority() {
        assertEquals("other", ForgelinAutomaticEventSubscriber.parseModId(
            setOf(data("example.Mod", "example")), data("example.Subscriber", "other")))
    }

    @Test
    fun missingOwnerUsesOnlyContainedMod() {
        assertEquals("example", ForgelinAutomaticEventSubscriber.parseModId(
            setOf(data("example.Mod", "example")), data("example.Subscriber")))
    }

    @Test
    fun emptyOwnerUsesOnlyContainedMod() {
        assertEquals("example", ForgelinAutomaticEventSubscriber.parseModId(
            setOf(data("example.Mod", "example")), data("example.Subscriber", "")))
    }

    @Test
    fun commonClassPrefixDoesNotSelectWrongMod() {
        val mods = linkedSetOf(data("example.Mod", "first"), data("example.ModExtra", "second"))
        assertEquals("second", ForgelinAutomaticEventSubscriber.parseModId(mods, data("example.ModExtra")))
        assertEquals("first", ForgelinAutomaticEventSubscriber.parseModId(mods, data("example.Mod\$Subscriber")))
        assertNull(ForgelinAutomaticEventSubscriber.parseModId(mods, data("example.Subscriber")))
    }
}
