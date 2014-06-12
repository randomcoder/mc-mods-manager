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
package uk.co.randomcoding.minecraft.modmanager

import org.scalatest._

/**
 * Base type for `FlatSpec` based classes including common helper traits
 */
abstract class SpecBase extends FlatSpec with GivenWhenThen with Inspectors with OptionValues

/**
 * Adds `BeforeAndAfterAll` to [[SpecBase]]
 */
abstract class SpecBaseBeforeAfterAll extends SpecBase with BeforeAndAfterAll

/**
 * Adds `BeforeAndAfterEach` to [[SpecBase]]
 */
abstract class SpecBaseBeforeAfterEach extends SpecBase with BeforeAndAfterEach

/**
 * Adds `BeforeAndAfterAll` and `BeforeAndAfterEach` to [[SpecBase]]
 */
abstract class SpecBaseBeforeAfterEachAll extends SpecBase with BeforeAndAfterEach with BeforeAndAfterAll
