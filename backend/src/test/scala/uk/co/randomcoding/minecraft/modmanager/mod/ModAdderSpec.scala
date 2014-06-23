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

import org.scalatest.Inside._
import uk.co.randomcoding.minecraft.modmanager.SpecBaseBeforeAfterEach

class ModAdderSpec extends SpecBaseBeforeAfterEach {

  final val modsLibrary = s"${System.getProperty("java.io.tmpdir")}/modslibrary"
  final val defaultModName = "Dummy Mod"
  final val defaultMinecraftVersion = "1.2.3"
  final val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

  override def beforeEach(): Unit = {
    val libDir = new File(modsLibrary)
    libDir.exists() should be(false)
    libDir.mkdir() should be(true)
  }

  override def afterEach(): Unit = {
    val libDir = new File(modsLibrary)
    libDir.exists() should be(true)
    forAll(libDir.listFiles.toSeq) { f: File => f.delete() should be(true)}
    libDir.delete() should be(true)
  }

  "A ModAdder" should "generate metadata for an added mod that includes the mod name and minecraft version provided" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("the dummy file is added with a name of 'Dummy Mod' and version of '1.4.5'")
    Then(s"the generated metadata has a mod name of 'Dummy Mod' and a Minecraft Version of '1.4.5'")
    modAdder.addMod(dummyModFile, "Dummy Mod", "1.4.5") should matchPattern { case ModMetadata("Dummy Mod", Seq("1.4.5"), _, _) =>}
  }

  it should "generate a file name made up of the original file name plus the mod name and the original file extension when a mod is added" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("the dummy file is added with a name of 'MyDummyMod'")
    val metadata = modAdder.addMod(dummyModFile, "MyDummyMod", defaultMinecraftVersion)

    Then(s"the generated metadata  contains the modFilePath $modsLibrary/dummyMod-Dummy Mod.mod")
    metadata.modFileLocation should be(s"$modsLibrary/dummyMod-MyDummyMod.mod")
  }

  it should "generate metadata with the hash of the mod file in the Mods Library" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("the dummy file is added")
    val metadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    Then(s"the generated metadata  contains the hash (MD5) 'd7363ddead39a3ef6a99821495301092'")
    metadata should matchPattern { case ModMetadata(_, _, _, "d7363ddead39a3ef6a99821495301092") =>}
  }

  it should "save the renamed mod file in the Mods Library" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("the dummy file is added in the default mods library")
    val metadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    Then("there should be a file with the name 'dummyMod.mod' in the mods library directory")
    val modFileName = new File(metadata.modFileLocation).getName
    new File(modsLibrary).list should contain(modFileName)
  }

  it should "save an exact copy of the original file in the Mods Library when the mod is added" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("the mod is added")
    val metadata = modAdder.addMod(dummyModFile, "Dummy Mod", "1.2.3")

    Then("the hash of the mod file saved to the mods library is the same as the hash of the original mod file")
    val savedFile = new File(metadata.modFileLocation)
    DigestCalculator.digest(new FileInputStream(savedFile)) should be(metadata.md5Sum)
  }

  it should "append the mod name from metadata to avoid duplication of file names and mod names" in {
    Given("a ModAdder")
    val modAdder = genModAdder

    When("a the saved target path for a mod file is requested")
    val filePath = modAdder.savedFileName(defaultModName, dummyModFile, modsLibrary)

    Then("the filename portion of the path confirms to the pattern 'modFile-modname.extension'")
    new File(filePath).getName should be(s"dummyMod-$defaultModName.mod")
  }

  it should "not add a new file to the Mods Library if a mod file with the same hash is added with the same mod name and version" in {
    Given("a ModAdder with a mod file already added")
    val modAdder = genModAdder
    val originalMetadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("a mod file with a different name but the same hash is added with the same mod name and version")
    val duplicateModFile = new File(getClass.getResource("/dummyModCopy.mod").toURI)
    val metadata = modAdder.addMod(duplicateModFile, defaultModName, defaultMinecraftVersion)

    Then("the returned metadata is the same as the the original mod")
    metadata should be(originalMetadata)
    And("the mods library only contains the originally added mod file")
    new File(modsLibrary).list().toSeq should contain only s"dummyMod-$defaultModName.mod"
  }

  it should "not add a new file to the Mods Library if a mod file with the same hash is added with a different mod name" in {
    Given("a ModAdder with a mod file already added")
    val modAdder = genModAdder
    val originalMetadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("a mod file with a different name but the same hash is added with a different mod name and version")
    val duplicateModFile = new File(getClass.getResource("/dummyModCopy.mod").toURI)

    val metadata = modAdder.addMod(duplicateModFile, "DifferentMod", defaultMinecraftVersion)

    Then("the returned metadata is the same as the the original mod")
    metadata should be(originalMetadata)
    And("the mods library only contains the originally added mod file")
    new File(modsLibrary).list().toSeq should contain only s"dummyMod-$defaultModName.mod"
  }

  it should "generate new metadata when adding a new supported version to a mod already in the Mods Library" in {
    Given("a mod adder with a mod added for the default minecraft version")
    val modAdder = genModAdder
    val originalMetadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("the same mod is added with the same name but a different minecraft version")
    val metadata = modAdder.addMod(dummyModFile, defaultModName, "3.4.5")

    Then("the metadata is the same as the original but with the new minecraft version added to the supported versions")
    inside(metadata) { case ModMetadata(name, versions, path, hash) =>
      name should be(originalMetadata.modName)
      path should be(originalMetadata.modFileLocation)
      hash should be(originalMetadata.md5Sum)
      versions should contain only(defaultMinecraftVersion, "3.4.5")
    }
  }

  it should "only update the supported versions when adding a new supported version to a mod already in the Mods Library and setting a different name" in {
    Given("a mod adder with a mod added for the default minecraft version")
    val modAdder = genModAdder
    val originalMetadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("the same mod is added with the same name but a different minecraft version")
    val metadata = modAdder.addMod(dummyModFile, "DifferentMod", "3.4.5")

    Then("the metadata is the same as the original but with the new minecraft version added to the supported versions")
    inside(metadata) { case ModMetadata(name, versions, path, hash) =>
      name should be(originalMetadata.modName)
      path should be(originalMetadata.modFileLocation)
      hash should be(originalMetadata.md5Sum)
      versions should contain only(defaultMinecraftVersion, "3.4.5")
    }
  }

  it should "not generate a new mod file when adding a new supported version to a mod that already exists in the mods library" in {
    Given("a mod adder with a mod added for the default minecraft version")
    val modAdder = genModAdder
    modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("the same mod is added with the same name but a different minecraft version")
    modAdder.addMod(dummyModFile, defaultModName, "3.4.5")

    Then("the mod library only contains the original mod file")
    new File(modsLibrary).list().toSeq should contain only s"dummyMod-$defaultModName.mod"
  }

  it should "not generate a new mod file when adding a new supported version to a mod that already exists in the mods library and setting a different mod name" in {
    Given("a mod adder with a mod added for the default minecraft version")
    val modAdder = genModAdder
    modAdder.addMod(dummyModFile, defaultModName, defaultMinecraftVersion)

    When("the same mod is added with the same name but a different minecraft version")
    modAdder.addMod(dummyModFile, "DifferentMod", "3.4.5")

    Then("the mod library only contains the original mod file")
    new File(modsLibrary).list().toSeq should contain only s"dummyMod-$defaultModName.mod"
  }

  private[this] def genModAdder = new ModAdder(modsLibrary)
}
