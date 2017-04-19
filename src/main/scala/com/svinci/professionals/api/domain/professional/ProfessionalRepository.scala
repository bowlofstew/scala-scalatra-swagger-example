package com.svinci.professionals.api.domain.professional

import scala.collection.concurrent.TrieMap

/**
 * We are using cake pattern to solve dependency injection without using any library. You can find a really good explanation of this pattern at http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth.
 *
 * This component holds the interface for professionals repository.
 */
trait ProfessionalRepositoryComponent {

  /**
   * Professional repository instance definition.
   */
  def professionalRepositoryInstance: ProfessionalRepository

  /**
   * Professional repository interface.
   */
  trait ProfessionalRepository {

    /**
     * Receives a creation request, transforms it to a Professional and stores it. Returns the created professional.
     */
    def create(professionalCreationRequest: ProfessionalCreationRequest): Professional

    /**
     * Removes the professional that has the given id. Returns the removed professional if any.
     */
    def remove(professionalId: String): Option[Professional]

    /**
     * Returns the professional that has the given id if any.
     */
    def findById(professionalId: String, displayContactInformation: Boolean): Option[Professional]

    /**
     * Returns a list with all the stored professionals.
     */
    def all(displayContactInformation: Boolean): List[Professional]

  }

}

/**
 * This component defines an implementation of ProfessionalRepository which holds the data in memory.
 */
trait InMemoryProfessionalRepositoryComponent extends ProfessionalRepositoryComponent {

  /**
   * In memory professional repository instantiation.
   */
  override def professionalRepositoryInstance: ProfessionalRepository = new InMemoryProfessionalRepository

  /**
   * In memory implementation of a professional repository.
   */
  class InMemoryProfessionalRepository extends ProfessionalRepository {

    /**
     * This is the data structure chosen to store the professionals.
     * In this Map you'll find professionals indexed by their ids.
     * TrieMap was chosen to make this class thread safe.
     */
    private[this] val storage: TrieMap[String, Professional] = TrieMap()

    /**
     * @inheritdoc
     */
    override def create(professionalCreationRequest: ProfessionalCreationRequest): Professional = {
      val professional = professionalCreationRequest.toProfessional
      storage.put(professional.id, professional)
      professional
    }

    /**
     * @inheritdoc
     */
    override def remove(professionalId: String): Option[Professional] = storage.remove(professionalId)

    /**
     * @inheritdoc
     */
    override def findById(professionalId: String, displayContactInformation: Boolean): Option[Professional] = {
      val maybeProfessional = storage.get(professionalId)

      if (displayContactInformation) {
        return maybeProfessional
      }

      maybeProfessional.map(professional => professional.copy(contactInformation = None))
    }

    /**
     * @inheritdoc
     */
    override def all(displayContactInformation: Boolean): List[Professional] = {
      val allProfessionals = storage.values.toList

      if (displayContactInformation) {
        return allProfessionals
      }

      allProfessionals.map(professional => professional.copy(contactInformation = None))
    }

  }

}