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
package files;

import java.util.Stack;

import exceptions.EmptyStackException;
import handlers.ErrorHandler;

/**
 * A stack of directories saved for later use.
 */
public class DirectoryStack {
  /**
   * The stack of directories.
   */
  private Stack<String> s;
  /**
   * The error handler for printing errors.
   */
  ErrorHandler errH;

  public DirectoryStack() {
    this.s = new Stack<String>();
    this.errH = new ErrorHandler();
  }

  /**
   * Adds specified directory to the top of the stack
   * 
   * @param directory The directory to be added
   */
  public void push(Directory directory) {
    s.push(directory.calculateFullPAthOfDirectory());
  }

  /**
   * Gets the directory from the top of the stack
   * 
   * @return The last saved directory
   * @throws EmptyStackException when there are no directories in the stack
   */
  public String pop() throws EmptyStackException {
    if (s.empty()) {
      throw new EmptyStackException(errH.getErrorMessage(8));
    }
    return s.pop();
  }
}
