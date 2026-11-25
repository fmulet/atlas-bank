package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * NamingConventionTest
 */
@AnalyzeClasses(
  packages = "com.atlas.bank.atlas_bank",
  importOptions = ImportOption.DoNotIncludeTests.class
)
public class NamingConventionTest {

  @ArchTest
  static final ArchRule controllers_should_reside_in_rest_adapter = classes()
    .that()
    .haveSimpleNameEndingWith("Controller")
    .should()
    .resideInAPackage("..infrastructure.adapter.in.rest..")
    .because(
      "Controllers should reside in the infrastructure layer and be named with the suffix 'Controller'"
    );

  @ArchTest
  static final ArchRule port_in_should_interfaces = classes()
    .that()
    .resideInAPackage("..application.port.in..")
    .should()
    .beInterfaces()
    .because("Port in interfaces should reside in the application layer");

  @ArchTest
  static final ArchRule port_out_should_interfaces = classes()
    .that()
    .resideInAPackage("..application.port.out..")
    .should()
    .beInterfaces()
    .because("Port out interfaces should reside in the application layer");

  @ArchTest
  static final ArchRule use_case_should_reside_port_in_application = classes()
    .that()
    .haveSimpleNameEndingWith("UseCase")
    .should()
    .resideInAPackage("..application.port.in..")
    .because(
      "Use cases should reside in the application layer and be named with the suffix 'UseCase'"
    );
}
