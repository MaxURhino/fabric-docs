---
title: Creating a fluid
description: Learn, how to create your own, custom fluid.
authors:
  - Maksiuhrino
---

Here we'll cover creation of a custom fluid. If you plan to create several fluids, it is recommended to make an abstract basic fluid class where you'll set necessary defaults that will be shared in its subclasses. We'll also make it generate in the world like lakes.

## Creating the fluid class {#creating-the-fluid-class}

Vanilla fluids extend `net.minecraft.fluid.FlowableFluid`, and so shall we.
For now, we will create an abstract class with all the methods for the source and the flowing fluid:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Then you would need to add the source and the flowing fluid classes:

@[code transcludeWith=:::2](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

Next you would need to create a class, called `ModFluids`, that would register all the fluids:

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/fluid/ModFluids.java)

Now, go back to the `AcidFluid` class, and add these methods:

@[code transcludeWith=:::3](@/reference/latest/src/main/java/com/example/docs/fluid/custom/AcidFluid.java)

The fluid now has registered the source and the flowing fluid. We just need a bucket to complete the acid fluid.
