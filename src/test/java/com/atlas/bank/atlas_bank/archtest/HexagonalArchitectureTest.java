package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * HexagonalArchitectureTest
 */
@AnalyzeClasses(
  packages = "com.atlas.bank.atlas_bank",
  importOptions = ImportOption.DoNotIncludeTests.class
)
public class HexagonalArchitectureTest {

  @ArchTest
  static final ArchRule domain_should_not_depend_on_infrastructure = noClasses()
    .that()
    .resideInAPackage("..domain..")
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage("..infrastructure..")
    .because("Domain should not depend on infrastructure");

  @ArchTest
  static final ArchRule domain_should_not_depend_on_application = noClasses()
    .that()
    .resideInAPackage("..domain..")
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage("..application..")
    .because("Domain should not depend on application");

  @ArchTest
  static final ArchRule application_should_not_depend_on_infrastructure = noClasses()
    .that()
    .resideInAPackage("..application..")
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage("..infrastructure..")
    .because("Application should not depend on infrastructure");

  @ArchTest
  static final ArchRule domain_should_not_depend_on_spring = noClasses()
    .that()
    .resideInAPackage("..domain..")
    .should()
    .dependOnClassesThat()
    .resideInAnyPackage("..org.springframework..")
    .because("Domain should not depend on spring");
}
