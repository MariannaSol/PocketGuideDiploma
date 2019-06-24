package com.online_guide.diploma.audioguide.reader;

import android.content.Context;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 *
 */
public class InfoFileReader {

    private Context context;


    public InfoFileReader(Context context) {
        this.context = context;
    }

    public InfoFileReader() {
    }

    /**
     *
     * @param fileName - название файла, из которого нужно считать данные. Хранится в БД.
     * @throws IOException
     * @return Содержимое файла в виде текста
     */
    public String openFile(String fileName) {
        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = context.getResources().openRawResource(
                    context.getResources().getIdentifier("raw/" + fileName,
                            "raw", context.getPackageName()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            if (inputStream != null) {
                String line;
                builder = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    builder.append(line + "\n");
                }

                inputStream.close();
            }
        } catch (IOException e) {

        }

        return builder.toString();
    }

}
