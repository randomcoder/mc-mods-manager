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

import uk.co.randomcoding.minecraft.modmanager.SpecBaseBeforeAfterEach

class ModAdderSpec extends SpecBaseBeforeAfterEach {

  final val modsLibrary = s"${System.getProperty("java.io.tmpdir")}/modslibrary"

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

  "A ModAdder" should "generate metadata for an added mod" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added with a name of 'Dummy Mod' and version of '1.4.5'")
    Then("the generated metadata is 'ModMetadata(Dummy Mod, 1.4.5, d7363ddead39a3ef6a99821495301092)")
    modAdder.addMod(dummyModFile, "Dummy Mod", "1.4.5") should be (ModMetadata("Dummy Mod", "1.4.5", "modslibrary/dummyMod.mod","d7363ddead39a3ef6a99821495301092"))
  }

  it should "return the saved mod file when a new mod is saved with an absolute path to the mods library" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file called dummyMod.mod")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is saved")
    val savedFile = modAdder.save(dummyModFile)
    Then(s"the saved file should have a full path of '$modsLibrary/dummyMod.mod")
    savedFile.getAbsolutePath should be (s"$modsLibrary/dummyMod.mod")
  }

  it should "save the mod file in the configured Mods Library with the same name as the original mod" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("a dummy mod file called dummyMod.mod")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is saved in the default mods library")
    modAdder.save(dummyModFile)
    Then("there should be a file with the name 'dummyMod.mod' in the mods library directory")
    new File(modsLibrary).list should contain("dummyMod.mod")
  }

  it should "save an exact copy of the original file in the Mods Library" in {
    Given("a ModAdder")
    val modAdder = genModAdder
    And("metadata for a dummy mod file called dummyMod.mod")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)
    val metadata = modAdder.addMod(dummyModFile, "Dummy Mod", "1.2.3")

    When("the mod is saved")
    val savedFile = modAdder.save(dummyModFile)

    Then("the hash of the mod file saved to the mods library is the same as the hash of the original mod file")
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
    Given("a ModAdder with a mod already saved")
    val modAdder = genModAdder
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)
    val originalSavedFile = modAdder.save(dummyModFile)
    And("a second mod file which has a different name but the same hash")
    val sameHashModFile = new File(getClass.getResource("/anotherDummyMod.mod").toURI)

    When("the second mod file (with the same hash) is saved")
    val savedFile = modAdder.save(dummyModFile)
    Then("the returned file should be the same as the original saved file")
    savedFile should be (originalSavedFile)
  }

  it should "generate new metadata when adding a new supported version to a mod already in the Mods Library" in {
    pending
  }

  private[this] def genModAdder = new ModAdder(modsLibrary)
}
