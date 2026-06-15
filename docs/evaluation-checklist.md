# Evaluation Checklist – ClinicQueue

## 1. SWOT – 2 puncte

| Cerință | Fișier / dovadă | Status |
|---|---|---|
| SWOT Project Management tradițional | `docs/swot-pm-traditional.md` | Done |
| SWOT Agile/Scrum | `docs/swot-agile-scrum.md` | Done |
| S&W ca factori interni | Ambele SWOT-uri | Done |
| O&T ca factori externi | Ambele SWOT-uri | Done |

## 2. Artefacte Project Management – 3 puncte

| Cerință | Fișier / dovadă | Status |
|---|---|---|
| Requirements document | `docs/requirements.md` | Done |
| Project charter | `docs/project-charter.md` | Done |
| Project timeline | `docs/project-timeline.md` | Done |
| Agile backlog, opțional suplimentar | `docs/agile-backlog.md` | Done |
| Risk log | `docs/risk-log.md` | Done |
| Business real minimal | Tema cabinet medical | Done |

## 3. Infrastructura tehnică – 4 puncte

| Cerință | Fișier / dovadă | Status |
|---|---|---|
| Proiect Java | `src/main/java/...` | Done |
| Source control | De pus pe GitHub/GitLab/Bitbucket/Azure DevOps | To Do după upload |
| Unit testing | `src/test/java/.../AppointmentServiceTestRunner.java` | Done |
| Continuous Integration | `.github/workflows/ci.yml` | Done |
| Automatic build | `scripts/build.sh` + CI | Done |
| Automatic unit testing execution | `scripts/test.sh` + CI | Done |
| Static code analysis | `scripts/static-analysis.sh` + CI | Done |
| Deployment simplu | `scripts/deploy.sh` + `deployment/` | Done |

## 4. Comenzi utile pentru demonstrație

```bash
chmod +x scripts/*.sh
./scripts/static-analysis.sh
./scripts/build.sh
./scripts/test.sh
./scripts/deploy.sh
java -jar build/clinicqueue.jar
```

## 5. Ce trebuie făcut înainte de predare

- creează repository pe GitHub/GitLab/Bitbucket/Azure DevOps;
- încarcă toate fișierele;
- verifică rularea workflow-ului CI;
- dacă repository-ul este privat, oferă acces profesorilor;
- trimite proiectul cu minimum 3 zile înainte de examen.
