# Experimental Menu

Client-side Fabric mod for Minecraft Java Edition 1.21.4. Designed first for testing in a single-player world.

## Features

- Menu: press Right Shift to open; press Escape or select Fechar to close.
- ESP Box: renders colored wireframe boxes around nearby entities up to 64 blocks away.
- Aim Bot Arc: shows a practice reticle and a bow-training status label. It does not move the camera or automatically aim at entities.
- Pulo BLZ: experimental toggle that applies Speed IV while the player is airborne, when no Speed effect is already active.

## Requirements

- Minecraft Java Edition 1.21.4
- Java 21
- Fabric Loader 0.16.10 or newer
- Fabric API for 1.21.4

## Build

1. Install a Java 21 JDK.
2. Download or clone this repository.
3. Run ./gradlew build on Linux/macOS or gradlew.bat build on Windows.
4. The mod JAR will be in build/libs/.

This repository does not include a prebuilt JAR. To install the built mod, put the JAR in the Minecraft mods folder alongside Fabric API.

## Notes

This is an experimental client-side prototype. Test in a single-player world first. The bow feature is a visual practice indicator, not an automatic aim system. The jump effect can interact with other Speed effects; when Pulo BLZ is enabled, it avoids replacing an already active Speed effect.
