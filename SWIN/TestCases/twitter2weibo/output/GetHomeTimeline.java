import twitter4j.*;
import java.util.List;

public class GetHomeTimeline {
    public static void main(java.lang.String[] args) {
        try {
            Weibo twitter = new Weibo();
            User user = twitter.verifyCredentials();
            java.util.List<twitter4j.Status> statuses = twitter.getHomeTimeline();
            java.lang.System.out.println("Showing @" + user.getScreeName() + "\'s home timeline.");
            for (twitter4j.Status status : statuses) {
                java.lang.System.out.println("@" + status.getUser().getScreeName() + " - " + status.getText()); }
        }
        catch (WeiboException te) {
            te.printStackTrace();
            java.lang.System.out.println("Failed to get timeline: " + te.getMessage());
            java.lang.System.exit(-1);
        }
    }
    
    public GetHomeTimeline() { super(); }
}
