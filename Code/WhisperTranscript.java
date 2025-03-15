import java.io.File;
import java.io.IOException;

public class WhisperTranscript {
    // Transcribes the audio file using Whisper in the specified language and model.
    public static void transcribeAudio(String audioFile, String language, String model) throws IOException, InterruptedException {
        // Determine the output directory (same folder as the audio file)
        String outputDir = new File(audioFile).getParent();
        
        // Build the command with the chosen model, language, and output directory.
        ProcessBuilder pb = new ProcessBuilder(
            "whisper",
            audioFile,
            "--model", model,
            "--language", language,
            "--task", "transcribe",
            "--output_dir", outputDir
        );
        // Inherit I/O so that Whisper's messages appear in the console.
        pb.inheritIO();
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("Whisper transcription failed with exit code " + exitCode);
        } else {
            System.out.println("Transcription completed successfully.");
        }
    }
}
