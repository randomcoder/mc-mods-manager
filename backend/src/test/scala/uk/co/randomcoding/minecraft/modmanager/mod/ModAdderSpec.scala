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

import uk.co.randomcoding.minecraft.modmanager.SpecBase

/**
 * TODO: Brief description
 */
class ModAdderSpec extends SpecBase {
  "A ModAdder" should "generate metadata for an added mod" in {
    Given("a ModAdder")
    val modAdder = new ModAdder
    And("a dummy mod file")
    val dummyModFile = new File(getClass.getResource("/dummyMod.mod").toURI)

    When("the dummy file is added with a name of 'Dummy Mod' and version of '1.4.5'")
    Then("the generated metadata is 'ModMetadata(Dummy Mod, 1.4.5, d7363ddead39a3ef6a99821495301092)")
    modAdder.addMod(dummyModFile, "Dummy Mod", "1.4.5") should be (ModMetadata("Dummy Mod", "1.4.5", "modslibrary/dummyMod.mod","d7363ddead39a3ef6a99821495301092"))
  }

  it should "save the mod file in the configured Mods Library with a name generated" in {
    pending
  }

  it should "not add a new file to the Mods Library if a mod file with the same hash is added" in {
    pending
  }

  it should "generate new metadata when adding a supported version to a mod already in the Mods Library" in {
    pending
  }
}
