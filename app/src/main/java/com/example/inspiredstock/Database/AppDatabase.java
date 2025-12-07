package com.example.inspiredstock.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.Models.ExpensesModel;
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.Models.SuppliersModelClass;

// Daftarkan semua tabel disini
@Database(entities = {
        ProductsModel.class,
        CustomersModelClass.class,
        SuppliersModelClass.class,
        ExpensesModel.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductsDao productsDao();
    public abstract CustomersDao customersDao();
    public abstract SuppliersDao suppliersDao();
    public abstract ExpensesDao expensesDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "StockApp_Offline_DB")
                    .allowMainThreadQueries() // Pintasan agar coding lebih simpel
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
