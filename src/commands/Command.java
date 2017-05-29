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

import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;

/**
 * Command
 * 
 * Abstract base class for all of the commands
 */
public abstract class Command {

  /**
   * Executes the command function based on the input received
   * 
   * @param arguments Arguments entered to the command
   * @param handler This is a reference to the errorHandler class
   * @param validator This is a reference to the validator class
   * @param resource This is a reference to the resource class
   */
  public abstract void execute(ArrayList<String> arguments,
      ErrorHandler handler, Validator validator, Resource resource);



}
