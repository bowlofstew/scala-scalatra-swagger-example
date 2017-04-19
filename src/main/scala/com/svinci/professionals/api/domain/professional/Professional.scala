package com.svinci.professionals.api.domain.professional

import java.util.UUID

case class Job(name: String, description: String)

case class ContactInformation(phone: String, eMail: String, address: String)

case class Professional(id: String, name: String, age: Int, jobs: List[Job], contactInformation: Option[ContactInformation])

case class ProfessionalCreationRequest(name: String, age: Int, jobs: List[Job], contactInformation: ContactInformation) {

  def toProfessional: Professional = Professional(
    id                 = UUID.randomUUID().toString,
    name               = name,
    age                = age,
    jobs               = jobs,
    contactInformation = Option(contactInformation)
  )

}