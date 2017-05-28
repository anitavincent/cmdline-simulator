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

import java.util.HashMap;
import java.util.Map;

/**
 * A directory that can contain a parent and multiple other Contents
 *
 */
public class Directory extends Content {

  /**
   * The parent directory of this directory
   */
  protected Directory parent;
  /**
   * The Directories contained within this directory
   */
  protected HashMap<String, Directory> children_directories;
  /**
   * The Files contained within this directory
   */
  protected HashMap<String, File> children_files;

  /**
   * The name of this directory
   */
  //protected String name;

  /**
   * Constructor receiving the parent directory and the name for this directory
   * 
   * @param parent The directory containing this directory
   * @param name The name for this directory
   */
  public Directory(Directory parent, String name) {
    this.parent = parent;
    this.name = name;
    children_directories = new HashMap<String, Directory>();
    children_files = new HashMap<String, File>();
    if (parent != null)
      parent.addChild(this);
  }

  /**
   * Adds a directory to be contained within this directory
   * 
   * @param child The directory to be added
   */
  public void addChild(Directory child) {
    child.setParent(this);
    children_directories.put(child.getName(), child);
  }

  /**
   * Calculates the full path of the current working directory
   * 
   * @return The full path of the working directory
   */
  public String calculateFullPAthOfDirectory() {
    String fullPath = this.getName() + "/";
    Directory parent = this.getParent();
    while (parent != null) {
      fullPath = parent.getName() + "/" + fullPath;
      parent = parent.getParent();
    }
    return fullPath;
  }

  /**
   * Removes a child directory from this directory
   * 
   * @param child The directory to be removed
   */
  public void removeChild(Directory child) {
    children_directories.remove(child.getName());
  }

  /**
   * Adds a file to be contained within this directory
   * 
   * @param file The file to be added
   */
  public void addFile(File file) {
    children_files.put(file.getName(), file);
  }

  /**
   * Sets the directory this directory is contained within
   * 
   * @param parent The new parent directory
   */
  private void setParent(Directory parent) {
    this.parent = parent;
  }

  /**
   * Removes a file contained within this directory
   * 
   * @param file The file to be removed
   */
  public void removeFile(File file) {
    children_files.remove(file.getName());
  }

  public boolean hasContent(String name) {
    if (this.children_directories.containsKey(name))
      return true;
    if (this.children_files.containsKey(name))
      return true;
    return false;
  }

  /**
   * Changes the parent directory for this directory
   * 
   * @param newParent The new parent directory
   */
  public void changeParent(Directory newParent) {
    if (parent != null) {
      parent.removeChild(this);
      parent = newParent;
      parent.addChild(this);
    }
  }

  public HashMap<String, Directory> getChildren() {
    return children_directories;
  }

  public HashMap<String, File> getFiles() {
    return children_files;
  }

  public String getName() {
    return name;
  }

  public Directory getParent() {
    return this.parent;
  }

  /**
   * Returns the content of this directory. This includes the child directories
   * as well as the files.
   */
  public String getContent() {
    String result = "";
    for (String child_dir : children_directories.keySet()) {
      result += child_dir + " \n";
    }
    for (String child_files : children_files.keySet()) {
      result += child_files + " \n";
    }
    return result;
  }

  /**
   * Copies content of another directory as a child. Overwriting old content
   * 
   * @param newCopiedDirectory Directory to be copied as child
   */
  public void copyDirectoryContentOf(Directory newCopiedDirectory) {
    // clears directory (we are overwriting)
    for (Map.Entry<String, Directory> entry : this.children_directories
        .entrySet()) {
      entry.getValue().deleteItself();
    }
    children_directories.clear();
    children_files.clear();

    // calls a recursive function to copy directory
    copyEntireDirectory(this, newCopiedDirectory);
  }

  /**
   * Copies an entire directory content recursively
   * 
   * @param base Directory that will be parent of new copied directory
   * @param originalDir Original directory that will be copied as child
   *        recursively
   */
  private void copyEntireDirectory(Directory base, Directory originalDir) {


    Directory newAddition = new Directory(base, originalDir.getName());
    base.addChild(newAddition);

    // for each file of originalDir creates a copy and adds to new directory
    // copy
    for (Map.Entry<String, File> entry : originalDir.children_files
        .entrySet()) {
      File file = new File(entry.getKey());
      file.setContent(entry.getValue().getContent());
      newAddition.addFile(file);
    }

    // for each directory in originalDir calls recursive function again to add
    // it to originalDir's copy
    for (Map.Entry<String, Directory> entry : originalDir.children_directories
        .entrySet()) {
      Directory child = entry.getValue();
      copyEntireDirectory(newAddition, child);
    }
  }

  /**
   * Determines if subDir is a subdirectory to this directory
   * 
   * @param subDir Directory to be searched as subdirectory
   * @return True if subDir is a subdirectory
   */
  public boolean hasSubDirectory(Content subDir) {
    if (this.findSubDirectory(this, subDir)) {
      return true;
    }
    return false;
  }

  /**
   * Searchs for a directory as a subdirectory of root
   * 
   * @param root Root directory of the search
   * @param subDir Directory to be searched for
   * @return True if subDir is found
   */
  private boolean findSubDirectory(Directory root, Content subDir) {
    // for each file of originalDir creates a copy and adds to base directory
    // copy
    for (Map.Entry<String, File> entry : root.children_files.entrySet()) {
      if (subDir == entry.getValue()) {
        return true;
      }
    }

    // for each directory in originalDir calls recursive function again to add
    // it to originalDir's copy
    for (Map.Entry<String, Directory> entry : root.children_directories
        .entrySet()) {
      if (subDir == entry.getValue()) {
        return true;
      } else {
        if (findSubDirectory(entry.getValue(), subDir)) {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * Deletes itself from the filesytem
   */
  public void deleteItself() {
    // cleans list of children files
    this.children_files.clear();

    // clears list of children directories
    for (Map.Entry<String, Directory> entry : this.children_directories
        .entrySet()) {
      Directory child = entry.getValue();
      child.setParent(null);
    }
    this.children_directories.clear();

    // removes itself
    if (parent != null) {
      parent.children_directories.remove(name);
      setParent(null);
    }
  }

  /**
   * Deletes a file from the directory
   * 
   * @param file File to be deleted
   */
  public void deleteFile(File file) {

    children_files.remove(file.getName());
  }

}
