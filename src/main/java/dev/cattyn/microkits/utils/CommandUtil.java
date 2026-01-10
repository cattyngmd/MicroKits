package dev.cattyn.microkits.utils;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.exceptions.WrapperCommandSyntaxException;

public class CommandUtil {
    public static void error(String message) throws WrapperCommandSyntaxException {
        throw CommandAPI.failWithString(message);
    }
}
