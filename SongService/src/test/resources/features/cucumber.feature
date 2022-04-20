Feature: Testing a song services
  KafkaListener received message with songDTO by topic name, and use songService
  for create songmetadata from songDTO and save it in database

  Scenario: Listener received songDTO
    When songservice extract songmetadata from songDTO
    Then usermetadata from songDTO convert into JSON format
    Then created metadataDTO save to database