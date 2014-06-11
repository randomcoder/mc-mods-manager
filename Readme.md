# Minecraft Mods Manager

This is a small application intended to make the management of mods, prior to playing Minecraft easier.

It should work by copying (or linking) the required mods from a number of `modfile` directories into the 
`mods` directory of the installation as well as removing any copies (or links) of mods that are not desired at this time.

To do this the user will have a list of mods that are currently installed and will be activated (depending on the
version of minecraft that will be played) - note that it is perfectly OK to have mods installed for multiple versions at the same time.

There is also a panel which lists all mods

### How Minecraft mods Work
Mods are read from two places (at least for **forge**):
* **Global Mods**: are read from the `.minecraft/mods` directory
* **Version Specific Mods**: are read from the `.minecraft/mods/x.y.z` directory 

## How to do it
One option is for the user to add a mod along with some metadata, Minecraft Version (poss global), Mod Name (their choice) etc. 
and the Mod Manager will then store all the mods in a dedicated directory structure which is then read by the main application.
  
### Mod Identification
If possible, pre loading some mod data based on the hash of the mod file would make this more pleasant.

## UI
### Option 1 - Tabs Based
One tab for each version and global mods

Each tab has list of the mods available &amp; _installed_ which are selected by checkboxes.

### Option 2 - Holistic View
The user has a list of all mods with checkboxes (as for option 1) but this time the checkboxes are for each version. 
This allows _installation_ of all versions to be managed in the same place

### On Completion
Once done (for all versions) then the user presses a button and the relevant links/copies are made/removed
