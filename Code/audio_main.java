import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class audio_main {
    public static void main(String[] args) {
        // Use the file chooser utility to select the input video file.
        String inputFilePath = file_choose.chooseInputFile();
        if (inputFilePath == null) {
            System.out.println("No input file selected. Exiting.");
            return;
        }
        
        // Generate the output destination based on the input file's name.
        String outputFilePath = file_choose.getOutputFilePath(inputFilePath);
        System.out.println("Input file: " + inputFilePath);
        System.out.println("Output file: " + outputFilePath);
        
        try {
            // Create an instance of audio_extract and call its extractAudio method.
            // "wav" tells the method to extract audio in the WAV format.
            audio_extract extractor = new audio_extract();
            extractor.extractAudio(inputFilePath, outputFilePath, "wav");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return;
        }
        
        // After extraction, ask the user if they want to create a transcript.
        int response = JOptionPane.showConfirmDialog(
                null, 
                "Audio extraction completed.\nDo you want to create a transcript for the extracted audio?", 
                "Transcript", 
                JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            // Define a catalog of languages.
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
            
            // Create a panel to hold both drop-down menus.
            JPanel panel = new JPanel(new GridLayout(0, 1));
            JComboBox<String> languageBox = new JComboBox<>(languages);
            JComboBox<String> modelBox = new JComboBox<>(models);
            
            panel.add(new JLabel("Select language for transcript:"));
            panel.add(languageBox);
            panel.add(new JLabel("Select Whisper model:"));
            panel.add(modelBox);
            
            // Show a single dialog containing both drop-down menus.
            int result = JOptionPane.showConfirmDialog(null, panel, "Language & Model Selection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            // Default values
            String defaultLanguage = "en";
            String defaultModel = "tiny";
            
            if (result == JOptionPane.OK_OPTION) {
                // Extract the language code from the selected item (format: "code - LanguageName").
                String languageOption = (String) languageBox.getSelectedItem();
                String languageCode = defaultLanguage;
                if (languageOption != null && !languageOption.trim().isEmpty()) {
                    languageCode = languageOption.split(" - ")[0];
                }
                // Get the selected model.
                String selectedModel = (String) modelBox.getSelectedItem();
                if (selectedModel == null || selectedModel.trim().isEmpty()) {
                    selectedModel = defaultModel;
                }
                
                try {
                    // Call the transcription method with the selected language code and model.
                    WhisperTranscript.transcribeAudio(outputFilePath, languageCode, selectedModel);
                    JOptionPane.showMessageDialog(null, 
                        "Transcription completed successfully.\nPlease check the transcript file in the same folder as the audio.");
                } catch (IOException | InterruptedException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error during transcription: " + ex.getMessage());
                }
            }
        }
    }
}
