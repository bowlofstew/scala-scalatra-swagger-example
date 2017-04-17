package com.svinci.professionals.api.infrastructure

import java.util.{ Date, UUID }

import org.json4s.{ DefaultFormats, Formats }
import org.scalatra._
import org.scalatra.json._
import org.scalatra.swagger.SwaggerSupport
import org.slf4j.{ LoggerFactory, MDC }

/**
 * We'll have a couple servlets probably (a status endpoint, the CRUD servlet for our professionals, and if we deploy this to production we'll probably add some more),
 * so it's convenient to have the things every servlet will need to define in one trait to extend it.
 *
 * This trait extends ScalatraServlet and adds json and swagger support.
 */
trait ServletSupport extends ScalatraServlet with JacksonJsonSupport with SwaggerSupport {

  /**
   * As we are going to document every endpoint of our API, we'll need our swagger instance in everyone of our servlets.
   */
  override protected implicit def swagger = ProfessionalsApiSwagger

  /**
   * This is a logger... to log stuff.
   */
  private[this] val logger = LoggerFactory.getLogger(getClass)
  /**
   * Scalatra requires us to define an implicit Formats instance for it to know how we want JSONs to be serialized/deserialized.
   * It provides a DefaultFormats that fill all our needs today, so we'll use it.
   */
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  /**
   * Before every request made to a servlet that extends this trait, the function passed to `before()` will be executed.
   * We are using this to :
   *   - Set the Content-Type header for every request, as we are always going to return JSON.
   *   - Set the date to the request, so we can calculate spent time afterwards.
   *   - Generate a transaction identifier, an add it to the MDC, so we know which lines of logs were triggered by which request.
   *   - Log that a request arrived.
   */
  before() {

    contentType = "application/json"
    request.setAttribute("startTime", new Date().getTime)
    MDC.put("tid", UUID.randomUUID().toString.substring(0, 8))

    logger.info(s"Received request ${request.getMethod} at ${request.getRequestURI}")

  }

  /**
   * NotFound handler. We just want to set the status code, and avoid the huge stack traces scalatra returns in the body.
   */
  notFound {

    response.setStatus(404)

  }

  /**
   * After every request made to a servlet that extends this trait, the function passed to `after()` will be executed.
   * We are using this to:
   *   - Retrieve the start time added in the `before()` handler an calculate how much time the API took to respond.
   *   - Log that the request handling finished, with how much time it took.
   */
  after() {

    val startTime: Long = request.getAttribute("startTime").asInstanceOf[Long]
    val spentTime: Long = new Date().getTime - startTime
    logger.info(s"Request ${request.getMethod} at ${request.getRequestURI} took ${spentTime}ms")

  }

}
