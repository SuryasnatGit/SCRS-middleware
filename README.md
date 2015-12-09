# Group11-SCRS
Repository for the SCRS Middleware Project in CSCI 5801

This repository contains an Eclipse project `SCRS`, the Student Class Registration System Middleware.

# To Use the Package

Import the `edu.umn.csci5801` package and create an instance of `SCRSImpl` (which is a concrete class that implements `SCRS`).

After that call the needed functions. 

# Database

The database is currently set to map to the dummy database located in `resources/SCRSDatabase.db`. 
Change the references in DBCoordinator to connect the Middleware to another database instance.

A copy of the schema that a database must obey for use with this middle ware can be found in `docs/DB_schema.md`

# Additional Notes:

Dependencies are found in `lib/`, which currently only contains SQLite v3.8.11.2.
Custom exceptions are found in the subpackage `edu.umn.csci5801.exceptions` 
A small set of test cases can be found in `tests/`, with a Simple Test Driver for convenience (must be moved into the same package for testing).

# Authors: 

Also known as Group 11 Members:

- Kurt Schefers
- Wen Chuan Lee
- Yijia Zhang
- Adam Subat 