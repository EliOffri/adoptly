package com.example.dogadoption.ui.adoption;

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
public final class AdoptionViewModel_Factory implements Factory<AdoptionViewModel> {
  private final Provider<FavoritesRepository> favoritesRepositoryProvider;

  public AdoptionViewModel_Factory(Provider<FavoritesRepository> favoritesRepositoryProvider) {
    this.favoritesRepositoryProvider = favoritesRepositoryProvider;
  }

  @Override
  public AdoptionViewModel get() {
    return newInstance(favoritesRepositoryProvider.get());
  }

  public static AdoptionViewModel_Factory create(
      javax.inject.Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new AdoptionViewModel_Factory(Providers.asDaggerProvider(favoritesRepositoryProvider));
  }

  public static AdoptionViewModel_Factory create(
      Provider<FavoritesRepository> favoritesRepositoryProvider) {
    return new AdoptionViewModel_Factory(favoritesRepositoryProvider);
  }

  public static AdoptionViewModel newInstance(FavoritesRepository favoritesRepository) {
    return new AdoptionViewModel(favoritesRepository);
  }
}
