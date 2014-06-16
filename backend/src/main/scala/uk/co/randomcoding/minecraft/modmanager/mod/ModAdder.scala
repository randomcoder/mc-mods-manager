/*
 * (c) QinetiQ Limited, 2014
 *
 * Copyright in this library belongs to:
 *
 * QinetiQ Limited,
 * St. Andrews Road,
 * Malvern,
 * Worcestershire.
 * WR14 3RJ
 * UK
 *
 * This software may not be used, sold, licensed, transferred, copied
 * or reproduced in whole or in part in any manner or form or in or
 * on any media by any person other than has been explicitly granted in the
 * relevant licence terms.
 *
 * The licence allows "Access Rights needed for the execution of the Project"
 * and specifically excludes "Access Rights for Use". You may not assign or
 * transfer this licence. You may not sublicense the Software.
 *
 * This software is distributed WITHOUT ANY WARRANTY, without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 */
package uk.co.randomcoding.minecraft.modmanager.mod

import java.io.{File, FileInputStream}
import java.nio.file.{Files, Paths}

/**
 * Adds a new Mod to the Mod Managers mod library
 */
class ModAdder(modLibraryPath: String) {
  /**
   * Add a Mod to the mods library
   *
   * This will copy the mod file into the applications mods library
   *
   * @param modFile The mod file to add
   * @param modName The name to give to the mod for easy identification
   * @param minecraftVersion The version of MineCraft supported by the mod
   * @return The Metadata
   */
  def addMod(modFile: File, modName: String, minecraftVersion: String): ModMetadata = ModMetadata(modName, minecraftVersion, s"modslibrary/${modFile.getName}", genMd5(modFile))

  def save(modFile: File): File = copyModFile(modFile)

  private[this] def copyModFile(original: File): File = {
    val targetPath = s"$modLibraryPath/${original.getName}"
    val path = Files.copy(Paths.get(original.getAbsolutePath), Paths.get(targetPath))

    path.toFile
  }

  private[this] def genMd5(modFile: File): String = {
    DigestCalculator.digest(new FileInputStream(modFile))
  }

}
