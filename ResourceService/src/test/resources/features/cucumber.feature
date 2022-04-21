Feature: Testing a song services storage and extract metadata
  KafkaListener received message with songDTO by topic name, and use songService
  for create songmetadata from songDTO and save it in database

  Scenario: User upload audio file in app
    When audio file upload in server
    Then It send by kafka and upload audio in aws server
#    Then usermetadata from songDTO convert into JSON format
#    Then created metadataDTO save to database