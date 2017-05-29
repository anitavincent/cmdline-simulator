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
import java.util.HashMap;

import commands.Command;
import files.DirectoryCursor;
import files.DirectoryStack;
import output.Output;
import output.OutputToFile;

/**
 * Interpreter
 * 
 * Is responsible for interpreting the input entered by the user and call the
 * appropriate commands
 */
public class Interpreter {
  private ErrorHandler errH;
  private Validator validator;
  private Resource resource;
  private DirectoryStack directoryStack;
  private OutputToFile outputToFile;
  private Output output;


  HashMap<String, String> commandMap = new HashMap<String, String>();

  /**
   * Initializes private attributes and commandMap
   * 
   * @param dc Reference to directory cursor
   */
  public Interpreter(DirectoryCursor dc, Output output) {
    this.errH = new ErrorHandler();
    this.validator = new Validator(errH, dc);
    this.directoryStack = new DirectoryStack();
    this.resource = new Resource(directoryStack, output);
    this.validator.setResource(resource);
    this.outputToFile = new OutputToFile(output);
    this.output = output;
    buildCommandMap();
  }

  private void buildCommandMap() {
    commandMap.put("cat", "commands.Cat");
    commandMap.put("cd", "commands.Cd");
    commandMap.put("echo", "commands.Echo");
    commandMap.put("exit", "commands.Exit");
    commandMap.put("history", "commands.History");
    commandMap.put("ls", "commands.Ls");
    commandMap.put("man", "commands.Man");
    commandMap.put("mkdir", "commands.Mkdir");
    commandMap.put("popd", "commands.Popd");
    commandMap.put("pushd", "commands.Pushd");
    commandMap.put("pwd", "commands.Pwd");
    commandMap.put("cp", "commands.Cp");
    commandMap.put("mv", "commands.Mv");
    commandMap.put("!", "commands.Exclamation");
    commandMap.put("get", "commands.Get");
    commandMap.put("grep", "commands.Grep");
  }

  public boolean hasRedirection(ArrayList<String> arguments) {
    for (int i = 0; i < arguments.size(); i++) {
      if (arguments.get(i).equals(">>") || arguments.get(i).equals(">")) {
        if (i == arguments.size() - 2) {
          return true;
        }
      }
    }
    return false;
  }

  public ArrayList<String> cleanArguments(ArrayList<String> arguments) {
    ArrayList<String> cleanArguments = new ArrayList<String>();
    for (int i = 0; i < arguments.size() - 2; i++) {
      cleanArguments.add(arguments.get(i));
    }
    return cleanArguments;
  }

  public void performRedirection(ArrayList<String> arguments) {
    int size = arguments.size() - 1;
    String path = arguments.get(size);
    String operator = arguments.get(size - 1);
    if (this.output.getStdOutput() != null) {
      outputToFile.saveOutputToFile(path, operator, validator, errH);
    }
  }

  /**
   * Parses input separating it between command and arguments. Creates
   * appropriate command and call execute for that command
   * 
   * @param input String containing input entered by the user
   */
  public void interpret(String input) {
    resource.addToInputLog(input);

    // remove spaces at beginning and end
    input = input.trim();
    // if there is anything in the input
    if (!input.equals("")) {
      // divide command and parameters
      String[] inputCollection = input.split("[ ]+");
      String commandString;
      // stores what the command is
      if (inputCollection[0].startsWith("!")) {
        // ! is a special case
        commandString = "!";
      } else {
        commandString = inputCollection[0];
      }
      // surrounded with try/catch in case user inputs non-existent command
      if (commandMap.containsKey(commandString)) {
        Command command;
        try {
          command = (Command) Class.forName(commandMap.get(commandString))
              .newInstance();
          // create list to store arguments
          ArrayList<String> arguments = new ArrayList<String>();
          if (commandString.equals("echo")) {
            // separate arguments for echo command
            // we cannot use the split for space anymore. Do it in another way
            arguments.add(input);
          } else if (commandString.startsWith("!")) {
            if ((inputCollection.length) > 0) {
              for (int i = 0; i < inputCollection.length; i++) {
                arguments.add(inputCollection[i]);
              }
            }
          } else {
            // store arguments in list
            if ((inputCollection.length - 1) > 0) {
              for (int i = 1; i < inputCollection.length; i++) {
                arguments.add(inputCollection[i]);
              }
            }
          }
          if (hasRedirection(arguments) && !commandString.equals("exit")) {
            command.execute(cleanArguments(arguments), errH, validator,
                resource);
            performRedirection(arguments);
          } else {
            command.execute(arguments, errH, validator, resource);
          }
        } catch (InstantiationException | IllegalAccessException
            | ClassNotFoundException e) {
          // internal error. will never happen if the hashmap has consistent
          // values.
          output.appendToStdError(errH.getErrorMessage(25));
        }
      } else {
        output.appendToStdError(errH.getErrorMessage(5));
      }
    }
  }

  public Resource getResource() {
    return resource;
  }

  public void setResource(Resource resource) {
    this.resource = resource;
  }

}
