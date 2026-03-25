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

You must first understand [how to create an block](../blocks/first-block) and [how to create an item](../items/first-item).

:::

This example will cover the creation of an Acid fluid that damages entities that stand inside of it.

## Creating the Fluid Class {#creating-the-fluid-class}

The baseline class for fluids is the `FlowingFluid` class. We'll start by creating an abstract class, in this case `AcidFluid`, that extends `FlowingFluid`, overriding all the methods that will be shared by the source and the flowing fluid:

@[code transcludeWith=:::abstractFluid](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Inside `AcidFluid`, we'll create two subclasses for the `Source` and `Flowing` fluids.

@[code transcludeWith=:::fluidSubclasses](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

### Registering Fluids {#registering-fluids}

Next, you would need to create a class, called `ModFluids`, that would register all the fluid instances.

@[code transcludeWith=:::register](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Just like with blocks, you need to ensure that the class is loaded so that all static fields containing your fluid instances are initialized.

You can do this by creating a dummy `initialize` method, which can be called in your [mod's initializer](../getting-started/project-structure#entrypoints) to trigger the static initialization.

Now, go back to the `AcidFluid` class, and add these methods to associate the registered fluid instances with this fluid:

@[code transcludeWith=:::sources](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

The fluid now has registered the source and the flowing fluid. We just need a bucket to complete the acid fluid, and a legacy block for it.

### Registering Fluid Blocks {#fluid-blocks}

Now, let's add a legacy block for our fluid. These are used for some behaviour if your fluid needs to exist in the world - one example would be the `setblock` command. Switch to your `ModBlocks` class and create the following block. If you do not yet have a `ModBlocks` class, follow the docs for [Creating Your First Block](../blocks/first-block).

@[code transcludeWith=:::acid](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

### Registering Buckets {#buckets}

Now, let's add the bucket for acid. Switch to your `ModItems` class and create the following item. If you do not yet have a `ModItems` class, follow the docs for [Creating Your First Item](../items/first-item).

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Then, override this method in `AcidFluid` to associate your bucket with the fluid:

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Don't forget that items require a translation, [texture](../first-item#adding-a-texture), [model](../first-item#adding-a-model), and [client item](<(../first-item#creating-the-client-item)>) with the name `acid_bucket` in order to render correctly. An example texture is provided below.

<DownloadEntry visualURL="/assets/develop/fluids/acid_bucket.png" downloadURL="/assets/develop/fluids/acid_bucket_small.png">Texture</DownloadEntry>

It's also recommended to add your mod's bucket to the `ConventionalItemTags.BUCKET` item tag so that other mods can handle it appropriately, either manually or through [data generation](../data-generation/tags).

### Transparency and Textures {#transparency-and-textures}

To add the texture for your fluid, you can use Fabric API's `FluidRenderHandlerRegistry`. Add the following lines to your `ClientModInitializer` to create a `SimpleFluidRenderHandler` that takes in two `Identifier`s for the still and flowing textures and an integer to tint it with.

For simplicity, this demo uses the water texture `Identifier`s provided by Fabric API, but you can replace those with an `Identifier` that points to your own texture in the format `minecraft:block/water_still`.

We'll also use the `BlockRenderLayerMap` to set the `ChunkSectionLayer` to transparent, so you can see through the fluid. For more information, see the docs on [Transparency and Tinting](../blocks/transparency-and-tinting).

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

At this point, we have all we need to see the Acid ingame! You can use `setblock` or the Acid Bucket item to place acid in the world.

![A screenshot of a green acid fluid in the world](/assets/develop/fluids/acid.png)

In the next chapter, we will add a fog effect when submerged in the fluid.

## Adding Fog {#adding-fog}

Fluids like water and lava change the fog when a player is submerged in them. This section of the tutorial will cover how to implement that for your fluid.

As there is not an API for registering fog environments, we'll need to use a mixin to add our mod's fog type to the `FogType` enum. Add the following mixin to your main mixin package and to the `example-mod.mixins.json` mixin config.

@[code transcludeWith=:::renderer](@/reference/latest/src/main/java/com/example/docs/mixin/FogTypeInvoker.java)

Now, we're able to create a class that will store our fog types.

@[code transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/fog/ExampleModFogTypes.java)

### Fog Environments {#fog-environments}

Let's start with creating a new fog environment. Fog environments are used to set where the fog should begin, where it should end, and what colour the fog should be when fully submerged in our fluid. Create an `AcidFogEnvironment` class in your client package with the following methods:

@[code transcludeWith=:::environment](@/reference/latest/src/client/java/com/example/docs/fog/client/AcidFogEnvironment.java)

As there is not an API for registering fog environments, we'll need to use mixins to the two places where Minecraft's client is checking for fog types.

First, we'll add our mod's fog environment to the `FogRenderer`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json` mixin config.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/FogRendererMixin.java)

First, we'll add our mod's fog environment to the `Camera`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json` mixin config.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/CameraMixin.java)

Be sure that all three mixins are added to their mixin configs, otherwise they will not be loaded.

Now, when we submerge ourselves in acid, the fog will change appropriately.

![A screenshot of a thick acidic green fog](/assets/develop/fluids/fog.png)

## Tagging Your Fluids {#tagging}

::: info

Users of [data generation](../data-generation/tags) may wish to register tags via `FabricTagProvider.FluidTagProvider`, rather than writing them by hand.

:::

As a fluid is considered two seperate blocks in its flowing and still states, tags are often used to check for fluids. We'll create a fluid tag in `data/example-mod/tags/fluid/acid.json` with the following contents:

@[code transclude](@/reference/latest/src/main/generated/data/example-mod/tags/fluid/acid.json)

Minecraft also uses fluid tags for behaviour. If you need your mod's fluid to behave like water (absorbed by sponges, swimmable, etc.), considering adding it to the `minecraft:water` fluid tag, and if you need it to behave like lava (swimmable by Striders/Ghasts, slows entities, etc.), consider adding it to the `minecraft:lava` tag. Note that if you only need a few things, you may wish to use mixins to change that behaviour yourself.

<!-- Thank you AlbanischeWurst for the code. ❤️ -->
