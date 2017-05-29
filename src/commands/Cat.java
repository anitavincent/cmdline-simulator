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

import exceptions.ContentDoesntExistException;
import exceptions.InvalidDirectoryOrFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.File;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Cat
 * 
 * Command class that is responsible for the tasks of the shell command cat. Its
 * function is to retrieve the content of a file
 */
public class Cat extends Command {

  /**
   * Fetches and returns content of specified files
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {
    Output out = resource.getOutput();
    try {
      // verifying if the number of arguments received is bigger than zero
      // it's ok to have any amount of arguments bigger than 0
      validator.validateNumberOfArgumentsIsNotZero(arguments);
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage()
          + "for the Cat command. This command must receive at least one "
          + "argument.");
      return;
    }
    for (String argument : arguments) {
      try {
        File file = validator.validateCat(argument);
        out.appendToStdOutput(file.getContent());
      } catch (InvalidPathException e) {
        out.appendToStdError(e.getMessage());
      } catch (ContentDoesntExistException e) {
        out.appendToStdError(e.getMessage());
      }
    }
    return;

  }

}
