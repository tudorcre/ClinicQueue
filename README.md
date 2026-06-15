# ClinicQueue – aplicație pentru gestionarea programărilor la un cabinet medical

ClinicQueue este un proiect Java cu interfață grafică Swing și funcționalitate redusă, dar cu utilitate practică: permite gestionarea programărilor pentru un cabinet medical. Proiectul este construit pentru tema de **Project Management și infrastructură tehnică**.

## Scopul proiectului

Aplicația ajută un cabinet mic să creeze, caute, reprogrameze și anuleze programări. Tema are business real minimal, deoarece tratează un proces administrativ întâlnit în cabinete medicale, saloane, centre de consiliere sau alte servicii pe bază de programare.

## Funcționalități implementate

- creare programare cu selecție dată din calendar;
- afișare programări;
- căutare programări după numele pacientului;
- anulare programare;
- reprogramare;
- validarea datelor de intrare;
- prevenirea suprapunerii programărilor în același interval;
- teste unitare pentru logica principală.

## Tehnologii

- Java 17;
- Java Swing pentru interfața grafică;
- Git/GitHub sau altă platformă de source control;
- GitHub Actions pentru CI;
- scripturi shell pentru build, testare, analiză statică și deployment simplu.

## Structura proiectului

```text
ClinicQueue_Project/
├── .github/workflows/ci.yml
├── docs/
│   ├── agile-backlog.md
│   ├── evaluation-checklist.md
│   ├── project-charter.md
│   ├── project-timeline.md
│   ├── requirements.md
│   ├── risk-log.md
│   ├── swot-agile-scrum.md
│   └── swot-pm-traditional.md
├── scripts/
│   ├── build.sh
│   ├── deploy.sh
│   ├── static-analysis.sh
│   └── test.sh
├── src/main/java/ro/ulbsibiu/clinicqueue/
└── src/test/java/ro/ulbsibiu/clinicqueue/
```

## Rulare locală

Din folderul proiectului:

```bash
chmod +x scripts/*.sh
./scripts/static-analysis.sh
./scripts/build.sh
./scripts/test.sh
./scripts/deploy.sh
java -jar build/clinicqueue.jar
```

Comanda pornește interfața grafică. Pentru varianta de consolă se poate folosi:

```bash
java -jar build/clinicqueue.jar --cli
```


## Interfața grafică

În fereastra principală, data programării nu se introduce manual. Utilizatorul apasă
**Alege data din calendar**, selectează ziua din calendar, apoi alege ora și minutul
din listele disponibile. Aceeași fereastră de calendar este folosită și pentru
reprogramarea unei programări selectate.

## Continuous Integration

Workflow-ul din `.github/workflows/ci.yml` rulează automat la `push` și `pull_request`:

1. checkout cod sursă;
2. instalare Java 17;
3. static code analysis;
4. automatic build;
5. automatic unit testing execution;
6. deployment simplu prin copierea artefactului `.jar` în folderul `deployment/`;
7. publicarea folderului `deployment/` ca artifact în GitHub Actions.

## Documente Project Management incluse

- `docs/swot-pm-traditional.md`;
- `docs/swot-agile-scrum.md`;
- `docs/requirements.md`;
- `docs/project-charter.md`;
- `docs/project-timeline.md`;
- `docs/agile-backlog.md`;
- `docs/risk-log.md`;
- `docs/evaluation-checklist.md`.

## Cum se poate prezenta la examen

1. Se explică problema: gestionarea programărilor într-un cabinet medical.
2. Se prezintă comparația SWOT între PM tradițional și Agile/Scrum.
3. Se arată documentele PM: requirements, charter/timeline sau backlog, risk log.
4. Se pornește interfața grafică și se demonstrează alegerea datei din calendar, crearea/căutarea/anularea unei programări.
5. Se deschide repository-ul și se arată codul sursă.
6. Se rulează sau se arată pipeline-ul CI: build, teste, analiză statică și deployment.
