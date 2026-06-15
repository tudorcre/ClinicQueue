# Prezentare succintă pentru examen – ClinicQueue

## 1. Descrierea proiectului

Proiectul se numește ClinicQueue și este o aplicație Java pentru gestionarea programărilor la un cabinet medical. Aplicația are business real minimal, deoarece organizează programări, previne suprapunerile și permite căutarea rapidă după pacient.

## 2. Funcționalități

Aplicația permite crearea unei programări, listarea programărilor, căutarea după numele pacientului, anularea și reprogramarea. Logica de business este implementată în clasa `AppointmentService`, iar interfața de utilizare este o interfață CLI simplă.

## 3. Project Management

Pentru partea de project management am realizat două analize SWOT: una pentru Project Management tradițional și una pentru Agile/Scrum. De asemenea, am inclus requirements document, project charter, project timeline, backlog Agile și risk log.

## 4. Infrastructură tehnică

Proiectul este pregătit pentru source control. Are teste unitare, build automat, rulare automată a testelor, analiză statică simplă și deployment demonstrativ. CI-ul este configurat prin GitHub Actions în fișierul `.github/workflows/ci.yml`.

## 5. Demonstrație

Pentru demonstrație pot rula comenzile:

```bash
./scripts/static-analysis.sh
./scripts/build.sh
./scripts/test.sh
./scripts/deploy.sh
java -jar build/clinicqueue.jar
```

## 6. Concluzie

Proiectul acoperă toate cerințele: SWOT, artefacte PM, proiect Java pe source control, unit testing, CI, build automat, testare automată, analiză statică și deployment simplu.


## Demonstrație interfață grafică

Pentru demonstrație, se rulează:

```bash
java -jar build/clinicqueue.jar
```

Pași recomandați în prezentare:

1. Se apasă butonul **Adaugă date exemplu**.
2. Se creează manual o programare nouă cu o dată viitoare.
3. Se caută un pacient după nume.
4. Se selectează o programare și se apasă **Anulează selectată**.
5. Se selectează o altă programare și se apasă **Reprogramează selectată**.

Această demonstrație acoperă funcționalitățile principale și arată că aplicația are o interfață mai ușor de prezentat decât varianta de consolă.
