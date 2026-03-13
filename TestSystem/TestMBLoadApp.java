import messageboard.*;
import java.io.*;

public class TestMBLoadApp {

    public static void main(String[] args) {

        MessageBoard board = new MessageBoard("Coding Support");

        try {

            board.loadMessageBoard("codingsupport.ser");

        } catch (IOException ex) {

            System.out.println("Board not loaded.");
            ex.printStackTrace();

        } catch (ClassNotFoundException ex) {

            System.out.println("Could not find class.");
            ex.printStackTrace();

        }

        int[] ids = board.getPostIDs();

        for (int id : ids) {
            try {
                System.out.println(board.getFormattedPost(id));
            } catch (IDInvalidException ex) {
                System.out.println("Invalid post ID.");
                ex.printStackTrace();
            }
        }

        int[] results = board.searchPostsBySubject("windows");

        if (results.length > 0) {

            try {

                board.savePostAsTextFile(results[0], "windowspost.txt");

            } catch (IOException ex) {

                System.out.println("File not saved.");
                ex.printStackTrace();

            } catch (IDInvalidException ex) {

                System.out.println("Invalid post ID.");
                ex.printStackTrace();
            }
        }
    }
}