package edu.knoldus

import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class TwitterFeedTest extends FunSuite {

  val twitter = new TwitterFeed

  test("Should get tweets for hashTag") {
    val tweets = Await.result(twitter.getTweets("#Scala"), 10.second)
    val actual = tweets.size
    val expected = 100
    assert(actual == expected)
  }

  test("Should give right hashTag") {
    intercept[Exception] {
      Await.result(twitter.getTweets(""), 10.second)
    }
  }

  test("Should get average of likes") {
    val tweets = Await.result(twitter.getLikes("#Scala"), 10.second)
    val actual = tweets
    val expected = 0
    assert(actual >= expected)
  }

  test("Should give right hashTag for likes") {
    intercept[Exception] {
      Await.result(twitter.getLikes(""), 10.second)
    }
  }


  test("Should get average of retweets") {
    val tweets = Await.result(twitter.getLikes("#Scala"), 10.second)
    val actual = tweets
    val expected = 0
    assert(actual >= expected)
  }

  test("Should give right hashTag for retweets") {
    intercept[Exception] {
      Await.result(twitter.getRetweets(""), 10.second)
    }
  }

}

