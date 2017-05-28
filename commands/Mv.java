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
import exceptions.InvalidPathException;
import files.Content;
import files.Directory;
import files.File;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Mv
 * 
 * Command class that is responsible for the tasks of the shell command mv. Its
 * function is to move a directory or file from its parent directory to a new
 * one.
 */
public class Mv extends Cp {

  /**
   * Calls Cp.execute making a copy of the desired item, then, deletes the old
   * item
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    // calls copy
    super.execute(arguments, handler, validator, resource);

    // if copy succeeded
    if (out.getStdError() == null) {

      // we must delete the original item
      String originalPath = arguments.get(0);

      Content originalItem;
      try {
        originalItem = validator.validateAndRetrieveContent(originalPath);

        if (originalItem instanceof Directory) {
          // deletes originalDirectory
          ((Directory) originalItem).deleteItself();

          return;
        }
        if (originalItem instanceof File) {
          // gets what the parent directory is
          Directory parent;
          try {
            parent = validator.validateMkdir(originalPath);
            // deletes original file
            parent.deleteFile(((File) originalItem));

            return;
          } catch (InvalidPathException e) {
            out.appendToStdError(e.getMessage());
            return;
          }
        }

      } catch (ContentDoesntExistException | InvalidPathException e1) {
        out.appendToStdError(e1.getMessage());
        return;
      }

    }
    return;

  }
}
