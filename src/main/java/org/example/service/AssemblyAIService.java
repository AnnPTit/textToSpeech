package org.example.service;
import okhttp3.*;
import java.io.File;
import java.io.IOException ;

public class AssemblyAIService {
    private static final String API_KEY = "dc62eb3902374d2fb83cc84616c1e715";
    private static final String UPLOAD_URL = "https://api.assemblyai.com/v2/upload";

    public static String uploadFile(String filePath) throws IOException {
        File file = new File(filePath);
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(file, MediaType.parse("audio/mpeg"))) // hoặc "audio/wav" nếu là WAV
                .build();

        Request request = new Request.Builder()
                .url(UPLOAD_URL)
                .addHeader("Authorization", API_KEY)
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Upload failed: " + response);

        String responseBody = response.body().string();
        return responseBody.substring(responseBody.indexOf("https"), responseBody.length() - 2); // Lấy URL từ JSON trả về
    }
}