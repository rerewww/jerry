package com.jerry.parse;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Tailer implements Runnable {
    private final static String PRE_ERROR_LOGS = "\tat";
    // source file
    @Setter
    private File srcFile;

    // tail sleep time out
    private static final long SLEEP_TIME_OUT = 2000;

    // check run tail -f
    private boolean isRun = true;

    // file position
    @Setter
    private long pointer = 0;

	@Getter
    private List<String> logs = new ArrayList<>();

    public void clear() {
        this.logs.clear();
    }

    public void stop() {
        this.isRun = false;
    }

    @Override
    public void run() {
        while(isRun) {
            if (srcFile.length() <= pointer) {
	            try {
                    Thread.sleep(SLEEP_TIME_OUT);
                } catch (InterruptedException e) {
                    log.warn(e.getMessage(), e);
                }
                continue;
            }

            try (RandomAccessFile reader = new RandomAccessFile(srcFile, "r")) {
                StringBuilder builder = new StringBuilder();
                reader.seek(pointer);
                String line = reader.readLine();

                while (line != null) {
                    if ("".equals(line)) {
                        line = reader.readLine();
                        continue;
                    }

                    if (line.contains(PRE_ERROR_LOGS)) {
                        builder.append(line);
                        builder.append("</br>");

                        line = reader.readLine();
                        continue;
                    }

                    this.buildErrorLogs(builder);
                    logs.add(new String(line.replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;").getBytes("ISO-8859-1"), "UTF-8"));
                    line = reader.readLine();
                }
                this.buildErrorLogs(builder);
                pointer = reader.getFilePointer();
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }

            try {
                Thread.sleep(SLEEP_TIME_OUT);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            }
        }
    }

    private void buildErrorLogs(final StringBuilder builder) throws UnsupportedEncodingException {
        if (builder.length() > 0) {
            String header = new String(logs.get(logs.size() - 1).replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;").getBytes("ISO-8859-1"), "UTF-8") + "</br>";
            logs.remove(logs.size() - 1);

            logs.add("\\a\\r" + header + new String(builder.toString().replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;").getBytes("ISO-8859-1"), "UTF-8"));
            builder.delete(0, builder.length());
        }
    }
}
