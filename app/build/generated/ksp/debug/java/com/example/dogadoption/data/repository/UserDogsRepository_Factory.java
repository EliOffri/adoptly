package com.example.dogadoption.data.repository;

import com.example.dogadoption.data.local.dao.UserDogDao;
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
public final class UserDogsRepository_Factory implements Factory<UserDogsRepository> {
  private final Provider<UserDogDao> userDogDaoProvider;

  public UserDogsRepository_Factory(Provider<UserDogDao> userDogDaoProvider) {
    this.userDogDaoProvider = userDogDaoProvider;
  }

  @Override
  public UserDogsRepository get() {
    return newInstance(userDogDaoProvider.get());
  }

  public static UserDogsRepository_Factory create(
      javax.inject.Provider<UserDogDao> userDogDaoProvider) {
    return new UserDogsRepository_Factory(Providers.asDaggerProvider(userDogDaoProvider));
  }

  public static UserDogsRepository_Factory create(Provider<UserDogDao> userDogDaoProvider) {
    return new UserDogsRepository_Factory(userDogDaoProvider);
  }

  public static UserDogsRepository newInstance(UserDogDao userDogDao) {
    return new UserDogsRepository(userDogDao);
  }
}
