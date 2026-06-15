# Project Charter – ClinicQueue

## 1. Titlul proiectului

ClinicQueue – aplicație pentru gestionarea programărilor la un cabinet medical.

## 2. Scopul proiectului

Scopul proiectului este dezvoltarea unei aplicații Java simple care permite gestionarea programărilor într-un cabinet medical mic. Proiectul urmărește atât implementarea funcționalității de bază, cât și configurarea unei infrastructuri tehnice minimale: source control, unit testing, continuous integration, static code analysis și deployment simplu.

## 3. Justificare business

Un cabinet medical are nevoie de o metodă simplă de organizare a programărilor. O aplicație dedicată reduce riscul de suprapuneri, ajută la găsirea rapidă a programărilor și oferă o bază pentru extindere ulterioară.

## 4. Obiective SMART

| Obiectiv | Descriere |
|---|---|
| Specific | Aplicația gestionează programări medicale cu pacient, serviciu și dată/oră. |
| Măsurabil | Sunt implementate minimum 5 funcționalități și minimum 6 teste unitare. |
| Accesibil | Funcționalitatea este redusă și poate fi realizată într-un proiect educațional. |
| Relevant | Tema are business real minimal. |
| Încadrat în timp | Proiectul poate fi finalizat înainte de termenul de predare. |

## 5. Livrabile

- cod sursă Java;
- teste unitare;
- workflow GitHub Actions;
- script de build automat;
- script de testare automată;
- script de analiză statică;
- script de deployment simplu;
- documente PM: SWOT, requirements, planning, risk log;
- README cu instrucțiuni de rulare.

## 6. Stakeholderi

| Rol | Responsabilitate |
|---|---|
| Student / dezvoltator | Analiză, implementare, testare, documentare. |
| Profesor evaluator | Verificarea proiectului și a prezentării. |
| Administrator cabinet | Utilizator ipotetic al aplicației. |
| Pacient | Beneficiar indirect al programărilor corecte. |

## 7. Scope inclus

- creare programare;
- listare programări;
- căutare după pacient;
- anulare programare;
- reprogramare;
- validare dată viitoare;
- prevenirea suprapunerii programărilor;
- CI și deployment simplu.

## 8. Scope exclus

- interfață grafică;
- bază de date;
- autentificare;
- notificări prin email/SMS;
- integrare cu sisteme medicale reale.

## 9. Riscuri principale

- configurare greșită CI;
- testare insuficientă;
- întârziere în finalizarea documentației;
- neînțelegerea diferenței dintre deployment real și deployment demonstrativ.

## 10. Criterii de succes

Proiectul este considerat reușit dacă poate fi rulat local, testele trec, pipeline-ul CI rulează corect și toate documentele cerute sunt incluse.
