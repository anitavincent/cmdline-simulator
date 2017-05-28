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
 * A text file with name and content
 */
public class File extends Content {

  /**
   * The content of the text file
   */
  private StringBuffer content;

  /**
   * Constructor for creating a file with a name.
   * 
   * @param name The name of the file
   */
  public File(String name) {
    this.name = name;
    this.content = new StringBuffer();
  }

  /**
   * Appends a new line of text to the file
   * 
   * @param text The text to be appended
   */
  public void append(String text) {
    content.append(text + "\n");
  }

  /**
   * Overwrites the content of the file
   * 
   * @param text The new content of the file
   */
  public void overwrite(String text) {
    content = new StringBuffer();
    content.append(text + "\n");
  }

  public String getContent() {
    return content.toString();
  }

  public String getName() {
    return name;
  }

  public void setContent(String content2) {
    this.append(content2);
  }

}
