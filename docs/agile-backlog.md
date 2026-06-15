# Agile Backlog – ClinicQueue

## Product Goal

Dezvoltarea unei aplicații simple care permite personalului unui cabinet medical să gestioneze programările într-un mod organizat și verificabil.

## Product Backlog

| ID | User story | Prioritate | Criterii de acceptare | Status |
|---|---|---|---|---|
| US1 | Ca administrator, vreau să creez o programare pentru un pacient, ca să pot organiza consultațiile. | High | Programarea conține pacient, serviciu și dată/oră. | Done |
| US2 | Ca administrator, vreau ca aplicația să respingă programările în trecut, ca să evit date invalide. | High | Programările cu dată trecută aruncă eroare. | Done |
| US3 | Ca administrator, vreau să evit suprapunerea programărilor, ca să nu rezerv același interval de două ori. | High | Două programări active nu pot avea aceeași dată/oră. | Done |
| US4 | Ca administrator, vreau să văd lista programărilor, ca să pot verifica agenda. | High | Programările sunt afișate ordonat după dată. | Done |
| US5 | Ca administrator, vreau să caut după numele pacientului, ca să găsesc rapid o programare. | Medium | Căutarea este parțială și case-insensitive. | Done |
| US6 | Ca administrator, vreau să anulez o programare, ca să reflect modificările cerute de pacient. | Medium | Statusul devine CANCELLED. | Done |
| US7 | Ca administrator, vreau să reprogramez o programare, ca să pot modifica data consultației. | Medium | Noua dată este validată și salvată. | Done |
| US8 | Ca dezvoltator, vreau teste automate, ca să verific logica de business. | High | Testele trec în CI. | Done |
| US9 | Ca dezvoltator, vreau build automat, ca să detectez rapid erorile de compilare. | High | Pipeline-ul creează fișierul `.jar`. | Done |
| US10 | Ca dezvoltator, vreau deployment simplu, ca să demonstrez livrarea artefactului. | Medium | `.jar` este copiat în `deployment/`. | Done |

## Sprint propus

| Sprint | Obiectiv | User stories |
|---|---|---|
| Sprint 1 | MVP funcțional | US1, US2, US3, US4 |
| Sprint 2 | Funcționalități administrative | US5, US6, US7 |
| Sprint 3 | Infrastructură tehnică | US8, US9, US10 |
