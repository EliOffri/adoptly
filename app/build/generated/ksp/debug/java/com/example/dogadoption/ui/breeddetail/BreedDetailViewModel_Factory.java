package com.example.dogadoption.ui.breeddetail;

import com.example.dogadoption.data.repository.DogsRepository;
import com.example.dogadoption.data.repository.FavoritesRepository;
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
public final class BreedDetailViewModel_Factory implements Factory<BreedDetailViewModel> {
  private final Provider<DogsRepository> dogsRepositoryProvider;

  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  public BreedDetailViewModel_Factory(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    this.dogsRepositoryProvider = dogsRepositoryProvider;
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
  }

  @Override
  public BreedDetailViewModel get() {
    return newInstance(dogsRepositoryProvider.get(), favoritesRepositoryProvider.get());
  }

  public static BreedDetailViewModel_Factory create(
      javax.inject.Provider<DogsRepository> dogsRepositoryProvider,
      javax.inject.Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new BreedDetailViewModel_Factory(Providers.asDaggerProvider(dogsRepositoryProvider), Providers.asDaggerProvider(favoritesRepositoryProvider));
  }

  public static BreedDetailViewModel_Factory create(Provider<DogsRepository> dogsRepositoryProvider,
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new BreedDetailViewModel_Factory(dogsRepositoryProvider, favoritesRepositoryProvider);
  }

  public static BreedDetailViewModel newInstance(DogsRepository dogsRepository,
      FavoritesRepository favoritesRepository) {
    return new BreedDetailViewModel(dogsRepository, favoritesRepository);
  }
}
