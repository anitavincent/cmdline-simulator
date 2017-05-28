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

package output;

public class Output {
  private String stdoutput;
  private String stderror;

  public Output() {
    this.stdoutput = null;
    this.stderror = null;
  }

  public void appendToStdOutput(String newContent) {
    if (!newContent.equals("")) {
      this.stdoutput = appendToString(this.stdoutput, newContent);
    }
  }

  public void appendToStdError(String newContent) {
    if (!newContent.equals("")) {
      this.stderror = appendToString(stderror, newContent);
    }
  }

  private String appendToString(String source, String newContent) {
    if (source == null)
      return newContent;
    return (source + "\n" + newContent.replaceAll("(\n)+","\n"));
  }

  public String consumeStdOutput() {
    String result = this.stdoutput;
    this.stdoutput = null;
    return result;
  }

  public String consumeStdError() {
    String result = this.stderror;
    this.stderror = null;
    return result;
  }

  public String getStdOutput() {
    return this.stdoutput;
  }

  public String getStdError() {
    return this.stderror;
  }
}
