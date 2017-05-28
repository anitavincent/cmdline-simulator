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
import java.util.HashMap;
import java.util.Stack;

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
 * Ls
 * 
 * Command class that is responsible for the tasks of the shell command ls. Its
 * function is to retrieve the child directories from a directory.
 */
public class Ls extends Command {

  private Directory startingDirectory;

  /**
   * Gets the list of child directories and files of Directory passed or gets
   * the name of the file passed.
   * 
   */
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    boolean recursive = false;

    if (arguments.size() == 0) {
      Directory dir = validator.getDirectoryCursor().getWorkingDirectory();
      out.appendToStdOutput(dir.getContent());
      return;
    } else 
    {
      for (String argument : arguments) 
      {
        if (argument.toUpperCase().equals("-R")) {
          recursive = true;
          if (arguments.size() == 1) 
          {
            Directory dir =
                validator.getDirectoryCursor().getWorkingDirectory();
            startingDirectory = dir;
            out.appendToStdOutput(getContentsOfDirectory(dir, recursive));
          }
          continue;
        }
        Content content;
        try {
          content = validator.validateLsAndGetContent(argument);
          if (content instanceof File) {
            String name = ((File) content).getName();
            out.appendToStdOutput(name);
          } else if (content instanceof Directory) {
            if(arguments.size()==1 && !recursive)
            {
              out.appendToStdOutput(content.getContent()+"\n");
            }
            else
            {
              startingDirectory = (Directory) content;
              out.appendToStdOutput(
                  getContentsOfDirectory(startingDirectory, recursive));
            }
            
          }
        } catch (ContentDoesntExistException | InvalidPathException e) {
          out.appendToStdError(e.getMessage());
        }
      }
    }
  }

  /**
   * Retrieves the contents of a given directory.
   * 
   * @param dir The directory whose contents are to be retrieved
   * @param recursive If true, will recursively retrieve contents of
   *        subdirectories
   * @return Contents of directory in String form
   */
  private String getContentsOfDirectory(Directory dir, boolean recursive) {
    String result = "";
    if (dir.equals(startingDirectory)) {
      // dont need to add parents to path
    } else {
      // add all parents up to starting directory to the path name
      Stack<Directory> parents = new Stack<Directory>();
      Directory parent = dir.getParent();
      while (!parent.equals(startingDirectory)) {
        parents.push(parent);
        parent = parent.getParent();
      }
      parents.push(parent);
      while (!parents.isEmpty()) {
        parent = parents.pop();
        result += parent.getName() + "/";
      }
    }
    result += dir.getName() + ": \n";
    String dirContent = dir.getContent();
    result += dirContent + "\n";
    if (recursive) {
      HashMap<String, Directory> children = dir.getChildren();
      for (Directory child : children.values()) {
        result += getContentsOfDirectory(child, recursive);
      }
    }
    return result;
  }
}
