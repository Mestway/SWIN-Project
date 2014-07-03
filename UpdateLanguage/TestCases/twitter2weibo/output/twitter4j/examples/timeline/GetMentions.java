package twitter4j.examples.timeline;

import twitter4j.*;
import java.util.List;

class NoF {public NoF() { }};
public class GetMentions {
    public static void main(java.lang.String[] args) {
        Weibo twitter = new Weibo();
        try {
            User user = twitter.verifyCredentials();
            java.util.List<Status> statuses = twitter.getMentions();
            java.lang.System.out.println("Showing @" + user.getScreenName() + "\'s mentions.");
            for (Status status : statuses) {
                java.lang.System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText()); }
        }
        catch (WeiboException te) {
            te.printStackTrace();
            java.lang.System.out.println("Failed to get timeline: " + te.getMessage());
            java.lang.System.exit(-1);
        }
    }
    
    public GetMentions() { super(); }
}
