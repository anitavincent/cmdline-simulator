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
import exceptions.InvalidArgumentException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Content;
import files.Directory;
import files.File;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Cp
 * 
 * Command class that is responsible for the tasks of the shell command cp. Its
 * function is to copy a directory or file from its parent directory to a new
 * one.
 */
public class Cp extends Command {

  /**
   * Copies the the content to another location returning confirmation if action
   * worked
   * 
   */

  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    Content finalDest;

    try {
      Content oldItem = validator.validateCpAndGetContent(arguments);
      String oldPath = arguments.get(0);
      String newPath = arguments.get(1);

      // if we are copying a file
      if (oldItem instanceof File) {
        String fileName = validator.separateFileNameFromFullPath(oldPath);
        File oldFile = (File) oldItem;
        ArrayList<String> commandTxt = new ArrayList<String>();

        try {
          finalDest = validator.validateAndRetrieveContent(newPath);
        } catch (ContentDoesntExistException e) {
          // if an item that doesnt exist was listed in copy we must create it
          commandTxt.add(
              "echo" + " \"" + oldFile.getContent() + "\"" + " > " + newPath);
          Echo echo = new Echo();
          echo.execute(commandTxt, handler, validator, resource);

          return;
        }

        if (finalDest instanceof Directory) {
          // call an echo with the content of the oldFile, creating a new file
          // with a new name
          commandTxt.add(" echo \"" + oldFile.getContent() + "\"" + " > "
              + newPath + "/" + fileName);
          Echo echo = new Echo();
          echo.execute(commandTxt, handler, validator, resource);
          return;
        }

        if (finalDest instanceof File) {
          // call an echo with the content of the copied file, overwriting the
          // other one
          ((File) finalDest).overwrite(oldFile.getContent());
          return;
        }
      }

      // if we are copying a directory
      if (oldItem instanceof Directory) {

        Directory parent = validator.validateAndRetrieveDirectory(newPath);
        parent.copyDirectoryContentOf((Directory) oldItem);
        return;
      }

    } catch (ContentDoesntExistException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidPathException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidArgumentException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    }

    return;
  }

}
