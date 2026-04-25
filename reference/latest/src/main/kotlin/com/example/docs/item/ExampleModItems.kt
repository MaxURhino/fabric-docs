package com.example.docs.item

import net.fabricmc.api.ModInitializer

// :::1
class ExampleModItems : ModInitializer {
  override fun onInitialize() {
    ModItems.initialize()
  }
}
// :::1
