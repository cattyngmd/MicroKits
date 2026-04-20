package dev.cattyn.microkits.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class CompressionUtil {
    private static final int BUF_SIZE = 2048;

    CompressionUtil() {
    }

    public static byte[] compress(byte[] input) {
        Deflater deflater = new Deflater();
        try {
            deflater.setInput(input);
            deflater.finish();

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[BUF_SIZE];

            while (!deflater.finished()) {
                int len = deflater.deflate(buf);
                os.write(buf, 0, len);
            }

            return os.toByteArray();
        } finally {
            deflater.end();
        }
    }

    public static byte[] decompress(byte[] input) throws DataFormatException {
        Inflater inflater = new Inflater();
        try {
            inflater.setInput(input);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] buf = new byte[BUF_SIZE];

            while (!inflater.finished()) {
                int len = inflater.inflate(buf);
                os.write(buf, 0, len);
            }

            return os.toByteArray();
        } finally {
            inflater.end();
        }
    }
}
