import java.io.IOException;

public class audio_extract {
    // Extracts audio from the video file and writes it to the output file.
    public void extractAudio(String videoFile, String outputFile, String audioFormat)
            throws IOException, InterruptedException {
        ProcessBuilder pb;
        if ("wav".equalsIgnoreCase(audioFormat)) {
            // For WAV output, use PCM 16-bit little-endian encoding.
            pb = new ProcessBuilder("ffmpeg", "-i", videoFile, "-vn", "-acodec", "pcm_s16le", outputFile);
        } else {
            // For other formats, let FFmpeg choose the default codec based on output file extension.
            pb = new ProcessBuilder("ffmpeg", "-i", videoFile, "-vn", outputFile);
        }
        // Inherit the parent's I/O so that FFmpeg's output/error messages show up in the console.
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg process failed with exit code " + exitCode);
        } else {
            System.out.println("Audio extraction successful.");
        }
    }
}
