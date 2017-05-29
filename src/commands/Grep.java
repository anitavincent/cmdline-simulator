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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import output.Output;

/**
 * Grep
 * 
 * Command class that is responsible for the tasks of the shell command grep.
 * Its function is to retrieve the lines of a file given as parameter that match
 * the given regular expression. For the recursive command, its function is to
 * retrieve the matching lines of all the files and subfiles inside the path for
 * a directory.
 * 
 */
public class Grep extends Command {
  /**
   * Reads the arguments to determine the regex to be matched against files.
   * Returns the lines of files that match the given regex
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {
    Output output = resource.getOutput();
    boolean recursive;
    try {
      recursive = validator.validateGrepAndFindOutIfItIsrecursive(arguments);
      int startIndex;
      if (recursive) {
        startIndex = 1;
      } else {
        startIndex = 0;
      }
      String regex = arguments.get(startIndex);
      startIndex++;
      for (int i = startIndex; i < arguments.size(); i++) {
        String path = arguments.get(i);
        try {
          Content content = validator.validateSingleInputGrep(path, recursive);
          if (recursive) {
            recursiveMatch((Directory) content, regex, output);
          } else {
            File file = (File) content;
            String result = matchRegexInFile(file, regex);
            if (!result.equals("")) {
              result = file.getName() + ":" + result;
            }
            output.appendToStdOutput(result);
          }
        } catch (InvalidPathException e) {
          output.appendToStdError(e.getMessage());
        } catch (InvalidArgumentException e) {
          output.appendToStdError(e.getMessage());
        } catch (ContentDoesntExistException e) {
          output.appendToStdError(e.getMessage());
        }
      }
    } catch (InvalidNumberOfArgumentsException e1) {
      output.appendToStdError(e1.getMessage());
      return;
    }
  }

  private void recursiveMatch(Directory directory, String regex, Output output) {
    HashMap<String, File> files = directory.getFiles();
    HashMap<String, Directory> subdirectories = directory.getChildren();
    for (String fileName : files.keySet()) {
      String result = matchRegexInFile(files.get(fileName), regex);
      if (!result.equals("")) {
        result =
            directory.calculateFullPAthOfDirectory()
                + files.get(fileName).getName() + ": " + result;
        output.appendToStdOutput(result);
      }
    }
    for (String directoryName : subdirectories.keySet()) {
      recursiveMatch(subdirectories.get(directoryName), regex, output);
    }
  }

  private String matchRegexInFile(File file, String regex) {
    Pattern p = Pattern.compile(regex);
    String result = "";
    String fileContent = file.getContent();
    String[] fileLines = fileContent.split("\n");
    Matcher matcher;
    for (String line : fileLines) {
      matcher = p.matcher(line);
      if (matcher.find()) {
        result += line + "\n";
      }
    }
    return result;
  }

}
