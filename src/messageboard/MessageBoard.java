package messageboard;
import java.util.*;
import java.time.LocalDate;
import java.io.*;

public class MessageBoard implements MessageBoardInterface, Serializable {

    private List<Post> posts;
    private String boardName;

    public MessageBoard(String boardName) {
        this.boardName = boardName;
        this.posts = new ArrayList<>();
    }

    public String getBoardName() {
        return boardName;
    }

    public int[] getPostIDs() {

        int[] postIDs = new int[posts.size()];
        int i = 0;

        for (Post post : posts) {
            postIDs[i++] = post.getPostID();
        }

        return postIDs;
    }

    public int getPostIndex(int postID) throws IDInvalidException {

        for (int i = 0; i < posts.size(); i++) {

            if (posts.get(i).getPostID() == postID) {
                return i;
            }

        }

        throw new IDInvalidException("Invalid post ID.");
    }

    public int addPost(String author, String subject, String message) {

        Post p = new Post(author, subject, message);

        posts.add(p);

        return p.getPostID();
    }

    public String getFormattedPost(int postID) throws IDInvalidException {

        int index = getPostIndex(postID);

        return posts.get(index).toFormattedString();
    }

    public void deletePost(int postID) throws IDInvalidException {

        int index = getPostIndex(postID);

        posts.remove(index);
    }

    public int[] searchPostsBySubject(String keyword) {

        List<Integer> matches = new ArrayList<>();

        for (Post p : posts) {

            if (p.getSubject().toLowerCase().contains(keyword.toLowerCase())) {
                matches.add(p.getPostID());
            }

        }

        int[] result = new int[matches.size()];

        for (int i = 0; i < matches.size(); i++) {
            result[i] = matches.get(i);
        }

        return result;
    }

    public int addPost(String author, String subject, String message, int epochDate) {

        LocalDate date = LocalDate.ofEpochDay(epochDate);

        Post p = new Post(author, subject, message, date);

        posts.add(p);

        return p.getPostID();
    }

    public int[] searchPostsByDate(int startDate, int endDate) {

        List<Integer> matches = new ArrayList<>();

        for (Post p : posts) {

            if (p.getDate() >= startDate && p.getDate() <= endDate) {
                matches.add(p.getPostID());
            }

        }

        int[] result = new int[matches.size()];

        for (int i = 0; i < matches.size(); i++) {
            result[i] = matches.get(i);
        }

        return result;
    }

    public void saveMessageBoard(String filename) throws IOException {

        ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(filename));

        out.writeObject(boardName);

        Post[] postArray = posts.toArray(new Post[posts.size()]);

        out.writeObject(postArray);

        out.close();
    }

    public void loadMessageBoard(String filename)
            throws IOException, ClassNotFoundException {

        ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(filename));

        boardName = (String) in.readObject();

        Post[] postArray = (Post[]) in.readObject();

        posts = new ArrayList<>(Arrays.asList(postArray));

        in.close();
    }

    public void savePostAsTextFile(int postID, String filename)
            throws IDInvalidException, IOException {

        int index = getPostIndex(postID);

        posts.get(index).saveAsTextFile(filename);
    }
}