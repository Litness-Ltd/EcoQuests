package com.willfp.ecoquests.gui.components

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.eco.core.gui.slot
import com.willfp.eco.core.items.Items
import com.willfp.eco.core.items.builder.modify
import com.willfp.eco.util.formatEco
import org.bukkit.Material

class QuestInfoComponent(
    config: Config
) : PositionedComponent {
    private val baseItem = Items.lookup(config.getString("item"))

    private val slot = slot { player, _ ->
        var item = baseItem.item.clone().modify {
            setDisplayName(config.getString("name").formatEco(player, formatPlaceholders = true))
            addLoreLines(config.getStrings("lore").formatEco(player, formatPlaceholders = true))
        }

        if (item.type == Material.PLAYER_HEAD) {
            val skullMeta = item.itemMeta as? org.bukkit.inventory.meta.SkullMeta
            skullMeta?.playerProfile = player.playerProfile
            item.itemMeta = skullMeta
        }

        return@slot item
    }

    override val row: Int = config.getInt("location.row")
    override val column: Int = config.getInt("location.column")

    override fun getSlotAt(row: Int, column: Int) = slot
}
