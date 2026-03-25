---
title: Adding Fog to Your Fluid
description: Learn how to use mixins to add fog to your first custom fluid in Minecraft.
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

You must have first followed [Creating Your First Fluid](./first-fluid).

:::

Fluids like water and lava change the fog when a player is submerged in them. This section of the tutorial will cover implementing this behavior for your fluid.

## Fog Types {#fog-types}

As there is not an API for registering fog types, we'll need to use a mixin to add our mod's fog type to the `FogType` enum. Add the following mixin to your main mixin package and to the `example-mod.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/main/java/com/example/docs/mixin/FogTypeInvoker.java)

Now, we're able to create a class that will store our fog types.

@[code transcludeWith=:::type](@/reference/latest/src/main/java/com/example/docs/fog/ExampleModFogTypes.java)

## Fog Environments {#fog-environments}

Next, we can create a new fog environment. Fog environments are used to set where the fog should begin, where it should end, and what color the fog should be when fully submerged in our fluid. Create an `AcidFogEnvironment` class in your client package with the following methods:

@[code transcludeWith=:::environment](@/reference/latest/src/client/java/com/example/docs/fog/client/AcidFogEnvironment.java)

As there is not an API for registering fog environments, we'll need to use mixins into the two places where Minecraft's client is checking for fog types.

First, we'll add our mod's fog environment to the `FogRenderer`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/FogRendererMixin.java)

Next, we'll add our mod's fog environment to the `Camera`. Add the following mixin to your client mixin package and to the `example-mod.client.mixins.json`.

@[code transcludeWith=:::renderer](@/reference/latest/src/client/java/com/example/docs/mixin/client/CameraMixin.java)

Be sure that all three mixins are added to their mixin configs, otherwise they will not be loaded.

Now, when we submerge ourselves in acid, the fog will change appropriately.

![A screenshot of a thick acidic green fog](/assets/develop/fluids/fog.png)

<!-- Thank you AlbanischeWurst for the code. ❤️ -->
