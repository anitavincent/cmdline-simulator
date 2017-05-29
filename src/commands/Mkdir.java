// **********************************************************
// Assignment2:
// Student1: Anita Paes Vincent
// UTOR user_name: paesvinc
// UT Student #: 1002812049
// Author: Anita Paes Vincent
//
// Student2:
// UTOR user_name: dinizroc
// UT Student #: 1002813459
// Author: Ives Levi Diniz Rocha
//
// Student3: Jessica Provenciano Silverio
// UTOR user_name: provenci
// UT Student #: 1002813486
// Author: Jessica Provenciano Silverio
//
// Student4:
// UTOR user_name:
// UT Student #:
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC 207 and understand the consequences.
// *********************************************************
package commands;

import java.util.ArrayList;

import exceptions.ContentAlreadyExistsException;
import exceptions.InvalidDirectoryOrFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Directory;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Mkdir
 * 
 * Command class that is responsible for the tasks of the shell command mkdir.
 * Its function is to create a new directory
 */
public class Mkdir extends Command {

  /**
   * Creates a new directory with the specified name
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();


    try {
      // validating, the number of args must be DIFFERENT than 0
      validator.validateNumberOfArgumentsIsNotZero(arguments);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage()
          + "for the mkdir command. There must be at least one argument.");
      return;
    }
    Directory parentDirectory;
    String newDirectoryName;
    for (String argument : arguments) {
      try {
        parentDirectory = validator.validateMkdir(argument);
        newDirectoryName =
            validator.validateNewDirectoryNameAndGetNameFromPath(argument);
        validator.validateCreationOfNewContent(newDirectoryName,
            parentDirectory);
        new Directory(parentDirectory, newDirectoryName);
      } catch (InvalidPathException e) {
        out.appendToStdError(e.getMessage() + "\n");
      } catch (InvalidDirectoryOrFileNameException e) {
        out.appendToStdError(e.getMessage() + "\n");
      } catch (ContentAlreadyExistsException e) {
        out.appendToStdError("Name: " + argument + ". "
            + handler.getErrorMessage(18) + e.getMessage() + "\n");
      }
    }
    return;
  }
}
