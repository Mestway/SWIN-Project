import twitter4j.*;
import java.util.List;

class NoF {public NoF() { }};
public class GetHomeTimeline {
    public static void main(java.lang.String[] args) {
        try {
            Weibo twitter = new Weibo();
            User user = twitter.verifyCredentials();
            java.util.List<Status> statuses = twitter.getHomeTimeline();
            java.lang.System.out.println("Showing @" + user.getScreenName() + "\'s home timeline.");
            for (Status status : statuses) {
                java.lang.System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()); }
        }
        catch (WeiboException te) {
            te.printStackTrace();
            java.lang.System.out.println("Failed to get timeline: " + te.getMessage());
            java.lang.System.exit(-1);
        }
    }
    
    public GetHomeTimeline() { super(); }
}
