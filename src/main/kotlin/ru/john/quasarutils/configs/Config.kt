package ru.john.quasarutils.configs

import space.arim.dazzleconf.annote.*
import space.arim.dazzleconf.annote.ConfDefault.DefaultString


@ConfHeader("Main configuration file.")
interface Config {
        @ConfComments("Блок, на которых игрок будет ускоряться. КРОМЕ DIRT_PATH")
        @ConfDefault.DefaultStrings(
            "DIRT_PATH"
        )
        @ConfKey("types")
        fun blockTypes(): List<String>


        @ConfComments("Отключить крафты в печках")
        @ConfDefault.DefaultStrings(
            "POTATO",
        )
        @ConfKey("furnaceTypes")
        fun furnaceTypes(): List<String>

        @ConfComments("Отключить дроп с рыбалки")
        @ConfDefault.DefaultStrings(
                "BOW",
                "ENCHANTED_BOOK",
                "FISHING_ROD",
                "NAUTILUS_SHELL",
                "LEATHER",
                "LEATHER_BOOTS",
                "POTION",
                "TRIPWIRE_HOOK",
                "ROTTEN_FLESH"

        )
        @ConfKey("fishTypes")
        fun fishTypes(): List<String>

        @ConfComments("Отключить крафты в верстаке")
        @ConfDefault.DefaultStrings(
                "BREAD",
                "PUMPKIN_PIE",
                "WOODEN_PICKAXE",
                "WOODEN_AXE",
                "WOODEN_SWORD",
                "WOODEN_SHOVEL",
                "WOODEN_HOE",
                "STONE_PICKAXE",
                "STONE_AXE",
                "STONE_SWORD",
                "STONE_SHOVEL",
                "STONE_HOE",
                "IRON_PICKAXE",
                "IRON_AXE",
                "IRON_SWORD",
                "IRON_SHOVEL",
                "IRON_HOE",
                "GOLDEN_PICKAXE",
                "GOLDEN_AXE",
                "GOLDEN_SWORD",
                "GOLDEN_SHOVEL",
                "GOLDEN_HOE",
                "DIAMOND_PICKAXE",
                "DIAMOND_AXE",
                "DIAMOND_SWORD",
                "DIAMOND_SHOVEL",
                "DIAMOND_HOE",
                "NETHERITE_PICKAXE",
                "NETHERITE_AXE",
                "NETHERITE_SWORD",
                "NETHERITE_SHOVEL",
                "NETHERITE_HOE",
                "ENDER_EYE",
                "OAK_BOAT",
                "SPRUCE_BOAT",
                "BIRCH_BOAT",
                "JUNGLE_BOAT",
                "ACACIA_BOAT",
                "DARK_OAK_BOAT",
                "MANGROVE_BOAT",
                "CHERRY_BOAT",
                "BAMBOO_RAFT",
                "OAK_CHEST_BOAT",
                "SPRUCE_CHEST_BOAT",
                "BIRCH_CHEST_BOAT",
                "JUNGLE_CHEST_BOAT",
                "ACACIA_CHEST_BOAT",
                "DARK_OAK_CHEST_BOAT",
                "MANGROVE_CHEST_BOAT",
                "CHERRY_CHEST_BOAT",
                "BAMBOO_CHEST_RAFT",
                "TNT_MINECART",
                "CHEST_MINECART",
                "HOPPER_MINECART",
                "MINECART",
                "RAIL",
                "POWERED_RAIL",
                "DETECTOR_RAIL",
                "ACTIVATOR_RAIL",
                "FISHING_ROD",
                "SHIELD",
                "LEATHER_HELMET",
                "LEATHER_CHESTPLATE",
                "LEATHER_LEGGINGS",
                "LEATHER_BOOTS",
                "IRON_HELMET",
                "IRON_CHESTPLATE",
                "IRON_LEGGINGS",
                "IRON_BOOTS",
                "GOLDEN_HELMET",
                "GOLDEN_CHESTPLATE",
                "GOLDEN_LEGGINGS",
                "GOLDEN_BOOTS",
                "DIAMOND_HELMET",
                "DIAMOND_CHESTPLATE",
                "DIAMOND_LEGGINGS",
                "DIAMOND_BOOTS",
                "NETHERITE_HELMET",
                "NETHERITE_CHESTPLATE",
                "NETHERITE_LEGGINGS",
                "NETHERITE_BOOTS",
                "TURTLE_HELMET",
                "BOW",
                "CROSSBOW",
                "END_CRYSTAL",
                "TNT",
                "RESTONE_BLOCK",
                "REDSTONE_TORCH",
                "REPEATER",
                "COMPARATOR",
                "TARGET",
                "CALIBRATED_SCULK_SENSOR",
                "DAYLIGHT_DETECTOR",
                "PISTON",
                "STICKY_PISTON",
                "DISPENSER",
                "DROPPER",
                "HOPPER",
                "TRAPPED_CHEST",
                "JUKEBOX",
                "OBSERVER",
                "NOTE_BLOCK",
                "REDSTONE_LAMP",
                "ENCHANTING_TABLE",
                "GRINDSTONE",
                "BREWING_STAND",
                "BEACON",
                "LODESTONE",
                "CONDUIT",
                "ENDER_CHEST",
                "FERMENTED_SPIDER_EYE"

        )
        @ConfKey("benchTypes")
        fun benchTypes(): List<String>

        @ConfComments("Длительность эффекта в секундах")
        @ConfDefault.DefaultDouble(0.5)
        @ConfKey("duration")
        fun duration(): Double

        @ConfComments("Блок и станция")
        @ConfDefault.DefaultMap(
                "CRAFTING_TABLE","DM:craft",
                "SMOKER","MI:food",
                "BLAST_FURNACE","MI:ingots",
                "LOOM","DM:loom",
                "SMITHING_TABLE","MI:blacksmith-materials",
                "FLETCHING_TABLE","DM:fletching",
                "BREWING_STAND","MI:potion",
                "GRINDSTONE","DM:grindstone",
                "ANVIL","DM:anvil"
                )
        @ConfKey("stationBlocks")
        fun stationBlocks(): Map<String, String>

        @ConfComments("У каких мобов заменяется дроп")
        @ConfDefault.DefaultStrings(
                "COW",
                "SHEEP",
                "PIG",
                "CHICKEN",
                "HORSE",
                "DONKEY"
        )
        @ConfKey("changedAnimal")
        fun changedAnimal(): List<String>



}