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

/**
 * This cursor keeps track of the root directory for the file system as well as
 * the current working directory.
 *
 */
public class DirectoryCursor {

  /**
   * The root directory of the file system.
   */
  private Directory root;
  /**
   * The current working directory.
   */
  private Directory workingDirectory;
  /**
   * The full path of the current working directory.
   */
  private String fullPathOfWorkingDirectory;

  /**
   * Initiates the cursor at the root directory.
   * 
   * @param root The root directory of the file system.
   */
  public DirectoryCursor(Directory root) {
    this.root = root;
    this.workingDirectory = root;
    this.fullPathOfWorkingDirectory = "/";
  }

  /**
   * Moves the cursor to the specified directory.
   * 
   * @param workingDirectory The new working directory.
   */
  public void moveToDirectory(Directory workingDirectory) {
    this.workingDirectory = workingDirectory;
    this.fullPathOfWorkingDirectory =
        workingDirectory.calculateFullPAthOfDirectory();
  }

  /**
   * @return The full path of the current working directory
   */
  public String getFullPathOfWorkingDirectory() {
    return this.fullPathOfWorkingDirectory;
  }

  public Directory getRoot() {
    return root;
  }

  public Directory getWorkingDirectory() {
    return workingDirectory;
  }

}
