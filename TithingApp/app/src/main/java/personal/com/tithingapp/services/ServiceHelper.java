package personal.com.tithingapp.services;

import android.content.Context;
import android.content.Intent;

import personal.com.tithingapp.database.Persistable;

public class ServiceHelper {

    public static void savePersistable(Context context, Persistable persistable) {
        createAndLaunchPersisterIntent(context, persistable, Persistable.ACTION_SAVE);
    }

    public static void updatePersistable(Context context, Persistable persistable) {
        createAndLaunchPersisterIntent(context, persistable, Persistable.ACTION_UPDATE);
    }

    public static void deletePersistable(Context context, Persistable persistable) {
        createAndLaunchPersisterIntent(context, persistable, Persistable.ACTION_DELETE);
    }

    private static void createAndLaunchPersisterIntent(Context context, Persistable persistable, String action) {
        Intent intent = new Intent(context, PersisterService.class);
        intent.setAction(action);
        intent.putExtra(Persistable.NAME, persistable);

        context.startService(intent);
    }
}
