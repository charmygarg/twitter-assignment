package edu.knoldus

import com.typesafe.config.ConfigFactory
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TwitterFeed {

  val count = 100

  /**
    * getTweets Method to retrieve all the tweets related to the #hashTag
    */
  @throws(classOf[Exception])
  def getTweets(search: String): Future[List[MyTweets]] = Future {
    try {
      val query = new Query(search)
      query.setSince("2016-01-01")
      query.setCount(count)
      val list = TwitterFeed.twitter.search(query)
      val tweets = list.getTweets.asScala.toList
      val allTweets = tweets.map {
        tweet =>
          MyTweets(tweet.getText, tweet.getUser.getScreenName, tweet.getCreatedAt)
      }
      allTweets.sortBy(_.date)
    }
    catch {
      case exception: Exception => {
        throw exception
      }
    }
  }

  /**
    * getLikes method to get average of likes per tweet
    */
  @throws(classOf[Exception])
  def getLikes(search: String): Future[Int] = Future {
    try {
      val query = new Query(search)
      query.setSince("2016-01-01")
      query.setCount(count)
      val list = TwitterFeed.twitter.search(query)
      val tweets = list.getTweets.asScala.toList
      val likesCount = tweets.map(_.getFavoriteCount)
      likesCount.size / tweets.size
    }
    catch {
      case exception: Exception => {
        throw exception
      }
    }
  }

  /**
    * getRetweets method to get average of retweets per tweet
    */
  @throws(classOf[Exception])
  def getRetweets(search: String): Future[Int] = Future {
    try {
      val query = new Query(search)
      query.setSince("2016-01-01")
      query.setCount(count)
      val list = TwitterFeed.twitter.search(query)
      val tweets = list.getTweets.asScala.toList
      val retweetCount = tweets.map(_.getRetweetCount)
      retweetCount.size / tweets.size
    }
    catch {
      case exception: Exception => {
        throw exception
      }
    }
  }

}


object TwitterFeed {

  val conf = ConfigFactory.load("application.conf")
  val consumerKey = conf.getString("my.secret.consumerKey")
  val consumerSecretKey = conf.getString("my.secret.consumerSecretKey")
  val accessToken = conf.getString("my.secret.accessToken")
  val accessTokenSecret = conf.getString("my.secret.accessTokenSecret")
  val configurationBuilder = new ConfigurationBuilder()
  configurationBuilder.setDebugEnabled(false)
    .setOAuthConsumerKey(consumerKey)
    .setOAuthConsumerSecret(consumerSecretKey)
    .setOAuthAccessToken(accessToken)
    .setOAuthAccessTokenSecret(accessTokenSecret)
  val twitter: twitter4j.Twitter = new TwitterFactory(configurationBuilder.build()).getInstance()

}
