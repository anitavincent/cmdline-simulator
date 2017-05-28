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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.ContentAlreadyExistsException;
import exceptions.ContentDoesntExistException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidDirectoryOrFileNameException;
import exceptions.InvalidNumberOfArgumentsException;
import exceptions.InvalidPathException;
import files.Content;
import files.Directory;
import files.DirectoryCursor;
import files.File;

/**
 * Validator
 * 
 * Aggregates validation methods for all of the commands
 */
public class Validator {

  private PathChecker checker;
  private ErrorHandler handler;
  private DirectoryCursor directoryCursor;
  private Resource resource;

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

  /**
   * Initializes errorHandler and directory cursor
   * 
   * @param eh Reference to error handler
   * @param dc Reference to directory cursor
   */
  public Validator(ErrorHandler eh, DirectoryCursor dc) {
    this.checker = new PathChecker(dc);
    this.handler = eh;
    this.directoryCursor = dc;


  }

  public DirectoryCursor getDirectoryCursor() {
    return this.directoryCursor;
  }


  /**
   * Verifies if the number of arguments received for a command is zero
   * 
   * @param arguments List of arguments received
   * @throws InvalidNumberOfArgumentsException If the number of arguments is
   *         invalid
   */
  public void validateNumberOfArgumentsIsZero(List<String> arguments)
      throws InvalidNumberOfArgumentsException {

    if (arguments.size() != 0) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2));

    }
  }

  /**
   * Verifies if the number of arguments passed to a command is bigger than zero
   * 
   * @param arguments List of arguments passed to the command
   * @throws InvalidNumberOfArgumentsException If the number of arguments is 0
   */
  public void validateNumberOfArgumentsIsNotZero(List<String> arguments)
      throws InvalidNumberOfArgumentsException {
    if (arguments.size() <= 0) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2));

    }
  }

  /**
   * Validates arguments for man command by verifying the number of arguments
   * 
   * @param arguments List of arguments
   * @throws InvalidNumberOfArgumentsException If the amount of arguments is
   *         invalid
   */
  public void validateMan(List<String> arguments)
      throws InvalidNumberOfArgumentsException {

    if (arguments.size() != 1) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the Man command. There must be exactly one argument. \n");

    }

  }

  /**
   * Checks if path is valid for ls and gets the content at the end of the path
   * 
   * @param argument Path for ls
   * @return Content at the end of the path
   * @throws ContentDoesntExistException If the content at the end of the path
   *         doesn't exist
   * @throws InvalidPathException If path that leads to the content is invalid
   */
  public Content validateLsAndGetContent(String argument)
      throws ContentDoesntExistException, InvalidPathException {
    return checker.checkValidPathAndRetrieveContent(argument);
  }

  /**
   * Verifies if number of arguments and path are valid for cd, then gets the
   * directory at the end o the path
   * 
   * @param arguments List of arguments containing the path
   * @return Directory at the end of the path
   * @throws InvalidPathException If the path is invalid
   * @throws InvalidNumberOfArgumentsException
   */
  public Directory validateCdAndGetDirectory(List<String> arguments)
      throws InvalidPathException, InvalidNumberOfArgumentsException {

    // validating amount of arguments
    if (arguments.size() != 1) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the Cd command. There must be exactly one argument.\n");
    }

    return checker.checkIfValidPathAndRetrieveDirectory(arguments.get(0));
  }

  /**
   * Validates path and number of arguments for pushd and gets directory at the
   * end of the path
   * 
   * @param arguments List of arguments for the command
   * @return Directory at the end of the path
   * @throws InvalidPathException If path received as argument is invalid
   * @throws InvalidNumberOfArgumentsException
   */
  public Directory validatePushDAndReturnNewDirectory(
      ArrayList<String> arguments) throws InvalidPathException,
      InvalidNumberOfArgumentsException {

    // validating number of arguments
    if (arguments.size() != 1) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the Pushd command. This command takes exactly one argument. \n");

    }
    return checker.checkIfValidPathAndRetrieveDirectory(arguments.get(0));
  }

  /**
   * Validates path for mkdir and gets path that leads to the new directory
   * 
   * @param argument Path for mkdir
   * @return Parent directory to the new directory
   * @throws InvalidPathException If path is invalid
   */
  public Directory validateMkdir(String argument) throws InvalidPathException {

    return checker
        .checkIfValidPathForParentAndRetrieveParentDirectory(argument);
  }

  /**
   * Separates final directory in the path from the rest
   * 
   * @param path String containing path to be separeted
   * @return Name of the last directory in the path
   */
  public String separatePathAndFinalDirectory(String path) {
    String finalDirectoryName = null;
    int position = path.lastIndexOf("/");
    if (position == -1) {
      // the path is just the directory name.
      finalDirectoryName = path;
    } else {
      // the path ends with a slash
      if (position == (path.length() - 1)) {
        // erasing the last slash in path
        path = path.substring(0, position);
        position = path.lastIndexOf("/");
      }
      finalDirectoryName = path.substring(position + 1);
    }
    return finalDirectoryName;
  }

  /**
   * Verifies if string is valid and generates a new list of arguments with the
   * string parsed correctly, then verifies if the amount of arguments is valid
   * 
   * @param inputUser Input exactly as the user entered
   * @return List of arguments with string parsed correctly
   * @throws InvalidArgumentException If string entered as first argument is
   *         invalid
   * @throws InvalidNumberOfArgumentsException If the amount of arguments is
   *         invalid
   */
  public ArrayList<String> validateAndRetriveOrganizedEchoArguments(
      String inputUser) throws InvalidArgumentException,
      InvalidNumberOfArgumentsException {
    ArrayList<String> organizedArguments = new ArrayList<String>();
    int positionOfTheLastLetterInEcho = inputUser.indexOf('o');
    String arguments =
        inputUser.substring(positionOfTheLastLetterInEcho + 1).trim();
    int positionOfTheFirstDoubleQuote = arguments.indexOf('\"');
    int positionOfTheLastDoubleQuote = arguments.lastIndexOf('\"');
    if (positionOfTheLastDoubleQuote == -1
        || positionOfTheFirstDoubleQuote == -1) {
      // no double quotation
      // sends message that string is invalid
      throw new InvalidArgumentException(handler.getErrorMessage(12));
    } else {
      if (positionOfTheFirstDoubleQuote == positionOfTheLastDoubleQuote) {
        // only one double quotation
        // sends message that string is invalid
        throw new InvalidArgumentException(handler.getErrorMessage(12));

      } else {
        if (positionOfTheFirstDoubleQuote != 0) {
          // the string doesnt begin with a double quotation
          // sends message that string is invalid
          throw new InvalidArgumentException(handler.getErrorMessage(12));
        }
        String stringToBeWritten =
            arguments.substring(positionOfTheFirstDoubleQuote + 1,
                positionOfTheLastDoubleQuote);
        organizedArguments.add(stringToBeWritten);
        String otherArguments =
            arguments.substring(positionOfTheLastDoubleQuote + 1).trim();
        String[] inputCollection = otherArguments.split("[ ]+");
        for (int i = 0; i < inputCollection.length; i++) {
          if (!inputCollection[i].equals(""))
            organizedArguments.add(inputCollection[i]);
        }
      }
    }

    // validating if the amount of arguments is valid
    if (organizedArguments.size() != 1 && organizedArguments.size() != 3) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + " for the Echo command\n");
    }

    return organizedArguments;
  }


  /**
   * Validates the arguments for cp, checks if the content of the first path
   * listed exists. Checks if the second path is valid. Checks if the second
   * path is a subPath of the first one.
   * 
   * @param arguments Contains the first path (to be moved) and second path
   * @return The content of the first path listed
   * @throws ContentDoesntExistException If the one of the contents doesn't
   *         exist
   * @throws InvalidPathException
   * @throws InvalidArgumentException
   * @throws InvalidNumberOfArgumentsException
   */
  public Content validateCpAndGetContent(ArrayList<String> arguments)
      throws ContentDoesntExistException, InvalidPathException,
      InvalidArgumentException, InvalidNumberOfArgumentsException {

    // verifying number of arguments
    if (arguments.size() != 2) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + " for this command\n");
    }


    Content parent = checker.checkValidPathAndRetrieveContent(arguments.get(0));
    Content newDir;

    try {
      newDir = checker.checkValidPathAndRetrieveContent(arguments.get(1));
    } catch (ContentDoesntExistException e) {
      if (parent instanceof File) {
        return parent;
      }
      // invalid argument
      throw new InvalidArgumentException(handler.getErrorMessage(4)
          + " Can't create copy to a directory that doesn't exist");

    }

    // the second argument must be a directory if the first one is a directory
    if ((parent instanceof Directory) && (newDir instanceof File)) {
      // throw this argument must be a directory
      throw new InvalidArgumentException(handler.getErrorMessage(15) + ": "
          + arguments.get(1));
    }

    // if the first argument is a directory we must check if the second argument
    // is a child of the first one
    if (parent instanceof Directory) {
      // if the second argument is a subDirectory of the first
      if (checker.checkIfPathIsSubDirectory(parent, newDir)) {
        // message: you cant copy a directory to subdirectory
        throw new InvalidArgumentException(handler.getErrorMessage(16));
      }
    }

    return parent;
  }



  /**
   * Verifies if path to directory is valid and directory exists
   * 
   * @param path String containing path
   * @return Directory at the end of the path
   * @throws InvalidPathException If path is invalid
   */
  public Directory validateAndRetrieveDirectory(String path)
      throws InvalidPathException {
    return checker.checkIfValidPathAndRetrieveDirectory(path);
  }

  /**
   * Separates filename from the rest of the path
   * 
   * @param path String containing path
   * @return String with the name of the file at the end of the path
   */
  public String separateFileNameFromFullPath(String path) {
    String fileName;
    int position = path.lastIndexOf("/");
    if (position == -1) {
      // the path is just the file name. verify in the current directory
      fileName = path;
    } else {
      fileName = path.substring(position + 1);
    }
    return fileName;
  }

  /**
   * Verifies if name for a new directory is valid
   * 
   * @param path Parent directory to the directory being created
   * @return Name of the new directory to be created
   * @throws InvalidDirectoryOrFileNameException If name for new directory is
   *         invalid
   */
  public String validateNewDirectoryNameAndGetNameFromPath(String path)
      throws InvalidDirectoryOrFileNameException {
    String newDirectoryName = separatePathAndFinalDirectory(path);
    if (validateContentName(newDirectoryName)) {
      return newDirectoryName;
    } else {
      throw new InvalidDirectoryOrFileNameException("Directory Name: "
          + newDirectoryName + ". " + handler.getErrorMessage(9));
    }
  }

  /**
   * Verifies if name for a new file is valid
   * 
   * @param path Parent directory to the file being created
   * @return Name of the file to be created
   * @throws InvalidDirectoryOrFileNameException If name for new file is invalid
   * @throws InvalidPathException
   */
  public String validateNewFileNameAndGetNameFromPath(String path)
      throws InvalidDirectoryOrFileNameException, InvalidPathException {
    String newFileName = separateFileNameFromFullPath(path);
    Directory parent = validateMkdir(path);

    // searching for some directory already with that name
    for (Map.Entry<String, Directory> entry : parent.getChildren().entrySet()) {

      if (entry.getKey().equals(newFileName)) {
        throw new InvalidDirectoryOrFileNameException(
            handler.getErrorMessage(17));
      }
    }

    if (validateContentName(newFileName)) {
      return newFileName;
    } else {
      throw new InvalidDirectoryOrFileNameException(handler.getErrorMessage(13)
          + "File Name: " + newFileName);
    }
  }

  /**
   * Checks if string is a valid name for a content. The name is considered
   * valid only if it does not have any invalid characters.
   * 
   * @param name Name of the content
   * @return True if the name is valid
   */

  public boolean validateContentName(String name) {
    Pattern slashesAndBackslashes = Pattern.compile("[/\\\\]");
    Pattern otherInvalidCharacters =
        Pattern.compile("[\\[\\]\\(\\)\\*'`\\{\\}>< $!&+,:;=?@#|\"]");
    Pattern startsWithSpecialCharacter = Pattern.compile("^[^A-Za-z0-9]");
    // the new filename cannot start with special characters
    Matcher matcher = startsWithSpecialCharacter.matcher(name);
    if (matcher.find())
      return false;
    Matcher matcher1 = slashesAndBackslashes.matcher(name);
    Matcher matcher2 = otherInvalidCharacters.matcher(name);
    // the name is valid only if it doesnt have any invalid characters
    return !(matcher1.find() || matcher2.find());
  }

  /**
   * Validates arguments for history and gets the number passed as argument
   * 
   * @param arguments List of arguments entered
   * @return Number of commands to be written
   * @throws NumberFormatException If number entered has invalid format
   * @throws InvalidArgumentException If argument is invalid
   * @throws InvalidNumberOfArgumentsException
   */
  public int validateHistoryAndReturnNumberOfCommands(
      ArrayList<String> arguments) throws NumberFormatException,
      InvalidArgumentException, InvalidNumberOfArgumentsException {

    // validate number of arguments
    if (arguments.size() > 1) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the History command. This commands takes at most 1 argument.\n");
    }
    if (arguments.size() == 0) {
      return this.getResource().getInputLog().size();
    } else {
      try {
        String possibleNum = arguments.get(0);
        int numberOfCommands = Integer.parseInt(possibleNum);
        if (numberOfCommands < 0) {
          throw new InvalidArgumentException(handler.getErrorMessage(4)
              + "Parameter for history command is a negative number.");
        }
        return numberOfCommands;
      } catch (NumberFormatException e) {
        throw new NumberFormatException(
            "The argument provided to History is not a number.");
      }
    }
  }

  /**
   * Validates argument for cat
   * 
   * @param argument single argument entered by the user
   * @return File at the end of the path entered
   * @throws InvalidDirectoryOrFileNameException
   * @throws InvalidPathException
   * @throws ContentDoesntExistException
   */

  public File validateCat(String argument)
      throws  InvalidPathException,
      ContentDoesntExistException {
    return checker.checkIfValidPathAndRetrieveFile(argument);

  }

  /**
   * Verifies if the content inside parentDirectory is a file
   * 
   * @param parentDirectory
   * @param fileName Name of the content
   * @return the file with name "fileName" inside parentDirectory
   * @throws ContentDoesntExistException if there is no content with the name
   *         specified
   * @throws InvalidArgumentException if the content specified by fileName is
   *         not a file
   */
  public File validateContentInRedirectionIsAFile(Directory parentDirectory,
      String fileName) throws ContentDoesntExistException,
      InvalidArgumentException {
    Content c = checker.getContentByName(parentDirectory, fileName);
    if (c instanceof File)
      return (File) c;
    throw new InvalidArgumentException(handler.getErrorMessage(24)
        + " Directory name: " + c.getName());

  }

  /**
   * Validates the creation of a new file
   * 
   * @param fileName Name of the new file to be created
   * @param parentDirectory Directory in which the file should be created
   * @throws ContentAlreadyExistsException if the parent directory already has a
   *         directory with the name given as parameter
   */
  public void validateCreationOfNewFile(String fileName,
      Directory parentDirectory) throws ContentAlreadyExistsException {
    if (parentDirectory.getChildren().containsKey(fileName)) {
      throw new ContentAlreadyExistsException(handler.getErrorMessage(10));
    }
  }

  public void validateCreationOfNewContent(String contentName,
      Directory parentDirectory) throws ContentAlreadyExistsException {
    if (parentDirectory.getFiles().containsKey(contentName)) {
      throw new ContentAlreadyExistsException(handler.getErrorMessage(10));
    }
    validateCreationOfNewFile(contentName, parentDirectory);

  }

  /**
   * Returns the last subdirectory in a path if the path is valid. Example: if
   * path is /a/b/c/d, the function returns directory c if the path exists
   * 
   * @param path Complete path
   * @return The last subdirectory in the path
   * @throws InvalidPathException if the path in invalid
   */
  public Directory validateAndRetrieveParentDirectory(String path)
      throws InvalidPathException {
    return checker.checkIfValidPathForParentAndRetrieveParentDirectory(path);
  }

  /**
   * Validates the operator for echo command
   * 
   * @param arguments The arguments for echo command
   * @throws InvalidArgumentException if the operator passed as argument is not
   *         > or >>
   */
  public void validateEchoOperator(ArrayList<String> arguments)
      throws InvalidArgumentException {

    String operator = arguments.get(1);
    // verifies if operator is valid
    if (!operator.equals(">>") && !operator.equals(">")) {
      // sends message that argument is invalid
      throw new InvalidArgumentException(handler.getErrorMessage(4)
          + " >> or > expected.");
    }
  }

  /**
   * Verifies if path to a content is valid and returns content
   * 
   * @param path To the content
   * @return Content
   * @throws ContentDoesntExistException
   * @throws InvalidPathException
   */
  public Content validateAndRetrieveContent(String path)
      throws ContentDoesntExistException, InvalidPathException {
    return checker.checkValidPathAndRetrieveContent(path);
  }

  /**
   * Validates parameters for ! command and returns the integer passed
   * 
   * @param arguments Arguments for !
   * @return Integer passed as an argument to !
   * @throws InvalidArgumentException If the arguments are inadequate
   * @throws InvalidNumberOfArgumentsException
   */
  public int validateExclamationAndGetNumber(ArrayList<String> arguments)
      throws InvalidArgumentException, NumberFormatException,
      InvalidNumberOfArgumentsException {
    // the reason for the -1 is because the command !number was already added to
    // history but it shouldn't be considered yet
    int maxValue = resource.getInputLog().size() - 1;

    if (arguments.size() != 1) {
      // invalid number of arguments
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for ! command.");
    }

    try {
      int endIndex = arguments.get(0).length();
      String possibleNum = arguments.get(0).substring(1, endIndex);
      int numberOfCommands = Integer.parseInt(possibleNum);
      if (numberOfCommands < 1) {
        throw new InvalidArgumentException(handler.getErrorMessage(4)
            + "Parameter for ! command is smaller than one.");
      }
      if (numberOfCommands > maxValue) {
        throw new InvalidArgumentException(handler.getErrorMessage(4)
            + "The entry -"+numberOfCommands+"- doesn't exist in history.");
      }

      return numberOfCommands;
    } catch (NumberFormatException e) {
      throw new NumberFormatException(handler.getErrorMessage(4)
          + " The argument provided to ! is not an acceptable number.");
    }
  }

  /**
   * Validates the arguments for get and return the new file name for the file
   * that will be created with the content of the url
   * 
   * @param arguments Arguments for get
   * @return String with the name of the file that should be created by get
   *         command
   * @throws ContentAlreadyExistsException if the current directory already has
   *         a content with the same name
   * @throws InvalidArgumentException if the url is invalid
   * @throws InvalidNumberOfArgumentsException
   */
  public String validateGetAndGetNewFileName(List<String> arguments)
      throws ContentAlreadyExistsException, InvalidArgumentException,
      InvalidNumberOfArgumentsException {

    // validating number of arguments
    if (arguments.size() != 1) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the Get command. This command takes exactly one argument. \n");
    }
    String url = arguments.get(0);
    if (url.matches("^.*[\\.](html|txt)$")) {
      String newFileName = separateFileNameFromFullPath(url);
      Directory currentWorkingDirectory = directoryCursor.getWorkingDirectory();
      // if the current working directory has a folder with the same name of the
      // new file.
      // then the new file cannot be created
      if (currentWorkingDirectory.getChildren().containsKey(newFileName)) {
        throw new ContentAlreadyExistsException(handler.getErrorMessage(17)
            + " Name: " + newFileName);
      }
      return newFileName;
    } else {
      throw new InvalidArgumentException(handler.getErrorMessage(20) + ": "
          + url);
    }

  }

  /**
   * Validates the parameters for grep and defines whether the command is
   * recursive or not
   * 
   * @param arguments arguments for grep
   * @return boolean value indicating whether the command is recursive or not
   * @throws InvalidNumberOfArgumentsException
   */
  public boolean validateGrepAndFindOutIfItIsrecursive(
      ArrayList<String> arguments) throws InvalidNumberOfArgumentsException {
    // grep is at least grep regex path
    if (arguments.size() < 2) {
      throw new InvalidNumberOfArgumentsException(handler.getErrorMessage(2)
          + "for the Grep command. This command takes at least two arguments. "
          + "\n");
    } else {
      // if first argument is the option to recursive call
      if (arguments.get(0).toUpperCase().equals("-R")) {
        // if the command is just grep -R something the input is invalid
        if (arguments.size() == 2) {
          throw new InvalidNumberOfArgumentsException(
              handler.getErrorMessage(2) + " for the recursive Grep command\n");
        } else {
          // the command may be valid
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Validates a single input for the grep command
   * 
   * @param path Path to the content in the argument of grep command
   * @param recursive - Parameter indicating whether grep is recursive or not
   * @return Content in the path provided
   * @throws InvalidPathException if the path does not exist
   * @throws InvalidArgumentException if a path to a file is passed in a
   *         recursive grep or if a path to a directory is passed in a
   *         non-recursive grep
   * @throws ContentDoesntExistException if the content does not exist in the
   *         path specified
   */
  public Content validateSingleInputGrep(String path, boolean recursive)
      throws InvalidPathException, InvalidArgumentException,
      ContentDoesntExistException {
    // if recursive the content has to be a directory
    // if not recursive the content has to be a file
    Content content = validateAndRetrieveContent(path);
    if (recursive) {
      if (!(content instanceof Directory)) {
        throw new InvalidArgumentException(handler.getErrorMessage(22)
            + "Name of the file: " + content.getName()+".");
      } else {
        return content;
      }
    } else {
      if (!(content instanceof File)) {
        throw new InvalidArgumentException(handler.getErrorMessage(23)
            + "Name of the directory: " + content.getName()+".");
      } else {
        return content;
      }
    }

  }

}
