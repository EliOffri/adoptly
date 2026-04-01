package com.example.dogadoption.ui.favorites;

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
public final class FavoritesViewModel_Factory implements Factory<FavoritesViewModel> {
  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  public FavoritesViewModel_Factory(Provider<FavoritesRepository> favoritesRepositoryProvider) {
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
  }

  @Override
  public FavoritesViewModel get() {
    return newInstance(favoritesRepositoryProvider.get());
  }

  public static FavoritesViewModel_Factory create(
      javax.inject.Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new FavoritesViewModel_Factory(Providers.asDaggerProvider(favoritesRepositoryProvider));
  }

  public static FavoritesViewModel_Factory create(
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new FavoritesViewModel_Factory(favoritesRepositoryProvider);
  }

  public static FavoritesViewModel newInstance(FavoritesRepository favoritesRepository) {
    return new FavoritesViewModel(favoritesRepository);
  }
}
