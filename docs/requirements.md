# Requirements Document – ClinicQueue

## 1. Descriere generală

ClinicQueue este o aplicație Java de tip CLI pentru gestionarea programărilor la un cabinet medical. Aplicația permite personalului administrativ să creeze, caute, reprogrameze și anuleze programări.

## 2. Problema de business

Cabinetele mici gestionează uneori programările manual, în agende sau foi de calcul. Acest proces poate genera suprapuneri, pierderi de informații și dificultăți la căutarea rapidă a programărilor. ClinicQueue propune o soluție minimală pentru organizarea acestor programări.

## 3. Obiective

- reducerea riscului de suprapunere a programărilor;
- acces rapid la lista programărilor;
- căutare după numele pacientului;
- anularea și reprogramarea programărilor;
- cod organizat și testabil;
- integrarea proiectului cu source control și CI.

## 4. Stakeholderi

| Stakeholder | Interes |
|---|---|
| Administrator cabinet | Creează și gestionează programările. |
| Medic / personal medical | Consultă lista programărilor. |
| Pacient | Beneficiază indirect de programări corecte. |
| Dezvoltator | Implementează și menține aplicația. |
| Profesor evaluator | Verifică infrastructura tehnică și artefactele PM. |

## 5. Cerințe funcționale

| ID | Cerință | Prioritate |
|---|---|---|
| FR1 | Utilizatorul poate crea o programare cu nume pacient, serviciu și dată/oră selectată din calendar. | High |
| FR2 | Aplicația validează că data programării este în viitor. | High |
| FR3 | Aplicația nu permite două programări active în același interval. | High |
| FR4 | Utilizatorul poate lista toate programările. | High |
| FR5 | Utilizatorul poate căuta programări după numele pacientului. | Medium |
| FR6 | Utilizatorul poate anula o programare existentă. | Medium |
| FR7 | Utilizatorul poate reprograma o programare existentă. | Medium |

## 6. Cerințe non-funcționale

| ID | Cerință | Descriere |
|---|---|---|
| NFR1 | Testabilitate | Logica de business trebuie separată de interfața CLI. |
| NFR2 | Mentenabilitate | Codul trebuie să fie organizat în clase clare. |
| NFR3 | Portabilitate | Aplicația trebuie să ruleze cu Java 17. |
| NFR4 | Automatizare | Build-ul și testele trebuie să ruleze automat în CI. |
| NFR5 | Calitate cod | Proiectul trebuie să includă analiză statică simplă. |
| NFR6 | Deployment | Artefactul `.jar` trebuie copiat automat într-un folder de deployment. |

## 7. Constrângeri

- limbaj de programare: Java;
- repository pe GitHub/GitLab/Bitbucket/Azure DevOps;
- funcționalitate redusă, dar relevantă pentru un scenariu real;
- prezentare succintă la examen;
- predare cu minimum 3 zile înainte de examen.

## 8. Criterii de acceptare

- aplicația se compilează fără erori;
- testele unitare rulează automat;
- CI rulează la push sau pull request;
- există analiză statică;
- există pas de deployment;
- documentele PM sunt incluse în folderul `docs/`.


## Cerință de interfață

Aplicația include o interfață grafică minimală realizată în Java Swing. Prin interfață, utilizatorul poate:

- crea o programare alegând data din calendar, fără introducere manuală;
- afișa toate programările;
- căuta programări după numele pacientului;
- anula o programare selectată;
- reprograma o programare selectată;
- adăuga date demonstrative pentru prezentare.

În interfața grafică, data nu poate fi introdusă manual; utilizatorul folosește un dialog de tip calendar și liste pentru oră/minut.

Pentru compatibilitate și testare rapidă, aplicația păstrează și o variantă CLI, pornită cu `java -jar build/clinicqueue.jar --cli`.
