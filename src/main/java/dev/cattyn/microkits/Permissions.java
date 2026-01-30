package dev.cattyn.microkits;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public final class Permissions {
    private Permissions() {
    }

    public static void init() {
        register("microkits.kitcreator", "Allows to open kit creator", PermissionDefault.TRUE);
        register("microkits.kit", "Allows to execute kit command", PermissionDefault.TRUE);
        register("microkits.reload", "Allows to reload plugins config", PermissionDefault.OP);
    }

    private static void register(String permission, String description, PermissionDefault permissionDefault) {
        Bukkit.getServer().getPluginManager().addPermission(new Permission(permission, description, permissionDefault));
    }
}
