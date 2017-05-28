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

import files.DirectoryStack;
import output.Output;

/**
 * Resource
 * 
 * Class responsible for keeping track of the input log and directory stack
 */
public class Resource {

  private ArrayList<String> inputLog;
  private DirectoryStack directoryStack;
  private Output output;

  /**
   * Initializes the input log and the directory stack
   * 
   * @param ds Reference to directory stack
   * @param output Reference to output
   */
  public Resource(DirectoryStack ds, Output output) {
    this.inputLog = new ArrayList<String>();
    this.directoryStack = ds;
    this.output = output;
  }

  public Output getOutput() {
    return this.output;
  }

  public ArrayList<String> getInputLog() {
    return inputLog;
  }

  public void setInputLog(ArrayList<String> log) {
    inputLog = log;
  }


  public DirectoryStack getDirectoryStack() {
    return directoryStack;
  }

  public void setDirectoryStack(DirectoryStack directoryStack) {
    this.directoryStack = directoryStack;
  }

  /**
   * Adds an input to the log
   * 
   * @param input String containing the input to be added
   */
  public void addToInputLog(String input) {
    inputLog.add(input);
  }
}
