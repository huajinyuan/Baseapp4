package com.example.choujiang.db;

import com.example.choujiang.model.BaseInfo;
import com.raizlabs.android.dbflow.annotation.ColumnIgnore;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.AsyncModel;
import com.raizlabs.android.dbflow.structure.Model;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

/**
 * Created by caiyk on 2017/6/15.
 */

/**
 * Description: The base implementation of {@link Model}. It is recommended to use this class as
 * the base for your {@link Model}, but it is not required.
 */
public class DBBaseModel extends BaseInfo implements Model {

    /**
     * Specifies the Action that was taken when data changes
     */
    public enum Action {

        /**
         * The model called {@link Model#save()}
         */
        SAVE,

        /**
         * The model called {@link Model#insert()}
         */
        INSERT,

        /**
         * The model called {@link Model#update()}
         */
        UPDATE,

        /**
         * The model called {@link Model#delete()}
         */
        DELETE,

        /**
         * The model was changed. used in prior to {@link android.os.Build.VERSION_CODES#JELLY_BEAN_MR1}
         */
        CHANGE
    }

    @ColumnIgnore
    private transient ModelAdapter modelAdapter;

    @SuppressWarnings("unchecked")
    @Override
    public void load() {
        getModelAdapter().load(this);
    }

    @SuppressWarnings("unchecked")
    public void load(DatabaseWrapper databaseWrapper) {
        getModelAdapter().load(this, databaseWrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean save() {
        return getModelAdapter().save(this);
    }

    @SuppressWarnings("unchecked")
    public boolean save(DatabaseWrapper databaseWrapper) {
        return getModelAdapter().save(this, databaseWrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean delete() {
        return getModelAdapter().delete(this);
    }

    @SuppressWarnings("unchecked")
    public boolean delete(DatabaseWrapper databaseWrapper) {
        return getModelAdapter().delete(this, databaseWrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean update() {
        return getModelAdapter().update(this);
    }

    @SuppressWarnings("unchecked")
    public void update(DatabaseWrapper databaseWrapper) {
        getModelAdapter().update(this, databaseWrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public long insert() {
        return getModelAdapter().insert(this);
    }

    @SuppressWarnings("unchecked")
    public void insert(DatabaseWrapper databaseWrapper) {
        getModelAdapter().insert(this, databaseWrapper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean exists() {
        return getModelAdapter().exists(this);
    }

    @SuppressWarnings("unchecked")
    public boolean exists(DatabaseWrapper databaseWrapper) {
        return getModelAdapter().exists(this, databaseWrapper);
    }

    /**
     * @return An async instance of this model where all transactions are on the {@linkDefaultTransactionQueue}
     */
    public AsyncModel<DBBaseModel> async() {
        return new AsyncModel<>(this);
    }

    /**
     * @return The associated {@link ModelAdapter}. The {@link FlowManager}
     * may throw a {@linkInvalidDBConfiguration} for this call if this class
     * is not associated with a table, so be careful when using this method.
     */
    public ModelAdapter getModelAdapter() {
        if (modelAdapter == null) {
            modelAdapter = FlowManager.getModelAdapter(getClass());
        }
        return modelAdapter;
    }
}
