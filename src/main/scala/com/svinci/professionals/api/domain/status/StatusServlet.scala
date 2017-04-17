package com.svinci.professionals.api.domain.status

import com.svinci.professionals.api.infrastructure.ServletSupport

/**
 * We are using cake pattern to solve dependency injection without using any library. You can find a really good explanation of this pattern at http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth.
 *
 * As this is an entry point to our application, there is no need to create an interface (it's a servlet after all, so there are no functions exposed).
 */
trait StatusServletComponent {

  /**
   * As defined by cake pattern, with self type annotations we are defining that any class that extends this trait, needs to extend StatusServiceComponent too.
   * This makes the interface and instance defined by StatusServiceComponent available in this trait.
   */
  this: StatusServiceComponent =>

  /**
   * This is the StatusServlet instance held by this component. Notice that we are instantiating StatusServlet passing the statusServiceInstance provided by StatusServiceComponent.
   */
  def statusServletInstance: StatusServlet = new StatusServlet(statusService = statusServiceInstance)

  /**
   * This is the scalatra servlet that will serve our status endpoint.
   */
  class StatusServlet(val statusService: StatusService) extends ServletSupport {

    /**
     * This value defines the documentation for this endpoint. We are giving the endpoint a name, the return type and a description/summary.
     */
    private[this] val getStatus = apiOperation[Status]("status") summary "Retrieve API status."

    /**
     * We are routing our status endpoint to the root of this servlet, and passing to scalatra our apiOperation.
     */
    get("/", operation(getStatus)) {
      statusService.status
    }

    /**
     * This is the description of this servlet, requested by swagger.
     */
    override protected def applicationDescription: String = "API Status."

  }

}

/**
 * This is the default instance of StatusServletComponent. Here we define that the StatusServletComponent will use the DefaultStatusServiceComponent.
 */
object DefaultStatusServletComponent extends StatusServletComponent with DefaultStatusServiceComponent
