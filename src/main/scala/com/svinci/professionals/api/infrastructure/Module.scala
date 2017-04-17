package com.svinci.professionals.api.infrastructure

import com.svinci.professionals.api.domain.docs.DocsServlet
import com.svinci.professionals.api.domain.status.DefaultStatusServletComponent

/**
 * We are using cake pattern to solve dependency injection without using any library. You can find a really good explanation of this pattern at http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth.
 *
 * In this object we'll hold all the instances required by our application.
 */
object Module {

  /**
   * Default instance of StatusServlet.
   */
  def statusServlet: DefaultStatusServletComponent.StatusServlet = DefaultStatusServletComponent.statusServletInstance

  /**
   * Swagger documentation servlet instance.
   */
  def docsServlet: DocsServlet = new DocsServlet

}
