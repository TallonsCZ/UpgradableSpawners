package cz.tallonscz.upgradablespawner.Utilities;

import com.zaxxer.hikari.HikariDataSource;
import cz.tallonscz.upgradablespawner.Upgradablespawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class Database {

    private static HikariDataSource hikariDataSource;

    public static void initializeDatabase(){
        try{
            hikariDataSource = new HikariDataSource();
            String host = getDatabaseConfig().getString("database.host", null);
            String database = getDatabaseConfig().getString("database.database", null);
            String username = getDatabaseConfig().getString("database.username", null);
            String password = getDatabaseConfig().getString("database.password", null);
            int port = getDatabaseConfig().getInt("database.port", -1);

            if(host == null || database == null || username == null || password == null || port == -1){
                return;
            }

            String finalUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;

            hikariDataSource.setJdbcUrl(finalUrl);
            hikariDataSource.setUsername(username);
            hikariDataSource.setPassword(password);
            hikariDataSource.setMaximumPoolSize(10);
            hikariDataSource.setMinimumIdle(10);
            hikariDataSource.setMaxLifetime(1800000);
            hikariDataSource.setConnectionTimeout(5000);
            hikariDataSource.setLeakDetectionThreshold(30000);
        }catch (IllegalArgumentException e){
            System.out.println("[UltimateSpawners] Database doesn't load...");
        }

    }

    public static Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private static File dataFolder = Upgradablespawner.INSTANCE.getDataFolder();

    private static FileConfiguration getDatabaseConfig(){
        File file = new File(dataFolder, "mysql.yml");
        if(file.exists()){
            return YamlConfiguration.loadConfiguration(file);
        } else {
            return null;
        }
    }

    public static void close() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }
}
