package personal.com.tithingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.os.Parcelable;

public interface Persistable extends Parcelable {
    String NAME = Persistable.class.getName();

    String ACTION_SAVE = "action_save";
    String ACTION_UPDATE = "action_update";
    String ACTION_DELETE = "action_delete";

    ContentValues getContentValues();

    void save(Context context);

    void update(Context context);

    void delete(Context context);
}
