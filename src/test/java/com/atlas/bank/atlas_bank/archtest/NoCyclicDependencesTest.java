package com.atlas.bank.atlas_bank.archtest;

import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

/**
 * NoCyclicDependencesTest
 */
@AnalyzeClasses(
  packages = "com.atlas.bank.atlas_bank",
  importOptions = ImportOption.DoNotIncludeTests.class
)
public class NoCyclicDependencesTest {

  @ArchTest
  static final ArchRule domain_modules_should_be_free_of_cycles = slices()
    .matching("com.atlas.bank.atlas_bank.domain.model.(*)..")
    .should()
    .beFreeOfCycles()
    .because("Domain modules should be free of cycles");

  @ArchTest
  static final ArchRule layers_should_be_free_of_cycles = slices()
    .matching("com.atlas.bank.atlas_bank.(*)..")
    .should()
    .beFreeOfCycles()
    .because("Layers should be free of cycles");
}
