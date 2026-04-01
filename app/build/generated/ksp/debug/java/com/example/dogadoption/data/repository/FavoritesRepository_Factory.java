package com.example.dogadoption.data.repository;

import com.example.dogadoption.data.local.dao.FavoriteDogDao;
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
public final class FavoritesRepository_Factory implements Factory<FavoritesRepository> {
  private final Provider<FavoriteDogDao> favoriteDogDaoProvider;

  public FavoritesRepository_Factory(Provider<FavoriteDogDao> favoriteDogDaoProvider) {
    this.favoriteDogDaoProvider = favoriteDogDaoProvider;
  }

  @Override
  public FavoritesRepository get() {
    return newInstance(favoriteDogDaoProvider.get());
  }

  public static FavoritesRepository_Factory create(
      javax.inject.Provider<FavoriteDogDao> favoriteDogDaoProvider) {
    return new FavoritesRepository_Factory(Providers.asDaggerProvider(favoriteDogDaoProvider));
  }

  public static FavoritesRepository_Factory create(
      Provider<FavoriteDogDao> favoriteDogDaoProvider) {
    return new FavoritesRepository_Factory(favoriteDogDaoProvider);
  }

  public static FavoritesRepository newInstance(FavoriteDogDao favoriteDogDao) {
    return new FavoritesRepository(favoriteDogDao);
  }
}
