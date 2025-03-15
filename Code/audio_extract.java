import java.io.IOException;

public class audio_extract {
     // Extracts the audio from a video file using FFmpeg.
     public void extractAudio(String videoFile, String outputFile, String audioFormat)
     throws IOException, InterruptedException {
 ProcessBuilder pb;
 if ("wav".equalsIgnoreCase(audioFormat)) {
     // For WAV output, use PCM 16-bit little-endian encoding.
     pb = new ProcessBuilder("ffmpeg", "-i", videoFile, "-vn", "-acodec", "pcm_s16le", outputFile);
 } else {
     // For other formats, let FFmpeg determine the codec.
     pb = new ProcessBuilder("ffmpeg", "-i", videoFile, "-vn", outputFile);
 }
 // Inherit the parent's I/O.
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

