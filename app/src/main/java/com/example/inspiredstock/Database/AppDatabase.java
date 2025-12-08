package com.example.inspiredstock.Database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.Models.ExpensesModel;
import com.example.inspiredstock.Models.BillingModel; // Pastikan file ini ada
import com.example.inspiredstock.Models.CostModel;    // Pastikan file ini ada

@Database(entities = {
        ProductsModel.class,
        CustomersModelClass.class,
        SuppliersModelClass.class,
        ExpensesModel.class,
        BillingModel.class,
        CostModel.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ProductsDao productsDao();
    public abstract CustomersDao customersDao();
    public abstract SuppliersDao suppliersDao();
    public abstract ExpensesDao expensesDao();
    public abstract BillingDao billingDao(); // Pastikan BillingDao ada
    public abstract CostDao costDao();       // Pastikan CostDao ada

    private static volatile AppDatabase INSTANCE;

    // KITA PAKAI NAMA 'getDbInstance' AGAR SESUAI ERROR LOG ANDA
    public static AppDatabase getDbInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "inspired_stock_db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}