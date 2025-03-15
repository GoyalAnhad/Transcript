import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class audio_controller {
    private file_choose fileChooser;
    private audio_extract audioExtractor;
    private WhisperTranscriber whisperTranscriber;
    
    // Constructor: Instantiate the component objects.
    public audio_controller() {
        fileChooser = new file_choose();
        audioExtractor = new audio_extract();
        whisperTranscriber = new WhisperTranscriber();
    }
    
    // Orchestrates the process: file selection, audio extraction, and transcription.
    public void process() {
        // Select input file.
        String inputFilePath = fileChooser.chooseInputFile();
        if (inputFilePath == null) {
            System.out.println("No input file selected. Exiting.");
            return;
        }
        
        // Generate output file path.
        String outputFilePath = fileChooser.getOutputFilePath(inputFilePath);
        System.out.println("Input file: " + inputFilePath);
        System.out.println("Output file: " + outputFilePath);
        
        // Extract audio.
        try {
            audioExtractor.extractAudio(inputFilePath, outputFilePath, "wav");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            
            return;
        }
        
        // Ask if the user wants to create a transcript.
        int response = JOptionPane.showConfirmDialog(
                null,
                "Audio extraction completed.\nDo you want to create a transcript for the extracted audio?",
                "Transcript",
                JOptionPane.YES_NO_OPTION
        );
        if (response == JOptionPane.YES_OPTION) {
            // Define catalog of languages.
            String[] languages = {
                "en - English", 
                "hi - Hindi", 
                "es - Spanish", 
                "fr - French", 
                "de - German", 
                "it - Italian", 
                "zh - Chinese", 
                "ja - Japanese", 
                "ko - Korean", 
                "ru - Russian", 
                "pt - Portuguese"
            };
            // Define available Whisper models.
            String[] models = {"tiny", "small", "medium", "large"};
            // Define available tasks.
            String[] tasks = {"transcribe", "translate"};
            
            // Create a panel to display three drop-downs in one dialog.
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JComboBox<String> languageBox = new JComboBox<>(languages);
            JComboBox<String> modelBox = new JComboBox<>(models);
            JComboBox<String> taskBox = new JComboBox<>(tasks);
            
            panel.add(new JLabel("Select language of the audio:"));
            panel.add(languageBox);
            panel.add(new JLabel("Select Whisper model:"));
            panel.add(modelBox);
            panel.add(new JLabel("Select task (transcribe = same language, translate = output in English):"));
            panel.add(taskBox);
            
            int result = JOptionPane.showConfirmDialog(
                    null,
                    panel,
                    "Language, Model & Task Selection",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            
            // Default values.
            String defaultLanguage = "en";
            String defaultModel = "tiny";
            String defaultTask = "transcribe";
            String languageCode = defaultLanguage;
            String selectedModel = defaultModel;
            String selectedTask = defaultTask;
            
            if (result == JOptionPane.OK_OPTION) {
                String languageOption = (String) languageBox.getSelectedItem();
                if (languageOption != null && !languageOption.trim().isEmpty()) {
                    // Extract code from "code - LanguageName"
                    languageCode = languageOption.split(" - ")[0];
                }
                selectedModel = (String) modelBox.getSelectedItem();
                if (selectedModel == null || selectedModel.trim().isEmpty()) {
                    selectedModel = defaultModel;
                }
                selectedTask = (String) taskBox.getSelectedItem();
                if (selectedTask == null || selectedTask.trim().isEmpty()) {
                    selectedTask = defaultTask;
                }
            }
            try {
                // Run transcription with the chosen language, model, and task.
                whisperTranscriber.transcribeAudio(outputFilePath, languageCode, selectedModel, selectedTask);
                JOptionPane.showMessageDialog(
                        null,
                        "Transcription completed successfully.\nPlease check the transcript file in the same folder as the audio."
                );
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error during transcription: " + e.getMessage());
            }
        }
    }
}
