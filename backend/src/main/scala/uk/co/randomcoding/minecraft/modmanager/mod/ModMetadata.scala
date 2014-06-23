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

/**
 * Metadata about a mod stored within the mod manager
 *
 * @param modName The name of the mod as given by the user
 * @param minecraftVersions The versions of minecraft the mod supports.
 *                         If there are multiple supported versions then there will be multiple items of metadata
 * @param modFileLocation The (relative) location of the mod file within the app's file system space
 * @param md5Sum The '''md5sum''' hash of the binary mod
 */
case class ModMetadata (modName: String, minecraftVersions: Seq[String], modFileLocation: String, md5Sum: String)
