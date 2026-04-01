package com.example.dogadoption.data.repository;

import com.example.dogadoption.data.remote.api.DogApiService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DogsRepository_Factory implements Factory<DogsRepository> {
  private final Provider<DogApiService> apiServiceProvider;

  public DogsRepository_Factory(Provider<DogApiService> apiServiceProvider) {
    this.apiServiceProvider = apiServiceProvider;
  }

  @Override
  public DogsRepository get() {
    return newInstance(apiServiceProvider.get());
  }

  public static DogsRepository_Factory create(
      javax.inject.Provider<DogApiService> apiServiceProvider) {
    return new DogsRepository_Factory(Providers.asDaggerProvider(apiServiceProvider));
  }

  public static DogsRepository_Factory create(Provider<DogApiService> apiServiceProvider) {
    return new DogsRepository_Factory(apiServiceProvider);
  }

  public static DogsRepository newInstance(DogApiService apiService) {
    return new DogsRepository(apiService);
  }
}
