package com.svinci.professionals.api.domain.docs

import com.svinci.professionals.api.infrastructure.ProfessionalsApiSwagger
import org.scalatra.ScalatraServlet
import org.scalatra.swagger.NativeSwaggerBase

/**
 * This servlet, as is, will be able to return swagger v1 JSONs. This is the entry point to our documentation.
 */
class DocsServlet extends ScalatraServlet with NativeSwaggerBase {

  /**
   * Application swagger global instance.
   */
  override protected implicit def swagger = ProfessionalsApiSwagger

}