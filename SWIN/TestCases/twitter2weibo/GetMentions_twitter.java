package twitter4j.examples.timeline;

import twitter4j.*;
//import weibo4j.Weibo;
//import weibo4j.WeiboException;
import java.util.List;

public class GetMentions {
    public static void main(String[] args) {
        Twitter twitter = new TwitterFactory().getInstance();
        try {
            User user = twitter.verifyCredentials();
            List<Status> statuses = twitter.getMentionsTimeline();
            System.out.println("Showing @" + user.getScreenName() + "'s mentions.");
            for (Status status : statuses) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to get timeline: " + te.getMessage());
            System.exit(-1);
        }
    }
}
