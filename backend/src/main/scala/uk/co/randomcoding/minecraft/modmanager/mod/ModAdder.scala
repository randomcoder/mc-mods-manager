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

import java.io.File
import java.nio.file.{Files, Paths}

import uk.co.randomcoding.minecraft.modmanager.mod.DigestCalculator._

/**
 * Adds a new Mod to the Mod Managers mod library
 */
class ModAdder(modLibraryPath: String) {

  private[this] var metadataLibrary = Map.empty[String, ModMetadata]

  /**
   * Add a Mod to the mods library
   *
   * This will copy the mod file into the applications mods library and return the [[ModMetadata]]
   * for the saved file
   *
   * @param modFile The mod file to add
   * @param modName The name to give to the mod for easy identification
   * @param minecraftVersion The version of MineCraft supported by the mod
   * @return The Metadata
   */
  def addMod(modFile: File, modName: String, minecraftVersion: String): ModMetadata = {
    val originalDigest = digest(modFile)
    metadataLibrary.get(originalDigest) match {
      case Some(metadata) => {
        if (metadata.minecraftVersions.contains(minecraftVersion)) metadata
        else updateVersions(metadata, minecraftVersion)
      }
      case _ => {
        val savedFile = copyModFile(modName, modFile)
        val metadata = ModMetadata(modName, Seq(minecraftVersion), savedFile.getAbsolutePath , digest(savedFile))
        metadataLibrary = metadataLibrary + (metadata.md5Sum -> metadata)

        metadata
      }
    }
  }

  private[this] def updateVersions(metadata: ModMetadata, newVersion: String) = {
    val newVersions = newVersion +: metadata.minecraftVersions
    val newMetadata = metadata.copy(minecraftVersions = newVersions)
    metadataLibrary = metadataLibrary.updated(metadata.md5Sum, newMetadata)

    newMetadata
  }

  private[this] def copyModFile(modName: String, modFile: File): File = {
    val targetPath = savedFileName(modName, modFile, modLibraryPath)
    val path = Files.copy(Paths.get(modFile.getAbsolutePath), Paths.get(targetPath))

    path.toFile
  }

  def savedFileName(modName: String, sourceFile: File, modsLibraryDir: String): String = {
    val originalFileName = sourceFile.getName
    val dotIndex = originalFileName.lastIndexOf('.')
    val(fileName, extension) = originalFileName.splitAt(dotIndex)

    s"$modsLibraryDir/$fileName-$modName$extension"
  }
}
