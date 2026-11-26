# AGENTS.md

Spring Boot 4.1.2 / Java 25 banking service, Maven wrapper, hexagonal architecture.

## Commands

- Run: `./mvnw spring-boot:run` (or `make run`)
- All tests: `./mvnw test` (or `make test`)
- One test class / method: `./mvnw test -Dtest=AccountDomainTest` / `./mvnw test -Dtest=AccountDomainTest#shouldDepositMoney`
- Package (skips tests): `make build` = `./mvnw clean package -DskipTests`
- No formatter or lint plugin exists; compiling and running tests is the only verification step.
- Keycloak + Postgres for real JWT auth: `docker compose -f docker/docker-compose.yaml up -d` (Keycloak on `:8181`, admin/admin, realm expected: `atlas-bank`). Tests and app startup do not need it.

## Package name

Root package is `com.atlas.bank.atlas_bank` (underscore). Maven rejects the hyphenated form; do not "normalize" it.

## Architecture rules (ArchUnit fails `mvn test` if broken)

- `domain` must not depend on Spring, Spring Security, `application`, or `infrastructure`.
- `application` must not depend on `infrastructure` or Spring Security.
- `*Controller` → `infrastructure.adapter.in.rest`; `*UseCase` → `application.port.in`; everything in `application.port.in` / `port.out` must be an interface.
- `domain.model.*` subpackages must stay free of cycles (also overall layer slices).
- Because `domain` is Spring-free, wire domain services/strategies/validators manually as `@Bean`s in `infrastructure/config/DomainBeanConfig`; use `@Order` where a `List<T>` chain is injected (fee calculators, validators).
- Dependencies point inward: REST controllers → `application.port.in` use cases; adapters in `infrastructure.adapter.out.*` implement `application.port.out` ports; services receive ports via constructor injection.

## Gotchas

- Two `Money` and two `Email` types exist: `domain.model.shared.*` (pure domain) and `infrastructure.adapter.out.persistence.*` (`@Embeddable` JPA). Persistence mappers convert between them; import the correct one.
- MapStruct implementations are generated into `target/generated-sources`; never edit them. Mappers use `componentModel = "spring"`.
- `AuditableAccountService` is `@Primary` and wraps `AccountServiceImpl`; injecting a use-case interface gets the audited wrapper. Use `@Qualifier("accountServiceImpl")` to reach the raw service.
- H2 in-memory DB `atlasbank` with `ddl-auto: create-drop`: all data is lost on restart. Console at `/h2-console`.
- JWT roles come from Keycloak `realm_access.roles` and are mapped without a `ROLE_` prefix, while `SecurityConfig` uses `hasRole(...)` (which expects `ROLE_`). Authorization may 403 until this is reconciled.
- User-facing messages, logs, and domain exceptions are in Spanish; keep that style.

## Known rough edges (verify before relying on or "fixing")

- `TransactionController.transfer` swaps `fromId`/`toId` when building `TransferMoneyCommand`.
- `AccountDashboardFacade` sets `ownerName` from `accountNumber`.

## Layout

`src/main/java/.../{domain,application,infrastructure}` — domain model/service/strategy/validation/event; application command/query/facade/service/port; infrastructure adapter in (REST), adapter out (persistence, fraud), listener, config. Tests mirror the package under `src/test/java`; architectural tests live in `archtest/`.

Commits follow Conventional Commits (`feat(domain): ...`).
