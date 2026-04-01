package com.example.dogadoption.ui.home;

import com.example.dogadoption.data.repository.DogsRepository;
import com.example.dogadoption.data.repository.UserDogsRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<DogsRepository> dogsRepositoryProvider;

  private final Provider<UserDogsRepository> userDogsRepositoryProvider;

  public HomeViewModel_Factory(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<UserDogsRepository> userDogsRepositoryProvider) {
    this.dogsRepositoryProvider = dogsRepositoryProvider;
    this.userDogsRepositoryProvider = userDogsRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(dogsRepositoryProvider.get(), userDogsRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<DogsRepository> dogsRepositoryProvider,
      javax.inject.Provider<UserDogsRepository> userDogsRepositoryProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(dogsRepositoryProvider), Providers.asDaggerProvider(userDogsRepositoryProvider));
  }

  public static HomeViewModel_Factory create(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<UserDogsRepository> userDogsRepositoryProvider) {
    return new HomeViewModel_Factory(dogsRepositoryProvider, userDogsRepositoryProvider);
  }

  public static HomeViewModel newInstance(DogsRepository dogsRepository,
      UserDogsRepository userDogsRepository) {
    return new HomeViewModel(dogsRepository, userDogsRepository);
  }
}
