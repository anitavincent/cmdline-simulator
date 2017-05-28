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
package handlers;

import exceptions.ContentDoesntExistException;
import exceptions.InvalidPathException;
import files.Content;
import files.Directory;
import files.DirectoryCursor;
import files.File;

/**
 * PathChecker
 * 
 * Aggregates all the functions for checking paths and retrieving files and
 * directories
 * 
 */
public class PathChecker {
  private DirectoryCursor dc;
  private ErrorHandler errH;

  /**
   * Initializes directory cursors and errorHandler
   * 
   * @param dc Reference to directory cursor
   */
  public PathChecker(DirectoryCursor dc) {
    this.dc = dc;
    this.errH = new ErrorHandler();
  }

  /**
   * Verifies if a path is valid excluding the final directory or file
   * 
   * @param path String containing path
   * @return Parent path to the final content
   * @throws InvalidPathException If path is invalid
   */
  public Directory checkIfValidPathForParentAndRetrieveParentDirectory(
      String path) throws InvalidPathException {
    String newPath;
    int position = path.lastIndexOf("/");
    if (position == -1) {
      // the path is just the file name. verify in the current directory
      newPath = ".";
    } else {
      // the path ends with a slash
      if (position == (path.length() - 1)) {
        // erasing the last slash in path
        path = path.substring(0, position);
        position = path.lastIndexOf("/");
        if (position == -1) {
          // the path is just the file name. verify in the current directory
          newPath = ".";
        } else {
          newPath = path.substring(0, position + 1);
        }
      } else {
        // path contains a slash and this slash is not the end of the path
        newPath = path.substring(0, position + 1);
      }

    }
    return checkIfValidPathAndRetrieveDirectory(newPath);
  }

  /**
   * Checks if entire path is valid and returns last directory
   * 
   * @param originalPath String containing path
   * @return Final directory of the path
   * @throws InvalidPathException If path is invalid
   */
  public Directory checkIfValidPathAndRetrieveDirectory(String originalPath)
      throws InvalidPathException {
    Directory result;
    String path = originalPath;
    if (path.charAt(0) == '/') {
      // absolute path
      result = dc.getRoot();
      path = path.substring(1);
      if (path.equals(""))
        return result;
    } else {
      // relative Path
      result = dc.getWorkingDirectory();
    }
    // linux shell allows this kind of command
    // for example mkdir folder1////folder2 will create folder2 inside folder1
    // if folder1 exists
    String[] levels = path.split("[/]+");
    for (int i = 0; i < levels.length; i++) {
      String level = levels[i];
      if (level.equals(".")) {
        continue;
      } else {
        if (level.equals("..")) {
          if (result.getParent() != null) {
            result = result.getParent();
          } else {
            // the folder does not have a parent
            throw new InvalidPathException("Path: "+originalPath+". "+errH.getErrorMessage(3));
          }
        } else {
          if (result.getChildren().containsKey(level)) {
            result = result.getChildren().get(level);
          } else {
            throw new InvalidPathException("Path: "+originalPath+". "+errH.getErrorMessage(6));
          }
        }
      }
    }
    return result;
  }

  /**
   * Check if entire path is valid and returns last file
   * 
   * @param path String containing path
   * @return Last file of the path
   * @throws InvalidPathException If path is invalid
   * @throws ContentDoesntExistException If file doesn't exist
   */
  public File checkIfValidPathAndRetrieveFile(String path)
      throws InvalidPathException, ContentDoesntExistException {
    String newPath;
    String fileName;
    int position = path.lastIndexOf("/");
    if (position == -1) {
      // the path is just the file name. verify in the current directory
      newPath = ".";
      fileName = path;
    } else {
      // the name of the file ends with a slash. this is not a valid path for a
      // file
      if (position == (path.length() - 1)) {
        throw new InvalidPathException(errH.getErrorMessage(7));
      }
      newPath = path.substring(0, position);
      fileName = path.substring(position + 1);
    }
    Directory result = checkIfValidPathAndRetrieveDirectory(newPath);
    if (result.getFiles().containsKey(fileName)) {
      return result.getFiles().get(fileName);
    }
    throw new ContentDoesntExistException("File name: "+fileName+". Path: "+newPath+". "+errH.getErrorMessage(11));
  }

  /**
   * Checks if the path is valid and returns the inner content (file or
   * directory)
   * 
   * @param path String containing path
   * @return Final content of the path
   * @throws ContentDoesntExistException If the content doesn't exist
   * @throws InvalidPathException If path is invalid
   */
  public Content checkValidPathAndRetrieveContent(String path)
      throws ContentDoesntExistException, InvalidPathException {
    String newPath;
    String contentName;
    if (path.equals(".")) {
      return checkIfValidPathAndRetrieveDirectory(path);
    }
    if (path.equals("..")) {
      return checkIfValidPathAndRetrieveDirectory(path);
    }
    int position = path.lastIndexOf("/");
    if (position == -1) {
      // the path is just the file name. verify in the current directory
      newPath = ".";
      contentName = path;
    } else {
      if (position == path.length() - 1) {
        // the path has to point to a directory
        return checkIfValidPathAndRetrieveDirectory(path);
      } else {
        newPath = path.substring(0, position);
        contentName = path.substring(position + 1);
      }

    }
    if(newPath.equals(""))
      newPath="/";
    Directory result = checkIfValidPathAndRetrieveDirectory(newPath);
    if (result != null) {
      if (result.getFiles().containsKey(contentName)) {
        return result.getFiles().get(contentName);
      }
      if (result.getChildren().containsKey(contentName)) {
        return result.getChildren().get(contentName);
      }
    }
    // file or directory doesnt exist
    throw new ContentDoesntExistException(
        "Path: " + path + ". " + errH.getErrorMessage(6));
  }

  /**
   * Checks if a directory with a given path is a subdirectory of another
   * directory passed as an argument
   * 
   * @param parent Directory that might be the parent directory
   * @param subDir Directory that might be the subdirectory
   * @return True if subDir is a subdirectory of parent
   */
  public boolean checkIfPathIsSubDirectory(Content parent, Content subDir) {
    // nothing can be a subDirectory of a file
    if (parent instanceof File) {
      return false;
    }

    Directory parentDir = (Directory) parent;
    if (parentDir.hasSubDirectory(subDir)) {
      return true;
    }

    return false;
  }

  public Content getContentByName(Directory parent, String name)
      throws ContentDoesntExistException {
    if (parent.hasContent(name)) {
      if (parent.getChildren().containsKey(name)) {
        return parent.getChildren().get(name);
      }
      if (parent.getFiles().containsKey(name)) {
        return parent.getFiles().get(name);
      }
    }
    throw new ContentDoesntExistException(errH.getErrorMessage(19));

  }



}
