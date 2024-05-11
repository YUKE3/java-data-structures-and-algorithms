/**
 * @author
 * Yugui Ke
 * 113778667
 * Recitation 3
 * yugui.ke@stonybrook.edu
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BashTerminal {
    /**
     * Main method.
     * @param args Not used.
     * @throws IOException Shouldn't happen.
     */
   public static void main(String[] args) throws IOException {
       InputStreamReader inputStream = new InputStreamReader(System.in);
       BufferedReader reader = new BufferedReader(inputStream);

       DirectoryTree tree = new DirectoryTree();

       while (true) {
           System.out.print("[YUKE@host]: $ ");

           String userCommand = reader.readLine();

           // All commands that don't have arguments.
           // Or have pre-defined answers.
           if (userCommand.equals("exit")) {
               System.out.println("Program terminating normally.");
               break;
           } else if (userCommand.equals("ls")) {
               System.out.println(tree.listDirectory());
           } else if (userCommand.equals("ls -R")) {
               tree.printDirectoryTree();
           } else if (userCommand.equals("pwd")) {
               System.out.println(tree.presentWorkingDirectory());
           } else if (userCommand.equals("cd /")) {
               tree.resetCursor();
           } else {

               String[] commands = userCommand.split("\\s");

               if (commands.length == 2) {
                   if (commands[0].equals("cd")) {
                       try {
                           tree.changeDirectory(commands[1]);
                       } catch (NotADirectoryException e) {
                           System.out.println(e.getMessage());
                       }
                   } else if (commands[0].equals("mkdir")) {
                       try {
                           tree.makeDirectory(commands[1]);
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                   } else if (commands[0].equals("touch")) {
                       try {
                           tree.makeFile(commands[1]);
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                   } else if (commands[0].equals("find")) {
                       System.out.print(tree.findNode(commands[1]));
                   } else {
                       System.out.println("ERROR: Invalid command.");
                   }
               } else {
                   if (commands[0].equals("mv") && commands.length == 3) {
                       try {
                           tree.moveNode(commands[1], commands[2]);
                       } catch (Exception e) {
                           System.out.println(e.getMessage());
                       }
                   } else {
                       System.out.println("ERROR: Invalid command.");
                   }
               }
           }
       }
   }
}
