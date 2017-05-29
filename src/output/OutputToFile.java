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

package output;

import handlers.ErrorHandler;
import handlers.Validator;
import exceptions.ContentAlreadyExistsException;
import exceptions.ContentDoesntExistException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidDirectoryOrFileNameException;
import exceptions.InvalidPathException;
import files.Directory;
import files.File;

public class OutputToFile {
  private Output output;

  public OutputToFile(Output output) {
    this.output = output;
  }

  public void saveOutputToFile(String path, String operator,
      Validator validator, ErrorHandler handler) {

    String message = null;
    if (output.getStdOutput() != null) {
      message = output.consumeStdOutput();
    }
    if (message == null) {
      return;
    }
    saveMessageToFile(message, path, operator, validator, handler);
  }

  public void saveMessageToFile(String message, String path, String operator,
      Validator validator, ErrorHandler handler) {
    try {
      Directory parentDirectory =
          validator.validateAndRetrieveParentDirectory(path);
      File file;
      try {
        String fileName = validator.validateNewFileNameAndGetNameFromPath(path);
        try {
          // file exists
          file = validator.validateContentInRedirectionIsAFile(parentDirectory,
              fileName);
          if (operator.equals(">>")) {
            file.append(message);
          } else {
            file.overwrite(message);
          }
        } catch (ContentDoesntExistException e) {
          // file doesnt exist - create
          try {
            validator.validateCreationOfNewFile(fileName, parentDirectory);
            File newFile = new File(fileName);
            newFile.append(message);
            parentDirectory.addFile(newFile);
          } catch (ContentAlreadyExistsException e1) {
            // there is a folder with the same name of the file, cannot create
            // a new file with this name
            output.appendToStdError("Name: "+fileName+". "+handler.getErrorMessage(24));
          }
        } catch (InvalidArgumentException e1) {
          // argument is a folder
          output.appendToStdError(" " + e1.getMessage());
        }
      } catch (InvalidDirectoryOrFileNameException e) {
        output.appendToStdError(e.getMessage());
      }

    } catch (InvalidPathException e) {
      // path for destination is wrong
      output.appendToStdError(e.getMessage());
    }

  }
}
