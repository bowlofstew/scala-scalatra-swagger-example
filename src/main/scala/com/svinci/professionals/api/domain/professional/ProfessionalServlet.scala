package com.svinci.professionals.api.domain.professional

import com.svinci.professionals.api.infrastructure.ServletSupport

/**
 * We are using cake pattern to solve dependency injection without using any library. You can find a really good explanation of this pattern at http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth.
 *
 * As this is an entry point to our application, there is no need to create an interface (it's a servlet after all, so there are no functions exposed).
 */
trait ProfessionalServletComponent {

  /**
   * As defined by cake pattern, with self type annotations we are defining that any class that extends this trait, needs to extend ProfessionalRepositoryComponent too.
   * This makes the interface and instance defined by ProfessionalRepositoryComponent available in this trait.
   */
  this: ProfessionalRepositoryComponent =>

  /**
   * Professional servlet instantiation.
   */
  def professionalServletInstance: ProfessionalServlet = new ProfessionalServlet(professionalRepositoryInstance)

  /**
   * This is the scalatra servlet that will serve our professionals management endpoints.
   */
  class ProfessionalServlet(val repository: ProfessionalRepository) extends ServletSupport {

    /**
     * This value defines the documentation for this endpoint. We are giving the endpoint a name, the return type, a description/summary and the schema for the expected boy.
     */
    private[this] val createProfessional = apiOperation[Professional]("create") summary "Insert a professional in the system" parameter bodyParam[ProfessionalCreationRequest]
    /**
     * We are routing our creation endpoint to the root of this servlet, passing the api operation for swagger to document.
     */
    post("/", operation(createProfessional)) {
      val creationRequest = parsedBody.extract[ProfessionalCreationRequest]
      repository.create(creationRequest)
    }

    /**
     * This value defines the documentation for this endpoint. We are giving the endpoint a name, the return type, a description/summary and the specifications for the id path param.
     */
    private[this] val removeProfessional = apiOperation[Option[Professional]]("remove") summary "Remove a professional by its id." parameter pathParam[String]("Id of the professional to remove")
    /**
     * We are routing our removal endpoint to the root of this servlet with the id as a path param, passing the api operation for swagger to document.
     */
    delete("/:id", operation(removeProfessional)) {
      val professionalId = params('id)
      repository.remove(professionalId)
    }

    /**
     * This value defines the documentation for this endpoint. We are giving the endpoint a name, the return type, a description/summary and the specifications for the id path parameter.
     */
    private[this] val findById = apiOperation[Option[Professional]]("findById") summary "Find a professional by its id." parameter pathParam[String]("id of the professional you are looking for.")
    /**
     * We are routing our findById endpoint to the root of this servlet with the id as a path param, passing the api operation for swagger to document.
     */
    get("/:id", operation(findById)) {
      val professionalId = params('id)
      val authenticated = request.getAttribute("authenticated").asInstanceOf[Boolean]
      repository.findById(professionalId, authenticated)
    }

    /**
     * This value defines the documentation for this endpoint. We are giving the endpoint a name, the return type and a description/summary.
     */
    private[this] val all = apiOperation[List[Professional]]("allProfessionals") summary "Retrieve all professionals."
    /**
     * We are routing our all endpoint to the root of this servlet, passing the api operation for swagger to document.
     */
    get("/", operation(all)) {
      val authenticated = request.getAttribute("authenticated").asInstanceOf[Boolean]
      repository.all(authenticated)
    }

    /**
     * This is the description of this servlet, requested by swagger.
     */
    override protected def applicationDescription: String = "Professionals management API."

  }

}

/**
 * This is the default instance of ProfessionalServletComponent. Here we define that the ProfessionalServletComponent will use the InMemoryProfessionalRepositoryComponent.
 */
object DefaultProfessionalServletComponent extends ProfessionalServletComponent with InMemoryProfessionalRepositoryComponent
