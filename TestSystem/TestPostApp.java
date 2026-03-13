import messageboard.*;
import java.io.IOException;

public class TestPostApp {

    public static void main(String[] args) {

        Post post = new Post(
            "Alex Adam",
            "Help with Java",
            "Hi, could anyone help me I need to learn how to code in java!"
        );

        System.out.println(post.toFormattedString());

        try {

            post.saveAsTextFile("mypost.txt");

        } catch(IOException ex) {

            System.out.println("File not saved.");
            ex.printStackTrace();

        }
    }
}