import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import java.util.List;

class NoF {public NoF() { }};
public class GetUserTimeline {
    public static void main(java.lang.String[] args) {
        Weibo twitter = new Weibo();
        try {
            java.util.List<Status> statuses;
            String user;
            if (args.length == 1) {
                user = args[0];
                statuses = twitter.getUserTimeline(String.valueOf(user),new Paging());
            } else {
                user = twitter.verifyCredentials().getScreenName();
                statuses = twitter.getUserTimeline(); }
            java.lang.System.out.println("Showing @" + user + "\'s user timeline.");
            for (Status status : statuses) {
                java.lang.System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()); }
        }
        catch (WeiboException te) {
            te.printStackTrace();
            java.lang.System.out.println("Failed to get timeline: " + te.getMessage());
            java.lang.System.exit(-1);
        }
    }
    
    public GetUserTimeline() { super(); }
}
