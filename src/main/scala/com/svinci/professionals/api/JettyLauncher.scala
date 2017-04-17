package com.svinci.professionals.api

import com.svinci.professionals.api.infrastructure.Configuration
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.DefaultServlet
import org.eclipse.jetty.webapp.WebAppContext
import org.scalatra.servlet.ScalatraListener

/**
 * This is the MainClass of our application.
 */
object JettyLauncher extends App {

  /**
   * Server instantiation. We are retrieving the port we need to listen at from our Configuration object.
   */
  val server = new Server(Configuration.getInt("application.port"))

  /**
   * Web application context instantiation.
   * This class will hold the context path, the resource base, the event listener and one servlet.
   */
  val context = new WebAppContext()

  context setContextPath "/"
  context.setResourceBase("src/main/webapp")
  context.addEventListener(new ScalatraListener) // We use scalatra listener as event listener.
  context.addServlet(classOf[DefaultServlet], "/") // We don't need to add our servlets here, we're going to add them using the scalatra life cycle.

  server.setHandler(context) // We add the WebAppContext to the server.

  server.start() // Start the server.
  server.join() // Join the server's thread pool so the application doesn't quit.

}