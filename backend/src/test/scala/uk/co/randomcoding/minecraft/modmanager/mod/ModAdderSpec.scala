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

import java.io.{FileInputStream, File}
import java.nio.file.{Path, Paths, Files}

import uk.co.randomcoding.minecraft.modmanager.SpecBaseBeforeAfterEach

class ModAdderSpec extends SpecBaseBeforeAfterEach {

  final val modsLibrary = s"${System.getProperty("java.io.tmpdir")}/modslibrary"

  final val defaultModName = "Dummy Mod"
  final val defaultMinectraftVersion = "1.2.3"

  override def beforeEach(): Unit = {
    val libDir = new File(modsLibrary)
    libDir.exists() should be (false)
    libDir.mkdir() should be (true)
  }

  override def afterEach(): Unit = {
    val libDir = new File(modsLibrary)
    libDir.exists() should be (true)
    forAll(libDir.listFiles.toSeq) {f: File => f.delete() should be (true)}
    libDir.delete() should be (true)
  }

  "A ModAdder" should "generate metadata for an added mod that includes the mod name and minecraft version provided" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added with a name of 'Dummy Mod' and version of '1.4.5'")
    Then(s"the generated metadata has a mod name of 'Dummy Mod' and a Minecraft Version of '1.4.5'")
    modAdder.addMod(dummyModFile, "Dummy Mod", "1.4.5") should matchPattern {case ModMetadata("Dummy Mod", "1.4.5", _, _) => }
  }

  it should "generate a file name made up of the original file name plus the mod name and the original file extension when a mod is added" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added with a name of 'MyDummyMod'")
    val metadata = modAdder.addMod(dummyModFile, "MyDummyMod", defaultMinectraftVersion)

    Then(s"the generated metadata  contains the modFilePath $modsLibrary/dummyMod-Dummy Mod.mod")
    metadata.modFileLocation should be (s"$modsLibrary/dummyMod-MyDummyMod.mod")
  }

  it should "generate metadata with the hash of the mod file in the Mods Library" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added")
    val metadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinectraftVersion)

    Then(s"the generated metadata  contains the hash (MD5) 'd7363ddead39a3ef6a99821495301092'")
    metadata should matchPattern {case ModMetadata(_, _, _, "d7363ddead39a3ef6a99821495301092") => }
  }

  it should "save the renamed mod file in the Mods Library"  in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file called dummyMod.mod")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added in the default mods library")
    val metadata = modAdder.addMod(dummyModFile, defaultModName, defaultMinectraftVersion)

    Then("there should be a file with the name 'dummyMod.mod' in the mods library directory")
    val modFileName = new File(metadata.modFileLocation).getName
    new File(modsLibrary).list should contain(modFileName)
  }

  it should "save an exact copy of the original file in the Mods Library when the mod is added" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("metadata for a dummy mod file called dummyMod.mod")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the mod is added")
    val metadata = modAdder.addMod(dummyModFile, "Dummy Mod", "1.2.3")

    Then("the hash of the mod file saved to the mods library is the same as the hash of the original mod file")
    val savedFile = new File(metadata.modFileLocation)
    DigestCalculator.digest(new FileInputStream(savedFile)) should be(metadata.md5Sum)
  }

  it should "append the mod name from metadata to avoid duplication of file names and mod names" in {
    pending
  }

  it should "not add a new file to the Mods Library if a mod file with the same hash is added" in {
    pending
  }

  it should "return the same saved mod file when a second mod with a different name but the same hash is saved" in {
    pending
    /*Given("a ModAdder with a mod already saved")
    val modAdder = genModAdder
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)
    val originalSavedFile = modAdder.save(dummyModFile)
    And("a second mod file which has a different name but the same hash")
    val sameHashModFile = new File(getClass.getResource("/anotherDummyMod.mod").toURI)

    When("the second mod file (with the same hash) is saved")
    val savedFile = modAdder.save(dummyModFile)
    Then("the returned file should be the same as the original saved file")
    savedFile should be (originalSavedFile)*/
  }

  it should "generate new metadata when adding a new supported version to a mod already in the Mods Library" in {
    pending
  }

  private[this] def genModAdder = new ModAdder(modsLibrary)
}
