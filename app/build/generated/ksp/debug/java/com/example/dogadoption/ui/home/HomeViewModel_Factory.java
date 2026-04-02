package com.example.dogadoption.ui.home;

import com.example.dogadoption.data.repository.DogsRepository;
import com.example.dogadoption.data.repository.FavoritesRepository;
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

  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  public HomeViewModel_Factory(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<UserDogsRepository> userDogsRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    this.dogsRepositoryProvider = dogsRepositoryProvider;
    this.userDogsRepositoryProvider = userDogsRepositoryProvider;
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(dogsRepositoryProvider.get(), userDogsRepositoryProvider.get(), favoritesRepositoryProvider.get());
  }

  public static HomeViewModel_Factory create(
      javax.inject.Provider<DogsRepository> dogsRepositoryProvider,
      javax.inject.Provider<UserDogsRepository> userDogsRepositoryProvider,
      javax.inject.Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new HomeViewModel_Factory(Providers.asDaggerProvider(dogsRepositoryProvider), Providers.asDaggerProvider(userDogsRepositoryProvider), Providers.asDaggerProvider(favoritesRepositoryProvider));
  }

  public static HomeViewModel_Factory create(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<UserDogsRepository> userDogsRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new HomeViewModel_Factory(dogsRepositoryProvider, userDogsRepositoryProvider, favoritesRepositoryProvider);
  }

  public static HomeViewModel newInstance(DogsRepository dogsRepository,
      UserDogsRepository userDogsRepository, FavoritesRepository favoritesRepository) {
    return new HomeViewModel(dogsRepository, userDogsRepository, favoritesRepository);
  }
}
