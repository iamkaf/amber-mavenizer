/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.mcmaven.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import net.minecraftforge.mcmaven.impl.util.Util;

public class StaleOutputTests {
    @TempDir
    Path tempDir;

    @Test
    public void regeneratingAnOutputLeavesLinkedCopiesAlone() throws IOException {
        var output = tempDir.resolve("recompiled.jar");
        var linked = tempDir.resolve("forge.jar");
        var input = tempDir.resolve("input.txt");
        Files.writeString(output, "old");
        Files.createLink(linked, output);
        Files.writeString(input, "changed input");

        assertFalse(Mavenizer.checkCache(output.toFile(), Util.cache(output.toFile()).add("input", input.toFile())));
        try (var out = new FileOutputStream(output.toFile())) {
            out.write("new".getBytes());
        }

        assertEquals("old", Files.readString(linked));
        assertEquals("new", Files.readString(output));
    }
}
