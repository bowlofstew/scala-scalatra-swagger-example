package com.svinci.professionals.api.domain.status

/**
 * We are using cake pattern to solve dependency injection without using any library. You can find a really good explanation of this pattern at http://www.cakesolutions.net/teamblogs/2011/12/19/cake-pattern-in-depth.
 *
 * This is the component that defines a StatusService interface (trait, actually), and names an instance of it.
 */
trait StatusServiceComponent {

  /**
   * This is the definition of the instance of StatusService an implementation of this component will hold.
   */
  def statusServiceInstance: StatusService

  /**
   * StatusService interface definition.
   */
  trait StatusService {

    /**
     * Retrieve the application status.
     * @return The application status.
     */
    def status: Status

  }

}

/**
 * Default StatusServiceComponent implementation.
 */
trait DefaultStatusServiceComponent extends StatusServiceComponent {

  /**
   * Here, we define how the DefaultStatusService is created.
   */
  override def statusServiceInstance: StatusService = new DefaultStatusService

  /**
   * Default StatusService implementation.
   */
  class DefaultStatusService extends StatusService {

    /**
     * @inheritdoc
     */
    override def status: Status = Status(healthy = true)

  }

}