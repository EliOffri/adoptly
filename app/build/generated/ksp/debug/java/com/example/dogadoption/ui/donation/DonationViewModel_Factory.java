package com.example.dogadoption.ui.donation;

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
public final class DonationViewModel_Factory implements Factory<DonationViewModel> {
  @Override
  public DonationViewModel get() {
    return newInstance();
  }

  public static DonationViewModel_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DonationViewModel newInstance() {
    return new DonationViewModel();
  }

  private static final class InstanceHolder {
    static final DonationViewModel_Factory INSTANCE = new DonationViewModel_Factory();
  }
}
