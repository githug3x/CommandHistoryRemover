package com.commandhistoryremover.mixin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import net.minecraft.client.util.CommandHistoryManager;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CommandHistoryManager.class)
public class CommandHistoryManagerMixin {
    /**
     * Deletes the existing command_history.txt from the game directory before
     * the manager tries to load it.
     */
    @Inject(method = "<init>", at = @At("HEAD"))
    private static void removeExistingHistory(Path directoryPath, CallbackInfo ci) {
        try {
            Files.deleteIfExists(directoryPath.resolve("command_history.txt"));
        } catch (IOException ignored) {
            // Nothing useful can be done if the file cannot be removed.
        }
    }

    /**
     * Cancels every attempt to append a command, so the history file is never
     * written again during this session.
     */
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void preventRecording(String command, CallbackInfo ci) {
        ci.cancel();
    }
}
