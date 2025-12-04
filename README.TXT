Requirements

Java 11 or higher

IDE (e.g., IntelliJ, Eclipse) or command-line tools

Running the Game

Clone the repository:

git clone https://github.com/RoryH06/ZorkGameRH

Open the project in your IDE.

Run the ZorkULGame class to start the game.

Project Structure:

Core Packages:

Game/ — Core gameplay classes

Room, Item, NPC, Command, Parser, etc.

Conditions/ — Custom gameplay conditions

BeerRequirementCondition, ElfHatCondition, RorysKeysCondition, etc.

GUI/ — Optional graphical interface components

GUI, Map

Key Classes

Class

Description

Room

Represents a location in the game

Item

Objects players can interact with

NPC / FriendlyNPC / MerchantNPC

Non-player characters

CommandWords

Valid player commands

Storage / LockedStorage / OpenStorage

Item containers

TeleportDestination

Enum for teleport targets

Game Logic:

Conditions are modular and can be attached to rooms, items, or events.

Commands are parsed and executed via the Parser and Command classes.

Items may be Drinkable, Container, or interactable in other ways.

Gameplay Features:

Modular condition system for unlocking rooms

NPC interactions with friendly and merchant types

Inventory management and storage systems

Teleportation and map-based navigation

Optional GUI layer for visual enhancements

Credits:

Created by Rory Harrington. Inspired by Zork and other classic text adventures.
