package com.example.dogadoption.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.dogadoption.data.local.entity.FavoriteDogEntity;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class FavoriteDogDao_Impl implements FavoriteDogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FavoriteDogEntity> __insertionAdapterOfFavoriteDogEntity;

  private final EntityDeletionOrUpdateAdapter<FavoriteDogEntity> __deletionAdapterOfFavoriteDogEntity;

  private final EntityDeletionOrUpdateAdapter<FavoriteDogEntity> __updateAdapterOfFavoriteDogEntity;

  public FavoriteDogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFavoriteDogEntity = new EntityInsertionAdapter<FavoriteDogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `favorite_dogs` (`breedName`,`imageUrl`,`notes`) VALUES (?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteDogEntity entity) {
        statement.bindString(1, entity.getBreedName());
        statement.bindString(2, entity.getImageUrl());
        statement.bindString(3, entity.getNotes());
      }
    };
    this.__deletionAdapterOfFavoriteDogEntity = new EntityDeletionOrUpdateAdapter<FavoriteDogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `favorite_dogs` WHERE `breedName` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteDogEntity entity) {
        statement.bindString(1, entity.getBreedName());
      }
    };
    this.__updateAdapterOfFavoriteDogEntity = new EntityDeletionOrUpdateAdapter<FavoriteDogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `favorite_dogs` SET `breedName` = ?,`imageUrl` = ?,`notes` = ? WHERE `breedName` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final FavoriteDogEntity entity) {
        statement.bindString(1, entity.getBreedName());
        statement.bindString(2, entity.getImageUrl());
        statement.bindString(3, entity.getNotes());
        statement.bindString(4, entity.getBreedName());
      }
    };
  }

  @Override
  public Object insertFavorite(final FavoriteDogEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfFavoriteDogEntity.insert(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFavorite(final FavoriteDogEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfFavoriteDogEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateFavorite(final FavoriteDogEntity entity,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfFavoriteDogEntity.handle(entity);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<FavoriteDogEntity>> getAllFavorites() {
    final String _sql = "SELECT * FROM favorite_dogs ORDER BY breedName ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favorite_dogs"}, new Callable<List<FavoriteDogEntity>>() {
      @Override
      @NonNull
      public List<FavoriteDogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBreedName = CursorUtil.getColumnIndexOrThrow(_cursor, "breedName");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final List<FavoriteDogEntity> _result = new ArrayList<FavoriteDogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final FavoriteDogEntity _item;
            final String _tmpBreedName;
            _tmpBreedName = _cursor.getString(_cursorIndexOfBreedName);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _item = new FavoriteDogEntity(_tmpBreedName,_tmpImageUrl,_tmpNotes);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getFavoriteByBreed(final String breedName,
      final Continuation<? super FavoriteDogEntity> $completion) {
    final String _sql = "SELECT * FROM favorite_dogs WHERE breedName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, breedName);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<FavoriteDogEntity>() {
      @Override
      @Nullable
      public FavoriteDogEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBreedName = CursorUtil.getColumnIndexOrThrow(_cursor, "breedName");
          final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final FavoriteDogEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpBreedName;
            _tmpBreedName = _cursor.getString(_cursorIndexOfBreedName);
            final String _tmpImageUrl;
            _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            _result = new FavoriteDogEntity(_tmpBreedName,_tmpImageUrl,_tmpNotes);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Boolean> isFavorite(final String breedName) {
    final String _sql = "SELECT EXISTS(SELECT 1 FROM favorite_dogs WHERE breedName = ?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, breedName);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"favorite_dogs"}, new Callable<Boolean>() {
      @Override
      @NonNull
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp != 0;
          } else {
            _result = false;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
