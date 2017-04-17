package com.svinci.professionals.api.infrastructure

import org.scalatra.swagger.{ ApiInfo, Swagger }

/**
 * Information of our API as a whole.
 */
object ProfessionalsApiInfo extends ApiInfo(
  title             = "professionals-api",
  description       = "Professionals CRUD operations.",
  termsOfServiceUrl = "some terms of service URL",
  contact           = "some contact information",
  license           = "MIT",
  licenseUrl        = "http://opensource.org/licenses/MIT"
)

/**
 * Swagger instance for our API. It's defined  as an object so we have only one instance for all our resources.
 */
object ProfessionalsApiSwagger extends Swagger(swaggerVersion = Swagger.SpecVersion, apiVersion = "1.0.0", apiInfo = ProfessionalsApiInfo)