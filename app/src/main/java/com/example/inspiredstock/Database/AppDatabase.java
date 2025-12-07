package com.example.inspiredstock.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Import Semua Model
import com.example.inspiredstock.Models.ProductsModel;
import com.example.inspiredstock.Models.CustomersModelClass;
import com.example.inspiredstock.Models.SuppliersModelClass;
import com.example.inspiredstock.Models.ExpensesModel;
import com.example.inspiredstock.Models.BillingModel;
import com.example.inspiredstock.Models.CostModel;

// DAFTARKAN SEMUA ENTITY (TABEL) DI SINI
@Database(entities = {
        ProductsModel.class,
        CustomersModelClass.class,
        SuppliersModelClass.class,
        ExpensesModel.class,
        BillingModel.class,  // <-- PENTING: Tambahkan ini
        CostModel.class      // <-- Tambahkan ini juga jika fitur Cost dipakai
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // DAFTARKAN SEMUA DAO (AKSES DATABSE) DI SINI
    public abstract ProductsDao productsDao();
    public abstract CustomersDao customersDao();
    public abstract SuppliersDao suppliersDao();
    public abstract ExpensesDao expensesDao();

    public abstract BillingDao billingDao(); // <-- INI YANG MENYEBABKAN ERROR ANDA
    public abstract CostDao costDao();       // <-- Tambahkan ini untuk fitur Cost

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "StockApp_Offline_DB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}