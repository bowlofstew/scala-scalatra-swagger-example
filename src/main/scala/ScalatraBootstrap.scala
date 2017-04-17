import javax.servlet.ServletContext

import com.svinci.professionals.api.infrastructure.Module
import org.scalatra._
import org.slf4j.LoggerFactory

/**
 * This class in looked up by scalatra and automatically instantiated. Here we need to code all the start up code of our API.
 */
class ScalatraBootstrap extends LifeCycle {

  private[this] val logger = LoggerFactory.getLogger(getClass)

  /**
   * In the method we need to mount our servlets to the ServletContext provided as parameter.
   * Any additional startup code should be wrote here too (warm up, scheduled tasks initialization, etc.).
   */
  override def init(context: ServletContext) {

    logger.info(context.getContextPath)

    logger.info("Mounting Changas API servlets.")
    context.mount(Module.statusServlet, "/professionals-api/status", "status")
    context.mount(Module.docsServlet, "/docs", "docs")
    logger.info(s"API started.")

  }

}
