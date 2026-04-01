package com.example.dogadoption.ui.reportstray;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class ReportStrayViewModel_Factory implements Factory<ReportStrayViewModel> {
  @Override
  public ReportStrayViewModel get() {
    return newInstance();
  }

  public static ReportStrayViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static ReportStrayViewModel newInstance() {
    return new ReportStrayViewModel();
  }

  private static final class InstanceHolder {
    static final ReportStrayViewModel_Factory INSTANCE = new ReportStrayViewModel_Factory();
  }
}
