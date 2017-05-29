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

import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import output.Output;
import exceptions.ContentAlreadyExistsException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidNumberOfArgumentsException;
import files.Directory;
import files.File;


/**
 * Get
 * 
 * Command class that is responsible for the tasks of the shell command get. Its
 * function is to retrieve the content in a given url and copy it into a new file
 * in the local directory
 * 
 */
public class Get extends Command {
  /**
   * Saves the content in fileContent in a file named newFileName in the current
   * working directory. If the current working directory already has a file
   * named newFileName, the content of this file is overwitten by the new
   * content
   * 
   * @param fileContent Content to be saved in file
   * @param validator
   * @param newFileName Name of the new file
   */
  public void saveFileContentInCurrentWorkingDirectory(String fileContent,
      Validator validator, String newFileName) {
    Directory currentWorkingDirectory =
        validator.getDirectoryCursor().getWorkingDirectory();
    if (currentWorkingDirectory.getFiles().containsKey(newFileName)) {
      // overwrite the existing file
      File file = currentWorkingDirectory.getFiles().get(newFileName);
      file.overwrite(fileContent);
    } else {
      // create a new file in the current working directory
      File newFile = new File(newFileName);
      newFile.append(fileContent);
      currentWorkingDirectory.addFile(newFile);
    }
  }

  /**
   * Read the content of the file at the given URL and creates a new local file
   * with that same content and name
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {
    Output output = resource.getOutput();
    try {
      String newFileName = validator.validateGetAndGetNewFileName(arguments);
      String urlInput = arguments.get(0);
      URL myURL = new URL(urlInput);
      URLConnection myURLConnection = myURL.openConnection();
      myURLConnection.connect();
      BufferedReader in =
          new BufferedReader(new InputStreamReader(myURL.openStream()));
      String inputLine = in.readLine();
      String fileContent = "";
      while (inputLine != null) {
        fileContent += inputLine + "\n";
        inputLine = in.readLine();
      }
      in.close();
      fileContent = fileContent.trim();
      saveFileContentInCurrentWorkingDirectory(fileContent, validator,
          newFileName);
    } catch (ContentAlreadyExistsException e1) {
      output.appendToStdError(e1.getMessage());
    } catch (InvalidArgumentException e1) {
      output.appendToStdError(e1.getMessage());
    } catch (MalformedURLException e) {
      output.appendToStdError(handler.getErrorMessage(20) + ": "
          + arguments.get(0));
    } catch (IOException e) {
      output.appendToStdError(handler.getErrorMessage(21));
    } catch (InvalidNumberOfArgumentsException e) {
      output.appendToStdError(e.getMessage());
    }
  }
}
