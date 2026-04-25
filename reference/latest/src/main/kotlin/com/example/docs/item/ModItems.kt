package com.example.docs.item

import java.util.List
import java.util.function.Function

import net.minecraft.core.Registry
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.tags.BlockTags
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.entity.player.Player
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.AxeItem
import net.minecraft.world.item.BucketItem
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.HoeItem
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.SpawnEggItem
import net.minecraft.world.item.ToolMaterial
import net.minecraft.world.item.component.Consumable
import net.minecraft.world.item.component.Consumables
import net.minecraft.world.item.component.ItemLore
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect
import net.minecraft.world.item.equipment.ArmorType
import net.minecraft.world.level.Level

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry
import net.fabricmc.fabric.api.registry.FuelRegistryEvents

import com.example.docs.ExampleMod
import com.example.docs.block.ModBlocks
import com.example.docs.component.ModComponents
import com.example.docs.entity.ModEntityTypes
import com.example.docs.fluid.ModFluids
import com.example.docs.item.armor.GuiditeArmorMaterial
import com.example.docs.item.custom.CounterItem
import com.example.docs.item.custom.LightningStick

// :::1
class ModItems {
  companion object {
    // :::1

    // :::guidite_tool_material
    @JvmField
    val GUIDITE_TOOL_MATERIAL: ToolMaterial = ToolMaterial(
      BlockTags.INCORRECT_FOR_WOODEN_TOOL,
      455,
      5.0F,
      1.5F,
      22,
      GuiditeArmorMaterial.REPAIRS_GUIDITE_ARMOR
    )
    // :::guidite_tool_material

    // :::6
    @JvmField
    val GUIDITE_HELMET: Item = register(
        "guidite_helmet",
        ::Item,
        Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.HELMET)
            .durability(ArmorType.HELMET.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    )
    @JvmField
    val GUIDITE_CHESTPLATE: Item = register(
        "guidite_chestplate",
        ::Item,
        Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.CHESTPLATE)
            .durability(ArmorType.CHESTPLATE.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    )

    @JvmField
    val GUIDITE_LEGGINGS: Item = register(
        "guidite_leggings",
        ::Item,
        Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.LEGGINGS)
            .durability(ArmorType.LEGGINGS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    )

    @JvmField
    val GUIDITE_BOOTS: Item = register(
        "guidite_boots",
        ::Item,
        Item.Properties().humanoidArmor(GuiditeArmorMaterial.INSTANCE, ArmorType.BOOTS)
            .durability(ArmorType.BOOTS.getDurability(GuiditeArmorMaterial.BASE_DURABILITY))
    )
    // :::6
    @JvmField
    val LIGHTNING_STICK: Item = register("lightning_stick", ::LightningStick, Item.Properties())
    // :::7
    @JvmField
    val GUIDITE_SWORD: Item = register(
        "guidite_sword",
        ::Item,
        Item.Properties().sword(GUIDITE_TOOL_MATERIAL, 1f, 1f)
    )
    // :::7
    // :::_13
    @JvmField
    val COUNTER: Item = register(
      "counter",
      ::CounterItem,
      Item.Properties()
          // Initialize the click count component with a default value of 0
          .component(ModComponents.CLICK_COUNT_COMPONENT, 0)
    )
    // :::_13
    // :::9
    @JvmField
    val CUSTOM_CREATIVE_TAB_KEY: ResourceKey<CreativeModeTab> = ResourceKey.create(
        BuiltInRegistries.CREATIVE_MODE_TAB.key(), Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, "creative_tab")
    )
    @JvmField
    val CUSTOM_CREATIVE_TAB: CreativeModeTab = FabricItemGroup.builder()
      .icon { ItemStack(ModItems.GUIDITE_SWORD) }
      .title(Component.translatable("itemGroup.example-mod"))
      .displayItems { params, output -> {
				output.accept(ModItems.SUSPICIOUS_SUBSTANCE)
				output.accept(ModItems.POISONOUS_APPLE)
				// :::9
				output.accept(ModItems.GUIDITE_SWORD)
				output.accept(ModItems.GUIDITE_HELMET)
				output.accept(ModItems.GUIDITE_BOOTS)
				output.accept(ModItems.GUIDITE_LEGGINGS)
				output.accept(ModItems.GUIDITE_CHESTPLATE)
				output.accept(ModItems.LIGHTNING_STICK)
				// :::9

				// The tab builder also accepts Blocks
				output.accept(ModBlocks.CONDENSED_OAK_LOG)
				output.accept(ModBlocks.PRISMARINE_LAMP)
				// :::9
				output.accept(ModBlocks.COUNTER_BLOCK)
				output.accept(ModBlocks.ENGINE_BLOCK)
				output.accept(ModBlocks.RUBY_BLOCK)
				output.accept(ModBlocks.RUBY_STAIRS)
				output.accept(ModBlocks.RUBY_SLAB)
				output.accept(ModBlocks.RUBY_FENCE)
				output.accept(ModBlocks.RUBY_DOOR)
				output.accept(ModBlocks.RUBY_TRAPDOOR)
				output.accept(ModBlocks.VERTICAL_OAK_LOG_SLAB)
				// :::9

				// And custom ItemStacks
				ItemStack stack = ItemStack(Items.SEA_PICKLE)
				stack.set(DataComponents.ITEM_NAME, Component.literal("Pickle Rick"))
				stack.set(DataComponents.LORE, ItemLore(List.of(Component.literal("I'm pickle riiick!!"))))
				output.accept(stack)
			}}
			.build()
    // :::9
    // :::5
    @JvmField
    val POISON_FOOD_CONSUMABLE_COMPONENT: Consumable = Consumables.defaultFood()
      // The duration is in ticks, 20 ticks = 1 second
      .onConsume(ApplyStatusEffectsConsumeEffect(MobEffectInstance(MobEffects.POISON, 6 * 20, 1), 1.0f))
      .build()
    @JvmField
    val POISON_FOOD_COMPONENT: FoodProperties = FoodProperties.Builder()
      .alwaysEdible()
      .build() 
    // :::5

    // :::poisonous_apple
    @JvmField
    val POISONOUS_APPLE: Item = register(
      "poisonous_apple",
      ::Item,
      Item.Properties().food(POISON_FOOD_COMPONENT, POISON_FOOD_CONSUMABLE_COMPONENT)
    )
    // :::poisonous_apple

    // :::2
    @JvmField
    val SUSPICIOUS_SUBSTANCE: Item = register("suspicious_substance", ::Item, Item.Properties())
    // :::2

    // :::custom_entity_spawn_egg
    @JvmField
    val MINI_GOLEM_SPAWN_EGG: Item = register(
      "mini_golem_spawn_egg",
      ::SpawnEggItem,
      Item.Properties().spawnEgg(ModEntityTypes.MINI_GOLEM)
    )
    // :::custom_entity_spawn_egg

    @JvmField
    val RUBY: Item = register("ruby", ::Item, Item.Properties())

    @JvmField
  	val GUIDITE_AXE: Item = register("guidite_axe", settings -> AxeItem(GUIDITE_TOOL_MATERIAL, 5.0F, -3.0F, settings), Item.Properties())

    @JvmField
  	val LEATHER_GLOVES: Item = register("leather_gloves", ::Item, Item.Properties())

    @JvmField
  	val FLASHLIGHT: Item = register("flashlight", {settings -> object : Item(settings) {
  		override fun use(level: Level, user: Player, hand: InteractionHand): InteractionResult {
  			user.startUsingItem(hand)
  			return InteractionResult.CONSUME
  		}
  	}}, Item.Properties())

    @JvmField
  	val BALLOON: Item = register("balloon", ::Item, Item.Properties())

    @JvmField
  	val ENHANCED_HOE: Item = register("enhanced_hoe", settings -> HoeItem(GUIDITE_TOOL_MATERIAL, -4.0F, 0.0F, settings), Item.Properties())

    @JvmField
  	val DIMENSIONAL_CRYSTAL: Item = register("dimensional_crystal", ::Item, new Item.Properties())

    @JvmField
  	val THROWING_KNIVES: Item = register("throwing_knives", ::Item, Item.Properties().stacksTo(3))

    // :::1
    @JvmStatic
    fun <T: Item> register(name: String, itemFactory: Function<Item.Properties, T>, settings: Item.Properties): T {
      // Create the item key.
      val itemKey: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExampleMod.MOD_ID, name))

      // Create the item instance.
      val item: T = itemFactory.apply(settings.setId(itemKey))

      // Register the item.
      Registry.register(BuiltInRegistries.ITEM, itemKey, item)

      return item
    }
    
    // :::1

    // :::3
    @JvmStatic
    fun initialize() {
      // :::3
      // :::4
      // Get the event for modifying entries in the ingredients group.
  		// And register an event handler that adds our suspicious item to the ingredients group.
  		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS)
  				.register { itemGroup -> itemGroup.accept(ModItems.SUSPICIOUS_SUBSTANCE) }
  		// :::4

      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
          .register { itemGroup -> {
            itemGroup.accept(ModItems.GUIDITE_HELMET)
  					itemGroup.accept(ModItems.GUIDITE_BOOTS)
  					itemGroup.accept(ModItems.GUIDITE_LEGGINGS)
  					itemGroup.accept(ModItems.GUIDITE_CHESTPLATE)
          } }

      // :::8
      ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
				.register { itemGroup -> itemGroup.accept(ModItems.GUIDITE_SWORD) }
      // :::8

      // :::_12
  		// Register the group.
  		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, CUSTOM_CREATIVE_TAB_KEY, CUSTOM_CREATIVE_TAB)
  		// :::_12

      // :::spawn_egg_creative_tab
  		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register { itemGroup -> {
  			itemGroup.accept(ModItems.MINI_GOLEM_SPAWN_EGG)
  		}}
  		// :::spawn_egg_creative_tab
  		ItemGroupEvents.modifyEntriesEvent(CUSTOM_CREATIVE_TAB_KEY).register { itemGroup -> {
  			itemGroup.accept(ModItems.RUBY)
  			itemGroup.accept(ModItems.GUIDITE_AXE)
  			itemGroup.accept(ModItems.LEATHER_GLOVES)
  			itemGroup.accept(ModItems.FLASHLIGHT)
  			itemGroup.accept(ModItems.BALLOON)
  			itemGroup.accept(ModItems.ENHANCED_HOE)
  			itemGroup.accept(ModItems.DIMENSIONAL_CRYSTAL)
  			itemGroup.accept(ModItems.THROWING_KNIVES)
  		} }
  
  		// :::_10
  		// Add the suspicious substance to the composting registry with a 30% chance of increasing the composter's level.
  		CompostingChanceRegistry.INSTANCE.add(ModItems.SUSPICIOUS_SUBSTANCE, 0.3f)
  		// :::_10
  
  		// :::_11
  		// Add the suspicious substance to the registry of fuels, with a burn time of 30 seconds.
  		// Remember, Minecraft deals with logical based-time using ticks.
  		// 20 ticks = 1 second.
  		FuelRegistryEvents.BUILD.register { builder, context -> {
  			builder.add(ModItems.SUSPICIOUS_SUBSTANCE, 30 * 20);
  		}}
  		// :::_11
  		// :::3
    }
    // :::3

    // :::acid_bucket
    val ACID_BUCKET: Item = register(
  			"acid_bucket",
  			props -> BucketItem(ModFluids.ACID_STILL, props),
  			Item.Properties()
  					.craftRemainder(Items.BUCKET)
  					.stacksTo(1)
  	);
    // :::acid_bucket

    // :::1
  }
}
// :::1
