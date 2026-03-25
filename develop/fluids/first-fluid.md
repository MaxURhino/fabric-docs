---
title: Creating Your First Fluid
description: Learn how to create your first custom fluid in Minecraft.
authors:
  - AlexiyOrlov
  - cassiancc
  - CelDaemon
  - Clomclem
  - comp500
  - Daomephsta
  - Earthcomputer
  - florensie
  - Fusion-Flux
  - InfinityChances
  - MaxURhino
  - SolidBlock-cn
  - SuperSoupr
  - Virtuoel
  - UpcraftLP
  - AlbanischeWurst
authors-nogithub:
  - alfiejfs
  - salvopelux
---

<!---->

::: info PREREQUISITES

You must first understand how to [create a block](../blocks/first-block) and how to [create an item](../items/first-item).

:::

This example will cover the creation of an acid fluid that hurts and weakens entities that stand inside of it.

## Creating the Fluid Class {#creating-the-fluid-class}

We'll start by creating an abstract class, in this case called `AcidFluid`, that extends the baseline `FlowingFluid` class. Then, we'll override any the methods that should be the same for both the source and the flowing fluid:

@[code transcludeWith=:::abstractFluid](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Inside `AcidFluid`, we'll create two subclasses for the `Source` and `Flowing` fluids.

@[code transcludeWith=:::fluidSubclasses](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Registering Fluids {#registering-fluids}

Next, we'll create a class to register all the fluid instances. We'll call it `ModFluids`.

@[code transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Just like with blocks, you need to ensure that the class is loaded so that all static fields containing your fluid instances are initialized. You can do this by creating a dummy `initialize` method, which can be called in your [mod's initializer](../getting-started/project-structure#entrypoints) to trigger the static initialization.

Now, go back to the `AcidFluid` class, and add these methods to associate the registered fluid instances with this fluid:

@[code transcludeWith=:::sources](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

So far, we've registered the fluid's source state and its flowing state. Next, we'll need to register a bucket and a `LiquidBlock` for it.

### Registering Fluid Blocks {#fluid-blocks}

Let's now add a liquid block for our fluid. This is needed by some commands like `setblock`, so your fluid can exist in the world. If you haven't done so yet, you should take a look at [how to create your first block](../blocks/first-block).

Open your `ModBlocks` class and register this following `LiquidBlock`:

@[code transcludeWith=:::acid](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

### Registering Buckets {#buckets}

Now, let's add a Bucket of Acid. Switch to your `ModItems` class and register the following `BucketItem`. If you do not yet have a `ModItems` class, follow the docs for [Creating Your First Item](../items/first-item).

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Then, override this method in `AcidFluid` to associate your bucket with the fluid:

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Don't forget that items require a translation, [texture](../items/first-item#adding-a-texture), [model](../items/first-item#adding-a-model), and [client item](../items/first-item#creating-the-client-item) with the name `acid_bucket` in order to render correctly. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Texture</DownloadEntry>

It's also recommended to add your mod's bucket to the `ConventionalItemTags.BUCKET` item tag so that other mods can handle it appropriately, either [manually](#tagging) or through [data generation](../data-generation/tags).

### Transparency and Textures {#transparency-and-textures}

To texture your fluid, you should use Fabric API's `FluidRenderHandlerRegistry`. We'll also use the `BlockRenderLayerMap` to set the `ChunkSectionLayer` to transparent, so you can see through the fluid. For more information, see the docs on [Transparency and Tinting](../blocks/transparency-and-tinting).

::: tip

For simplicity, this demo uses the water texture `Identifier`s provided by Fabric API, but you can replace those with an `Identifier` that points to your own texture in the format `minecraft:block/water_still`.

:::

Add the following lines to your `ClientModInitializer` to create a `SimpleFluidRenderHandler`, that takes in two `Identifier`s for the textures—one for the still source and one for the flowing fluid—and an integer for the color to tint it with.

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

At this point, we have all we need to see the Acid in-game! You can use `setblock` or the Acid Bucket item to place acid in the world.

![A screenshot of a green acid fluid in the world](/assets/develop/fluids/acid.png)

## Adding Fog {#adding-fog}

Fluids like water and lava change the fog when a player is submerged in them. This section of the tutorial will cover implementing this behavior for your fluid.

As there is not an API for registering fog environments, we'll need to use a mixin to add our mod's fog type to the `FogType` enum. Add the following mixin to your main mixin package and to the `example-mod.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/main/java/com/example/docs/mixin/FogTypeInvoker.java)

Now, we're able to create a class that will store our fog types.

@[code transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/fog/ExampleModFogTypes.java)

### Fog Environments {#fog-environments}

Let's start with creating a new fog environment. Fog environments are used to set where the fog should begin, where it should end, and what color the fog should be when fully submerged in our fluid. Create an `AcidFogEnvironment` class in your client package with the following methods:

@[code transcludeWith=:::environment](@/reference/latest/src/client/java/com/example/docs/fog/client/AcidFogEnvironment.java)

As there is not an API for registering fog environments, we'll need to use mixins into the two places where Minecraft's client is checking for fog types.

First, we'll add our mod's fog environment to the `FogRenderer`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/FogRendererMixin.java)

Next, we'll add our mod's fog environment to the `Camera`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/CameraMixin.java)

Be sure that all three mixins are added to their mixin configs, otherwise they will not be loaded.

Now, when we submerge ourselves in acid, the fog will change appropriately.

![A screenshot of a thick acidic green fog](/assets/develop/fluids/fog.png)

## Tagging Your Fluids {#tagging}

::: info

Users of [data generation](../data-generation/tags) may wish to register tags via `FabricTagProvider.FluidTagProvider`, rather than writing them by hand.

:::

Because a fluid is considered two separate blocks in its flowing and still states, a tag is often used to check for both states together. We'll create a fluid tag in `data/example-mod/tags/fluid/acid.json`:

@[code transclude](@/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json)

::: tip

Minecraft also provider other tags to control the behavior of fluids:

- If you need your mod's fluid to behave like water (absorbed by sponges, swimmable...), considering adding it to the `minecraft:water` fluid tag
- If you need it to behave like lava (swimmable by Striders/Ghasts, slows entities...), consider adding it to the `minecraft:lava` fluid tag

If you only need *some* of those things, you may wish to use mixins to control the behavior finely.

:::

<!-- Thank you AlbanischeWurst for the code. ❤️ -->
