package com.svinci.professionals.api.infrastructure

import java.util.{ Date, UUID }
import javax.servlet.http.HttpServletRequest

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
   * From the servlet request, check if the client is authenticated.
   *
   * This function can be exposed as protected as is, and let the servlets that extend ServletSupport use it,
   * but that wouldn't be convenient in a real situation, as checking if an authentication token is actually valid
   * would have performance implications (query to a database, REST API call, etc.).
   */
  private[this] def isAuthenticated()(implicit request: HttpServletRequest): Boolean = {
    val authenticationToken: Option[String] = Option(request.getHeader("X-PROFESSIONALS-API-TOKEN"))

    // Here you may perform token validation calling an authentication service or something.
    // We'll keep it simple in this example, and we'll assume the client is authenticated if the header is present.
    authenticationToken.isDefined
  }

  /**
   * Before every request made to a servlet that extends this trait, the function passed to `before()` will be executed.
   * We are using this to :
   *   - Set the Content-Type header for every request, as we are always going to return JSON.
   *   - Set the date to the request, so we can calculate spent time afterwards.
   *   - Generate a transaction identifier, an add it to the MDC, so we know which lines of logs were triggered by which request.
   *   - Check if the client is authenticated and add the flag to the request attributes.
   *   - Log that a request arrived.
   */
  before() {

    contentType = "application/json"
    request.setAttribute("startTime", new Date().getTime)
    MDC.put("tid", UUID.randomUUID().toString.substring(0, 8))

    val authenticated = isAuthenticated()
    request.setAttribute("authenticated", authenticated)

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
