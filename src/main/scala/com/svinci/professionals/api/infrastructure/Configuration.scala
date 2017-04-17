package com.svinci.professionals.api.infrastructure

import com.typesafe.config.ConfigFactory

/**
 * This will provide a singleton instance of our configuration.
 * Also, it will encapsulate typesafe.config, so the rest of the application doesn't need to know about the configuration library we are using.
 */
object Configuration {

  /**
   * This is our configuration instance. Private and immutable.
   */
  private[this] val configuration = ConfigFactory.load()

  /**
   * Methods like this one should be defined to access any type of configuration from its key.
   * The reason we do it is to define an interface that makes sense for our application, and make the rest of the code
   * agnostic to what library we are using. Just a good practice.
   * @param key Configuration key.
   * @return The configured Int.
   */
  def getInt(key: String): Int = configuration.getInt(key)

}
