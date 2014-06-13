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

import java.io.InputStream
import java.security.{DigestInputStream, MessageDigest}

/**
 * TODO: Brief description
 */
object DigestCalculator {
  def digest(inputStream: InputStream): String = {
    val digest = MessageDigest.getInstance("MD5")
    val digestStream = new DigestInputStream(inputStream, digest)

    while (digestStream.read != -1) {}
    digestStream.close()

    digest.digest().toHexString
  }

  /**
   * Render an Array of bytes as a hex string.
   *
   * If this implicit class is in scope (i.e. has been imported), then the <code>toHex</code> method will be
   * available on any instance of <code>Array[Byte]</code>.
   *
   * This is intended for logging of small byte sequences such as digests: performance is unlikely to be good for
   * converting large volumes of data.
   */
  implicit class RichByteArray(n: Array[Byte]) {
    /**
     * Convert the byte array to a hex string as used for MD5 representations
     *
     * @return The hex string generated from the input `Array[Byte]`
     */
    def toHexString = n.foldLeft("")((cur, byte) => f"$cur${byte & 0xff}%02x")
  }

}
