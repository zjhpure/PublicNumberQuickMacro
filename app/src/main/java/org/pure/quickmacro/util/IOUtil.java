package org.pure.quickmacro.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

/**
 * IO工具
 */
public class IOUtil {
    /**
     * 输入流转换成字节数组
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 解压
     */
    public static byte[] ungzip(byte[] bs) throws Exception {
        GZIPInputStream gzin = null;
        ByteArrayInputStream bin = null;
        try {
            bin = new ByteArrayInputStream(bs);
            gzin = new GZIPInputStream(bin);
            return toByteArray(gzin);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (bin != null) {
                bin.close();
            }
            if (gzin != null) {
                gzin.close();
            }
        }
    }
}
