---
title: Creating a fluid
description: Learn how to create your own custom fluid.
authors:
  - Maksiuhrino
---

Here we'll cover creation of a custom fluid. If you plan to create several fluids, it is recommended to make an abstract basic fluid class where you'll set necessary defaults that will be shared in its subclasses. We'll also make it generate in the world like lakes.

## Making an abstract fluid {#making-an-abstract-fluid}

Vanilla fluids extend `net.minecraft.fluid.FlowableFluid`, and so shall we.

@[code transcludeWith=:::1](@/reference/latest/src/main/java/com/example/docs/fluid/custom/TutorialFluid.java)
