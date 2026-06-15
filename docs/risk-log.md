# Risk Log – ClinicQueue

| ID | Risc | Probabilitate | Impact | Nivel | Strategie de răspuns | Owner |
|---|---|---|---|---|---|---|
| R1 | Cerințele sunt interpretate greșit. | Medie | Mare | Ridicat | Se verifică enunțul și se mapează fiecare cerință la un livrabil. | Student |
| R2 | Pipeline-ul CI nu rulează corect. | Medie | Mare | Ridicat | Se testează scripturile local și se verifică YAML-ul. | Student |
| R3 | Testele unitare sunt insuficiente. | Medie | Mediu | Mediu | Se scriu teste pentru creare, validare, căutare, anulare și reprogramare. | Student |
| R4 | Analiza statică produce erori înainte de predare. | Medie | Mediu | Mediu | Se rulează `scripts/static-analysis.sh` înainte de upload. | Student |
| R5 | Deployment-ul este confundat cu o lansare reală în producție. | Mică | Mediu | Scăzut | Se explică faptul că este un deployment demonstrativ prin copiere de fișiere. | Student |
| R6 | Proiectul nu este trimis cu minimum 3 zile înainte de examen. | Medie | Mare | Ridicat | Se finalizează repository-ul și documentația înainte de termen. | Student |
| R7 | Profesorii nu au acces la repository dacă este privat. | Medie | Mare | Ridicat | Se oferă acces evaluatorilor înainte de predare. | Student |
| R8 | Funcționalitatea este prea simplă și pare fără business real. | Mică | Mediu | Scăzut | Tema este formulată ca aplicație pentru cabinet medical, cu flux real de programări. | Student |
