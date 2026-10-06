/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.mcmaven.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class LinkOrCopyTests {
    @TempDir
    Path tempDir;

    @Test
    public void replacesExistingOutputWithLinkToCache() throws IOException {
        var source = tempDir.resolve("cache.jar");
        var target = tempDir.resolve("output.jar");
        Files.writeString(source, "new");
        Files.writeString(target, "old");

        MinecraftMaven.linkOrCopy(source.toFile(), target.toFile());

        assertEquals("new", Files.readString(target));
        assertTrue(Files.isSameFile(source, target));
    }
}
