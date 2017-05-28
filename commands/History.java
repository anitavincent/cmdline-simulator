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

import exceptions.InvalidArgumentException;
import exceptions.InvalidNumberOfArgumentsException;
import handlers.ErrorHandler;
import handlers.Resource;
import handlers.Validator;
import output.Output;

/**
 * History
 * 
 * Command class that is responsible for the tasks of the shell command history.
 * Its function is to get the past inputs entered to the shell so that these
 * inputs can be printed to the screen
 */
public class History extends Command {

  /**
   * Gets the past entered inputs from the log
   * 
   */
  @Override
  public void execute(ArrayList<String> arguments, ErrorHandler handler,
      Validator validator, Resource resource) {

    Output out = resource.getOutput();

    try {
      int numberOfCommands =
          validator.validateHistoryAndReturnNumberOfCommands(arguments);
      out.appendToStdOutput(getLastCommands(numberOfCommands, resource));
      return;
    } catch (InvalidNumberOfArgumentsException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (NumberFormatException e) {
      out.appendToStdError(e.getMessage());
      return;
    } catch (InvalidArgumentException e) {
      out.appendToStdError(e.getMessage());
      return;
    }

  }

  /**
   * Gets a certain amount of past inputs from the log
   * 
   * @param numberOfCommands Number of inputs to be fetched
   * @param r Reference to the resource class
   * @return string Composed by the past inputs concatenated
   */
  private String getLastCommands(int numberOfCommands, Resource r) {
    String finalResult = "";
    ArrayList<String> inputLog = r.getInputLog();

    int upperLimit = inputLog.size();
    int begin = inputLog.size() - numberOfCommands;
    if (begin < 0) {
      begin = 0;
    }
    for (int i = begin; i < upperLimit; i++) {
      finalResult += ((i + 1) + " " + inputLog.get(i)) + "\n";
    }

    return finalResult;
  }

}
