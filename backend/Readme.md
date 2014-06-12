# Minecraft Mods Manager Backend Library

This library provides all the functionality of the mods manager, including:

* Loading and verify the currently installed mods
    * This should verify the MD5 of the mod file with the metadata for the file
* Listing available mods from the configured mods storage directories 
* Verifying available mods (checksum) on load
* Storing and retrieving mod metadata
* Saving a mod and its metadata
    * If a single mod is compatible with multiple versions of Minecraft then there will be several metadata entries, 
      but there should only be a single copy of the mod file
    * This leads to there only being a single directory for all mod files 
* Load and save user config (minecraft dir etc.)
* Identify OS Version and provide OS specific data (base locations etc.)
* Add mods into the correct mods directory and remove unused mods
    * `global` -> `.mods`
    * `version x.y.z` -> `.mods/x.y.z`
    * The decision is based solely on the version in the metadata
