import java.io.File;
import java.io.IOException;

public class WhisperTranscriber {
    // Transcribes the audio file using Whisper with the specified language, model, and task.
    public void transcribeAudio(String audioFile, String language, String model, String task)
            throws IOException, InterruptedException {
        // Determine the output directory (same as the audio file's directory).
        String outputDir = new File(audioFile).getParent();
        
        // Build the command:
        // whisper <audioFile> --model <model> --language <language> --task <task> --output_dir <outputDir>
        ProcessBuilder pb = new ProcessBuilder(
            "whisper",
            audioFile,
            "--model", model,
            "--language", language,
            "--task", task,
            "--output_dir", outputDir
        );
        // Inherit I/O so Whisper's messages appear in the console.
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
