package org.xydata

import com.typesafe.config.ConfigFactory
import twitter4j._
import twitter4j.conf.{Configuration, ConfigurationBuilder}

object TwitterStream {

  private val conf = ConfigFactory.load()

  private val getTwitterConf: Configuration = {
    new ConfigurationBuilder()
      .setOAuthConsumerKey(conf.getString("twitter.consumerKey"))
      .setOAuthConsumerSecret(conf.getString("twitter.consumerSecret"))
      .setOAuthAccessToken(conf.getString("twitter.accessToken"))
      .setOAuthAccessTokenSecret(conf.getString("twitter.accessTokenSecret"))
      .build()
  }


  def getStream = new TwitterStreamFactory(getTwitterConf).getInstance()

  class OnTweetPosted(cb: Status => Unit) extends StatusListener {

    override def onStatus(status: Status): Unit = cb(status)

    override def onException(ex: Exception): Unit = throw ex

    // no-op for the following events
    override def onStallWarning(warning: StallWarning): Unit = {}

    override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice): Unit = {}

    override def onScrubGeo(userId: Long, upToStatusId: Long): Unit = {}

    override def onTrackLimitationNotice(numberOfLimitedStatuses: Int): Unit = {}
  }


}
