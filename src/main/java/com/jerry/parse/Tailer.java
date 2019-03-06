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
    // source file
    @Setter
    private File srcFile;

    // tail sleep time out
    private static final long SLEEP_TIME_OUT = 2000;

    // check run tail -f
    private boolean isRun = true;

    // file position
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
                reader.seek(pointer);
                String line = reader.readLine();
                while (line != null) {
                    if ("".equals(line)) {
                        line = reader.readLine();
                        continue;
                    }
                    // TODO 변수에 담아서 getter로 값을 가져올 수 있도록 하자.
	                logs.add(new String(line.getBytes("ISO-8859-1"), "UTF-8"));
                    log.info("Chagned line: " + new String(line.getBytes("ISO-8859-1"), "UTF-8"));
                    line = reader.readLine();
                }

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
}
