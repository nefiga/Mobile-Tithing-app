package personal.com.tithingapp.services;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import personal.com.tithingapp.database.Persistable;

public class PersisterService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null)
            return START_STICKY;

        Bundle extras = intent.getExtras();
        if (extras.containsKey(Persistable.NAME)) {
            Persistable persistable = extras.getParcelable(Persistable.NAME);

            if (persistable != null) {
                switch (intent.getAction()) {
                    case Persistable.ACTION_SAVE:
                        persistable.save(this);
                        break;
                    case Persistable.ACTION_UPDATE:
                        persistable.update(this);
                        break;
                    case Persistable.ACTION_DELETE:
                        persistable.delete(this);
                        break;
                }
            }
        }

        return START_STICKY;
    }
}
