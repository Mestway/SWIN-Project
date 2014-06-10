//omitting namespaces
//in total : 16

//HomeTimeline
{
	() 
	[ TwitterFactory().getInstance():Twitter
        -> (new Weibo()):Weibo ]
}

{
	() 
	[ TwitterFactory().getSingleton():Twitter
            -> (new Weibo()):Weibo ]
}

{
	(x: Twitter ->> Weibo) 
	[ x.verifyCredentials():User
         -> x.verifyCredentials():User ]
}

{
	(x: Twitter ->> Weibo) 
	[ x.getHomeTimeline():List<Status>
           -> x.getHomeTimeline():List<Status> ]
}


{
	(x: User ->> User) 
	[ x.getScreenName():String
     -> x.getScreeName():String ]
}

{
	(x: Status ->> Status) 
	[ x.getText():String
        -> x.getText():String ]
}

{
	(x: Status ->> Status) 
	[ x.getUser():User
        -> x.getUser():User ]
}

{
	() 
	[ (new TwitterException()):TwitterException
      -> (new WeiboException()):WeiboException ]
}


//MentionsTimeline
{
	(x: Twitter ->> Weibo) 
	[ x.getMentionsTimeline():List<Status>
        -> x.getMentionsTimeline():List<Status> ]
}

//UserTimeline
{
	(x: Twitter ->> Weibo) 
	[ x.getUserTimeline():List<Status>
         -> x.getUserTimeline():List<Status> ]
}

{
	() 
	[ (new ResponseList()):ResponseList
        -> (new List()):List ]
}

{
	(x: Twitter ->> Weibo, y: long ->> long, z: Paging ->> Paging)
    [ x.getUserTimeline(y, z):ResponseList
                              -> x.getUserTime(String.valueOf(y), z):List ]
}

{
	(x: Twitter ->> Weibo, y: String ->> String)
    [ x.getUserTimeline(y):ResponseList
                           -> x.getUserTimeline(y, new Paging()):List ]
}

{
	(x: Twitter ->> Weibo, y: long ->> long)
    [ x.getUserTimeline(y):ResponseList
                           -> x.getUserTimeline(String.valueOf(y), new Paging()):List ]
}

{
	(x: Twitter ->> Weibo)
    [ x.getMentionsTimeline():ResponseList
                              -> x.getMentions():List ]
}

{
	(x: Twitter ->> Weibo, y: Paging ->> Paging)
    [ x.getMentionsTimeline(y):ResponseList
            -> x.getMentions(y):List ]
}
