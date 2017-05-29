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

import java.util.HashMap;

/**
 * ErrorHandler
 * 
 * Class responsible for managing errors by keeping all of the error messages
 * and allowing them to be added and to be accessed using an errorCode
 */
public class ErrorHandler {

  private HashMap<Integer, String> messageTable;
  private final String undefinedErrorMessage = "Undefined error";

  public ErrorHandler() {
    this.messageTable = new HashMap<Integer, String>();
    this.initializeAllMessages();
  }

  // initializes all error messages
  private void initializeAllMessages() {
    // 1
    this.addMessage("Invalid command/parameter combination. ");
    // 2
    this.addMessage("Invalid number of arguments ");
    // 3
    this.addMessage("Invalid path. The directory does not have a parent. ");
    // 4
    this.addMessage("Invalid argument. ");
    // 5
    this.addMessage("Invalid command.");
    // 6
    this.addMessage(" Invalid path. The file or directory does not exist. ");
    // 7
    this.addMessage("Invalid file name. ");
    // 8
    this.addMessage("The directory stack is empty. ");
    // 9
    this.addMessage("Invalid name for a directory. ");
    // 10
    this.addMessage("The name for the directory or file already exists in the directory. ");
    // 11
    this.addMessage("File not found on the specified path. ");
    // 12
    this.addMessage("The string entered is invalid. ");
    // 13
    this.addMessage("Invalid name for a file. ");
    // 14
    this.addMessage("Impossible to create new content with this name. ");
    // 15
    this.addMessage("This argument must be a directory");
    // 16
    this.addMessage("Can't copy or move a directory to a subDirectory. ");
    // 17
    this.addMessage("Can't create file with name of an existing directory. ");
    // 18
    this.addMessage("Cannot create new directory. ");
    // 19
    this.addMessage("Content not found on the directory. ");
    // 20
    this.addMessage("Invalid URL");
    // 21
    this.addMessage("Cannot connect to the provided URL. ");
    // 22
    this.addMessage("The argument for the grep command is a file. "
        + "The argument for a recursive grep should be a directory. ");
    // 23
    this.addMessage("The argument for the grep command is a directory. "
        + "The argument for a non-recursive grep should be a file. ");
    // 24
    this.addMessage("The argument for redirection is a directory. Cannot "
        + "redirect the output to a directory. ");
    // 25
    this.addMessage("Internal error. ");
    // 26
    this.addMessage("Cannot perform popd. ");
  }

  /**
   * Adds an error message to the handler
   * 
   * @param message Message to be added
   */
  public void addMessage(String message) {
    int index = messageTable.size() + 1;
    this.messageTable.put(index, message);
  }

  /**
   * Fetches error message from handler
   * 
   * @param index Error code
   * @return error Message
   */
  public String getErrorMessage(int index) {
    if (this.messageTable.containsKey(index))
      return this.messageTable.get(index);
    return this.undefinedErrorMessage;
  }


}
