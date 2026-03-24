---
title: Creating Your First Fluid
description: Learn, how to create your own, custom fluid.
authors:
  - AlexiyOrlov
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
authors-nogithub:
  - alfiejfs
  - salvopelux
---

Here we'll cover creation of a custom fluid. If you plan to create several fluids, it is recommended to make an abstract basic fluid class where you'll set necessary defaults that will be shared in its subclasses. We'll also make it generate in the world like lakes.

## Creating the Fluid Class {#creating-the-fluid-class}

Vanilla fluids extend `net.minecraft.fluid.FlowableFluid`, and so shall we.
For now, we will create an abstract class with all the methods for the source and the flowing fluid:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Then you would need to add the source and the flowing fluid classes:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Next you would need to create a class, called `ModFluids`, that would register all the fluids:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Now, go back to the `AcidFluid` class, and add these methods:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

The fluid now has registered the source and the flowing fluid. We just need a bucket to complete the acid fluid, and a legacy block for it. Let's start with the legacy block. Go to the `ModBlocks` class. If you don't have one, follow [this](../blocks/first-block) tutorial.

@[code transcludeWith=:::7](@/reference/latest/src/main/java/com/example/docs/block/ModBlocks.java)

Now, let's add the bucket for acid. If you haven't created the class, called `ModItems`, follow [this](../items/first-item) tutorial.

@[code transcludeWith=:::acid_bucket](@/reference/latest/src/main/java/com/example/docs/item/ModItems.java)

Then, add this to the `AcidFluid` class:

@[code transcludeWith=:::bucket](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

To add the texture for your fluid, add this line to your `ClientModInitializer`:

@[code transcludeWith=:::fluid_texture](@/reference/latest/src/client/java/com/example/docs/appearance/ExampleModAppearanceClient.java)

In the next chapter, we will add effects for the fluid

## Adding Fluid Effects {#adding-fluid-effects}

Let's start with creating a new fog environment:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/fluid/fog/ModFogEnvironment.java)

Then you will need to apply it with a mixin:

@[code transcludeWith=:::1](@/reference/latest/src/client/java/com/example/docs/mixin/client/FogRendererMixin.java)

Of course, remember to add the mixin to the `example-mod.client.mixins.json`.

Now add the acid fluid to the water fluid tag, located in `data/minecraft/tags/fluid/water.json`:

```json
{
  "replace": false,
  "values": [
    "example-mod:acid",
    "example-mod:acid_flow"
  ]
}
```
