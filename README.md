# CommandHistoryRemover

A client-side Fabric mod that stops Minecraft from saving your commands to `command_history.txt`.

## Features

- Deletes an existing `command_history.txt` from the game directory automatically on startup.
- Prevents every command sent in game from being written to `command_history.txt`, including commands typed in chat.
- Works entirely client-side and requires no Fabric API.

Commands you entered during the current session can still be recalled with the up arrow key, but they are never persisted to disk.

## Supported versions

- Minecraft **1.21 – 1.21.11**
- Fabric Loader **0.15.11+**
- Java **21+**

The mod is compiled against Minecraft 1.21.1. The mixin targets `CommandHistoryManager` (`class_8657` / `method_52697`), whose signature is identical across all supported versions, so a single jar works for the whole version range.

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/installer/) for your Minecraft version.
2. Drop `CommandHistoryRemover-1.0.0.jar` into your `mods` folder.
3. Launch the game with the Fabric profile.

No configuration is required.

## How it works

`net.minecraft.client.util.CommandHistoryManager` is responsible for reading and writing `command_history.txt`.

- At the head of its constructor, the file in the game directory is deleted before any previous history is loaded.
- At the head of its `add(String)` method, the call is cancelled, so the history file is never written again.

## Building from source

```bash
./gradlew build
```

The built jar is placed in `build/libs/`.

## License

This project is provided as-is for personal use.
