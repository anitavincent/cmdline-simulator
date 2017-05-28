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

import exceptions.InvalidNumberOfArgumentsException;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * Man
 * 
 * Command class that is responsible for the tasks of the shell command man. Its
 * function is to provide info about any command accepted by the shell
 */
public class Man extends Command {
  private HashMap<String, String> manual;

  /**
   * Initializes the hashmap for the manual
   */
  public Man() {

    this.manual = new HashMap<String, String>();
    manual.put("cat", "Display the contents of FILE in the shell..");
    manual.put("cd",
        "Change directory to DIR, which may be relative to the current "
            + "directory or may be a full path. As with \nUnix, .. means a "
            + "parent directory and a . means the current directory. The "
            + "directory separator must be \n/, the forward slash. The root "
            + "of the file system is a single slash: /. ");
    manual.put("echo",
        "If OUTFILE is not provided, print STRING on the shell. "
            + "Otherwise, put STRING \ninto file OUTFILE. STRING is a string"
            + " of characters surrounded by double \nquotation marks. This "
            + "creates a new file if OUTFILE does not exists and erases \nthe"
            + " old contents if OUTFILE already exists. In either case, the"
            + " only thing in \nOUTFILE should be STRING.");
    manual.put("exit", "Quit the program.");
    manual.put("history",
        "This command will print out recent commands, one command "
            + "per line. i.e. ");
    manual.put("ls", "If no paths are given, print the contents (file or "
        + "directory) of the current \ndirectory, with a new line"
        + " following each of the content (file or directory). \n"
        + "Otherwise, for each path p, the order listed: If p "
        + "specifies a file, print p \n(i.e. the name of the file "
        + "only)If p specifies a directory, print p, a colon, \nthen"
        + " the contents of that directory, then an extra new line. "
        + "If p does not \nexist, print a suitable error message.");
    manual.put("man", "Print documentation for CMD");
    manual.put("mkdir",
        "Create directories, each of which may be relative to the "
            + "current directory or may be a full path.");
    manual.put("popd",
        "Remove the top entry from the directory stack, and cd into "
            + "it. The removal must \nbe consistent as per the LIFO behavior"
            + " of a stack. The popd command removes the \ntop most directory"
            + " from the directory stack and makes it to the current "
            + "working \n"
            + "directory. If there is no directory onto the stack, then give "
            + "appropriate error \nmessage.");
    manual.put("pushd",
        "Saves the current working directory by pushing onto the "
            + "directory stack and then changes the new current \n"
            + "working directory to DIR. The push must be consistent "
            + "as per the LIFO behavior of a stack. The pushd \ncommand "
            + "saves the old current working directory in directory of "
            + "stack so that it can be returned to at \nany time (via "
            + "the popd command). The size of the directory stack is "
            + "dynamic and dependent on the pushd and \npopd commands. ");
    manual.put("pwd", "Print the current working directory path "
        + "(i.e. the whole" + " absolute path)");
    manual.put("cp",
        "Like mv, but don’t remove OLDPATH. If OLDPATH is a directory, "
            + "recursively copy the \ncontents. ");
    manual.put("mv",
        "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be "
            + "relative to the current \ndirectory or may be full paths. If "
            + "NEWPATH is a directory, move the item into the \ndirectory.");
    manual.put("get",
        "URL is a web address. Retrieve the file at that URL and add it to "
            + "the current working \ndirectory.");
    manual.put("grep",
        "If –R is not supplied, print any lines containing REGEX in PATH, "
            + "which must be a file. \nIf –R is supplied, and PATH is a "
            + "directory, recursively traverse the directory and, \nfor all "
            + "lines in all files that contain REGEX, print the path to the "
            + "file (including \nthe filename), then a colon, then the line "
            + "that contained REGEX.");
    manual.put("!", "This command will recall any of previous history by its "
        + "number(>=1) preceded by an \nexclamation point (!). For "
        + "instance, if your history looks like above, you could type "
        + "\nthe following on the command line of your JShell i.e. \n!3 "
        + "\nThis will immediately recall and execute the command "
        + "associated with the history \nnumber 3.");
  }

  /**
   * Identifies command and gets appropriate manual
   * 
   */
  @Override
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    try {
      validator.validateMan(arguments);
      String command = arguments.get(0);
      if (manual.containsKey(command)) {
        out.appendToStdOutput(manual.get(command));
        return;
      } else {
        out.appendToStdError(handler.getErrorMessage(4) + "Command -" + command
            + "- does not exist");
        return;
      }
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    }
  }
}
